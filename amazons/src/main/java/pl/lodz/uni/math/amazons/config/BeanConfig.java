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
	private static final String SAVE_GAME_WINDOW_CREATION_ERROR = "Couldn't create SaveGameWindow bean: ";
	private static final String NEW_GAME_WINDOW_CREATION_ERROR = "Couldn't create NewGameWindowController bean: ";
	private static final String MAIN_WINDOW_CREATION_ERROR = "Couldn't create MainWindowController bean: ";
	private static final String VIEW_SAVE_WINDOW_FXML = "/view/SaveWindow.fxml";
	private static final String VIEW_NEW_GAME_WINDOW_FXML = "/view/NewGameWindow.fxml";
	private static final String VIEW_MAIN_WINDOW_FXML = "/view/MainWindow.fxml";
	private static final Logger LOGGER = Logger.getLogger(BeanConfig.class);

	@Bean
	public MainWindowController getMainWindowController() {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(AmazonsApplication.class.getResource(VIEW_MAIN_WINDOW_FXML));
		try {
			BorderPane rootLayout = (BorderPane) loader.load();
			MainWindowController controller = loader.getController();
			controller.setRootLayout(rootLayout);
			return controller;
		} catch (IOException e) {
			LOGGER.error(MAIN_WINDOW_CREATION_ERROR, e);
		}

		return null;
	}

	@Bean
	public NewGameWindowController getNewGameController() {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(AmazonsApplication.class.getResource(VIEW_NEW_GAME_WINDOW_FXML));
		try {
			BorderPane rootLayout = (BorderPane) loader.load();
			NewGameWindowController controller = loader.getController();
			controller.setRootLayout(rootLayout);
			return controller;
		} catch (IOException e) {
			LOGGER.error(NEW_GAME_WINDOW_CREATION_ERROR, e);

		}
		return null;
	}

	@Bean
	public SaveGameController getSaveGameController() {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(AmazonsApplication.class.getResource(VIEW_SAVE_WINDOW_FXML));
		try {
			BorderPane rootLayout = (BorderPane) loader.load();
			SaveGameController controller = loader.getController();
			controller.setRootLayout(rootLayout);
			return controller;
		} catch (IOException e) {
			LOGGER.error(SAVE_GAME_WINDOW_CREATION_ERROR, e);

		}
		return null;
	}
}
