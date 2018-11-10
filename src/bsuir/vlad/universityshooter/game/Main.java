package bsuir.vlad.universityshooter.game;

import bsuir.vlad.universityshooter.game.windows.MainMenuWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        new MainMenuWindow(primaryStage);
    }
}
