package bsuir.vlad.universityshooter.game.windows;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuWindow {
    public MainMenuWindow(Stage stage) {
        Button newGameButton = new Button("New Game");
        newGameButton.setId("mainMenuElement");
        newGameButton.setOnAction(newGameEvent -> {
            stage.close();

            new CreateProfileWindow(stage);
        });

        Button leadersTableButton = new Button("Leaders Table");
        leadersTableButton.setId("mainMenuElement");
        leadersTableButton.setOnAction(leadersTableEvent -> {
            stage.close();

            new LeadersTableWindow(stage);
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
        int mainMenuHeight = 210;
        mainMenu.setPrefSize(mainMenuWidth, mainMenuHeight);

        double buttonsLeft = 30;
        double buttonsTop = 10;
        AnchorPane.setTopAnchor(mainMenuButtons, buttonsTop);
        AnchorPane.setLeftAnchor(mainMenuButtons, buttonsLeft);

        Scene scene = new Scene(mainMenu);
        scene.getStylesheets().add("css/styles.css");

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
