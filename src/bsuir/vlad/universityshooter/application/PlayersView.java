package bsuir.vlad.universityshooter.application;

import javafx.animation.RotateTransition;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.HashMap;

public class PlayersView implements Movable {
    private Player player;
    private Pane playersPane;
    private double playersPaneAngle;
    private SpriteAnimation playersAnimation;
    private HashMap<String, SpriteAnimation> allAnimationsMap;
    private KeysMap keysMap;

    public PlayersView(Player player, Scene scene) {
        this.player = player;

        String playersAnimationsFilePath
                = "src/bsuir/vlad/universityshooter/application/resources/players_animation_characteristics.xml";
        allAnimationsMap = new SpriteAnimationFile(playersAnimationsFilePath).loadAnimations();

        playersPane = new Pane();
        playersPaneAngle = 0;

        updatePlayersAnimation("student_idle_knife");

        keysMap = new KeysMap();
        KeysController keysController = new KeysController(keysMap);
        keysController.controlOnScene(scene);
    }

    private void updatePlayersAnimation(String animationName) {
        SpriteAnimation updatingAnimation = allAnimationsMap.get(animationName);

        if (!updatingAnimation.equals(playersAnimation)) {
            playersAnimation = updatingAnimation;

            ImageView playersImageView = updatingAnimation.getSpriteImageView();
            playersPane.getChildren().clear();
            playersPane.getChildren().add(playersImageView);
        }
    }

    public final Pane getPlayersPane() {
        return playersPane;
    }

    public final void updatePlayersView() {
        if (keysMap.isPressed(KeyCode.DIGIT1)) {
            String weaponType = "knife";
            updateCurrentWeaponView(weaponType);
        } else if (keysMap.isPressed(KeyCode.DIGIT2)) {
            String weaponType = "handgun";
            updateCurrentWeaponView(weaponType);
        } else if (keysMap.isPressed(KeyCode.DIGIT3)) {
            String weaponType = "rifle";
            updateCurrentWeaponView(weaponType);
        } else if (keysMap.isPressed(KeyCode.DIGIT4)) {
            String weaponType = "shotgun";
            updateCurrentWeaponView(weaponType);
        }

        PlayersController controller = new PlayersController(player);
        String typeOfWeaponInHands = controller.controlTypeOfWeaponInHands();

        int movementX = 3;
        int movementY = 3;

        if (keysMap.isPressed(KeyCode.UP) && keysMap.isPressed(KeyCode.LEFT)) {
            if (!playersAnimation.isLock()) {
                updatePlayersAnimation("student_move_" + typeOfWeaponInHands);

                playersAnimation.play();
            }

            double currentMovementAngle = 225;
            updateMovementAngle(currentMovementAngle);

            moveUp(movementY);
            moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.UP) && keysMap.isPressed(KeyCode.RIGHT)) {
            if (!playersAnimation.isLock()) {
                updatePlayersAnimation("student_move_" + typeOfWeaponInHands);

                playersAnimation.play();
            }

            double currentMovementAngle = 315;
            updateMovementAngle(currentMovementAngle);

            moveUp(movementY);
            moveRight(movementX);
        } else if (keysMap.isPressed(KeyCode.DOWN) && keysMap.isPressed(KeyCode.LEFT)) {
            if (!playersAnimation.isLock()) {
                updatePlayersAnimation("student_move_" + typeOfWeaponInHands);

                playersAnimation.play();
            }

            double currentMovementAngle = 135;
            updateMovementAngle(currentMovementAngle);

            moveDown(movementY);
            moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.DOWN) && keysMap.isPressed(KeyCode.RIGHT)) {
            if (!playersAnimation.isLock()) {
                updatePlayersAnimation("student_move_" + typeOfWeaponInHands);

                playersAnimation.play();
            }

            double currentMovementAngle = 45;
            updateMovementAngle(currentMovementAngle);

            moveDown(movementY);
            moveRight(movementX);
        } else if (keysMap.isPressed(KeyCode.UP)) {
            if (!playersAnimation.isLock()) {
                updatePlayersAnimation("student_move_" + typeOfWeaponInHands);

                playersAnimation.play();
            }

            double currentMovementAngle = 270;
            updateMovementAngle(currentMovementAngle);

            moveUp(movementY);
        } else if (keysMap.isPressed(KeyCode.DOWN)) {
            if (!playersAnimation.isLock()) {
                updatePlayersAnimation("student_move_" + typeOfWeaponInHands);

                playersAnimation.play();
            }

            double currentMovementAngle = 90;
            updateMovementAngle(currentMovementAngle);

            moveDown(movementY);
        } else if (keysMap.isPressed(KeyCode.LEFT)) {
            if (!playersAnimation.isLock()) {
                updatePlayersAnimation("student_move_" + typeOfWeaponInHands);

                playersAnimation.play();
            }

            double currentMovementAngle = 180;
            updateMovementAngle(currentMovementAngle);

            moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.RIGHT)) {
            if (!playersAnimation.isLock()) {
                updatePlayersAnimation("student_move_" + typeOfWeaponInHands);

                playersAnimation.play();
            }

            double currentMovementAngle = 0;
            updateMovementAngle(currentMovementAngle);

            moveRight(movementX);
        } else if (!playersAnimation.isLock()) {
            updatePlayersAnimation("student_idle_" + typeOfWeaponInHands);

            playersAnimation.play();
        }

        if (keysMap.isPressed(KeyCode.E) && !playersAnimation.isLock()) {
            updatePlayersAnimation("student_use_" + typeOfWeaponInHands);

            playersAnimation.lock();
            playersAnimation.play();

            if(!typeOfWeaponInHands.equals("knife")) {
                Bullet bullet = controller.controlShooting();
                double bulletsPaneAngle = playersPaneAngle;
                GameSpace.addNewBullet(bullet, bulletsPaneAngle);
            }
        }
    }

    private void updateCurrentWeaponView(String weaponType) {
        PlayersController playersController = new PlayersController(player);
        boolean weaponExisting = playersController.controlChangingWeapon(weaponType);

        if (weaponExisting) {
            updatePlayersAnimation("student_idle_" + weaponType);
        }
    }

    public void updateMovementAngle(double currentMovementAngle) {
        if (playersPaneAngle != currentMovementAngle) {
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(1), playersPane);
            rotateTransition.setToAngle(currentMovementAngle);
            rotateTransition.play();

            playersPaneAngle = currentMovementAngle;
        }
    }

    @Override
    public void moveLeft(int movementX) {
        if (isOpportunityToMoveLeft()) {
            int newPaneX = (int) playersPane.getTranslateX() - movementX;
            playersPane.setTranslateX(newPaneX);
        }
    }

    @Override
    public void moveRight(int movementX) {
        if (isOpportunityToMoveRight()) {
            int newPaneX = (int) playersPane.getTranslateX() + movementX;
            playersPane.setTranslateX(newPaneX);
        }
    }

    @Override
    public void moveUp(int movementY) {
        if (isOpportunityToMoveUp()) {
            int newPaneY = (int) playersPane.getTranslateY() - movementY;
            playersPane.setTranslateY(newPaneY);
        }
    }

    @Override
    public void moveDown(int movementY) {
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

        Pane pane = (Pane) playersPane.getParent();
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

        Pane pane = (Pane) playersPane.getParent();
        double maxPaneY = pane.getPrefHeight();
        double animationFrameHeight = (double) playersAnimation.getAnimationFrameHeight();

        double maxPlayerTranslateY = maxPaneY - animationFrameHeight;

        return playerLayoutY < maxPlayerTranslateY;
    }
}
