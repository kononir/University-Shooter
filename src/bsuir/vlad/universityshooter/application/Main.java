package bsuir.vlad.universityshooter.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Button newGameButton = new Button("New Game");
        newGameButton.setId("mainMenuElement");
        newGameButton.setOnAction(newGameEvent -> {
            primaryStage.close();
            Stage newGameStage = new Stage();

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
                newGameStage.close();
                primaryStage.show();
            });
            AnchorPane.setLeftAnchor(cancelButton, spaceBetweenBorderAndButtons);
            AnchorPane.setBottomAnchor(cancelButton, spaceBetweenBorderAndButtons);

            Button startButton = new Button("Start");
            startButton.setId("startMenuElement");
            startButton.setOnAction(startEvent -> {
                newGameStage.close();
                Stage startStage = new Stage();

                String difficulty = difficultyChangingComboBox.getValue();
                String name = profileNameTextField.getText();

                Profile newProfile = new Profile(name, difficulty);

                GameSpace gameSpace = new GameSpace(newProfile);

                Scene gameSpaceScene = gameSpace.getScene();

                startStage.setScene(gameSpaceScene);
                startStage.setResizable(false);
                startStage.show();
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

            newGameStage.setScene(scene);
            newGameStage.setResizable(false);
            newGameStage.show();
        });
        Button loadButton = new Button("Load");
        loadButton.setId("mainMenuElement");

        Button saveButton = new Button("Save");
        saveButton.setId("mainMenuElement");

        Button optionsButton = new Button("Options");
        optionsButton.setId("mainMenuElement");

        Button exitButton = new Button("Exit");
        exitButton.setId("mainMenuElement");
        exitButton.setOnAction(exitEvent -> Platform.exit());

        VBox mainMenuButtons = new VBox();
        mainMenuButtons.getChildren().addAll(newGameButton, loadButton, saveButton, optionsButton, exitButton);
        double spaceBetweenButtons = 10.0;
        mainMenuButtons.setSpacing(spaceBetweenButtons);

        AnchorPane mainMenu = new AnchorPane(mainMenuButtons);

        int mainMenuWidth = 600;
        int mainMenuHeight = 400;
        mainMenu.setPrefSize(mainMenuWidth, mainMenuHeight);

        double spaceBetweenBorderAndButtons = 30.0;
        AnchorPane.setTopAnchor(mainMenuButtons, spaceBetweenBorderAndButtons);
        AnchorPane.setLeftAnchor(mainMenuButtons, spaceBetweenBorderAndButtons);

        Scene scene = new Scene(mainMenu);
        scene.getStylesheets().add("css/styles.css");

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
