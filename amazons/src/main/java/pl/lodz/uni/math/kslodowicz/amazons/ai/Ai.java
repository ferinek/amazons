package pl.lodz.uni.math.kslodowicz.amazons.ai;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.lodz.uni.math.kslodowicz.amazons.controller.MainWindowController;
import pl.lodz.uni.math.kslodowicz.amazons.dto.TileDTO;
import pl.lodz.uni.math.kslodowicz.amazons.enums.PlayerType;
import pl.lodz.uni.math.kslodowicz.amazons.logic.Board;

@Component
public class Ai {

    private static final String COLON = ": ";
    private static final String SHOOT_TO = "Shoot to %s.\n";
    private static final String MOVE_FROM_TO = "Move from %s to %s. ";
    @Autowired
    private Board board;
    @Autowired
    private MainWindowController controller;
    @Autowired
    private MonteCarloAi monteCarloAi;
    
    
    private int difficulty;
    private int player;

    public static final int WEAK_AI = 1;
    public static final int STRONG_AI = 2;

    public void moveAndShoot() {
        if (difficulty == WEAK_AI) {
            moveAndShootByWeakAi();
        }
        if (difficulty == STRONG_AI) {
            monteCarloAi.moveAndShoot(player);
        }
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    private void moveAndShootByWeakAi() {
        Random random = new Random();

        List<TileDTO> playerFields = board.getPlayerFieldsWithMoves(getPlayer());
        TileDTO playerField = playerFields.get(random.nextInt(playerFields.size()));

        List<TileDTO> possibleMoves = board.getPossibleMoves(playerField);
        TileDTO possibleMove = possibleMoves.get(random.nextInt(possibleMoves.size()));
        board.move(playerField, possibleMove);
        controller.addText(PlayerType.getNameByNumber(player) + COLON);
        controller.addText(MOVE_FROM_TO, playerField, possibleMove);

        List<TileDTO> possibleShoots = board.getPossibleMoves(possibleMove);
        TileDTO possibleShoot = possibleShoots.get(random.nextInt(possibleShoots.size()));
        board.shoot(possibleShoot);

        controller.addText(SHOOT_TO, possibleShoot);
    }

}