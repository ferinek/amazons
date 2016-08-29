package pl.lodz.uni.math.kslodowicz.amazons.dto;

import java.util.LinkedList;
import java.util.List;

public class GameStateDTO {

    private String boardState;
    private boolean end;
    private long win;
    private long all;
    private List<GameStateDTO> children;

    public GameStateDTO() {
        super();
    }

    public GameStateDTO(String boardState) {
        super();
        this.boardState = boardState;
        children = new LinkedList<>();
    }

    public String getBoardState() {
        return boardState;
    }

    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public long getWin() {
        return win;
    }

    public void setWin(long win) {
        this.win = win;
    }

    public long getAll() {
        return all;
    }

    public void setAll(long all) {
        this.all = all;
    }

    public List<GameStateDTO> getChildren() {
        return children;
    }

    public void setChildren(List<GameStateDTO> children) {
        this.children = children;
    }


}
