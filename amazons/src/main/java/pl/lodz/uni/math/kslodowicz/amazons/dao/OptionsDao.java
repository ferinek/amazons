package pl.lodz.uni.math.kslodowicz.amazons.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class OptionsDao extends JdbcDaoSupport {
    private static final String MONTE_CARLO_TIME = "MONTE_CARLO_TIME";
    private static final String CREATE_OPTION_TABLE_SQL = "CREATE TABLE IF NOT EXISTS TBL_OPTIONS (ID BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL, NAME VARCHAR(255) UNIQUE, OPTION_VALUE VARCHAR(255) NOT NULL) ";
    private static final String GENERATE_DEFAULT_OPTIONS_SQL = "INSERT INTO TBL_OPTIONS (NAME, OPTION_VALUE) VALUES ('MONTE_CARLO_TIME', '1')";
    private static final String CHECK_IF_EXISTS_OPTION_SQL = "SELECT COUNT(*) AS RESULT FROM TBL_OPTIONS WHERE NAME LIKE ?";
    private static final String GET_MONTE_CARLO_TIME_SQL = "SELECT OPTION_VALUE FROM TBL_OPTIONS WHERE NAME LIKE 'MONTE_CARLO_TIME'";
    private static final String UPDATE_MONTE_CARLO_TIME_SQL = "UPDATE TBL_OPTIONS SET OPTION_VALUE=? WHERE NAME LIKE 'MONTE_CARLO_TIME'";

    @Autowired
    public OptionsDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
        getJdbcTemplate().execute(CREATE_OPTION_TABLE_SQL);
        insertDefaultValues();
    }

    private void insertDefaultValues() {
        Integer result = getJdbcTemplate().queryForObject(CHECK_IF_EXISTS_OPTION_SQL, new Object[] { MONTE_CARLO_TIME }, Integer.class);
        if (result==0) {
            getJdbcTemplate().execute(GENERATE_DEFAULT_OPTIONS_SQL);
        }
    }

    public Integer getMonteCarloTimeValue() {
        return getJdbcTemplate().queryForObject(GET_MONTE_CARLO_TIME_SQL, Integer.class);
    }

    public void setMonteCarloTimeValue(Integer time) {
        getJdbcTemplate().update(UPDATE_MONTE_CARLO_TIME_SQL, new Object[] { time });
    }
}
