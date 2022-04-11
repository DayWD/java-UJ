package uj.java.gvt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GvtVersions {

    String gvt = "./.gvt";

    public void copyFilesToNewestVersion(GeneralInfo info) throws IOException {
        for (var element : info.trackedFiles) {
            Files.copy(Paths.get(String.format("./.gvt/%d", info.latestVersion - 1), element), Paths.get(String.format("./.gvt/%d/", info.latestVersion), element));
        }
    }

    public void createNewVersionFile(String message, Long latestVersion) throws IOException {
        Files.createDirectory(Paths.get(gvt, String.valueOf(latestVersion)));
        Files.createFile(Paths.get(gvt, String.valueOf(latestVersion), "version"));
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(String.format("./.gvt/%d/version", latestVersion)))) {
            output.writeObject(new GvtVersion(latestVersion, message));
        }
    }

    public void initGvt() throws IOException {
        Files.createFile(Paths.get(gvt, "GeneralInfo"));
        try (ObjectOutputStream readInfo = new ObjectOutputStream(new FileOutputStream("./.gvt/GeneralInfo"))) {
            readInfo.writeObject(new GeneralInfo(0L));
        }
        Files.createDirectory(Paths.get(gvt, "0"));
        Files.createFile(Paths.get(gvt, "0", "version"));
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("./.gvt/0/version"))) {
            output.writeObject(new GvtVersion(0L, "GVT initialized."));
        }
    }
}
