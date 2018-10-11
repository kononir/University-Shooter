package bsuir.vlad.universityshooter.game;

import bsuir.vlad.universityshooter.activeobjects.characters.PlayersView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class Menu {
    private Stage primaryStage;

    public Menu(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
    }

    public void createMainMenu() {
        Button newGameButton = new Button("New Game");
        newGameButton.setId("mainMenuElement");
        newGameButton.setOnAction(newGameEvent -> {
            createNewProfileScene();
        });

        Button leadersTableButton = new Button("Leaders Table");
        leadersTableButton.setId("mainMenuElement");
        leadersTableButton.setOnAction(leadersTableEvent -> {
            createLeadersTableScene();
        });

        Button exitButton = new Button("Exit");
        exitButton.setId("mainMenuElement");
        exitButton.setOnAction(exitEvent -> Platform.exit());

        VBox mainMenuButtons = new VBox();
        mainMenuButtons.getChildren().addAll(newGameButton, leadersTableButton, exitButton);
        double spaceBetweenButtons = 10;
        mainMenuButtons.setSpacing(spaceBetweenButtons);

        AnchorPane mainMenu = new AnchorPane(mainMenuButtons);

        int mainMenuWidth = 280;
        int mainMenuHeight = 200;
        mainMenu.setPrefSize(mainMenuWidth, mainMenuHeight);

        double buttonsLeft = 30;
        double buttonsTop = 10;
        AnchorPane.setTopAnchor(mainMenuButtons, buttonsTop);
        AnchorPane.setLeftAnchor(mainMenuButtons, buttonsLeft);

        Scene scene = new Scene(mainMenu);
        scene.getStylesheets().add("css/styles.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createNewProfileScene() {
        Label creatingProfileLabel = new Label("Creating profile");
        creatingProfileLabel.setId("startMenuElement");

        double spaceBetweenTopBorderAndCreatingProfileLabel = 30.0;
        double spaceBetweenLeftBorderAndCreatingProfileLabel = 265.0;
        AnchorPane.setLeftAnchor(creatingProfileLabel, spaceBetweenLeftBorderAndCreatingProfileLabel);
        AnchorPane.setTopAnchor(creatingProfileLabel, spaceBetweenTopBorderAndCreatingProfileLabel);

        TextField profileNameTextField = new TextField("Player");
        profileNameTextField.setId("startMenuElement");

        double spaceBetweenTopBorderAndProfileNameTextField = 200.0;
        double spaceBetweenLeftBorderAndProfileNameTextField = 225.0;
        AnchorPane.setLeftAnchor(profileNameTextField, spaceBetweenLeftBorderAndProfileNameTextField);
        AnchorPane.setTopAnchor(profileNameTextField, spaceBetweenTopBorderAndProfileNameTextField);

        ComboBox<String> difficultyChangingComboBox = new ComboBox<>();
        difficultyChangingComboBox.setId("startMenuElement");
        ObservableList<String> difficultyList = FXCollections.observableArrayList(
                "easy", "medium", "hard"
        );
        difficultyChangingComboBox.setItems(difficultyList);
        difficultyChangingComboBox.setValue("medium");

        double spaceBetweenBorderAndDifficultyChanging = 265.0;
        AnchorPane.setLeftAnchor(difficultyChangingComboBox, spaceBetweenBorderAndDifficultyChanging);
        AnchorPane.setTopAnchor(difficultyChangingComboBox, spaceBetweenBorderAndDifficultyChanging);

        double spaceBetweenBorderAndButtons = 30.0;

        Button cancelButton = new Button("Cancel");
        cancelButton.setId("startMenuElement");
        cancelButton.setOnAction(cancelEvent -> {
            createMainMenu();
        });
        AnchorPane.setLeftAnchor(cancelButton, spaceBetweenBorderAndButtons);
        AnchorPane.setBottomAnchor(cancelButton, spaceBetweenBorderAndButtons);

        Button startButton = new Button("Start");
        startButton.setId("startMenuElement");
        startButton.setOnAction(startEvent -> {
            String difficulty = difficultyChangingComboBox.getValue();
            String profileName = profileNameTextField.getText();

            startNewGame(difficulty, profileName);
        });
        AnchorPane.setRightAnchor(startButton, spaceBetweenBorderAndButtons);
        AnchorPane.setBottomAnchor(startButton, spaceBetweenBorderAndButtons);

        AnchorPane newGamePane = new AnchorPane(
                creatingProfileLabel,
                profileNameTextField,
                difficultyChangingComboBox,
                cancelButton,
                startButton
        );
        int PaneWidth = 600;
        int PaneHeight = 400;
        newGamePane.setPrefSize(PaneWidth, PaneHeight);

        Scene scene = new Scene(newGamePane);
        scene.getStylesheets().add("css/styles.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startNewGame(String difficultyString, String profileName) {
        Difficulty difficulty = Difficulty.valueOf(difficultyString.toUpperCase());
        Profile profile = new Profile(profileName, difficulty);

        Level level = new Level(this, profile);

        GameSpace gameSpace = level.getGameSpace();
        Scene gameSpaceScene = gameSpace.getScene();

        primaryStage.setOnCloseRequest(closeEvent -> {
            PlayersView playersView = gameSpace.getPlayersView();
            Pane playersPane = playersView.getCharacterPane();
            playersPane.setVisible(false);
        });

        primaryStage.setScene(gameSpaceScene);
        primaryStage.show();
    }

    public final void createGameOverScene(Profile profile) {
        Stage gameOverStage = new Stage();
        gameOverStage.setOnCloseRequest(closeEvent -> {
            gameOverStage.close();
            createMainMenu();
        });

        Label gameOverLabel = new Label("Game Over!");
        gameOverLabel.setId("gameOverLabelText");

        double gameOverLabelLeft = 50;
        double gameOverLabelTop = 10;
        AnchorPane.setLeftAnchor(gameOverLabel, gameOverLabelLeft);
        AnchorPane.setTopAnchor(gameOverLabel, gameOverLabelTop);

        Label totalScoreLabel = new Label("Total Score:");
        totalScoreLabel.setId("scoreLabelText");

        double totalScoreLabelLeft = 30;
        double totalScoreLabelTop = 35;
        AnchorPane.setLeftAnchor(totalScoreLabel, totalScoreLabelLeft);
        AnchorPane.setTopAnchor(totalScoreLabel, totalScoreLabelTop);

        ProfileController profileController = new ProfileController(profile);
        long score = profileController.controlGettingScore();

        Label totalScoreResultLabel = new Label(String.valueOf(score));
        totalScoreResultLabel.setId("scoreLabelText");

        double totalScoreResultLabelRight = 30;
        double totalScoreResultLabelTop = 35;
        AnchorPane.setRightAnchor(totalScoreResultLabel, totalScoreResultLabelRight);
        AnchorPane.setTopAnchor(totalScoreResultLabel, totalScoreResultLabelTop);

        Button saveButton = new Button("Save Score");
        saveButton.setOnAction(backToMainMenuEvent -> {
            String filePath = "src/bsuir/vlad/universityshooter/resources/profiles.xml";
            ProfileFile profileFile = new ProfileFile(filePath);
            profileFile.modify(profile);

            gameOverStage.close();
            createMainMenu();
        });

        double saveButtonLeft = 10;
        double saveButtonTop = 60;
        AnchorPane.setLeftAnchor(saveButton, saveButtonLeft);
        AnchorPane.setTopAnchor(saveButton, saveButtonTop);

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(backToMainMenuEvent -> {
            gameOverStage.close();
            createMainMenu();
        });

        double continueButtonRight = 10;
        double continueButtonTop = 60;
        AnchorPane.setRightAnchor(continueButton, continueButtonRight);
        AnchorPane.setTopAnchor(continueButton, continueButtonTop);

        AnchorPane gameOverPane = new AnchorPane(
                gameOverLabel,
                totalScoreLabel,
                totalScoreResultLabel,
                saveButton,
                continueButton
        );
        double paneWidth = 200;
        double paneHeight = 100;
        gameOverPane.setPrefSize(paneWidth, paneHeight);

        Scene gameOverScene = new Scene(gameOverPane);
        gameOverScene.getStylesheets().add("css/styles.css");

        gameOverStage.setScene(gameOverScene);
        gameOverStage.show();
    }

    private void createLeadersTableScene() {
        TableView<Profile> scoreTable = new TableView<>();

        TableColumn<Profile, String> name = new TableColumn<>("Profile name");
        TableColumn<Profile, Difficulty> difficulty = new TableColumn<>("Difficulty");
        TableColumn<Profile, Long> score = new TableColumn<>("Score");

        scoreTable.getColumns().addAll(name, difficulty, score);

        for (TableColumn tc : scoreTable.getColumns()) {
            double minWidth = 200;
            tc.setMinWidth(minWidth);
        }

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        difficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        String filePath = "src/bsuir/vlad/universityshooter/resources/profiles.xml";
        ProfileFile profileFile = new ProfileFile(filePath);

        List<Profile> profileList = profileFile.load();
        ObservableList<Profile> profileObservableList = FXCollections.observableList(profileList);

        scoreTable.setItems(profileObservableList);

        double tableBottom = 45;
        AnchorPane.setBottomAnchor(scoreTable, tableBottom);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(cancelEvent -> createMainMenu());

        double buttonRight = 260;
        double buttonBottom = 10;
        AnchorPane.setRightAnchor(cancelButton, buttonRight);
        AnchorPane.setBottomAnchor(cancelButton, buttonBottom);

        AnchorPane pane = new AnchorPane(scoreTable, cancelButton);

        Scene leadersTableScene = new Scene(pane);

        primaryStage.setScene(leadersTableScene);
        primaryStage.show();
    }
}
