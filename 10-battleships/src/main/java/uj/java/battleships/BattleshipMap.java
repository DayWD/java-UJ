package uj.java.battleships;

import java.util.List;

public class BattleshipMap {

    Character[][] map;
    List<Ship> ships;

    public BattleshipMap(Character[][] map, List<Ship> ships) {
        this.map = map;
        this.ships = ships;
    }

    public String updateChunkAndGetFeedback(int row, int column) {
        return switch (map[row][column]) {
            case '#' -> {
                map[row][column] = '@';
                yield removeShipFragment(row, column);
            }
            case '.' -> {
                map[row][column] = '~';
                yield "pudło";
            }
            case '@' -> "trafiony";
            case '~' -> "pudło";
            default -> "coś jest źle";
        };
    }

    private String removeShipFragment(int row, int column) {
        for (var ship : ships) {
            if (ship.containCoordinate(row, column)) {
                ship.removeFragment(row, column);
                if (ship.isSunken()) {
                    ships.remove(ship);
                    return "trafiony zatopiony";
                } else
                    return "trafiony";
            }
        }
        return null;
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
