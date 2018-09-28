package bsuir.vlad.universityshooter;

import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class BulletsView implements Movable {
    private Bullet bullet;
    private Pane bulletsPane;
    private double bulletsPaneAngle;

    public Pane getBulletsPane() {
        return bulletsPane;
    }

    public BulletsView(Bullet bullet, double bulletsPaneAngle) {
        this.bullet = bullet;
        this.bulletsPaneAngle = bulletsPaneAngle;

        Image bulletsImage = new Image(getClass().getResourceAsStream("resources/textures/bullet.png"));
        ImageView bulletsImageView = new ImageView(bulletsImage);

        bulletsPane = new Pane();
        bulletsPane.getChildren().add(bulletsImageView);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1), bulletsPane);
        rotateTransition.setToAngle(bulletsPaneAngle);
        rotateTransition.play();
    }

    public void setRelativeLocation(Pane pane) {
        double paneX = pane.getTranslateX();
        double paneY = pane.getTranslateY();

        double paneWidth = pane.getWidth();
        double paneHeight = pane.getHeight();

        double bulletsPaneX = 0;
        double bulletsPaneY = 0;

        if (bulletsPaneAngle == 225.0) {
            bulletsPaneX = paneX + (paneWidth / 2);
            bulletsPaneY = paneY + (paneHeight / 2);
        } else if (bulletsPaneAngle == 315.0) {
            bulletsPaneX = paneX + (paneWidth / 2);
            bulletsPaneY = paneY + (paneHeight / 2);
        } else if (bulletsPaneAngle == 135.0) {
            bulletsPaneX = paneX;
            bulletsPaneY = paneY + (paneHeight / 2);
        } else if (bulletsPaneAngle == 45.0) {
            bulletsPaneX = paneX + (paneWidth / 2);
            bulletsPaneY = paneY + (paneHeight);
        } else if (bulletsPaneAngle == 270.0) {
            bulletsPaneX = paneX + (paneWidth / 2);
            bulletsPaneY = paneY + (paneHeight / 2);
        } else if (bulletsPaneAngle == 90.0) {
            bulletsPaneX = paneX + (paneWidth / 2);
            bulletsPaneY = paneY + (paneHeight / 2);
        } else if (bulletsPaneAngle == 180.0) {
            bulletsPaneX = paneX + (paneWidth / 2);
            bulletsPaneY = paneY + (paneHeight / 2);
        } else if (bulletsPaneAngle == 0.0) {
            bulletsPaneX = paneX + (paneWidth / 2);
            bulletsPaneY = paneY + (paneHeight / 2);
        }

        bulletsPane.setLayoutX(bulletsPaneX);
        bulletsPane.setLayoutY(bulletsPaneY);
    }

    public void updateBulletsView() {
        BulletsController bulletsController = new BulletsController(bullet);

        int maxMovementX = 6;
        int minMovementX = 0;
        int maxMovementY = 6;
        int minMovementY = 0;

        if (bulletsPaneAngle == 225.0) {
            bulletsController.controlDistance(maxMovementX, maxMovementY);

            moveUp(maxMovementY);
            moveLeft(maxMovementX);
        } else if (bulletsPaneAngle == 315.0) {
            bulletsController.controlDistance(maxMovementX, maxMovementY);

            moveUp(maxMovementY);
            moveRight(maxMovementX);
        } else if (bulletsPaneAngle == 135.0) {
            bulletsController.controlDistance(maxMovementX, maxMovementY);

            moveDown(maxMovementY);
            moveLeft(maxMovementX);
        } else if (bulletsPaneAngle == 45.0) {
            bulletsController.controlDistance(maxMovementX, maxMovementY);

            moveDown(maxMovementY);
            moveRight(maxMovementX);
        } else if (bulletsPaneAngle == 270.0) {
            bulletsController.controlDistance(minMovementX, maxMovementY);

            moveUp(maxMovementY);
        } else if (bulletsPaneAngle == 90.0) {
            bulletsController.controlDistance(minMovementX, maxMovementY);

            moveDown(maxMovementY);
        } else if (bulletsPaneAngle == 180.0) {
            bulletsController.controlDistance(maxMovementX, minMovementY);

            moveLeft(maxMovementX);
        } else if (bulletsPaneAngle == 0.0) {
            bulletsController.controlDistance(maxMovementX, minMovementY);

            moveRight(maxMovementX);
        }
    }

    @Override
    public void moveLeft(int movementX) {
        int newPaneX = (int) bulletsPane.getTranslateX() - movementX;
        bulletsPane.setTranslateX(newPaneX);
    }

    @Override
    public void moveRight(int movementX) {
        int newPaneX = (int) bulletsPane.getTranslateX() + movementX;
        bulletsPane.setTranslateX(newPaneX);
    }

    @Override
    public void moveUp(int movementY) {
        int newPaneY = (int) bulletsPane.getTranslateY() - movementY;
        bulletsPane.setTranslateY(newPaneY);
    }

    @Override
    public void moveDown(int movementY) {
        int newPaneY = (int) bulletsPane.getTranslateY() + movementY;
        bulletsPane.setTranslateY(newPaneY);
    }
}
