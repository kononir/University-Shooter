package bsuir.vlad.universityshooter.application;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
    private final ImageView spriteImageView;
    private final int countOfAnimationFrames;
    private final int animationFrameHeight;
    private final int animationFrameWidth;
    private int offsetX;
    private int offsetY;

    public SpriteAnimation(
            ImageView spriteImageView,
            Duration cycleDuration,
            int countOfAnimationFrames,
            int animationFrameHeight,
            int animationFrameWidth,
            int offsetX,
            int offsetY
    ) {
        this.spriteImageView = spriteImageView;
        this.countOfAnimationFrames = countOfAnimationFrames;
        this.animationFrameHeight = animationFrameHeight;
        this.animationFrameWidth = animationFrameWidth;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        this.setCycleDuration(cycleDuration);
        this.setCycleCount(Animation.INDEFINITE);
        this.setInterpolator(Interpolator.LINEAR);

        this.spriteImageView.setViewport(
                new Rectangle2D(offsetX, offsetY, animationFrameWidth, animationFrameHeight)
        );
    }

    public final void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public final void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public final int getAnimationFrameHeight() {
        return animationFrameHeight;
    }

    public final int getAnimationFrameWidth() {
        return animationFrameWidth;
    }

    @Override
    protected void interpolate(double fraction) {
        final int maxIndex = countOfAnimationFrames - 1;
        final int computingIndex = (int) Math.floor(countOfAnimationFrames * fraction);
        final int frameIndex = Math.min(computingIndex, maxIndex);

        final int currentX = frameIndex * animationFrameWidth + offsetX;
        final int currentY = offsetY;

        this.spriteImageView.setViewport(
                new Rectangle2D(currentX, currentY, animationFrameWidth, animationFrameHeight)
        );
    }
}
