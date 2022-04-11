package uj.java.battleships;

public class CoordinatesTranslator {

    String numericToOriginal(int row, int column) {
        char letter = (char) (column + 65);
        char number = (char) (row + 49);
        if (number == ':')
            return letter + "10";
        else
            return Character.toString(letter) + number;
    }

    Coordinate originalToNumeric(String coordinate) {
        int row = Integer.parseInt(coordinate.substring(1)) - 1;
        int column = coordinate.charAt(0) - 65;
        return new Coordinate(row, column);
    }
}
