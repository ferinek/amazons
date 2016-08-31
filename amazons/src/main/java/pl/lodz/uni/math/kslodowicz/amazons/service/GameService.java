package pl.lodz.uni.math.kslodowicz.amazons.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.lodz.uni.math.kslodowicz.amazons.ai.Ai;
import pl.lodz.uni.math.kslodowicz.amazons.controller.MainWindowController;
import pl.lodz.uni.math.kslodowicz.amazons.dto.SaveGameDTO;
import pl.lodz.uni.math.kslodowicz.amazons.dto.TileDTO;
import pl.lodz.uni.math.kslodowicz.amazons.enums.GameType;
import pl.lodz.uni.math.kslodowicz.amazons.enums.PlayerType;

@Component
public class GameService {
    @Autowired
    private BoardService board;
    @Autowired
    private Ai ai;
    @Autowired
    private MainWindowController controller;

    private boolean change;
    private boolean end;
    private int activePlayer;
    private int phase;
    private TileDTO select;
    private List<TileDTO> moves;
    private boolean lockPlayer = false;

    public GameService() {
        super();
    }

    public void startGame(GameType gameType, int aiDifficulty) {
        Random random = new Random();
        end = false;
        change = false;
        board.startFields(gameType);
        activePlayer = random.nextInt(2) + 1;
        phase = 1;
        select = null;
        moves = null;
        ai.setDifficulty(aiDifficulty);
        ai.setPlayer(random.nextInt(2) + 1);

    }

    private void moveAi() {
        checkIfEnd();
        if (!end) {
            Runnable task = () -> {
                lockPlayer = true;
                ai.moveAndShoot();
                change = true;
                activePlayer = (activePlayer % 2) + 1;
                checkIfEnd();
                controller.update();
                lockPlayer = false;
            };
            new Thread(task).start();

        }
    }

    public void clicked(TileDTO clicked) {
        if (!lockPlayer) {
            if (phase == 1) {
                move(clicked);
            } else {
                shoot(clicked);
            }
        }
    }

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public int[][] getFields() {
        return board.getFields();
    }

    public List<TileDTO> getMoves() {
        return moves;
    }

    private void move(TileDTO clicked) {
        if (select == null) {
            if (board.getPlayerFields(activePlayer).contains(clicked)) {
                select = clicked;
                moves = board.getPossibleMoves(clicked);
                change = true;
            }
        } else {
            if (moves.contains(clicked)) {
                controller.addText(PlayerType.getNameByNumber(activePlayer) + ": ");
                controller.addText("Move from %s to %s. ", select, clicked);
                board.move(select, clicked);
                select = clicked;
                moves = board.getPossibleMoves(clicked);
                phase = 2;
                change = true;

            } else {
                select = null;
                moves = null;
                change = true;
            }
        }
    }

    private void shoot(TileDTO clickedTile) {
        if (moves.contains(clickedTile)) {
            controller.addText("Shoot to %s.\n", clickedTile);
            board.shoot(clickedTile);
            change = true;
            select = null;
            moves = null;
            activePlayer = (activePlayer % 2) + 1;
            if (ai.getDifficulty() > 0) {

                moveAi();
            }
            phase = 1;
            checkIfEnd();
        }
    }

    private void checkIfEnd() {
        if (board.checkIfEnd(activePlayer) && !end) {
            controller.addText("End of game. Winner: ");
            if (activePlayer == 2) {
                controller.addText("White Player.\n");
            } else {
                controller.addText("Black Player.\n");
            }
            end = true;
        }

    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public void runStartingTasks() {

        controller.addText("Starting new game. \n");
        if (ai.getDifficulty() == 0) {
            controller.addText("White: Player.\n");
            controller.addText("Black: Player.\n");
        } else if (ai.getPlayer() == 2) {
            controller.addText("White: Player.\n");
            controller.addText("Black: Computer \n");
        } else {
            controller.addText("White: Computer.\n");
            controller.addText("Black: Player.\n");
        }
        controller.addText("First move: ");

        if (activePlayer == 1) {
            controller.addText("White Player.\n");
        } else {
            controller.addText("Black Player.\n");
        }
        if (activePlayer == ai.getPlayer() && ai.getDifficulty() > 0) {
            moveAi();
        }
    }

    public SaveGameDTO getSaveGame(String name) {
        SaveGameDTO save = new SaveGameDTO();
        save.setActivePlayer(activePlayer);
        save.setAiDifficulty(ai.getDifficulty());
        save.setAiPlayer(ai.getPlayer());
        save.setBoardState(board.toDatabaseString());
        save.setName(name);
        save.setPhase(phase);
        save.setSelectTile(select);
        save.setTextAreaText(controller.getText());
        return save;
    }

    public boolean isGameRunning() {
        return (board.getFields() != null) && (!end);
    }

    public void loadGame(SaveGameDTO save) {
        activePlayer = save.getActivePlayer();
        phase = save.getPhase();
        ai.setDifficulty(save.getAiDifficulty());
        ai.setPlayer(save.getAiPlayer());
        board.fromDatabaseString(save.getBoardState());
        controller.setText(save.getTextAreaText());
        end = false;
        change = true;
        if (phase == 1) {
            select = null;
            moves = null;
        } else {
            select = save.getSelectTile();
            moves = board.getPossibleMoves(select);
        }

    }

    public int getPlayer() {
        return activePlayer;
    }

    public String getAiType() {
        if (ai.getDifficulty() != 0 && activePlayer == ai.getPlayer()) {
            return "AI";
        } else {
            return "Human";
        }
    }
}
