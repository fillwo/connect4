package com.fillwo.wins.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BoardTest {
    private int[][] conf1 = {
            { 1,2,3,4,5 },
            { 6,7,8,9,10 },
            { 11,12,13,14,15 },
    };
    private int[][] conf2 = {
            { 0,0,1,0,0 },
            { 0,2,2,1,0 },
            { 0,1,1,1,2 },
            { 2,2,1,2,1 },
    };

    private int[][] conf3 = {
            { 0,0,1,0,0,0,0 },
            { 0,2,2,1,0,0,0 },
            { 0,1,1,1,2,0,0 },
            { 2,2,1,2,1,0,0 },
            { 2,2,1,2,1,0,0 },
            { 2,2,1,2,1,0,0 },
    };

    private int[][] conf4 = {
            { 0,0,1,0,0,0,0 },
            { 0,2,1,1,0,0,0 },
            { 0,1,2,1,2,0,0 },
            { 2,2,2,2,1,0,0 },
            { 2,2,2,1,1,1,0 },
            { 1,2,2,2,1,1,0 },
    };
    @Test
    void getColumn() {
        Board board = new Board(conf1);
        int[] col1 = board.getColumn(0);
        int[] col3 = board.getColumn(2);
        int[] col4 = board.getColumn(3);
        assertArrayEquals(new int[] {1,6,11}, col1);
        assertArrayEquals(new int[] {3,8,13}, col3);
        assertArrayEquals(new int[] {4,9,14}, col4);
    }
    @Test
    void getRow() {
        Board board = new Board(conf1);
        int[] row1 = board.getRow(0);
        int[] row2 = board.getRow(1);
        int[] row3 = board.getRow(2);
        assertArrayEquals(new int[] { 1,2,3,4,5 }, row1);
        assertArrayEquals(new int[] { 6,7,8,9,10 }, row2);
        assertArrayEquals(new int[] { 11,12,13,14,15 }, row3);
    }
    @Test
    void addChip() {
        Board board = new Board(conf2);
        assertEquals(false, board.addChip(1, 2));
        assertEquals(true, board.addChip(1, 3));
        assertEquals(false, board.addChip(1, 3));
        assertEquals(true, board.addChip(1, 4));
        assertEquals(true, board.addChip(1, 4));
        assertEquals(false, board.addChip(1, 4));
        assertEquals(false, board.addChip(1, 4));
    }
    @Test
    void fourInARow() {
        assertEquals(true, Board.fourInARow(new int[] {0,1,1,1,0,1,1,1,1}, 1));
        assertEquals(false, Board.fourInARow(new int[] {0,1,1,1,0,1,1,1,1}, 2));
        assertEquals(false, Board.fourInARow(new int[] {0,0,0,0,0,0,2,2,2}, 2));
        assertEquals(true, Board.fourInARow(new int[] {0,2,0,0,0,2,2,2,2}, 2));
    }
    @Test
    void findHorizontalWin() {
        // no wins
        Board board = new Board(conf2);
        assertEquals(false, board.findHorizontalWin(1));
        assertEquals(false, board.findHorizontalWin(2));
        assertEquals(false, new Board(conf3).findHorizontalWin(1));
        assertEquals(false, new Board(conf3).findHorizontalWin(2));
        assertEquals(false, new Board(conf4).findHorizontalWin(1));
        // wins
        assertEquals(true, new Board(conf4).findHorizontalWin(2));
    }
    @Test
    void findVerticalWin() {
        // no wins
        assertEquals(false, new Board(conf4).findVerticalWin(1));
        assertEquals(false, new Board(conf3).findVerticalWin(2));
        // wins
        assertEquals(true, new Board(conf4).findVerticalWin(2));
        assertEquals(true, new Board(conf3).findVerticalWin(1));
    }
    @Test
    void getDiagonalTopLeftToBottomRight() {
        Board board = new Board(conf4);
        assertArrayEquals(new int[] {0,2,2,2,1,1}, board.getDiagonalTopLeftToBottomRight(0,0));
        assertArrayEquals(new int[] {1,2,0,0}, board.getDiagonalTopLeftToBottomRight(3,1));
        assertArrayEquals(new int[] {1,1,0,}, board.getDiagonalTopLeftToBottomRight(4,3));
    }
    @Test
    void getDiagonalTopRightToBottomLeft() {
        int[][] conf4 = {
                { 0,0,1,0,0,0,0 },
                { 0,2,1,1,0,0,0 },
                { 0,1,2,1,2,0,0 },
                { 2,2,2,2,1,0,0 },
                { 2,2,2,1,1,1,0 },
                { 1,2,2,2,1,1,0 },
        };
        Board board = new Board(conf4);
        assertArrayEquals(new int[] {0,0,2,2,2,2}, board.getDiagonalTopRightToBottomLeft(6,0));
        assertArrayEquals(new int[] {0}, board.getDiagonalTopRightToBottomLeft(6,5));
        assertArrayEquals(new int[] {0,1,1,2}, board.getDiagonalTopRightToBottomLeft(3,0));
        assertArrayEquals(new int[] {2,2,1}, board.getDiagonalTopRightToBottomLeft(2,3));
    }
}