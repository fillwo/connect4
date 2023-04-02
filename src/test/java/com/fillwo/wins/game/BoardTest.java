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
        int fIdx;

        fIdx = Board.findFourInARow(new int[] {0,1,1,1,0,1,1,1,1}, 1);
        assertEquals(5, fIdx);

        fIdx = Board.findFourInARow(new int[] {0,1,1,1,0,1,1,1,1}, 2);
        assertEquals(-1, fIdx);

        fIdx = Board.findFourInARow(new int[] {0,0,0,0,0,0,2,2,2}, 2);
        assertEquals(-1, fIdx);

        fIdx = Board.findFourInARow(new int[] {0,2,0,0,0,2,2,2,2}, 2);
        assertEquals(5, fIdx);
    }
    @Test
    void findHorizontalWin() {
        WinReturn result;
        // no wins
        result = new Board(conf2).findHorizontalWin(1);
        assertEquals(false, result.isWin);
        result = new Board(conf2).findHorizontalWin(2);
        assertEquals(false, result.isWin);
        result = new Board(conf3).findHorizontalWin(1);
        assertEquals(false, result.isWin);
        result = new Board(conf3).findHorizontalWin(2);
        assertEquals(false, result.isWin);
        result = new Board(conf4).findHorizontalWin(1);
        assertEquals(false, result.isWin);
        // wins
        result = new Board(conf4).findHorizontalWin(2);
        assertEquals(true, result.isWin);
        assertArrayEquals(new int[][] {{0,3},{1,3},{2,3},{3,3}}, result.positions);
    }
    @Test
    void findVerticalWin() {
        WinReturn result;
        // no wins
        result = new Board(conf4).findVerticalWin(1);
        assertEquals(false, result.isWin);
        assertEquals(1, result.chipNum);

        result = new Board(conf3).findVerticalWin(2);
        assertEquals(false, result.isWin);

        // wins
        result = new Board(conf4).findVerticalWin(2);
        assertEquals(true, result.isWin);
        assertArrayEquals(new int[][] {{2,2},{2,3},{2,4},{2,5}}, result.positions);

        result = new Board(conf3).findVerticalWin(1);
        assertEquals(true, result.isWin);
        assertArrayEquals(new int[][] {{2,2},{2,3},{2,4},{2,5}}, result.positions);
    }
    @Test
    void findDiagonalWin() {
        int[][] conf4 = {
                { 0,0,1,0,0,0,0 },
                { 0,2,1,1,0,0,0 },
                { 0,1,2,1,2,0,0 },
                { 2,2,2,2,1,0,0 },
                { 2,2,2,1,1,1,0 },
                { 1,2,2,2,1,1,0 },
        };
        WinReturn result;
        Board board = new Board(conf4);

        result = board.findDiagonalWin(1);
        assertEquals(true, result.isWin);
        assertArrayEquals(new int[][] {{2,1},{3,2},{4,3},{5,4}}, result.positions);

        result = board.findDiagonalWin(2);
        assertEquals(true, result.isWin);
        assertArrayEquals(new int[][] {{4,2},{3,3},{2,4},{1,5}}, result.positions);
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
        Board board = new Board(conf4);
        assertArrayEquals(new int[] {0,0,2,2,2,2}, board.getDiagonalTopRightToBottomLeft(6,0));
        assertArrayEquals(new int[] {0}, board.getDiagonalTopRightToBottomLeft(6,5));
        assertArrayEquals(new int[] {0,1,1,2}, board.getDiagonalTopRightToBottomLeft(3,0));
        assertArrayEquals(new int[] {2,2,1}, board.getDiagonalTopRightToBottomLeft(2,3));
    }
}