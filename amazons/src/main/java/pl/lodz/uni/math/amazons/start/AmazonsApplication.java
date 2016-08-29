package pl.lodz.uni.math.amazons.start;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import pl.lodz.uni.math.amazons.controller.MainWindowController;

@SpringBootApplication
@ComponentScan({ "pl.lodz.uni.math.amazons.controller", "pl.lodz.uni.math.amazons.database",
		"pl.lodz.uni.math.amazons.start", "pl.lodz.uni.math.amazons.ai", "pl.lodz.uni.math.amazons.logic",
		"pl.lodz.uni.math.amazons.config" })
public class AmazonsApplication extends Application {
	private static final String TITLE = "Amazons";

	private Stage primaryStage;

	private static ConfigurableApplicationContext run;

	public static void main(String[] args) {
		JFXPanel fxPanel = new JFXPanel();
		run = SpringApplication.run(AmazonsApplication.class, args);
		launch(args);
		run.close();
	}

	@Override
	public void start(Stage pStage) throws Exception {

		this.primaryStage = pStage;
		this.primaryStage.setTitle(TITLE);
		MainWindowController bean = run.getBean(MainWindowController.class);
		BorderPane rootLayout = bean.getRootLayout();
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setMaximized(true);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
