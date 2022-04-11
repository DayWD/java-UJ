package uj.java.w3;

import java.util.Random;

public class ShipGenerator {

    int headCoordinateX;
    int headCoordinateY;

    public ShipPlacedSuccessfully createShipFromFragments(int size, ChunkType[][] board) {
        headCoordinateX = new Random().nextInt(board.length);
        headCoordinateY = new Random().nextInt(board.length);
        ChunkType[][] gridForNewShip = ChunkType.fillGrid(board.length, ChunkType.WATER);

        for (int i = 0; i < size; i++) {
            gridForNewShip[headCoordinateX][headCoordinateY] = ChunkType.SHIP_FRAGMENT;
            newHeadPosition(gridForNewShip);
        }
        return mergeGridWithBoard(gridForNewShip, board);
    }

    public ShipPlacedSuccessfully mergeGridWithBoard(ChunkType[][] gridWithNewShip, ChunkType[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (gridWithNewShip[i][j] == ChunkType.SHIP_FRAGMENT && ShipsAround(i, j, board))
                    return ShipPlacedSuccessfully.NO;
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (gridWithNewShip[i][j] == ChunkType.SHIP_FRAGMENT)
                    board[i][j] = ChunkType.SHIP;
            }
        }
        return ShipPlacedSuccessfully.YES;
    }

    public boolean ShipsAround(int i, int j, ChunkType[][] board) {
        int len = board.length;
        return (i >= 0 && j >= 0 && i < len && j < len && board[i][j] == ChunkType.SHIP) ||
                (i + 1 >= 0 && j + 1 >= 0 && i + 1 < len && j + 1 < len && board[i + 1][j + 1] == ChunkType.SHIP) ||
                (i + 1 >= 0 && j - 1 >= 0 && i + 1 < len && j - 1 < len && board[i + 1][j - 1] == ChunkType.SHIP) ||
                (i - 1 >= 0 && j + 1 >= 0 && i - 1 < len && j + 1 < len && board[i - 1][j + 1] == ChunkType.SHIP) ||
                (i - 1 >= 0 && j - 1 >= 0 && i - 1 < len && j - 1 < len && board[i - 1][j - 1] == ChunkType.SHIP) ||
                (i + 1 >= 0 && j >= 0 && i + 1 < len && j < len && board[i + 1][j] == ChunkType.SHIP) ||
                (i - 1 >= 0 && j >= 0 && i - 1 < len && j < len && board[i - 1][j] == ChunkType.SHIP) ||
                (i >= 0 && j + 1 >= 0 && i < len && j + 1 < len && board[i][j + 1] == ChunkType.SHIP) ||
                (i >= 0 && j - 1 >= 0 && i < len && j - 1 < len && board[i][j - 1] == ChunkType.SHIP);
    }

    public void newHeadPosition(ChunkType[][] gridWithShip) {
        int newCoordinateX, newCoordinateY;

        do {
            newCoordinateX = headCoordinateX;
            newCoordinateY = headCoordinateY;
            int direction = new Random().nextInt(4);

            if (direction == 0 && newCoordinateX - 1 >= 0)
                newCoordinateX--;
            else if (direction == 1 && newCoordinateX + 1 < gridWithShip.length)
                newCoordinateX++;
            else if (direction == 2 && newCoordinateY - 1 >= 0)
                newCoordinateY--;
            else if (direction == 3 && newCoordinateY + 1 < gridWithShip.length)
                newCoordinateY++;

        } while (gridWithShip[newCoordinateX][newCoordinateY] == ChunkType.SHIP_FRAGMENT);

        headCoordinateX = newCoordinateX;
        headCoordinateY = newCoordinateY;
    }
}
