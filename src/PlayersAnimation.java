import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class PlayersAnimation {
    Player player;
    Scene scene;
    HashMap<KeyCode, Boolean> keysMap;

    public PlayersAnimation(Player player, Scene scene){
        this.player = player;
        this.scene = scene;
        keysMap = new HashMap<>();
    }

    public final void playPlayersAnimation(){
        Pane playersPane = player.getPlayersPane();

        scene.setOnKeyPressed(key -> {
            KeyCode keyCode = key.getCode();
            if(keyCode.equals(KeyCode.UP)){
                SpriteAnimation playersSpriteAnimation = player.getSpriteAnimation();
                playersSpriteAnimation.play();

                int newPaneY = (int)playersPane.getTranslateY() - 3;
                playersPane.setTranslateY(newPaneY);
            } else if(keyCode.equals(KeyCode.DOWN)){
                SpriteAnimation playersSpriteAnimation = player.getSpriteAnimation();
                playersSpriteAnimation.play();

                int newPaneY = (int)playersPane.getTranslateY() + 3;
                playersPane.setTranslateY(newPaneY);
            } else if(keyCode.equals(KeyCode.LEFT)){
                SpriteAnimation playersSpriteAnimation = player.getSpriteAnimation();
                playersSpriteAnimation.play();

                int newPaneX = (int)playersPane.getTranslateX() - 3;
                playersPane.setTranslateX(newPaneX);
            } else if(keyCode.equals(KeyCode.RIGHT)){
                SpriteAnimation playersSpriteAnimation = player.getSpriteAnimation();
                playersSpriteAnimation.play();

                int newPaneX = (int)playersPane.getTranslateX() + 3;
                playersPane.setTranslateX(newPaneX);
            }
        });

        scene.setOnKeyReleased(key -> {
            SpriteAnimation playersSpriteAnimation = player.getSpriteAnimation();
            playersSpriteAnimation.stop();
        });
    }
}
