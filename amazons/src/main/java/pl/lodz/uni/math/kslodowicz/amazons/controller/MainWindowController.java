package pl.lodz.uni.math.kslodowicz.amazons.controller;

import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import pl.lodz.uni.math.kslodowicz.amazons.dto.TileDTO;
import pl.lodz.uni.math.kslodowicz.amazons.enums.GameType;
import pl.lodz.uni.math.kslodowicz.amazons.enums.OpponentType;
import pl.lodz.uni.math.kslodowicz.amazons.helper.OrderedImageView;
import pl.lodz.uni.math.kslodowicz.amazons.service.GameService;
import pl.lodz.uni.math.kslodowicz.amazons.service.OptionsService;
import pl.lodz.uni.math.kslodowicz.amazons.start.AmazonsApplication;
import pl.lodz.uni.math.kslodowicz.amazons.utils.ArrayUtils;
import pl.lodz.uni.math.kslodowicz.amazons.utils.StringUtils;
import pl.lodz.uni.math.kslodowicz.amazons.utils.StyleUtils;

public class MainWindowController implements EventHandler<MouseEvent> {
    private static final Logger logger = Logger.getLogger(MainWindowController.class);
    @FXML
    private HBox mainHBox;

    @Autowired
    private NewGameWindowController newGameWindowController;
    @Autowired
    private GameService game;
    @Autowired
    private AmazonsApplication main;
    @Autowired
    private OptionsService optionService;
    @Autowired
    private SaveGameController saveGameController;

    private GridPane pane;
    private Image[] images;
    private OrderedImageView[][] imageTiles;
    private BorderPane rootLayout;
    private TextArea textArea;
    private ImageView actualPlayerImage;
    private VBox rightPanel;
    private Text actualPlayer;
    public MainWindowController() {
        String[] names = { "/images/empty.png", "/images/first.png", "/images/second.png", "/images/removed.png", "/images/move.png" };
        images = new Image[names.length];
        for (int i = 0; i < names.length; i++) {
            InputStream inputStream = MainWindowController.class.getResourceAsStream(names[i]);
            if (inputStream != null) {
                images[i] = new Image(inputStream);
            } else {
                logger.error("Can't load File with name " + names[i]);
            }

        }

    }

    public void setMainApp(AmazonsApplication main) {
        this.main = main;

    }

    public void addText(String text, Object... args) {
        textArea.appendText(String.format(text, args));
    }

