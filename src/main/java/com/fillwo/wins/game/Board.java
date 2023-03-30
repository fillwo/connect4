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

    public boolean addChip(int chip, int posX) {
        if (chip < 1 || chip > 2) {
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

        this.entries[insertIdx][posX] = chip;
        return true;
    }
}
