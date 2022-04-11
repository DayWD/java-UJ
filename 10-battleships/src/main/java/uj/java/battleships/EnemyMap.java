package uj.java.battleships;

import java.util.ArrayList;
import java.util.List;

public class EnemyMap {

    Character[][] map = new Character[10][10];

    public EnemyMap() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j] = '?';
            }
        }
    }

    public void missed(Coordinate target) {
        map[target.row()][target.column()] = '.';
    }

    public void hit(Coordinate target) {
        map[target.row()][target.column()] = '#';
    }

    public void sunken(Coordinate target) {
        List<Coordinate> ship = revealShipBorder(new ArrayList<>(), target.row(), target.column());
        ship.add(target);
        revealShipBorder(ship, target.row(), target.column());
        changeSymbolOfShip(ship);
    }

    private void changeSymbolOfShip(List<Coordinate> ship) {
        for (var coordinate : ship) {
            map[coordinate.row()][coordinate.column()] = '#';
        }
    }

    private List<Coordinate> revealShipBorder(List<Coordinate> ship, int row, int col) {
        map[row][col] = '$';
        ship.add(new Coordinate(row, col));

        if (row + 1 <= 9 && map[row + 1][col] == '?')
            map[row + 1][col] = '.';
        if (row + 1 <= 9 && col + 1 <= 9 && map[row + 1][col + 1] == '?')
            map[row + 1][col + 1] = '.';
        if (row + 1 <= 9 && col - 1 >= 0 && map[row + 1][col - 1] == '?')
            map[row + 1][col - 1] = '.';
        if (row - 1 >= 0 && map[row - 1][col] == '?')
            map[row - 1][col] = '.';
        if (row - 1 >= 0 && col + 1 <= 9 && map[row - 1][col + 1] == '?')
            map[row - 1][col + 1] = '.';
        if (row - 1 >= 0 && col - 1 >= 0 && map[row - 1][col - 1] == '?')
            map[row - 1][col - 1] = '.';
        if (col + 1 <= 9 && map[row][col + 1] == '?')
            map[row][col + 1] = '.';
        if (col - 1 >= 0 && map[row][col - 1] == '?')
            map[row][col - 1] = '.';

        if (row + 1 <= 9 && map[row + 1][col] == '#')
            revealShipBorder(ship, row + 1, col);
        if (row - 1 >= 0 && map[row - 1][col] == '#')
            revealShipBorder(ship, row - 1, col);
        if (col + 1 <= 9 && map[row][col + 1] == '#')
            revealShipBorder(ship, row, col + 1);
        if (col - 1 >= 0 && map[row][col - 1] == '#')
            revealShipBorder(ship, row, col - 1);
        return ship;

    }

    public void showMap() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

}
