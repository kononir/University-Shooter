import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int health;
    private int defence;
    private List<Weapon> listOfWeapons;
    private ImageView playersImageView;
    private Pane playersPane;
    private SpriteAnimation spriteAnimation;

    public Player(ImageView playersImageView){
        health = 100;
        defence = 0;

        newAnimation(playersImageView);

        playersPane = new Pane(playersImageView);

        String weaponsType = "knife";
        int difficultyCoefficient = 1;
        int damage = 35;
        int distance = 0;
        int maxAmmo = 0;

        Weapon knife = new Weapon(weaponsType, difficultyCoefficient, damage, distance, maxAmmo);
        listOfWeapons = new ArrayList<>();
        listOfWeapons.add(knife);
    }

    public final void increaseHealth(int newHealth){
        health += newHealth;
    }

    public final void reduceHealth(int newHealth){
        health -= newHealth;
    }

    public final void increaseDefence(int newDefence){
        defence += newDefence;
    }

    public final void reduceDefence(int newDefence){
        defence -= newDefence;
    }

    public final void setPlayersImageView(ImageView newPlayersImageView){
        playersImageView = newPlayersImageView;
    }

    public final ImageView getPlayersImageView(){
        return playersImageView;
    }

    public final void newAnimation(ImageView newPlayersImageView){
        int countOfAnimationFrames = 20;
        int animationFrameHeight = 55;
        int animationFrameWidth = 70;
        int offsetX = 10;
        int offsetY = 0;

        playersImageView = newPlayersImageView;

        playersImageView.setViewport(new Rectangle2D(offsetX, offsetY, animationFrameHeight, animationFrameWidth));

        spriteAnimation = new SpriteAnimation(
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

    public final SpriteAnimation getSpriteAnimation(){
        return spriteAnimation;
    }




}
