package pl.lodz.uni.math.amazons.logic;

import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.lodz.uni.math.amazons.controller.MainWindowController;
import pl.lodz.uni.math.amazons.database.SaveGameDao;
import pl.lodz.uni.math.amazons.dto.SaveGameDTO;

@Component
public class SaveService {
    private static final String NEW_SAVE = "New...";
    private static final String SAVE_DIALOG="Save Dialog";
    @Autowired
    private Game game;
    @Autowired
    private SaveGameDao saveGameDao;
    @Autowired
    private MainWindowController mainWindowController;

    public boolean saveGame(String saveName, int index) {
        if (saveName == null || index < 1) {
            // User create new save slot

            TextInputDialog dialog = new TextInputDialog("Slot1");
            dialog.setTitle(SAVE_DIALOG);
            dialog.setHeaderText("Please enter save state name:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                // name was inputed
                if (saveGameDao.isSaveExists(result.get())) {
                    // duplicated name
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Save Error Dialog");
                    alert.setHeaderText("Save with this name exists.");
                    alert.showAndWait();
                    return false;

                } else {
                    // there are no saves with that name
                    SaveGameDTO saveGame = game.getSaveGame(result.get());
                    saveGameDao.insertSave(saveGame);

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle(SAVE_DIALOG);
                    alert.setHeaderText("You successfully save the game.");
                    alert.showAndWait();
                    return true;

                }
            } else {
                // no name typed

                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Save Error Dialog");
                alert.setHeaderText("No name typed.");
                alert.showAndWait();
                return false;
            }

        } else {
            // User override slot
            SaveGameDTO saveGame = game.getSaveGame(saveName);
            saveGameDao.updateSave(saveGame);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(SAVE_DIALOG);
            alert.setHeaderText("You successfully save the game.");
            alert.showAndWait();
            return true;
        }

    }

    public void loadGame(String string) {
        SaveGameDTO save = saveGameDao.getSave(string);
        mainWindowController.resetBoard((int) Math.sqrt(save.getBoardState().length()));
        game.loadGame(save);
        mainWindowController.update();
    }

    public void setSaveButton(Button save) {
        if (!game.isGameRunning()) {
            save.setDisable(true);
        } else {
            save.setDisable(false);
        }

    }

    public void setListView(ListView<String> listView) {
        List<String> allSavesNames = saveGameDao.getAllSavesNames();
        allSavesNames.add(0, NEW_SAVE);
        ObservableList<String> items = FXCollections.observableArrayList(allSavesNames);
        listView.setItems(items);
    }

    public void deleteSave(String name) {
        if (name != null) {
            saveGameDao.deleteSave(name);
        }

    }

    public void clicked(int selectedIndex, Button load) {

        if (selectedIndex < 1) {
            load.setDisable(true);
        } else {
            load.setDisable(false);
        }

    }
}
