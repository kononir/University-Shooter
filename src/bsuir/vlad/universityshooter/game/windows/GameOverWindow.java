package bsuir.vlad.universityshooter.game.windows;

import bsuir.vlad.universityshooter.game.profile.Profile;
import bsuir.vlad.universityshooter.game.profile.ProfileController;
import bsuir.vlad.universityshooter.game.profile.ProfileFile;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

class GameOverWindow {
    GameOverWindow(Stage stage, Profile profile) {
        Stage gameOverStage = new Stage();

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
            String filePath = "/profiles.xml";
            ProfileFile profileFile = new ProfileFile(filePath);
            profileFile.modify(profile);

            gameOverStage.close();

            stage.close();
            new MainMenuWindow(stage);
        });

        double saveButtonLeft = 10;
        double saveButtonTop = 60;
        AnchorPane.setLeftAnchor(saveButton, saveButtonLeft);
        AnchorPane.setTopAnchor(saveButton, saveButtonTop);

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(backToMainMenuEvent -> {
            gameOverStage.close();

            stage.close();
            new MainMenuWindow(stage);
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
        double paneHeight = 80;
        gameOverPane.setPrefSize(paneWidth, paneHeight);

        Scene gameOverScene = new Scene(gameOverPane);
        gameOverScene.getStylesheets().add("css/styles.css");

        stage.setScene(gameOverScene);
        stage.show();
    }
}
