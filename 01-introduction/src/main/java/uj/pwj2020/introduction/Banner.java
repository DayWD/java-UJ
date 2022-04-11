package uj.pwj2020.introduction;

public class Banner {

    private static final int FONT_SIZE = 7;

    public static void main(String[] args) {

        Banner object = new Banner();
        String[] str = object.toBanner("miejsce na twoja reklame.");

        for (int i = 0; i < FONT_SIZE; i++) {
             System.out.println(str[i]);
        }
    }

    public String[] toBanner(String input) {
        if (input == null)
            return new String[0];

        input = input.toUpperCase();

        StringBuilder[] alphabet = new StringBuilder[FONT_SIZE];

        for (int i = 0; i < FONT_SIZE; i++) {
            alphabet[i] = new StringBuilder();
        }

        for (int i = 0; i < input.length(); i++) {
            chooseLetter(input, alphabet, i);
            doSpace(alphabet);
        }

        String[] output = new String[FONT_SIZE];

        for (int k = 0; k < FONT_SIZE; k++) {
            output[k] = alphabet[k].toString();
            output[k] = output[k].stripTrailing();
        }

        return output;
    }

    private void doSpace(StringBuilder[] alphabet) {
        alphabet[0].append(" ");
        alphabet[1].append(" ");
        alphabet[2].append(" ");
        alphabet[3].append(" ");
        alphabet[4].append(" ");
        alphabet[5].append(" ");
        alphabet[6].append(" ");
    }

