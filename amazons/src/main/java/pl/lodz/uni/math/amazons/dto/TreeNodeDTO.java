package pl.lodz.uni.math.amazons.dto;

import pl.lodz.uni.math.amazons.logic.Board;

public class TreeNodeDTO {
    private int gameWins;
    private int gamePlayed;
    private Board board;
    private TileDTO playerField;
    private TileDTO move;
    private TileDTO shoot;
    private boolean dangerous;

    public int getGameWins() {
        return gameWins;
    }

    public void setGameWins(int gameWins) {
        this.gameWins = gameWins;
    }

    public int getGamePlayed() {
        return gamePlayed;
    }

    public void setGamePlayed(int gamePlayed) {
        this.gamePlayed = gamePlayed;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public TileDTO getPlayerField() {
        return playerField;
    }

    public void setPlayerField(TileDTO playerField) {
        this.playerField = playerField;
    }

    public TileDTO getMove() {
        return move;
    }

    public void setMove(TileDTO move) {
        this.move = move;
    }

    public TileDTO getShoot() {
        return shoot;
    }

    public void setShoot(TileDTO shoot) {
        this.shoot = shoot;
    }

    public boolean isDangerous() {
        return dangerous;
    }

    public void setDangerous(boolean dangerous) {
        this.dangerous = dangerous;
    }
}
