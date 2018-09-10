import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){

        Button newGameButton = new Button("New Game");
        newGameButton.setId("mainMenuButton");
        newGameButton.setOnAction(event -> {

            ComboBox<String> difficultyChangingComboBox = new ComboBox<>();
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
            AnchorPane.setLeftAnchor(cancelButton, spaceBetweenBorderAndButtons);
            AnchorPane.setBottomAnchor(cancelButton, spaceBetweenBorderAndButtons);

            Button startButton = new Button("Start");
            startButton.setOnAction(startEvent -> {
                AnchorPane startPane = new AnchorPane();
                int PaneWidth = 600;
                int PaneHeight = 400;
                startPane.setPrefSize(PaneWidth, PaneHeight);

                Image playersImage = new Image(getClass().getResourceAsStream("resourses/student_move_knife.png"));
                ImageView playersImageView = new ImageView(playersImage);
                Player player = new Player(playersImageView);

                Pane playersPane = player.getPlayersPane();
                startPane.getChildren().add(playersPane);

                Scene scene = new Scene(startPane);

                HashMap<KeyCode, Boolean> keysMap = new HashMap<>();
                KeysController keysController = new KeysController(keysMap);
                keysController.controllOnScene(scene);

                AnimationTimer animTimer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        update(keysMap, player);
                    }
                };

                animTimer.start();

                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();
            });
            AnchorPane.setRightAnchor(startButton, spaceBetweenBorderAndButtons);
            AnchorPane.setBottomAnchor(startButton, spaceBetweenBorderAndButtons);

            AnchorPane newGamePane = new AnchorPane(difficultyChangingComboBox, cancelButton, startButton);
            int PaneWidth = 600;
            int PaneHeight = 400;
            newGamePane.setPrefSize(PaneWidth, PaneHeight);

            Scene scene = new Scene(newGamePane);

            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        });
        Button loadButton = new Button("Load");
        loadButton.setId("mainMenuButton");
        Button saveButton = new Button("Save");
        saveButton.setId("mainMenuButton");
        Button exitButton = new Button("Exit");
        exitButton.setId("mainMenuButton");

        VBox mainMenuButtons = new VBox();
        mainMenuButtons.getChildren().addAll(newGameButton, loadButton, saveButton, exitButton);
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

    private void update(HashMap<KeyCode, Boolean> keysMap, Player player){
        Pane playersPane = player.getPlayersPane();

        if(isPressed(keysMap, KeyCode.UP)){
            SpriteAnimation playersSpriteAnimation = player.getSpriteAnimation();
            playersSpriteAnimation.play();

            int newPaneY = (int)playersPane.getTranslateY() - 3;
            playersPane.setTranslateY(newPaneY);
        } else if(isPressed(keysMap, KeyCode.DOWN)){
            SpriteAnimation playersSpriteAnimation = player.getSpriteAnimation();
            playersSpriteAnimation.play();

            int newPaneY = (int)playersPane.getTranslateY() + 3;
            playersPane.setTranslateY(newPaneY);
        } else if(isPressed(keysMap, KeyCode.LEFT)){
            SpriteAnimation playersSpriteAnimation = player.getSpriteAnimation();
            playersSpriteAnimation.play();

            int newPaneX = (int)playersPane.getTranslateX() - 3;
            playersPane.setTranslateX(newPaneX);
        } else if(isPressed(keysMap, KeyCode.RIGHT)){
            SpriteAnimation playersSpriteAnimation = player.getSpriteAnimation();
            playersSpriteAnimation.play();

            int newPaneX = (int)playersPane.getTranslateX() + 3;
            playersPane.setTranslateX(newPaneX);
        }
        else{
            SpriteAnimation playersSpriteAnimation = player.getSpriteAnimation();
            playersSpriteAnimation.stop();
        }
    }

    private boolean isPressed(HashMap<KeyCode, Boolean> keysMap, KeyCode keyCode){
        return keysMap.getOrDefault(keyCode, false);
    }
}
