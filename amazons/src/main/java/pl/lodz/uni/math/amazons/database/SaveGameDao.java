package pl.lodz.uni.math.amazons.database;

import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import pl.lodz.uni.math.amazons.dto.SaveGameDTO;
import pl.lodz.uni.math.amazons.utils.StringUtils;

@Component
public class SaveGameDao extends JdbcDaoSupport {

    private static final String CREATE_SAVE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS TBL_SAVE (ID BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL, NAME VARCHAR(255) UNIQUE, ACTUAL_PLAYER INT, PHASE INT,SELECTED VARCHAR(10), AI_PLAYER INT, AI_DIFFICULTY INT, BOARD_STATE VARCHAR(255), TEXT CLOB)";
    private static final String INSERT_SAVE_SQL = "INSERT INTO TBL_SAVE (NAME, ACTUAL_PLAYER, PHASE,SELECTED, AI_PLAYER, AI_DIFFICULTY, BOARD_STATE, TEXT) VALUES (?, ?, ?, ?, ?, ?,?,?);";
    private static final String SELECT_SAVE_NAMES_SQL = "SELECT NAME FROM TBL_SAVE;";
    private static final String SELECT_SAVE_SQL = "SELECT * FROM TBL_SAVE WHERE NAME LIKE ?;";
    private static final String DELETE_SAVE_SQL = "DELETE FROM TBL_SAVE WHERE NAME LIKE ?;";
    private static final String SELECT_COUNT_SAVE_SQL = "SELECT COUNT(*) FROM TBL_SAVE WHERE NAME LIKE ?;";

    private final SaveGameRowMapper saveGameRowMapper = new SaveGameRowMapper();;

    @Autowired
    public SaveGameDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
        getJdbcTemplate().execute(CREATE_SAVE_TABLE_SQL);
    }

    public void insertSave(SaveGameDTO save) {
        Object[] params = new Object[] { save.getName(), save.getActivePlayer(), save.getPhase(), StringUtils.tileDtoToString(save.getSelectTile()),
                save.getAiPlayer(), save.getAiDifficulty(), save.getBoardState(), save.getTextAreaText() };
        int[] types = new int[] { Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.CLOB };
        getJdbcTemplate().update(INSERT_SAVE_SQL, params, types);
    }

    public List<String> getAllSavesNames() {
        return getJdbcTemplate().queryForList(SELECT_SAVE_NAMES_SQL, String.class);
    }

    public SaveGameDTO getSave(String name) {
        return getJdbcTemplate().queryForObject(SELECT_SAVE_SQL, new Object[] { name }, saveGameRowMapper);
    }

    public boolean isSaveExists(String name) {
        Integer result = getJdbcTemplate().queryForObject(SELECT_COUNT_SAVE_SQL, new Object[] { name }, Integer.class);
        return result == 1;
    }

    public void updateSave(SaveGameDTO saveGame) {
        deleteSave(saveGame.getName());
        insertSave(saveGame);

    }

    public void deleteSave(String name) {
        getJdbcTemplate().update(DELETE_SAVE_SQL, new Object[] { name });

    }
}
