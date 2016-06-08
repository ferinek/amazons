package pl.lodz.uni.math.amazons.dto;

public class SaveGameDTO {
    private int id;
    private String name;
    private int activePlayer;
    private int phase;
    private TileDTO selectTile;
    private int aiPlayer;
    private int aiDifficulty;
    private String boardState;
    private String textAreaText;

    public SaveGameDTO() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

    public int getAiPlayer() {
        return aiPlayer;
    }

    public void setAiPlayer(int aiPlayer) {
        this.aiPlayer = aiPlayer;
    }

    public int getAiDifficulty() {
        return aiDifficulty;
    }

    public void setAiDifficulty(int aiDifficulty) {
        this.aiDifficulty = aiDifficulty;
    }

    public String getBoardState() {
        return boardState;
    }

    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public String getTextAreaText() {
        return textAreaText;
    }

    public void setTextAreaText(String textAreaText) {
        this.textAreaText = textAreaText;
    }

    public TileDTO getSelectTile() {
        return selectTile;
    }

    public void setSelectTile(TileDTO selectTile) {
        this.selectTile = selectTile;
    }

}
