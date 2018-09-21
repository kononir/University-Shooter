package bsuir.vlad.universityshooter.application;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class KeysMap extends HashMap<KeyCode, Boolean> {
    public final boolean isPressed(KeyCode keyCode){
        return this.getOrDefault(keyCode, false);
    }
}
