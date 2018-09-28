package bsuir.vlad.universityshooter;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class PlayersView extends Character {
    private Player player;
    private KeysMap keysMap;

    public PlayersView(Player player, Scene scene) {
        super("src/bsuir/vlad/universityshooter/resources/configs/players_animation_characteristics.xml");

        this.player = player;

        updateAnimation("student_idle_knife");

        keysMap = new KeysMap();
        KeysController keysController = new KeysController(keysMap);
        keysController.controlOnScene(scene);
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
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 225;
            updateMovementAngle(currentMovementAngle);

            moveUp(movementY);
            moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.UP) && keysMap.isPressed(KeyCode.RIGHT)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 315;
            updateMovementAngle(currentMovementAngle);

            moveUp(movementY);
            moveRight(movementX);
        } else if (keysMap.isPressed(KeyCode.DOWN) && keysMap.isPressed(KeyCode.LEFT)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 135;
            updateMovementAngle(currentMovementAngle);

            moveDown(movementY);
            moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.DOWN) && keysMap.isPressed(KeyCode.RIGHT)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 45;
            updateMovementAngle(currentMovementAngle);

            moveDown(movementY);
            moveRight(movementX);
        } else if (keysMap.isPressed(KeyCode.UP)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 270;
            updateMovementAngle(currentMovementAngle);

            moveUp(movementY);
        } else if (keysMap.isPressed(KeyCode.DOWN)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 90;
            updateMovementAngle(currentMovementAngle);

            moveDown(movementY);
        } else if (keysMap.isPressed(KeyCode.LEFT)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 180;
            updateMovementAngle(currentMovementAngle);

            moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.RIGHT)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 0;
            updateMovementAngle(currentMovementAngle);

            moveRight(movementX);
        } else if (!currentAnimation.isLock()) {
            updateAnimation("student_idle_" + typeOfWeaponInHands);

            currentAnimation.play();
        }

        if (keysMap.isPressed(KeyCode.E) && !currentAnimation.isLock()) {
            if (typeOfWeaponInHands.equals("knife")) {
                updateAnimation("student_use_" + typeOfWeaponInHands);

                currentAnimation.lock();
                currentAnimation.play();
            } else {
                Bullet bullet = controller.controlShooting();

                if (bullet != null) {
                    updateAnimation("student_use_" + typeOfWeaponInHands);

                    currentAnimation.lock();
                    currentAnimation.play();

                    double bulletsPaneAngle = characterPaneAngle;
                    GameSpace.addNewBullet(bullet, bulletsPaneAngle);
                } else {
                    controller.controlReloading();

                    updateAnimation("student_reload_" + typeOfWeaponInHands);

                    currentAnimation.lock();
                    currentAnimation.play();
                }
            }
        }

        if (keysMap.isPressed(KeyCode.R)
                && !currentAnimation.isLock()
                && !typeOfWeaponInHands.equals("knife")
        ) {
            controller.controlReloading();

            updateAnimation("student_reload_" + typeOfWeaponInHands);

            currentAnimation.lock();
            currentAnimation.play();
        }
    }

    private void updateCurrentWeaponView(String weaponType) {
        PlayersController playersController = new PlayersController(player);
        boolean weaponExisting = playersController.controlChangingWeapon(weaponType);

        if (weaponExisting) {
            updateAnimation("student_idle_" + weaponType);
        }
    }
}
