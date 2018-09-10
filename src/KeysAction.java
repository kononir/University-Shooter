import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class KeysAction {
    Scene scene;

    public KeysAction(Scene scene){
        this.scene = scene;
    }

    public final void fillMap(HashMap<KeyCode, Boolean> keysMap){
        scene.setOnKeyPressed(key -> {
            keysMap.put(key.getCode(), true);
        });

        scene.setOnKeyReleased(key -> {
            keysMap.put(key.getCode(), false);
        });
    }
}
