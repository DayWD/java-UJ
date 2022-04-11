package uj.java.battleships;

import java.util.ArrayList;
import java.util.List;

public class BattleshipMapParser {

    public Character[][] stringTo2DMap(String generatedMap) {
        Character[][] map = new Character[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j] = generatedMap.charAt(i * 10 + j);
            }
        }
        return map;
    }

    public List<Ship> findShips(Character[][] map) {
        List<Ship> ships = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map[i][j].equals('#')) {
                    findShip(ships, i, j);
                }
            }
        }
        return ships;
    }

    private void findShip(List<Ship> ships, int i, int j) {
        if (!isShipFragmentAddedToNearestShip(ships, i, j)) {
            ships.add(new Ship(i, j));
        }
    }

    private boolean isShipFragmentAddedToNearestShip(List<Ship> ships, int i, int j) {
        for (var ship : ships) {
            if (ship.containCoordinate(i + 1, j) || ship.containCoordinate(i - 1, j) || ship.containCoordinate(i, j + 1) || ship.containCoordinate(i, j - 1)) {
                Coordinate coordinate = new Coordinate(i, j);
                ship.coords.add(coordinate);
                return true;
            }
        }
        return false;
    }

}
