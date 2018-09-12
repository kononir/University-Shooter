import javafx.animation.RotateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.HashMap;

public class PlayerView {
    private Pane playersPane;
    private double playersPaneAngle;
    private ImageView playersImageView;
    private SpriteAnimation playersAnimation;
    private ImageViewMap imageViewMap;
    private HashMap<KeyCode, Boolean> keysMap;

    public PlayerView(Scene scene){
        imageViewMap = new ImageViewMap();
        imageViewMap.fillImageViewMap();

        playersPane = new Pane();
        playersPaneAngle = 0;

        playersImageView = imageViewMap.get("student_idle_knife");
        updatePlayersPane();
        updatePlayersAnimation();

        keysMap = new HashMap<>();
        KeysController keysController = new KeysController(keysMap);
        keysController.controllOnScene(scene);
    }

    private void updatePlayersPane(){
        playersPane.getChildren().clear();
        playersPane.getChildren().add(playersImageView);
    }

    private void updatePlayersAnimation(){
        int countOfAnimationFrames = 20;
        int animationFrameHeight = 55;
        int animationFrameWidth = 70;
        int offsetX = 0;
        int offsetY = 0;

        playersImageView.setViewport(new Rectangle2D(offsetX, offsetY, animationFrameWidth, animationFrameHeight));

        playersAnimation = new SpriteAnimation(
                playersImageView,
                Duration.millis(800.0),
                countOfAnimationFrames,
                animationFrameHeight,
                animationFrameWidth,
                offsetX,
                offsetY
        );
    }

    public final Pane getPlayersPane(){
        return playersPane;
    }

    public final void updatePlayersView(){
        if(isPressed(KeyCode.UP) && isPressed(KeyCode.LEFT)){
            updateCurrentAction("student_move_knife");

            playersAnimation.play();

            double currentMovementAngle = 225;
            updateCurrentAngle(currentMovementAngle);

            if(isOpportunityToMoveUp() && isOpportunityToMoveLeft()){
                int newPaneY = (int)playersPane.getTranslateY() - 3;
                int newPaneX = (int)playersPane.getTranslateX() - 3;

                playersPane.setTranslateY(newPaneY);
                playersPane.setTranslateX(newPaneX);
            }
        } else if(isPressed(KeyCode.UP) && isPressed(KeyCode.RIGHT)){
            updateCurrentAction("student_move_knife");

            playersAnimation.play();

            double currentMovementAngle = 315;
            updateCurrentAngle(currentMovementAngle);

            if(isOpportunityToMoveUp() && isOpportunityToMoveRight()){
                int newPaneY = (int)playersPane.getTranslateY() - 3;
                int newPaneX = (int)playersPane.getTranslateX() + 3;

                playersPane.setTranslateY(newPaneY);
                playersPane.setTranslateX(newPaneX);
            }
        } else if(isPressed(KeyCode.DOWN) && isPressed(KeyCode.LEFT)){
            updateCurrentAction("student_move_knife");

            playersAnimation.play();

            double currentMovementAngle = 135;
            updateCurrentAngle(currentMovementAngle);

            if(isOpportunityToMoveDown() && isOpportunityToMoveLeft()){
                int newPaneY = (int)playersPane.getTranslateY() + 3;
                int newPaneX = (int)playersPane.getTranslateX() - 3;

                playersPane.setTranslateY(newPaneY);
                playersPane.setTranslateX(newPaneX);
            }
        } else if(isPressed(KeyCode.DOWN) && isPressed(KeyCode.RIGHT)){
            updateCurrentAction("student_move_knife");

            playersAnimation.play();

            double currentMovementAngle = 45;
            updateCurrentAngle(currentMovementAngle);

            if(isOpportunityToMoveDown() && isOpportunityToMoveRight()){
                int newPaneY = (int)playersPane.getTranslateY() + 3;
                int newPaneX = (int)playersPane.getTranslateX() + 3;

                playersPane.setTranslateY(newPaneY);
                playersPane.setTranslateX(newPaneX);
            }
        } else if(isPressed(KeyCode.UP)){
            updateCurrentAction("student_move_knife");

            playersAnimation.play();

            double currentMovementAngle = 270;
            updateCurrentAngle(currentMovementAngle);

            if(isOpportunityToMoveUp()){
                int newPaneY = (int)playersPane.getTranslateY() - 3;
                playersPane.setTranslateY(newPaneY);
            }
        } else if(isPressed(KeyCode.DOWN)){
            updateCurrentAction("student_move_knife");

            playersAnimation.play();

            double currentMovementAngle = 90;
            updateCurrentAngle(currentMovementAngle);

            if(isOpportunityToMoveDown()){
                int newPaneY = (int)playersPane.getTranslateY() + 3;
                playersPane.setTranslateY(newPaneY);
            }
        } else if(isPressed(KeyCode.LEFT)){
            updateCurrentAction("student_move_knife");

            playersAnimation.play();

            double currentMovementAngle = 180;
            updateCurrentAngle(currentMovementAngle);

            if(isOpportunityToMoveLeft()){
                int newPaneX = (int)playersPane.getTranslateX() - 3;
                playersPane.setTranslateX(newPaneX);
            }
        } else if(isPressed(KeyCode.RIGHT)){
            updateCurrentAction("student_move_knife");

            playersAnimation.play();

            double currentMovementAngle = 0;
            updateCurrentAngle(currentMovementAngle);

            if(isOpportunityToMoveRight()){
                int newPaneX = (int)playersPane.getTranslateX() + 3;
                playersPane.setTranslateX(newPaneX);
            }
        }
        else{
            updateCurrentAction("student_idle_knife");

            playersAnimation.play();
        }
    }

