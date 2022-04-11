package uj.java.battleships;

import java.util.ArrayList;
import java.util.List;

record Coordinate(int row, int column) {}

public class Ship {
    public List<Coordinate> coords;

    public Ship(int row, int column) {
        Coordinate coordinate = new Coordinate(row, column);
        coords = new ArrayList<>();
        coords.add(coordinate);
    }

    public boolean containCoordinate(int row, int column) {
        for (var coordinate : coords) {
            if (coordinate.row() == row && coordinate.column() == column)
                return true;
        }
        return false;
    }

    public boolean isSunken() {
        return coords.size() == 0;
    }

    public void removeFragment(int row, int column) {
        for (int i = 0; i < coords.size(); i++) {
            if (coords.get(i).row() == row && coords.get(i).column() == column) {
                coords.remove(i);
                break;
            }
        }
    }
}
