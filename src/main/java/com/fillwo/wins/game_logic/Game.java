package com.fillwo.wins.game_logic;

public class Game {

    private int turn;
    private boolean finished;
    private Board board;
    private Player playerOne;
    private Player playerTwo;

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

    public boolean submitMove(Player player) {
        // todo: not ready needs to have move arg
        // todo: toggle turn
        return false;
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
}
