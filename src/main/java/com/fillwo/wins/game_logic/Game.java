package com.fillwo.wins.game_logic;

public class Game {
    private int turn;
    private boolean finished;
    private final Board board;
    private final Player playerOne;
    private final Player playerTwo;
    private  Player winner;
    private int[][] winningChipPositions;

    public Game() {
        this.board = new Board();
        this.playerOne = new Player();
        this.playerTwo = new Player();
    }

    public Game(Player playerOne, Player playerTwo) {
        this.board = new Board();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public void addChip(Player player, int posX) throws GameException {
        boolean isPlayerOne = player.equals(this.playerOne);
        boolean isPlayerTwo = player.equals(this.playerTwo);
        int chipNum;

        // check if game is already finished
        if (this.finished) {
            throw new GameException("game has already finished");
        }
        // check if player id is correct
        if (!isPlayerOne && !isPlayerTwo) {
            throw new GameException("player id not correct");
        }
        if (isPlayerOne) {
            chipNum = 1;
        } else {
            chipNum = 2;
        }
        // check if it is the players turn
        if (isPlayerOne && this.turn != 0) {
            throw new GameException("not playerOnes turn");
        }
        if (isPlayerTwo && this.turn != 1) {
            throw new GameException("not playerTwos turn");
        }
        // add chip & throw if not possible
        boolean addedChip = this.board.addChip(chipNum, posX);
        if (!addedChip) {
            throw new GameException("cannot add chip, column is full");
        }
        // check for a win
        WinReturn result = this.board.findWin(chipNum);
        if (result.isWin) {
            this.finished = true;
            if (isPlayerOne) {
                this.winner = this.playerOne;
            }
            if (isPlayerTwo) {
                this.winner = this.playerTwo;
            }
            this.winningChipPositions = result.positions;
        } else {
            // need to check for a draw
            if (this.board.isFull()) {
                this.finished = true;
            }
        }
        // toggle turn
        this.turn = (this.turn + 1) % 2;
    }

    public int getTurn() {
        return turn;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public int[][] getBoardStatus() {
        return this.board.getEntries();
    }

    public boolean isFinished() {
        return finished;
    }

    public Player getWinner() {
        return winner;
    }

    public int[][] getWinningChipPositions() {
        return winningChipPositions;
    }
}
