package pl.lodz.uni.math.kslodowicz.amazons.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.lodz.uni.math.kslodowicz.amazons.dto.TileDTO;
import pl.lodz.uni.math.kslodowicz.amazons.enums.GameType;
import pl.lodz.uni.math.kslodowicz.amazons.utils.ArrayUtils;

@Component
public class Board {
    private int size;
    private int[][] fields;
    private static final int EMPTY_FIELD = 0;
    private static final int ARROW = 3;

    public Board() {
        super();
    }

    public Board(Board old) {
        super();
        this.size = old.size;
        this.fields = ArrayUtils.copyTable(old.fields);
    }

    public void startFields(GameType gameType) {
        String[] split = gameType.getDescription().split("I");
        size = Integer.parseInt(split[0]);
        fields = new int[size][size];
        for (int i = 1; i < 3; i++) {
            String[] split2 = split[i].split(";");
            for (String tile : split2) {
                String[] split3 = tile.split(",");
                fields[Integer.parseInt(split3[0])][Integer.parseInt(split3[1])] = i;
            }
        }

    }

    public int[][] getFields() {
        return fields;
    }

    public void setField(int x, int y, int value) {
        fields[x][y] = value;
    }

    public int getSize() {
        return size;
    }

    public List<TileDTO> getRemovedFields() {
        List<TileDTO> result = new LinkedList<>();
        for (int i = 0; i < getSize(); i++)
            for (int j = 0; j < getSize(); j++)
                if (fields[i][j] == ARROW) {
                    result.add(new TileDTO(i, j));
                }
        return result;
    }

    public List<TileDTO> getPlayerFields(int player) {
        List<TileDTO> result = new LinkedList<>();
        for (int i = 0; i < getSize(); i++)
            for (int j = 0; j < getSize(); j++)
                if (fields[i][j] == player) {
                    result.add(new TileDTO(i, j));
                }
        return result;
    }

    public List<TileDTO> getPlayerFieldsWithMoves(int player) {
        List<TileDTO> result = new LinkedList<>();
        for (int i = 0; i < getSize(); i++)
            for (int j = 0; j < getSize(); j++)
                if ((fields[i][j] == player) && (!getPossibleMoves(new TileDTO(i, j)).isEmpty())) {
                    result.add(new TileDTO(i, j));
                }
        return result;
    }

    public List<TileDTO> getPossibleMoves(TileDTO piece) {
        List<TileDTO> result = new LinkedList<>();
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (i != 0 || j != 0) {
                    result.addAll(getMovesInDirection(piece, i, j));
                }
        return result;
    }

    public void setFields(int[][] generated) {
        fields = ArrayUtils.copyTable(generated);

    }

    public void move(TileDTO source, TileDTO destination) {
        int value = fields[source.getX()][source.getY()];
        fields[source.getX()][source.getY()] = fields[destination.getX()][destination.getY()];
        fields[destination.getX()][destination.getY()] = value;
    }

    public void shoot(TileDTO destination) {
        fields[destination.getX()][destination.getY()] = ARROW;
    }

    private List<TileDTO> getMovesInDirection(TileDTO field, int right, int down) {
        List<TileDTO> result = new LinkedList<>();
        for (int i = 1; i < getSize(); i++) {
            TileDTO checkingTile = new TileDTO(field.getX() + right * i, field.getY() + down * i);
            if (isTileOnBoard(checkingTile)) {
                if (fields[checkingTile.getX()][checkingTile.getY()] == EMPTY_FIELD) {
                    result.add(checkingTile);
                } else {
                    return result;
                }
            } else {
                return result;
            }
        }
        return result;
    }

    private boolean isTileOnBoard(TileDTO tile) {
        return (tile.getX() >= 0) && (tile.getX() < getSize()) && (tile.getY() >= 0) && (tile.getY() < getSize());
    }

    public boolean checkIfEnd(int activePlayer) {
        List<TileDTO> playerFields = getPlayerFields(activePlayer);
        List<TileDTO> moves = new ArrayList<>();
        for (int i = 0; i < playerFields.size(); i++)
            moves.addAll(getPossibleMoves(playerFields.get(i)));
        if (moves.isEmpty()) {
            return true;
        }
        return false;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String toDatabaseString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                sb.append(fields[i][j]);
        return sb.toString();
    }

    public void fromDatabaseString(String board) {
        size = (int) Math.sqrt(board.length());
        fields = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                fields[i][j] = Integer.valueOf(board.charAt(i * size + j)) - 48;

    }

    public int getTurn() {
        int count = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (fields[i][j] == ARROW) {
                    count++;
                }
        return count;
    }

    @Override
    public String toString() {
        String result = "";
        for (int j = 0; j < getSize(); j++) {
            for (int i = 0; i < getSize(); i++)
                result = result + fields[i][j];
            result = result + "\n";
        }
        return result;
    }
}