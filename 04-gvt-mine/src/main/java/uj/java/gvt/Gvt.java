package uj.java.gvt;

public class Gvt {
    public static void main(String... args) {

        if (args.length == 0) {
            System.out.println("Please specify command.");
            System.exit(1);
        }

        GvtCommands command = new GvtCommands(args);
        command.selectCommand();
    }
}
