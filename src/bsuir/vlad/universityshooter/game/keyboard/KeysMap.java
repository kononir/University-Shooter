package bsuir.vlad.universityshooter.game.keyboard;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class KeysMap extends HashMap<KeyCode, Boolean> {
    public final boolean isPressed(KeyCode keyCode){
        return this.getOrDefault(keyCode, false);
    }

    public void putTrue(KeyCode keyCode) {
        this.put(keyCode, true);
    }

    public void putFalse(KeyCode keyCode) {
        this.put(keyCode, false);
    }
}
