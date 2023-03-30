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
}