    private void chooseLetter(String input, StringBuilder[] alphabet, int i) {
        switch (input.charAt(i)) {
            case ' ' -> {
                alphabet[0].append("  ");
                alphabet[1].append("  ");
                alphabet[2].append("  ");
                alphabet[3].append("  ");
                alphabet[4].append("  ");
                alphabet[5].append("  ");
                alphabet[6].append("  ");
            }
            case 'A' -> {
                alphabet[0].append("   #   ");
                alphabet[1].append("  # #  ");
                alphabet[2].append(" #   # ");
                alphabet[3].append("#     #");
                alphabet[4].append("#######");
                alphabet[5].append("#     #");
                alphabet[6].append("#     #");
            }
            case 'B' -> {
                alphabet[0].append("###### ");
                alphabet[1].append("#     #");
                alphabet[2].append("#     #");
                alphabet[3].append("###### ");
                alphabet[4].append("#     #");
                alphabet[5].append("#     #");
                alphabet[6].append("###### ");
            }
            case 'C' -> {
                alphabet[0].append(" ##### ");
                alphabet[1].append("#     #");
                alphabet[2].append("#      ");
                alphabet[3].append("#      ");
                alphabet[4].append("#      ");
                alphabet[5].append("#     #");
                alphabet[6].append(" ##### ");
            }
            case 'D' -> {
                alphabet[0].append("###### ");
                alphabet[1].append("#     #");
                alphabet[2].append("#     #");
                alphabet[3].append("#     #");
                alphabet[4].append("#     #");
                alphabet[5].append("#     #");
                alphabet[6].append("###### ");
            }
            case 'E' -> {
                alphabet[0].append("#######");
                alphabet[1].append("#      ");
                alphabet[2].append("#      ");
                alphabet[3].append("#####  ");
                alphabet[4].append("#      ");
                alphabet[5].append("#      ");
                alphabet[6].append("#######");
            }
            case 'F' -> {
                alphabet[0].append("#######");
                alphabet[1].append("#      ");
                alphabet[2].append("#      ");
                alphabet[3].append("#####  ");
                alphabet[4].append("#      ");
                alphabet[5].append("#      ");
                alphabet[6].append("#      ");
            }
            case 'G' -> {
                alphabet[0].append(" ##### ");
                alphabet[1].append("#     #");
                alphabet[2].append("#      ");
                alphabet[3].append("#  ####");
                alphabet[4].append("#     #");
                alphabet[5].append("#     #");
                alphabet[6].append(" ##### ");
            }
            case 'H' -> {
                alphabet[0].append("#     #");
                alphabet[1].append("#     #");
                alphabet[2].append("#     #");
                alphabet[3].append("#######");
                alphabet[4].append("#     #");
                alphabet[5].append("#     #");
                alphabet[6].append("#     #");
            }
            case 'I' -> {
                alphabet[0].append("###");
                alphabet[1].append(" # ");
                alphabet[2].append(" # ");
                alphabet[3].append(" # ");
                alphabet[4].append(" # ");
                alphabet[5].append(" # ");
                alphabet[6].append("###");
            }
            case 'J' -> {
                alphabet[0].append("      #");
                alphabet[1].append("      #");
                alphabet[2].append("      #");
                alphabet[3].append("      #");
                alphabet[4].append("#     #");
                alphabet[5].append("#     #");
                alphabet[6].append(" ##### ");
            }
            case 'K' -> {
                alphabet[0].append("#    #");
                alphabet[1].append("#   # ");
                alphabet[2].append("#  #  ");
                alphabet[3].append("###   ");
                alphabet[4].append("#  #  ");
                alphabet[5].append("#   # ");
                alphabet[6].append("#    #");
            }
            case 'L' -> {
                alphabet[0].append("#      ");
                alphabet[1].append("#      ");
                alphabet[2].append("#      ");
                alphabet[3].append("#      ");
                alphabet[4].append("#      ");
                alphabet[5].append("#      ");
                alphabet[6].append("#######");
            }
            case 'M' -> {
                alphabet[0].append("#     #");
                alphabet[1].append("##   ##");
                alphabet[2].append("# # # #");
                alphabet[3].append("#  #  #");
                alphabet[4].append("#     #");
                alphabet[5].append("#     #");
                alphabet[6].append("#     #");
            }
            case 'N' -> {
                alphabet[0].append("#     #");
                alphabet[1].append("##    #");
                alphabet[2].append("# #   #");
                alphabet[3].append("#  #  #");
                alphabet[4].append("#   # #");
                alphabet[5].append("#    ##");
                alphabet[6].append("#     #");
            }
            case 'O' -> {
                alphabet[0].append("#######");
                alphabet[1].append("#     #");
                alphabet[2].append("#     #");
                alphabet[3].append("#     #");
                alphabet[4].append("#     #");
                alphabet[5].append("#     #");
                alphabet[6].append("#######");
            }
            case 'P' -> {
                alphabet[0].append("###### ");
                alphabet[1].append("#     #");
                alphabet[2].append("#     #");
                alphabet[3].append("###### ");
                alphabet[4].append("#      ");
                alphabet[5].append("#      ");
                alphabet[6].append("#      ");
            }
            case 'Q' -> {
                alphabet[0].append(" ##### ");
                alphabet[1].append("#     #");
                alphabet[2].append("#     #");
                alphabet[3].append("#     #");
                alphabet[4].append("#   # #");
                alphabet[5].append("#    # ");
                alphabet[6].append(" #### #");
            }
            case 'R' -> {
                alphabet[0].append("###### ");
                alphabet[1].append("#     #");
                alphabet[2].append("#     #");
                alphabet[3].append("###### ");
                alphabet[4].append("#   #  ");
                alphabet[5].append("#    # ");
                alphabet[6].append("#     #");
            }
            case 'S' -> {
                alphabet[0].append(" ##### ");
                alphabet[1].append("#     #");
                alphabet[2].append("#      ");
                alphabet[3].append(" ##### ");
                alphabet[4].append("      #");
                alphabet[5].append("#     #");
                alphabet[6].append(" ##### ");
            }
            case 'T' -> {
                alphabet[0].append("#######");
                alphabet[1].append("   #   ");
                alphabet[2].append("   #   ");
                alphabet[3].append("   #   ");
                alphabet[4].append("   #   ");
                alphabet[5].append("   #   ");
                alphabet[6].append("   #   ");
            }
            case 'U' -> {
                alphabet[0].append("#     #");
                alphabet[1].append("#     #");
                alphabet[2].append("#     #");
                alphabet[3].append("#     #");
                alphabet[4].append("#     #");
                alphabet[5].append("#     #");
                alphabet[6].append(" ##### ");
            }
            case 'V' -> {
                alphabet[0].append("#     #");
                alphabet[1].append("#     #");
                alphabet[2].append("#     #");
                alphabet[3].append("#     #");
                alphabet[4].append(" #   # ");
                alphabet[5].append("  # #  ");
                alphabet[6].append("   #   ");
            }
            case 'W' -> {
                alphabet[0].append("#     #");
                alphabet[1].append("#  #  #");
                alphabet[2].append("#  #  #");
                alphabet[3].append("#  #  #");
                alphabet[4].append("#  #  #");
                alphabet[5].append("#  #  #");
                alphabet[6].append(" ## ## ");
            }
            case 'X' -> {
                alphabet[0].append("#     #");
                alphabet[1].append(" #   # ");
                alphabet[2].append("  # #  ");
                alphabet[3].append("   #   ");
                alphabet[4].append("  # #  ");
                alphabet[5].append(" #   # ");
                alphabet[6].append("#     #");
            }
            case 'Y' -> {
                alphabet[0].append("#     #");
                alphabet[1].append(" #   # ");
                alphabet[2].append("  # #  ");
                alphabet[3].append("   #   ");
                alphabet[4].append("   #   ");
                alphabet[5].append("   #   ");
                alphabet[6].append("   #   ");
            }
            case 'Z' -> {
                alphabet[0].append("#######");
                alphabet[1].append("     # ");
                alphabet[2].append("    #  ");
                alphabet[3].append("   #   ");
                alphabet[4].append("  #    ");
                alphabet[5].append(" #     ");
                alphabet[6].append("#######");
            }
        }
    }
}

