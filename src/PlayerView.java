import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.HashMap;

public class PlayerView {
    private Pane playersPane;
    private ImageView playersImageView;
    private SpriteAnimation playersAnimation;
    private ImageViewMap imageViewMap;
    private HashMap<KeyCode, Boolean> keysMap;

    public PlayerView(Scene scene){
        imageViewMap = new ImageViewMap();
        imageViewMap.fillImageViewMap();

        playersPane = new Pane();

        playersImageView = imageViewMap.get("student_idle_knife");
        updatePlayersPane();
        updatePlayersAnimation();

        keysMap = new HashMap<>();
        KeysController keysController = new KeysController(keysMap);
        keysController.controllOnScene(scene);
    }

    private void updatePlayersPane(){
        playersPane.getChildren().clear();
        playersPane.getChildren().add(playersImageView);
    }

    private void updatePlayersAnimation(){
        int countOfAnimationFrames = 20;
        int animationFrameHeight = 55;
        int animationFrameWidth = 70;
        int offsetX = 0;
        int offsetY = 0;

        playersImageView.setViewport(new Rectangle2D(offsetX, offsetY, animationFrameWidth, animationFrameHeight));

        playersAnimation = new SpriteAnimation(
                playersImageView,
                Duration.millis(1600.0),
                countOfAnimationFrames,
                animationFrameHeight,
                animationFrameWidth,
                offsetX,
                offsetY
        );
    }

    public final Pane getPlayersPane(){
        return playersPane;
    }

    public final void updatePlayersView(){
        if(isPressed(keysMap, KeyCode.UP)){
            checkCurrentAction("student_move_knife");

            playersAnimation.play();

            int newPaneY = (int)playersPane.getTranslateY() - 3;
            playersPane.setTranslateY(newPaneY);
        } else if(isPressed(keysMap, KeyCode.DOWN)){
            checkCurrentAction("student_move_knife");

            playersAnimation.play();

            int newPaneY = (int)playersPane.getTranslateY() + 3;
            playersPane.setTranslateY(newPaneY);
        } else if(isPressed(keysMap, KeyCode.LEFT)){
            checkCurrentAction("student_move_knife");

            playersAnimation.play();

            int newPaneX = (int)playersPane.getTranslateX() - 3;
            playersPane.setTranslateX(newPaneX);
        } else if(isPressed(keysMap, KeyCode.RIGHT)){
            checkCurrentAction("student_move_knife");

            playersAnimation.play();

            int newPaneX = (int)playersPane.getTranslateX() + 3;
            playersPane.setTranslateX(newPaneX);
        }
        else{
            checkCurrentAction("student_idle_knife");

            playersAnimation.play();
        }
    }

    private boolean isPressed(HashMap<KeyCode, Boolean> keysMap, KeyCode keyCode){
        return keysMap.getOrDefault(keyCode, false);
    }

    private void checkCurrentAction(String nameOfAction){
        ImageView imageView = imageViewMap.get(nameOfAction);

        if(!playersImageView.equals(imageView)){
            playersImageView = imageView;
            updatePlayersPane();
            updatePlayersAnimation();
        }
    }
}
