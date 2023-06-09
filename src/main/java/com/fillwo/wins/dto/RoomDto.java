package com.fillwo.wins.dto;

import com.fillwo.wins.game_logic.Game;
import com.fillwo.wins.model.Room;

public class RoomDto {
    private final String roomId;
    private final String playerId;
    private boolean yourTurn;
    private final boolean finished;
    private final int[][] boardStatus;
    private final String gameState;

    private int playerNum;

    private final int[][] winningChipPositions;

    public RoomDto(String playerId, Room room) {
        Game game = room.getGame();

        this.roomId = room.getId();
        this.playerId = playerId;
        this.boardStatus = game.getBoard().getEntries();
        this.finished = game.isFinished();
        this.winningChipPositions = game.getWinningChipPositions();


        boolean isPlayerOne = playerId.equals(game.getPlayerOne().getId());
        boolean isPlayerTwo = playerId.equals(game.getPlayerTwo().getId());

        if (isPlayerOne) {
            this.playerNum = 1;
            if (game.getTurn() == 0) {
                this.yourTurn = true;
            }
        }
        if (isPlayerTwo) {
            this.playerNum = 2;
            if (game.getTurn() == 1) {
                this.yourTurn = true;
            }
        }

        // determine the game state: ONGOING, WON, LOST, DRAW
        if (!game.isFinished()) {
            this.gameState = "ONGOING";
        } else {
            if (game.getWinner() == null) {
                this.gameState = "DRAW";
            } else {
                if (playerId.equals(game.getWinner().getId())) {
                    this.gameState = "WON";
                } else {
                    this.gameState = "LOST";
                }
            }
        }
    }
    public String getRoomId() {
        return roomId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public int[][] getBoardStatus() {
        return boardStatus;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getGameState() {
        return gameState;
    }

    public int[][] getWinningChipPositions() {
        return winningChipPositions;
    }

    public int getPlayerNum() {
        return playerNum;
    }
}
