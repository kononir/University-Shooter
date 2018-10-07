package bsuir.vlad.universityshooter.weapons;

import bsuir.vlad.universityshooter.activeobjects.Movable;
import bsuir.vlad.universityshooter.activeobjects.characters.*;
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

    public BulletsView(
            Bullet bullet,
            CharacterView gunslingerView,
            PlayersView playersView,
            List<BotsView> botsViewList
    ) {
        this.bullet = bullet;
        this.playersView = playersView;
        this.botsViewList = botsViewList;

        BulletsController controller = new BulletsController(bullet);
        String bulletsType = controller.controlGettingType();

        Image bulletsImage = new Image(getClass().getResourceAsStream(
                "../resources/textures/" + bulletsType + ".png"
        ));
        ImageView bulletsImageView = new ImageView(bulletsImage);

        bulletsPane = new Pane();
        bulletsPane.getChildren().add(bulletsImageView);

        setRelativeLocation(gunslingerView);
        setRelativeAngle(gunslingerView);
    }

    private void setRelativeLocation(CharacterView gunslingerView) {
        Pane gunslingerPane = gunslingerView.getCharacterPane();

        double paneX = gunslingerPane.getTranslateX();
        double paneY = gunslingerPane.getTranslateY();

        double paneWidth = gunslingerPane.getWidth();
        double paneHeight = gunslingerPane.getHeight();

        double bulletsPaneX = paneX + (paneWidth / 2);
        double bulletsPaneY = paneY + (paneHeight / 2);

        bulletsPane.setLayoutX(bulletsPaneX);
        bulletsPane.setLayoutY(bulletsPaneY);
    }

    private void setRelativeAngle(CharacterView gunslingerView) {
        bulletsPaneAngle = gunslingerView.getCharacterPaneAngle();

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1), bulletsPane);
        rotateTransition.setToAngle(bulletsPaneAngle);
        rotateTransition.play();
    }

    public final void updateBulletsView() {
        updateMoving();

        BulletsController bulletsController = new BulletsController(bullet);

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

    private void updateMoving() {
        BulletsController bulletsController = new BulletsController(bullet);

        int maxMovementX = 6;
        int minMovementX = 0;
        int maxMovementY = 6;
        int minMovementY = 0;

        boolean distancePassed = bulletsController.controlDistancePassing();

        if (!distancePassed) {
            int bulletsPaneAngleInt = (int) bulletsPaneAngle;

            switch(bulletsPaneAngleInt) {
                case 225:
                    bulletsController.controlReducingDistance(maxMovementX, maxMovementY);

                    moveUp(maxMovementY);
                    moveLeft(maxMovementX);

                    break;
                case 315:
                    bulletsController.controlReducingDistance(maxMovementX, maxMovementY);

                    moveUp(maxMovementY);
                    moveRight(maxMovementX);

                    break;
                case 135:
                    bulletsController.controlReducingDistance(maxMovementX, maxMovementY);

                    moveDown(maxMovementY);
                    moveLeft(maxMovementX);

                    break;
                case 45:
                    bulletsController.controlReducingDistance(maxMovementX, maxMovementY);

                    moveDown(maxMovementY);
                    moveRight(maxMovementX);

                    break;
                case 270:
                    bulletsController.controlReducingDistance(minMovementX, maxMovementY);

                    moveUp(maxMovementY);

                    break;
                case 90:
                    bulletsController.controlReducingDistance(minMovementX, maxMovementY);

                    moveDown(maxMovementY);

                    break;
                case 180:
                    bulletsController.controlReducingDistance(maxMovementX, minMovementY);

                    moveLeft(maxMovementX);

                    break;
                case 0:
                    bulletsController.controlReducingDistance(maxMovementX, minMovementY);

                    moveRight(maxMovementX);

                    break;
            }
        } else {
            bulletsPane.setVisible(false);
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
