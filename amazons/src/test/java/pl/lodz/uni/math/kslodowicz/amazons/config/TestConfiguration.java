package pl.lodz.uni.math.kslodowicz.amazons.config;

import javax.sql.DataSource;

import org.mockito.Mock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import pl.lodz.uni.math.kslodowicz.amazons.controller.MainWindowController;
import pl.lodz.uni.math.kslodowicz.amazons.controller.NewGameWindowController;
import pl.lodz.uni.math.kslodowicz.amazons.controller.SaveGameController;

@Configuration
@ComponentScan({ "pl.lodz.uni.math.kslodowicz.amazons.ai", "pl.lodz.uni.math.kslodowicz.amazons.dao", "pl.lodz.uni.math.kslodowicz.amazons.service" })
public class TestConfiguration {

    @Mock
    private MainWindowController mainWindowController;
    @Mock
    private NewGameWindowController newWindowController;
    @Mock
    private SaveGameController saveGameController;

    @Bean
    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    @Bean
    public NewGameWindowController getNewGameWindowController() {
        return newWindowController;
    }

    @Bean
    public SaveGameController getSaveGameController() {
        return saveGameController;
    }

    @Bean()
    public DataSource createEmbeddedDatabase() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).setName("database").build();
        return db;
    }
}
