package pl.lodz.uni.math.kslodowicz.amazons.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.lodz.uni.math.kslodowicz.amazons.service.SaveService;
import pl.lodz.uni.math.kslodowicz.amazons.utils.StyleUtils;

public class SaveGameController {

	@FXML
	private Button save;
	@FXML
	private Button load;
	@FXML
	private Button delete;
	@FXML
	private ListView<String> listView;

	@Autowired
	private MainWindowController mainWindowController;
	@Autowired
	private SaveService saveService;

	private BorderPane rootLayout;
	private Stage stage;

	@FXML
	private void cancel(ActionEvent event) {
		closeWindow();
	}

	private void closeWindow() {
		((Stage) getRootLayout().getScene().getWindow()).close();

	}

	@FXML
	private void delete() {
		saveService.deleteSave(getSelectedSave());
		setListView();
	}

	@FXML
	private void load() {
		saveService.loadGame(getSelectedSave());
		closeWindow();

	}

	@FXML
	private void save() {
		boolean saveGame = saveService.saveGame(getSelectedSave(), getSelectedSaveIndex());
		if (saveGame) {
			closeWindow();
		}
	}

	public void show() {
		setStage();
		setSaveButton();
		setListView();
		stage.show();

	}

	private void setListView() {
		saveService.setListView(listView);

	}

	private void setStage() {
		if (stage == null) {
			stage = new Stage();
			rootLayout.setStyle(StyleUtils.BORDER_STYLE);
			stage.setScene(new Scene(rootLayout));
			stage.setResizable(false);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(mainWindowController.getRootLayout().getScene().getWindow());
			saveService.clicked(listView.getSelectionModel().getSelectedIndex(), load);
			listView.getSelectionModel().select(0);
			listView.setOnMouseClicked(event -> {
				event.consume();
				saveService.clicked(listView.getSelectionModel().getSelectedIndex(), load);
			});
		}
	}

	private void setSaveButton() {
		saveService.setSaveButton(save);

	}

	public BorderPane getRootLayout() {
		return rootLayout;
	}

	public void setRootLayout(BorderPane rootLayout) {
		this.rootLayout = rootLayout;
	}

	private String getSelectedSave() {
		return listView.getSelectionModel().getSelectedItem();
	}

	private int getSelectedSaveIndex() {
		return listView.getSelectionModel().getSelectedIndex();
	}
}
