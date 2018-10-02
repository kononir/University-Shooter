package bsuir.vlad.universityshooter.activeobjects.characters;

import bsuir.vlad.universityshooter.activeobjects.Gunslinger;
import bsuir.vlad.universityshooter.activeobjects.Militant;
import bsuir.vlad.universityshooter.animations.Animated;
import bsuir.vlad.universityshooter.activeobjects.Movable;
import bsuir.vlad.universityshooter.activeobjects.Rotatable;
import bsuir.vlad.universityshooter.animations.SpriteAnimation;
import bsuir.vlad.universityshooter.animations.SpriteAnimationFile;
import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.HashMap;

public abstract class CharacterView implements Movable, Rotatable, Animated, Militant, Gunslinger {
    protected String characterName;
    protected Pane characterPane;
    protected double characterPaneAngle;
    protected SpriteAnimation currentAnimation;
    protected HashMap<String, SpriteAnimation> allAnimationsMap;

    public Pane getCharacterPane() {
        return characterPane;
    }

    public CharacterView(String characterName) {
        this.characterName = characterName;

        characterPane = new Pane();
        characterPaneAngle = 0;

        String animationsFilePath = "src/bsuir/vlad/universityshooter/resources/configs/"
                + characterName + "_animation_characteristics.xml";

        loadAnimation(animationsFilePath);
    }

    @Override
    public void loadAnimation(String animationsFilePath) {
        allAnimationsMap = new SpriteAnimationFile(animationsFilePath).loadAnimations(characterName);
    }

    @Override
    public void updateAnimation(String animationName) {
        SpriteAnimation updatingAnimation = allAnimationsMap.get(animationName);

        if (!updatingAnimation.equals(currentAnimation)) {
            currentAnimation = updatingAnimation;

            ImageView playersImageView = updatingAnimation.getSpriteImageView();
            characterPane.getChildren().clear();
            characterPane.getChildren().add(playersImageView);
        }
    }

    @Override
    public void updateMovementAngle(double currentMovementAngle) {
        if (characterPaneAngle != currentMovementAngle) {
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(1), characterPane);
            rotateTransition.setToAngle(currentMovementAngle);
            rotateTransition.play();

            characterPaneAngle = currentMovementAngle;
        }
    }

    @Override
    public final void moveLeft(int movementX) {
        if (isOpportunityToMoveLeft()) {
            int newPaneX = (int) characterPane.getTranslateX() - movementX;
            characterPane.setTranslateX(newPaneX);
        }
    }

    @Override
    public final void moveRight(int movementX) {
        if (isOpportunityToMoveRight()) {
            int newPaneX = (int) characterPane.getTranslateX() + movementX;
            characterPane.setTranslateX(newPaneX);
        }
    }

    @Override
    public final void moveUp(int movementY) {
        if (isOpportunityToMoveUp()) {
            int newPaneY = (int) characterPane.getTranslateY() - movementY;
            characterPane.setTranslateY(newPaneY);
        }
    }

    @Override
    public final void moveDown(int movementY) {
        if (isOpportunityToMoveDown()) {
            int newPaneY = (int) characterPane.getTranslateY() + movementY;
            characterPane.setTranslateY(newPaneY);
        }
    }

    private boolean isOpportunityToMoveLeft() {
        double playerTranslateX = characterPane.getTranslateX();
        double minPlayerTranslateX = 0.0;

        return playerTranslateX > minPlayerTranslateX;
    }

    private boolean isOpportunityToMoveRight() {
        double playerTranslateX = characterPane.getTranslateX();

        Pane pane = (Pane) characterPane.getParent();
        double maxPaneX = pane.getPrefWidth();
        double playersPaneWidth = characterPane.getWidth();

        double maxPlayerTranslateX = maxPaneX - playersPaneWidth;

        return playerTranslateX < maxPlayerTranslateX;
    }

    private boolean isOpportunityToMoveUp() {
        double playerLayoutY = characterPane.getTranslateY();
        double minPlayerTranslateY = 0.0;

        return playerLayoutY > minPlayerTranslateY;
    }

    private boolean isOpportunityToMoveDown() {
        double playerLayoutY = characterPane.getTranslateY();

        Pane pane = (Pane) characterPane.getParent();
        double maxPaneY = pane.getPrefHeight();
        double playersPaneHeight = characterPane.getHeight();

        double maxPlayerTranslateY = maxPaneY - playersPaneHeight;

        return playerLayoutY < maxPlayerTranslateY;
    }
}
