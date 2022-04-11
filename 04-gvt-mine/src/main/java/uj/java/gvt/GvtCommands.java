package uj.java.gvt;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.*;

record Args(String fileName, String message) {
}

public class GvtCommands {
    GvtVersions versions = new GvtVersions();
    String[] args;
    Path mainPath = Path.of("./");
    Path gvtPath = Path.of("./.gvt");
    String gvtStringPath = "./.gvt";

    public GvtCommands(String[] args) {
        this.args = args;
    }

    void selectCommand() {
        switch (args[0]) {
            case "init" -> init();
            case "add" -> add();
            case "detach" -> detach();
            case "checkout" -> checkout();
            case "commit" -> commit();
            case "history" -> history();
            case "version" -> version();
            default -> {
                System.out.printf("Unknown command %s.%n", args[0]);
                System.exit(1);
            }
        }
    }

    private void version() {
        isGvtDirectoryInitialized();

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("./.gvt/GeneralInfo"))) {
            GeneralInfo readInfo = (GeneralInfo) input.readObject();

            if (args.length == 1)
                showVersion(readInfo.latestVersion);
            else if (args.length == 2 && args[1].matches("[0-9]+") && Long.parseLong(args[1]) <= readInfo.latestVersion)
                showVersion(Long.parseLong(args[1]));
            else {
                System.out.printf("Invalid version number: %s\n", args[1]);
                System.exit(60);
            }
        } catch (IOException | ClassNotFoundException e) {
            operatingSystemErrors(e);
        }
    }

    private void showVersion(long version) throws IOException, ClassNotFoundException {
        try (ObjectInputStream output = new ObjectInputStream(new FileInputStream(String.format("./.gvt/%d/version", version)))) {
            GvtVersion readVersionInfo = (GvtVersion) output.readObject();
            System.out.printf("Version: %s\n", version);
            System.out.print(readVersionInfo.commitMessage);
        }
    }

    private void history() {
        isGvtDirectoryInitialized();

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("./.gvt/GeneralInfo"))) {
            GeneralInfo readInfo = (GeneralInfo) input.readObject();

            for (long i = amountOfVersionsToDisplay(readInfo.latestVersion); i <= readInfo.latestVersion; i++) {
                showShortenedVersion(i);
            }
        } catch (IOException | ClassNotFoundException e) {
            operatingSystemErrors(e);
        }
    }

    private void showShortenedVersion(long version) throws IOException, ClassNotFoundException {
        try (ObjectInputStream output = new ObjectInputStream(new FileInputStream(String.format("./.gvt/%d/version", version)))) {
            GvtVersion readVersionInfo = (GvtVersion) output.readObject();
            String message;
            if (readVersionInfo.commitMessage.contains("\n"))
                message = readVersionInfo.commitMessage.substring(0, readVersionInfo.commitMessage.indexOf("\n"));
            else
                message = readVersionInfo.commitMessage;
            System.out.printf("%s: %s\n", version, message);
        }
    }

    private Long amountOfVersionsToDisplay(long latestVersion) {
        if (args.length == 3 && args[2].matches("[0-9]+") && args[1].equals("-last") && Long.parseLong(args[2]) != 0) {
            long parameter = Long.parseLong(args[2]);
            if (parameter > latestVersion)
                return (long) 0;
            else return latestVersion - parameter + 1;
        }
        return (long) 0;
    }

    private void checkout() {
        isGvtDirectoryInitialized();

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("./.gvt/GeneralInfo"))) {
            GeneralInfo readInfo = (GeneralInfo) input.readObject();
            versionIsNotCorrect(args[1], readInfo);
            copyVersionedFilesToMainPath(args[1]);
            System.out.printf("Version %s checked out successfully.\n", args[1]);
        } catch (IOException | ClassNotFoundException e) {
            operatingSystemErrors(e);
        }
    }

    private void copyVersionedFilesToMainPath(String version) throws IOException {
        List<Path> files = Files.list(Paths.get(gvtStringPath, version)).collect(Collectors.toList());
        files.remove(Paths.get(gvtStringPath, version, "version"));
        for (var file : files) {
            Path fileName = Paths.get(".gvt/", version).relativize(file);
            Files.copy(file, mainPath.resolve(fileName), REPLACE_EXISTING);
        }
    }

    private void versionIsNotCorrect(String version, GeneralInfo readInfo) {
        if (!(version.matches("[0-9]+") && Long.parseLong(version) <= readInfo.latestVersion)) {
            System.out.printf("Invalid version number: %s.\n", version);
            System.exit(40);
        }
    }

    private void commit() {
        isGvtDirectoryInitialized();
        Args base = parseArgs(50, "Committed");

        if (Files.notExists(mainPath.resolve(base.fileName()))) {
            System.out.printf("File %s does not exist.\n", base.fileName());
            System.exit(51);
        }

        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("./.gvt/GeneralInfo"));
            GeneralInfo readInfo = (GeneralInfo) input.readObject();
            if (!readInfo.trackedFiles.contains(base.fileName())) {
                System.out.printf("File %s is not added to gvt.\n", base.fileName());
            } else {
                versions.createNewVersionFile(base.message(), readInfo.upgradeVersion());
                versions.copyFilesToNewestVersion(readInfo);
                Files.copy(mainPath.resolve(base.fileName()), Paths.get(gvtStringPath, String.valueOf(readInfo.latestVersion), base.fileName()), REPLACE_EXISTING);
                System.out.printf("File %s committed successfully.\n", base.fileName());
            }
            input.close();
            saveClassOnDisk(readInfo);
        } catch (IOException | ClassNotFoundException e) {
            System.out.printf("File %s cannot be commited, see ERR for details.\n", base.fileName());
            e.printStackTrace();
            System.exit(-52);
        }
    }

    private void detach() {
        isGvtDirectoryInitialized();
        Args base = parseArgs(30, "Detached");

        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("./.gvt/GeneralInfo"));
            GeneralInfo readInfo = (GeneralInfo) input.readObject();
            if (!readInfo.trackedFiles.contains(base.fileName())) {
                System.out.printf("File %s is not added to gvt.\n", base.fileName());
            } else {
                readInfo.trackedFiles.remove(base.fileName());
                versions.createNewVersionFile(base.message(), readInfo.upgradeVersion());
                versions.copyFilesToNewestVersion(readInfo);
                System.out.printf("File %s detached successfully.\n", base.fileName());
            }
            input.close();
            saveClassOnDisk(readInfo);
        } catch (IOException | ClassNotFoundException e) {
            System.out.printf("File %s cannot be detached, see ERR for details.\n", base.fileName());
            e.printStackTrace();
            System.exit(31);
        }

    }

    private void add() {
        isGvtDirectoryInitialized();
        Args base = parseArgs(20, "Added");

        if (Files.notExists(mainPath.resolve(base.fileName()))) {
            System.out.printf("File %s not found.\n", base.fileName());
            System.exit(21);
        }

        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("./.gvt/GeneralInfo"));
            GeneralInfo readInfo = (GeneralInfo) input.readObject();
            if (readInfo.trackedFiles.contains(base.fileName())) {
                System.out.printf("File %s already added.\n", base.fileName());
            } else {
                versions.createNewVersionFile(base.message(), readInfo.upgradeVersion());
                versions.copyFilesToNewestVersion(readInfo);
                readInfo.trackedFiles.add(base.fileName());
                Files.copy(mainPath.resolve(base.fileName()), Paths.get(gvtStringPath, String.valueOf(readInfo.latestVersion), base.fileName()));
                System.out.printf("File %s added successfully.\n", base.fileName());
            }
            input.close();
            saveClassOnDisk(readInfo);
        } catch (IOException | ClassNotFoundException e) {
            System.out.printf("File %s cannot be added, see ERR for details.\n", base.fileName());
            e.printStackTrace();
            System.exit(22);
        }
    }

    private void init() {
        if (Files.notExists(gvtPath)) {
            try {
                Files.createDirectory(gvtPath);
                System.out.println("Current directory initialized successfully.\n");
                versions.initGvt();
            } catch (IOException e) {
                operatingSystemErrors(e);
            }
        } else {
            System.out.println("Current directory is already initialized.\n");
            System.exit(10);
        }

    }

    //region GENERAL RULES
    public void isGvtDirectoryInitialized() {
        if (Files.notExists(gvtPath)) {
            System.out.println("Current directory is not initialized. Please use \"init\" command to initialize.\n");
            System.exit(-2);
        }
    }

    public void operatingSystemErrors(Exception e) {
        System.out.println("Underlying system problem. See ERR for details.\n");
        e.printStackTrace();
        System.exit(-3);
    }
    //endregion

    Args parseArgs(int errorCode, String defaultMessage) {
        if (args.length == 1) {
            System.out.printf("Please specify file to %s.\n", args[0]);
            System.exit(errorCode);
        }

        String fileName = args[1];
        StringBuilder message = new StringBuilder(String.format("%s file: %s", defaultMessage, fileName));
        if (args.length == 4 && args[2].equals("-m")) {
            message.append("\n");
            message.append(args[3].replace("\"", ""));
        }
        return new Args(fileName, message.toString());
    }

    private void saveClassOnDisk(GeneralInfo readInfo) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("./.gvt/GeneralInfo"))) {
            output.writeObject(readInfo);
        }
    }
}
