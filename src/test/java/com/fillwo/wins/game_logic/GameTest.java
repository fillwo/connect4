package com.fillwo.wins.game_logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    public void playOnlyWhenYourTurn() throws GameException {
        Game game = new Game();
        Player player1 = game.getPlayerOne();
        Player player2 = game.getPlayerTwo();

        Exception exception = assertThrows(GameException.class, () -> {
            game.addChip(player2, 0);
        });
        assertEquals("not playerTwos turn", exception.getMessage());

        game.addChip(player1, 0);

        assertThrows(GameException.class, () -> {
            game.addChip(player1, 0);
        });
    }

    @Test
    public void finishGameWithWin() throws GameException {
        Game game = new Game();
        Player player1 = game.getPlayerOne();
        Player player2 = game.getPlayerTwo();

        for (int i = 0; i < 3; i++) {
            game.addChip(player1, 0);
            game.addChip(player2, 1);
        }
        // set winning chip
        game.addChip(player1, 0);

        assertEquals(true, game.isFinished());
        assertEquals(player1.getId(), game.getWinner().getId());
        assertArrayEquals(new int[][] {{0,2},{0,3},{0,4},{0,5}}, game.getWinningChipPositions());

        Exception exception = assertThrows(GameException.class, () -> {
            game.addChip(player2, 0);
        });
        assertEquals("game has already finished", exception.getMessage());
    }

    @Test
    public void finishGameWithDraw() throws GameException {
        Game game = new Game();
        Player player1 = game.getPlayerOne();
        Player player2 = game.getPlayerTwo();

        for (int col = 0; col < 7; col++) {
            if (col % 2 == 0) {
                game.addChip(player1, col);
            } else {
                game.addChip(player2, col);
            }
        }
        for (int col = 0; col < 7; col++) {
            if (col % 2 == 0) {
                game.addChip(player2, col);
            } else {
                game.addChip(player1, col);
            }
        }
        for (int col = 0; col < 7; col++) {
            if (col % 2 == 0) {
                game.addChip(player1, col);
            } else {
                game.addChip(player2, col);
            }
        }
        // change up pattern otherwise win would occur
        for (int col = 1; col < 6; col++) {
            if (col % 2 == 0) {
                game.addChip(player1, col);
            } else {
                game.addChip(player2, col);
            }
        }
        for (int col = 1; col < 6; col++) {
            if (col % 2 == 0) {
                game.addChip(player2, col);
            } else {
                game.addChip(player1, col);
            }
        }
        for (int col = 1; col < 6; col++) {
            if (col % 2 == 0) {
                game.addChip(player1, col);
            } else {
                game.addChip(player2, col);
            }
        }
        // last manual steps
        game.addChip(player1, 0);
        game.addChip(player2, 0);
        game.addChip(player1, 6);
        game.addChip(player2, 6);
        game.addChip(player1, 0);
        game.addChip(player2, 6);

        assertTrue(game.isFinished());
        assertNull(game.getWinner());
    }


}