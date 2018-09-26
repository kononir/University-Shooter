package bsuir.vlad.universityshooter.application;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class KeysController {
    private HashMap<KeyCode, Boolean> keysMap;

    public KeysController(HashMap<KeyCode, Boolean> keysMap) {
        this.keysMap = keysMap;
    }

    public final void controlOnScene(Scene scene) {
        KeysAction keysAction = new KeysAction(scene);
        keysAction.fillMap(keysMap);
    }
}
