package pl.lodz.uni.math.kslodowicz.amazons.config;

import org.mockito.Mock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
    private MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    @Bean
    private NewGameWindowController getNewGameWindowController() {
        return newWindowController;
    }

    @Bean
    private SaveGameController getSaveGameController() {
        return saveGameController;
    }

}
