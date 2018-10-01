package bsuir.vlad.universityshooter.weapons;

import bsuir.vlad.universityshooter.activeobjects.Movable;
import bsuir.vlad.universityshooter.characters.*;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;

public class BulletsView implements Movable {
    private Bullet bullet;
    private Pane bulletsPane;
    private double bulletsPaneAngle;
    private PlayersView playersView;
    private List<BotsView> botsViewList;

    public Pane getBulletsPane() {
        return bulletsPane;
    }

    public BulletsView(Bullet bullet, double bulletsPaneAngle, PlayersView playersView, List<BotsView> botsViewList) {
        this.bullet = bullet;
        this.bulletsPaneAngle = bulletsPaneAngle;
        this.playersView = playersView;
        this.botsViewList = botsViewList;

        Image bulletsImage = new Image(getClass().getResourceAsStream("../resources/textures/bullet.png"));
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

    public final void updateBulletsView() {
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

        int bulletsDamage = bulletsController.controlGettingDamage();

        String gunslingerName = bulletsController.controlGunslinger();

        if (gunslingerName.equals("player")) {
            int listSize = botsViewList.size();

            for (int index = 0; index < listSize; index++) {
                BotsView botsView = botsViewList.get(index);

                if (botsView.getCharacterPane().getBoundsInParent().intersects(
                        bulletsPane.getBoundsInParent()
                )) {
                    Bot bot = botsView.getBot();
                    BotsController botsController = new BotsController(bot);

                    boolean botIsDead = botsController.controlStatusReducing(bulletsDamage);

                    if (botIsDead) {
                        botsView.getCharacterPane().setVisible(false);
                        botsViewList.remove(botsView);
                    }

                    bulletsPane.setVisible(false);

                    break;
                }
            }
        } else {
            if (playersView.getCharacterPane().getBoundsInParent().intersects(
                    bulletsPane.getBoundsInParent()
            )) {
                Player player = playersView.getPlayer();
                PlayersController playersController = new PlayersController(player);

                boolean playerIsDead = playersController.controlStatusReducing(bulletsDamage);

                if (playerIsDead) {

                }

                bulletsPane.setVisible(false);
            }
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
