package com.fillwo.wins.game;

public class Game {

    private int turn = 0;
    private Player playerOne;
    private Player playerTwo;

    public Game() {
        this.playerOne = new Player();
        this.playerTwo = new Player();
    }

    public Game(Player playerOne, Player playerTwo) {
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
