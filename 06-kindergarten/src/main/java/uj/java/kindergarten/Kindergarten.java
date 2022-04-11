package uj.java.kindergarten;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

record ChildInfo(String name, int hungerSpeed){}

public class Kindergarten {

    public static void main(String[] args) throws IOException {
        init();
        final var fileName = args[0];
        System.out.println("File name: " + fileName);

        List<String> fileInfo = readInput(fileName);
        ChildImpl[] children = createChildren(fileInfo);
        seating(children);
        startEating(children);
    }

    private static void startEating(ChildImpl[] children) {
        for (var child : children) {
            new Thread(child).start();
        }
    }

    private static void seating(ChildImpl[] children){
        int numberOfChildren = children.length;
        Object[] forks = new Object[numberOfChildren];

        for (int i = 0; i < numberOfChildren; i++) {
            forks[i] = new Object();
        }

        for (int i = 0; i < numberOfChildren; i++) {
            children[i].pickForks(forks[i],forks[(i + 1) % numberOfChildren]);
            children[i].meetNeighbors(children[(i + 1) % numberOfChildren],children[(i + numberOfChildren - 1) % numberOfChildren]);
        }

        children[numberOfChildren-1].changeForks();
    }

    private static ChildImpl[] createChildren(List<String> fileInfo){
        int numberOfChildren = Integer.parseInt(fileInfo.remove(0));
        ChildImpl[] children = new ChildImpl[numberOfChildren];

        for (int i = 0; i < numberOfChildren; i++){
            ChildInfo info = parseInputLine(fileInfo.get(i));
            children[i] = new ChildImpl(info.name(),info.hungerSpeed());
        }
        return children;
    }

    private static ChildInfo parseInputLine(String childInfo) {
        String[] line = childInfo.split(" ");
        return new ChildInfo(line[0], Integer.parseInt(line[1]));
    }

    private static List<String> readInput(String fileName){
        List<String> allLines = null;
        try {
            allLines = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allLines;
    }

    private static void init() throws IOException {
        Files.deleteIfExists(Path.of("out.txt"));
        System.setErr(new PrintStream(new FileOutputStream("out.txt")));
        new Thread(Kindergarten::runKindergarden).start();
    }

    private static void runKindergarden() {
        try {
            Thread.sleep(10100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            List<String> errLines = Files.readAllLines(Path.of("out.txt"));
            System.out.println("Children cries count: " + errLines.size());
            errLines.forEach(System.out::println);
            System.exit(errLines.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
