package pl.lodz.uni.math.amazons.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import pl.lodz.uni.math.amazons.dto.SaveGameDTO;
import pl.lodz.uni.math.amazons.utils.StringUtils;

class SaveGameRowMapper implements RowMapper<SaveGameDTO> {
    private LobHandler lobHandler = new DefaultLobHandler();

    @Override
    public SaveGameDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        SaveGameDTO dto = new SaveGameDTO();
        dto.setId(rs.getInt(1));
        dto.setName(rs.getString(2));
        dto.setActivePlayer(rs.getInt(3));
        dto.setPhase(rs.getInt(4));
        dto.setSelectTile(StringUtils.stringToStileDTO(rs.getString(5)));
        dto.setAiPlayer(rs.getInt(6));
        dto.setAiDifficulty(rs.getInt(7));
        dto.setBoardState(rs.getString(8));
        dto.setTextAreaText(lobHandler.getClobAsString(rs, 9));
        return dto;
    }
}
