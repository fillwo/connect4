package com.fillwo.wins.game;

public class Board {

//    [ [0,1,2],
//      [3,4,5] ]

// coordinate system for posX, posY has origin
// in top left corner!

    private final int width;
    private final int height;

    private int[][] entries; // 0 empty, 1 player one, 2 player two

    public Board() {
        this(7, 6);
    }

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.entries = new int[this.height][this.width];
    }

    public Board(int[][] entries) {
        this.height = entries.length;
        this.width = entries[0].length;
        this.entries = entries;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getEntries() {
        return entries;
    }

    public boolean isEmpty(int posX, int posY) {
        return this.entries[posX][posY] == 0;
    }

    public int[] getColumn(int posX) {
        int[] column = new int[this.height];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (j == posX) {
                    column[i] = this.entries[i][j];
                }
            }
        }
        return column;
    }

    public int[] getRow(int posY) {
        return this.entries[posY];
    }

    public boolean addChip(int chipNum, int posX) {
        if (chipNum < 1 || chipNum > 2) {
            throw new IllegalArgumentException("chip value must be 1 or 2");
        }
        int[] column = this.getColumn(posX);
        if (column[0] != 0) {
            return false;
        }

        int insertIdx = 0;
        for (int i = 0; i < this.height; i++) {
            if (column[i] != 0) {
                insertIdx = i - 1;
                break;
            }
        }

        this.entries[insertIdx][posX] = chipNum;
        return true;
    }

    public static boolean fourInARow(int[] arr, int value) {
        int cnt = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                cnt += 1;
                if (cnt == 4) {
                    break;
                }
            } else {
                cnt = 0;
            }
        }
        return cnt == 4;
    }

    public boolean findHorizontalWin(int chipNum) {
        for (int[] row : this.entries) {
            if(this.fourInARow(row, chipNum)) {
                return true;
            }
        }
        return false;
    }

    public boolean findVerticalWin(int chipNum) {
        for (int i = 0; i < this.width; i++) {
            int[] col = this.getColumn(i);
            if(this.fourInARow(col, chipNum)) {
                return true;
            }
        }
        return false;
    }

    public int[] getDiagonalTopLeftToBottomRight(int posX, int posY) {
        int remaining_height = this.height - posY;
        int remaining_width = this.width - posX;
        int diagonalLength = Math.min(remaining_height, remaining_width);
        int[] diagonal = new int[diagonalLength];
        for (int d = 0; d < diagonalLength; d++) {
            diagonal[d] = this.entries[posY + d][posX + d];
        }
        return diagonal;
    }

    public int[] getDiagonalTopRightToBottomLeft(int posX, int posY) {
        int remaining_height = this.height - posY;
        int remaining_width = posX + 1;
        int diagonalLength = Math.min(remaining_height, remaining_width);
        int[] diagonal = new int[diagonalLength];
        for (int d = 0; d < diagonalLength; d++) {
            diagonal[d] = this.entries[posY + d][posX - d];
        }
        return diagonal;
    }

    public boolean findDiagonalWin(int chipNum) {
        // check diagonal rows from
        // top left to bottom right
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                // todo: work in progress
                int remaining_height = this.height - i;
                int remaining_width = this.width - j;
                if (remaining_height >= 4 && remaining_width >= 4) {

                }
            }
        }
        return false;
    }
}
