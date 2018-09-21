package bsuir.vlad.universityshooter.application;

import javafx.animation.RotateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PlayersView {
    private Player player;
    private Pane playersPane;
    private double playersPaneAngle;
    private ImageView playersImageView;
    private SpriteAnimation playersAnimation;
    private ImageViewMap imageViewMap;
    private KeysMap keysMap;

    public PlayersView(Player player, Scene scene) {
        this.player = player;

        imageViewMap = new ImageViewMap();
        imageViewMap.fillImageViewMap();

        playersPane = new Pane();
        playersPaneAngle = 0;

        playersImageView = imageViewMap.get("student_idle_knife");
        updatePlayersPane();
        updatePlayersAnimation();

        keysMap = new KeysMap();
        KeysController keysController = new KeysController(keysMap);
        keysController.controllOnScene(scene);
    }

    private void updatePlayersPane() {
        playersPane.getChildren().clear();
        playersPane.getChildren().add(playersImageView);
    }

    private void updatePlayersAnimation() {
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

    public final Pane getPlayersPane() {
        return playersPane;
    }

    public final void updatePlayersView() {
        if (keysMap.isPressed(KeyCode.DIGIT1)) {
            String weaponType = "knife";
            updateCurrentWeaponView(weaponType);
        } else if(keysMap.isPressed(KeyCode.DIGIT2)){
            String weaponType = "handgun";
            updateCurrentWeaponView(weaponType);
        } else if(keysMap.isPressed(KeyCode.DIGIT3)){
            String weaponType = "rifle";
            updateCurrentWeaponView(weaponType);
        } else if(keysMap.isPressed(KeyCode.DIGIT4)){
            String weaponType = "shotgun";
            updateCurrentWeaponView(weaponType);
        }

        PlayersController controller = new PlayersController(player);
        String typeOfWeaponInHands = controller.controlTypeOfWeaponInHands();

        if (keysMap.isPressed(KeyCode.UP) && keysMap.isPressed(KeyCode.LEFT)) {
            updateCurrentAction("student_move_" + typeOfWeaponInHands);

            playersAnimation.play();

            double currentMovementAngle = 225;
            updateMovementAngle(currentMovementAngle);

            int movementX = 3;
            int movementY = 3;

            moveUp(movementY);
            moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.UP) && keysMap.isPressed(KeyCode.RIGHT)) {
            updateCurrentAction("student_move_" + typeOfWeaponInHands);

            playersAnimation.play();

            double currentMovementAngle = 315;
            updateMovementAngle(currentMovementAngle);

            int movementX = 3;
            int movementY = 3;

            moveUp(movementY);
            moveRight(movementX);
        } else if (keysMap.isPressed(KeyCode.DOWN) && keysMap.isPressed(KeyCode.LEFT)) {
            updateCurrentAction("student_move_" + typeOfWeaponInHands);

            playersAnimation.play();

            double currentMovementAngle = 135;
            updateMovementAngle(currentMovementAngle);

            int movementX = 3;
            int movementY = 3;

            moveDown(movementY);
            moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.DOWN) && keysMap.isPressed(KeyCode.RIGHT)) {
            updateCurrentAction("student_move_" + typeOfWeaponInHands);

            playersAnimation.play();

            double currentMovementAngle = 45;
            updateMovementAngle(currentMovementAngle);

            int movementX = 3;
            int movementY = 3;

            moveDown(movementY);
            moveRight(movementX);
        } else if (keysMap.isPressed(KeyCode.UP)) {
            updateCurrentAction("student_move_" + typeOfWeaponInHands);

            playersAnimation.play();

            double currentMovementAngle = 270;
            updateMovementAngle(currentMovementAngle);

            int movementY = 3;

            moveUp(movementY);
        } else if (keysMap.isPressed(KeyCode.DOWN)) {
            updateCurrentAction("student_move_" + typeOfWeaponInHands);

            playersAnimation.play();

            double currentMovementAngle = 90;
            updateMovementAngle(currentMovementAngle);

            int movementY = 3;

            moveDown(movementY);
        } else if (keysMap.isPressed(KeyCode.LEFT)) {
            updateCurrentAction("student_move_" + typeOfWeaponInHands);

            playersAnimation.play();

            double currentMovementAngle = 180;
            updateMovementAngle(currentMovementAngle);

            int movementX = 3;

            moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.RIGHT)) {
            updateCurrentAction("student_move_" + typeOfWeaponInHands);

            playersAnimation.play();

            double currentMovementAngle = 0;
            updateMovementAngle(currentMovementAngle);

            int movementX = 3;

            moveRight(movementX);
        } else {
            updateCurrentAction("student_idle_" + typeOfWeaponInHands);

            playersAnimation.play();
        }
    }

    private void updateCurrentWeaponView(String weaponType){
        PlayersController playersController = new PlayersController(player);
        boolean weaponExisting = playersController.controlChangingWeapon(weaponType);

        if (weaponExisting) {
            updateCurrentAction("student_idle_" + weaponType);
        }
    }

    private void updateCurrentAction(String nameOfAction) {
        ImageView imageView = imageViewMap.get(nameOfAction);

        if (!playersImageView.equals(imageView)) {
            playersImageView = imageView;
            updatePlayersPane();
            updatePlayersAnimation();
        }
    }

    private void updateMovementAngle(double currentMovementAngle) {
        if (playersPaneAngle != currentMovementAngle) {
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(1), playersPane);
            rotateTransition.setToAngle(currentMovementAngle);
            rotateTransition.play();

            playersPaneAngle = currentMovementAngle;
        }
    }

    private void moveLeft(int movementX) {
        if (isOpportunityToMoveLeft()) {
            int newPaneX = (int) playersPane.getTranslateX() - movementX;
            playersPane.setTranslateX(newPaneX);
        }
    }

    private void moveRight(int movementX) {
        if (isOpportunityToMoveRight()) {
            int newPaneX = (int) playersPane.getTranslateX() + movementX;
            playersPane.setTranslateX(newPaneX);
        }
    }

    private void moveUp(int movementY) {
        if (isOpportunityToMoveUp()) {
            int newPaneY = (int) playersPane.getTranslateY() - movementY;
            playersPane.setTranslateY(newPaneY);
        }
    }

    private void moveDown(int movementY) {
        if (isOpportunityToMoveDown()) {
            int newPaneY = (int) playersPane.getTranslateY() + movementY;
            playersPane.setTranslateY(newPaneY);
        }
    }

    private boolean isOpportunityToMoveLeft() {
        double playerTranslateX = playersPane.getTranslateX();
        double minPlayerTranslateX = 0.0;

        return playerTranslateX > minPlayerTranslateX;
    }

    private boolean isOpportunityToMoveRight() {
        double playerTranslateX = playersPane.getTranslateX();

        AnchorPane pane = (AnchorPane) playersPane.getParent();
        double maxPaneX = pane.getPrefWidth();
        double animationFrameWidth = (double) playersAnimation.getAnimationFrameWidth();

        double maxPlayerTranslateX = maxPaneX - animationFrameWidth;

        return playerTranslateX < maxPlayerTranslateX;
    }

    private boolean isOpportunityToMoveUp() {
        double playerLayoutY = playersPane.getTranslateY();
        double minPlayerTranslateY = 0.0;

        return playerLayoutY > minPlayerTranslateY;
    }

    private boolean isOpportunityToMoveDown() {
        double playerLayoutY = playersPane.getTranslateY();

        AnchorPane pane = (AnchorPane) playersPane.getParent();
        double maxPaneY = pane.getPrefHeight();
        double animationFrameHeight = (double) playersAnimation.getAnimationFrameHeight();

        double maxPlayerTranslateY = maxPaneY - animationFrameHeight;

        return playerLayoutY < maxPlayerTranslateY;
    }
}
