package uj.java.w3;

import java.util.Arrays;
import java.util.stream.IntStream;

enum ChunkType {
    SHIP_FRAGMENT,
    SHIP,
    WATER;

    public static ChunkType[][] fillGrid(int size, ChunkType type) {
        ChunkType[][] grid = new ChunkType[size][size];
        for (ChunkType[] eachRow : grid) {
            Arrays.fill(eachRow, type);
        }
        return grid;
    }
}

enum ShipPlacedSuccessfully {
    YES,
    NO
}

public class BattleshipGeneratorClass implements BattleshipGenerator {

    public ChunkType[][] board;

    @Override
    public String generateMap() {
        board = ChunkType.fillGrid(10, ChunkType.WATER);
        placeShipsOnTheBoard(1, 2, 3, 4);

        return convertBoardToString();
    }

    public void placeShipsOnTheBoard(int fourMastedShips, int threeMastedShips, int twoMastedShips, int oneMastedShips) {
        IntStream.range(0, fourMastedShips).map(i -> 4).forEach(this::placeShip);
        IntStream.range(0, threeMastedShips).map(i -> 3).forEach(this::placeShip);
        IntStream.range(0, twoMastedShips).map(i -> 2).forEach(this::placeShip);
        IntStream.range(0, oneMastedShips).map(i -> 1).forEach(this::placeShip);
    }

    public void placeShip(int size) {
        ShipGenerator ship = new ShipGenerator();
        ShipPlacedSuccessfully isShipPlaced = ShipPlacedSuccessfully.NO;

        while (isShipPlaced == ShipPlacedSuccessfully.NO) {
            isShipPlaced = ship.createShipFromFragments(size, board);
        }
    }

    public String convertBoardToString() {
        StringBuilder output = new StringBuilder();
        for (ChunkType[] eachRow : board) {
            for (ChunkType chunk : eachRow) {
                switch (chunk) {
                    case WATER -> output.append(".");
                    case SHIP -> output.append("#");
                }
            }
        }
        return output.toString();
    }
}