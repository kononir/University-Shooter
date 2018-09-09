import javafx.scene.Scene;

public class PlayersAnimationController {
    private Player player;

    public PlayersAnimationController(Player player){
        this.player = player;
    }

    public final void controllOnScene(Scene scene){
        PlayersAnimation playersAnimation = new PlayersAnimation(player, scene);
        playersAnimation.playPlayersAnimation();
    }
}
