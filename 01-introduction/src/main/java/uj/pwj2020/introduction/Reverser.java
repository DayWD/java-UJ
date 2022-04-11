package uj.pwj2020.introduction;

public class Reverser {

    public String reverse(String input) {
        if (input == null)
            return null;

        StringBuilder output = new StringBuilder(input);

        return output.reverse().toString().strip();
    }

    public String reverseWords(String input) {
        if (input == null)
            return null;

        input = input.strip();

        String[] stringArray = input.split(" ");
        StringBuilder string = new StringBuilder();

        for (int i = stringArray.length - 1; i >= 0; i--) {
            string.append(stringArray[i]);
            string.append(" ");
        }

        String output = string.toString();

        return output.strip();
    }
}
