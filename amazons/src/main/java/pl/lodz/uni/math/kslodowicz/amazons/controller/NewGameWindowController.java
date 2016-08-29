package pl.lodz.uni.math.kslodowicz.amazons.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.lodz.uni.math.kslodowicz.amazons.enums.GameType;
import pl.lodz.uni.math.kslodowicz.amazons.enums.OpponentType;
import pl.lodz.uni.math.kslodowicz.amazons.utils.StyleUtils;

public class NewGameWindowController {

    @FXML
    private ComboBox<GameType> boardChoice;

    @FXML
    private Button startButton;
    @FXML
    private ComboBox<OpponentType> opponentChoice;

    @Autowired
    private MainWindowController mainWindowController;
    private BorderPane rootLayout;
    private Stage stage;

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    @FXML
    void closeWindow(ActionEvent event) {
        ((Stage) getRootLayout().getScene().getWindow()).close();
    }

    @FXML
    void startNewGame(ActionEvent event) {
        ((Stage) getRootLayout().getScene().getWindow()).close();
        mainWindowController.initializeNewGame(boardChoice.getSelectionModel().getSelectedItem(), opponentChoice.getSelectionModel().getSelectedItem());
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;

    }

    private void setStage() {
        stage = new Stage();
        rootLayout.setStyle(StyleUtils.BORDER_STYLE);
        stage.setScene(new Scene(rootLayout));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainWindowController.getRootLayout().getScene().getWindow());
        startButton.setDisable(true);
        ObservableList<GameType> observableArrayList = FXCollections.observableArrayList(GameType.values());
        boardChoice.setItems(observableArrayList);
    }

    public void show() {
        if (stage == null) {
            setStage();
        }
        setChoices();
        stage.show();
    }

    private void setChoices() {
        setupStartButton();
        if (boardChoice.getSelectionModel().getSelectedItem() == null) {
            opponentChoice.setValue(OpponentType.TYPE_CHOOSE);
        }
        boardChoice.setOnAction(event -> {
            event.consume();
            setOpponentChoices(boardChoice.getSelectionModel().getSelectedItem());
            setupStartButton();
        });

    }

    private void setOpponentChoices(GameType gameType) {
        opponentChoice.setValue(OpponentType.TYPE_HUMAN);
        ObservableList<OpponentType> observableArrayList = FXCollections.observableArrayList();
        for (int i = 0; i < OpponentType.values().length - 1; i++)
            observableArrayList.add(OpponentType.values()[i + 1]);
        opponentChoice.setItems(observableArrayList);
        opponentChoice.setOnAction(event -> {
            event.consume();
            setupStartButton();
        });

    }

    private void setupStartButton() {
        if (boardChoice.getSelectionModel().getSelectedItem() != null && opponentChoice.getSelectionModel().getSelectedItem() != null
                && opponentChoice.getSelectionModel().getSelectedItem().getValue() >= 0) {
            startButton.setDisable(false);
        } else {
            startButton.setDisable(true);
        }
    }
}
