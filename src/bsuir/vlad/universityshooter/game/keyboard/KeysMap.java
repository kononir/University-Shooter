package bsuir.vlad.universityshooter.game.keyboard;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

class KeysMap extends HashMap<KeyCode, Boolean> {
    final boolean isPressed(KeyCode keyCode) {
        return this.getOrDefault(keyCode, false);
    }

    void putTrue(KeyCode keyCode) {
        this.put(keyCode, true);
    }

    void putFalse(KeyCode keyCode) {
        this.put(keyCode, false);
    }
}