    private boolean isPressed(KeyCode keyCode){
        return keysMap.getOrDefault(keyCode, false);
    }

    private void updateCurrentAction(String nameOfAction){
        ImageView imageView = imageViewMap.get(nameOfAction);

        if(!playersImageView.equals(imageView)){
            playersImageView = imageView;
            updatePlayersPane();
            updatePlayersAnimation();
        }
    }

    private boolean isOpportunityToMoveLeft(){
        double playerTranslateX = playersPane.getTranslateX();
        double minPlayerTranslateX = 0.0;

        return playerTranslateX > minPlayerTranslateX;
    }

    private boolean isOpportunityToMoveRight(){
        double playerTranslateX = playersPane.getTranslateX();

        AnchorPane pane = (AnchorPane) playersPane.getParent();
        double maxPaneX = pane.getPrefWidth();
        double animationFrameWidth = (double) playersAnimation.getAnimationFrameWidth();

        double maxPlayerTranslateX = maxPaneX - animationFrameWidth;

        return playerTranslateX < maxPlayerTranslateX;
    }

    private boolean isOpportunityToMoveUp(){
        double playerLayoutY = playersPane.getTranslateY();
        double minPlayerTranslateY = 0.0;

        return playerLayoutY > minPlayerTranslateY;
    }

    private boolean isOpportunityToMoveDown(){
        double playerLayoutY = playersPane.getTranslateY();

        AnchorPane pane = (AnchorPane) playersPane.getParent();
        double maxPaneY = pane.getPrefHeight();
        double animationFrameHeight = (double) playersAnimation.getAnimationFrameHeight();

        double maxPlayerTranslateY = maxPaneY - animationFrameHeight;

        return playerLayoutY < maxPlayerTranslateY;
    }

    private void updateCurrentAngle(double currentMovementAngle){
        if(playersPaneAngle != currentMovementAngle){
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(1), playersPane);
            rotateTransition.setToAngle(currentMovementAngle);
            rotateTransition.play();

            playersPaneAngle = currentMovementAngle;
        }
    }

}
