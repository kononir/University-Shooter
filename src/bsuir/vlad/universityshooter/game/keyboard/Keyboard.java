package bsuir.vlad.universityshooter.game.keyboard;

import bsuir.vlad.universityshooter.activeobjects.characters.player.PlayersView;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class Keyboard {
    private KeysMap keysMap;
    private PlayersView playersView;

    public Keyboard(Scene scene, PlayersView playersView) {
        this.playersView = playersView;

        keysMap = new KeysMap();

        scene.setOnKeyPressed(key -> {
            KeyCode keyCode = key.getCode();
            keysMap.putTrue(keyCode);
        });

        scene.setOnKeyReleased(key -> {
            KeyCode keyCode = key.getCode();
            keysMap.putFalse(keyCode);
        });
    }

    public final void updateKeyboard() {
        int movementX = 3;
        int movementY = 3;

        if (keysMap.isPressed(KeyCode.ESCAPE)) {
            playersView.getCharacterPane().setVisible(false);
        }

        if (keysMap.isPressed(KeyCode.DIGIT1)) {
            String weaponType = "knife";
            playersView.updateCurrentWeaponView(weaponType);
        } else if (keysMap.isPressed(KeyCode.DIGIT2)) {
            String weaponType = "handgun";
            playersView.updateCurrentWeaponView(weaponType);
        } else if (keysMap.isPressed(KeyCode.DIGIT3)) {
            String weaponType = "rifle";
            playersView.updateCurrentWeaponView(weaponType);
        } else if (keysMap.isPressed(KeyCode.DIGIT4)) {
            String weaponType = "shotgun";
            playersView.updateCurrentWeaponView(weaponType);
        }

        if (keysMap.isPressed(KeyCode.UP) && keysMap.isPressed(KeyCode.LEFT)) {
            double currentMovementAngle = 225;

            playersView.rotate(currentMovementAngle);

            playersView.updateMovementAnimation();

            playersView.moveUp(movementY);
            playersView.moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.UP) && keysMap.isPressed(KeyCode.RIGHT)) {
            double currentMovementAngle = 315;

            playersView.rotate(currentMovementAngle);

            playersView.updateMovementAnimation();

            playersView.moveUp(movementY);
            playersView.moveRight(movementX);
        } else if (keysMap.isPressed(KeyCode.DOWN) && keysMap.isPressed(KeyCode.LEFT)) {
            double currentMovementAngle = 135;

            playersView.rotate(currentMovementAngle);

            playersView.updateMovementAnimation();

            playersView.moveDown(movementY);
            playersView.moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.DOWN) && keysMap.isPressed(KeyCode.RIGHT)) {
            double currentMovementAngle = 45;

            playersView.rotate(currentMovementAngle);

            playersView.updateMovementAnimation();

            playersView.moveDown(movementY);
            playersView.moveRight(movementX);
        } else if (keysMap.isPressed(KeyCode.UP)) {
            double currentMovementAngle = 270;

            playersView.rotate(currentMovementAngle);

            playersView.updateMovementAnimation();

            playersView.moveUp(movementY);
        } else if (keysMap.isPressed(KeyCode.DOWN)) {
            double currentMovementAngle = 90;

            playersView.rotate(currentMovementAngle);

            playersView.updateMovementAnimation();

            playersView.moveDown(movementY);
        } else if (keysMap.isPressed(KeyCode.LEFT)) {
            double currentMovementAngle = 180;

            playersView.rotate(currentMovementAngle);

            playersView.updateMovementAnimation();

            playersView.moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.RIGHT)) {
            double currentMovementAngle = 0;

            playersView.rotate(currentMovementAngle);

            playersView.updateMovementAnimation();

            playersView.moveRight(movementX);
        } else {
            playersView.idle();
        }

        if (keysMap.isPressed(KeyCode.E)) {
            playersView.attack();
        }

        if (keysMap.isPressed(KeyCode.R)) {
            playersView.reload();
        }
    }
}