    public void showAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle("About");
        alert.setHeaderText("Author: Krzysztof Slodowicz");
        alert.setContentText("If you fing any bugs, please, contact me: kslodowicz@o2.pl.");
        alert.showAndWait();
    }

    public void close() {
        Platform.exit();
    }

    public void newGame() {
        newGameWindowController.show();

    }

    public void loadGame() {
        saveGameController.show();
        update();
    }

    public void drawBoard(int[][] c) {
        // Create board with textArea and PlayArea
        resetBoard(c.length);
        for (int i = 0; i < c.length; i++)
            for (int j = 0; j < c.length; j++)
                imageTiles[j][i].setImage(images[c[j][i]]);
    }

    private void initializeLabels(int size) {
        for (int i = 0; i < size; i++) {
            pane.add(generateLabel(String.valueOf(i)), 0, i + 1);
            pane.add(generateLabel(String.valueOf(i)), size + 1, i + 1);
            pane.add(generateLabel(StringUtils.getChar(i)), i + 1, 0);
            pane.add(generateLabel(StringUtils.getChar(i)), i + 1, size + 1);
        }

    }

    private Label generateLabel(String text) {
        Label c = new Label(text);
        c.setStyle(StyleUtils.LABEL_STYLE);
        GridPane.setHalignment(c, HPos.CENTER);
        return c;
    }

    private void initializeTextArea() {

        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setStyle("-fx-focus-color: transparent;");
        textArea.setFont(Font.font("Tahoma", 16));
    }

    public void updateBoard(int[][] c, List<TileDTO> moves) {
        if (moves != null) {
            for (int i = 0; i < moves.size(); i++) {
                TileDTO temp = moves.get(i);
                c[temp.getX()][temp.getY()] = 4;
            }
        }

        for (int i = 0; i < c.length; i++)
            for (int j = 0; j < c[i].length; j++) {
                imageTiles[j][i].setImage(images[c[j][i]]);
            }

    }

    // Temporary unimplemented
    public void learnAi() {
        optionService.ChangeMonteCarloTime();
    }

    @Override
    public void handle(MouseEvent event) {
        OrderedImageView c = (OrderedImageView) event.getSource();
        TileDTO clicked = new TileDTO(c.getxPosition(), c.getyPosition());
        getGame().clicked(clicked);
        update();
    }

    public void update() {
        pane.requestLayout();
        if (getGame().isChange()) {
            updateBoard(ArrayUtils.copyTable(getGame().getFields()), getGame().getMoves());
            getGame().setChange(false);
        }
        updateActualPlayer();
    }

    private void updateActualPlayer() {
        actualPlayer.setText(String.format("Actual Player: (%s) ", game.getAiType()));
        actualPlayerImage.setImage(images[game.getPlayer()]);

    }

    public GameService getGame() {
        return game;
    }

    public void setGame(GameService game) {
        this.game = game;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public void initializeNewGame(GameType gameType, OpponentType opponentType) {
        game.startGame(gameType, opponentType.getValue());
        drawBoard(game.getFields());
        game.runStartingTasks();
        update();

    }

    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public void resetBoard(int size) {
        if (mainHBox.getChildren().contains(pane)) {
            mainHBox.getChildren().remove(pane);

        }
        if (mainHBox.getChildren().contains(rightPanel)) {
            mainHBox.getChildren().remove(rightPanel);

        }

        imageTiles = new OrderedImageView[size][size];
        pane = new GridPane();
        // Add Tiles to PlayArea
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                imageTiles[j][i] = new OrderedImageView();
                imageTiles[j][i].setOnMouseClicked(this);

                imageTiles[j][i].setxPosition(j);
                imageTiles[j][i].setyPosition(i);
                imageTiles[j][i].setFitHeight((mainHBox.getHeight() - 100) / size);
                imageTiles[j][i].setPreserveRatio(true);
                Pane filler = new Pane();
                GridPane.setFillHeight(filler, true);
                GridPane.setFillWidth(filler, true);
                filler.setStyle(StyleUtils.BACKGROUND_STYLE);
                filler.getChildren().add(imageTiles[j][i]);
                pane.add(filler, j + 1, i + 1);

            }
        initializeLabels(size);
        initializeTextArea();
        mainHBox.getChildren().addAll(pane);
        VBox rightPanel = initializeRightPanel();
        mainHBox.getChildren().addAll(rightPanel);
    }

    private VBox initializeRightPanel() {
        rightPanel = new VBox();
        initializeTextArea();

        Pane indicator = initializeIndicator();
        VBox.setVgrow(textArea, Priority.ALWAYS);
        rightPanel.getChildren().addAll(indicator);
        rightPanel.getChildren().addAll(textArea);
        return rightPanel;
    }

    private Pane initializeIndicator() {
        GridPane pane = new GridPane();
        actualPlayer = new Text();
        actualPlayer.setText(String.format("Actual Player: (%s) ", game.getAiType()));
        actualPlayer.setFont(Font.font("Tahoma", FontWeight.BOLD, 22));
        actualPlayerImage = new ImageView(images[1]);
        actualPlayerImage.setPreserveRatio(true);
        actualPlayerImage.setFitHeight(50);
        pane.add(actualPlayer, 1, 1);
        pane.add(actualPlayerImage, 2, 1);
        
        pane.setStyle("-fx-padding: 10 0 10 0;");
        
        GridPane.setHalignment(actualPlayerImage, HPos.RIGHT);
        // actualPlayerImage.setAlignment(Pos.BASELINE_RIGHT);
        return pane;
    }

}
