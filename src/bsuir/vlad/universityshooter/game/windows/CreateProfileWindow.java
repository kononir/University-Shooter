package bsuir.vlad.universityshooter.game.windows;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

class CreateProfileWindow {
    CreateProfileWindow(Stage stage) {
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
            stage.close();

            new MainMenuWindow(stage);
        });
        AnchorPane.setLeftAnchor(cancelButton, spaceBetweenBorderAndButtons);
        AnchorPane.setBottomAnchor(cancelButton, spaceBetweenBorderAndButtons);

        Button startButton = new Button("Start");
        startButton.setId("startMenuElement");
        startButton.setOnAction(startEvent -> {
            String difficulty = difficultyChangingComboBox.getValue();
            String profileName = profileNameTextField.getText();

            stage.close();
            new GameSpaceWindow(stage, difficulty, profileName);
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

        stage.setScene(scene);
        stage.show();
    }
}
