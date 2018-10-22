package bsuir.vlad.universityshooter.game.keyboard;

import bsuir.vlad.universityshooter.activeobjects.characters.PlayersView;
import bsuir.vlad.universityshooter.game.GameSpace;
import bsuir.vlad.universityshooter.game.Level;
import bsuir.vlad.universityshooter.game.Menu;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class Keyboard {
    private KeysMap keysMap;
    private Scene scene;
    private PlayersView playersView;

    public Keyboard(Scene scene, PlayersView playersView) {
        this.scene = scene;
        this.playersView = playersView;

        keysMap = new KeysMap();

        setOnKey();
    }

    private void setOnKey() {
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
            playersView.move(currentMovementAngle);
        } else if (keysMap.isPressed(KeyCode.UP) && keysMap.isPressed(KeyCode.RIGHT)) {
            double currentMovementAngle = 315;
            playersView.move(currentMovementAngle);
        } else if (keysMap.isPressed(KeyCode.DOWN) && keysMap.isPressed(KeyCode.LEFT)) {
            double currentMovementAngle = 135;
            playersView.move(currentMovementAngle);
        } else if (keysMap.isPressed(KeyCode.DOWN) && keysMap.isPressed(KeyCode.RIGHT)) {
            double currentMovementAngle = 45;
            playersView.move(currentMovementAngle);
        } else if (keysMap.isPressed(KeyCode.UP)) {
            double currentMovementAngle = 270;
            playersView.move(currentMovementAngle);
        } else if (keysMap.isPressed(KeyCode.DOWN)) {
            double currentMovementAngle = 90;
            playersView.move(currentMovementAngle);
        } else if (keysMap.isPressed(KeyCode.LEFT)) {
            double currentMovementAngle = 180;
            playersView.move(currentMovementAngle);
        } else if (keysMap.isPressed(KeyCode.RIGHT)) {
            double currentMovementAngle = 0;
            playersView.move(currentMovementAngle);
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
