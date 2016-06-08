package pl.lodz.uni.math.amazons.config;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.lodz.uni.math.amazons.controller.MainWindowController;
import pl.lodz.uni.math.amazons.controller.NewGameWindowController;
import pl.lodz.uni.math.amazons.controller.SaveGameController;
import pl.lodz.uni.math.amazons.start.AmazonsApplication;

@Configuration
public class BeanConfig {
    private static Logger logger = Logger.getLogger(BeanConfig.class);

    @Bean
    public MainWindowController getMainWindowController() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AmazonsApplication.class.getResource("/view/MainWindow.fxml"));
        try {
            BorderPane rootLayout = (BorderPane) loader.load();
            MainWindowController controller = loader.getController();
            controller.setRootLayout(rootLayout);
            return controller;
        } catch (IOException e) {
            logger.error("Couldn't create MainWindowController bean: " + e);
        }

        return null;
    }

    @Bean
    public NewGameWindowController getNewGameController() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AmazonsApplication.class.getResource("/view/NewGameWindow.fxml"));
        try {
            BorderPane rootLayout = (BorderPane) loader.load();
            NewGameWindowController controller = loader.getController();
            controller.setRootLayout(rootLayout);
            return controller;
        } catch (IOException e) {
            logger.error("Couldn't create NewGameWindowController bean: " + e);
           
        }
        return null;
    }
    @Bean
    public SaveGameController getSaveGameController() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AmazonsApplication.class.getResource("/view/SaveWindow.fxml"));
        try {
            BorderPane rootLayout = (BorderPane) loader.load();
            SaveGameController controller = loader.getController();
            controller.setRootLayout(rootLayout);
            return controller;
        } catch (IOException e) {
            logger.error("Couldn't create SaveGameWindow bean: " + e);
       
        }
        return null;
    }
}
