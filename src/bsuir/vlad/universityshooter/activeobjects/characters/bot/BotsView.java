package bsuir.vlad.universityshooter.activeobjects.characters.bot;

import bsuir.vlad.universityshooter.activeobjects.characters.CharacterView;
import bsuir.vlad.universityshooter.activeobjects.characters.player.Player;
import bsuir.vlad.universityshooter.activeobjects.characters.player.PlayersController;
import bsuir.vlad.universityshooter.activeobjects.characters.player.PlayersView;
import bsuir.vlad.universityshooter.game.windows.GameSpaceWindow;
import bsuir.vlad.universityshooter.activeobjects.bullet.Bullet;
import bsuir.vlad.universityshooter.weapons.Weapon;
import bsuir.vlad.universityshooter.weapons.WeaponsController;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.*;
import static java.lang.Math.*;

public class BotsView extends CharacterView {
    private Bot bot;
    private PlayersView playersView;

    public Bot getBot() {
        return bot;
    }

    public BotsView(Bot bot, double botX, double botY, String type, PlayersView playersView) {
        super(type, botX, botY);
        this.bot = bot;
        this.playersView = playersView;

        updateAnimation(type + "_idle");
    }

    public void updateBotsView() {
        updateRotationAngle();

        BotsController botsController = new BotsController(bot);
        boolean movable = botsController.controlGettingMovable();
        Weapon weaponInHands = botsController.controlGettingWeaponInHands();

        WeaponsController weaponsController = new WeaponsController(weaponInHands);
        String attackType = weaponsController.controlGettingAttackType();

        if (!currentAnimation.isLock()) {   // переделать под Factory method (дописать условие выполнения для конкретного бота)
            switch (attackType) {
                case "punch":               // if (isBoundsIntersect())
                case "explode":             // -|-|-
                    meleeAttack(attackType);
                    break;
                case "shoot":               // if (isStandOnFireLine())
                    shoot();
                    break;
            }
        }

        boolean gunslinger = attackType.equals("shoot");

        if (!currentAnimation.isLock()) {
            if (!movable) {
                idle();
            } else if (!gunslinger) {
                moveToPlayer();
            } else {
                moveToFireLine();
            }
        }
    }

    private void meleeAttack(String attackType) {
        if (isBoundsIntersect()) {
            updateAnimation(characterName + "_attack");

            currentAnimation.lock();
            currentAnimation.play();

            double coefficient = 0.75;
            double animationDuration = currentAnimation.getCycleDuration().toMillis();
            int cycleDuration = (int) (animationDuration * coefficient);

            int corePoolSize = 1;

            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(corePoolSize);

            executor.schedule(new TimerTask() {
                @Override
                public void run() {
                    boolean botExploded = attackType.equals("explode");

                    if (botExploded) {
                        characterPane.setVisible(false);
                    }

                    if (isBoundsIntersect()) {
                        BotsController botsController = new BotsController(bot);

                        int receivedDamage = botsController.controlMelee();

                        Player player = playersView.getPlayer();

                        PlayersController playersController = new PlayersController(player);
                        playersController.controlStatusReducing(receivedDamage);
                        boolean playerIsDead = playersController.controlIsDead();

                        if (playerIsDead) {
                            playersView.getCharacterPane().setVisible(false);
                        }
                    }

                    executor.shutdown();
                }
            }, cycleDuration, TimeUnit.MILLISECONDS);
        }
    }

    private boolean isBoundsIntersect() {
        Pane playersPane = playersView.getCharacterPane();

        return characterPane.getBoundsInParent().intersects(
                playersPane.getBoundsInParent()
        );
    }

    private boolean isStandOnFireLine() {
        return isStandHorizontal() || isStandVertical() || isStandDiagonal();
    }

    private boolean isStandHorizontal() {
        double botsPainY = characterPane.getTranslateY();
        double botsPainHeight = characterPane.getHeight();
        double botsPainYMiddle = botsPainY + botsPainHeight / 2;

        Pane playersPane = playersView.getCharacterPane();
        double playerPainY = playersPane.getTranslateY();
        double playerPainHeight = playersPane.getHeight();
        double playerPainYBorder = playerPainY + playerPainHeight;

        return (botsPainYMiddle >= playerPainY) && (botsPainYMiddle <= playerPainYBorder);
    }

    private boolean isStandVertical() {
        double botsPainX = characterPane.getTranslateX();
        double botsPainWidth = characterPane.getWidth();
        double botsPainXMiddle = botsPainX + botsPainWidth / 2;

        Pane playersPane = playersView.getCharacterPane();
        double playerPainX = playersPane.getTranslateX();
        double playerPainWidth = playersPane.getWidth();
        double playerPainXBorder = playerPainX + playerPainWidth;

        return (botsPainXMiddle >= playerPainX) && (botsPainXMiddle <= playerPainXBorder);
    }

    private boolean isStandDiagonal() {
        double botsPainX = characterPane.getTranslateX();
        double botsPainY = characterPane.getTranslateY();

        Pane playersPane = playersView.getCharacterPane();
        double playerPainX = playersPane.getTranslateX();
        double playerPainY = playersPane.getTranslateY();

        double playerPainWidth = playersPane.getWidth();
        double playerPainHeight = playersPane.getHeight();
        double playerPainDiagonal = sqrt(pow(playerPainWidth, 2) + pow(playerPainHeight, 2));

        double squareYSide = abs(botsPainY - playerPainY);
        double squareXSide = abs(botsPainX - playerPainX);
        return (abs(squareXSide - squareYSide)) > 0 && (abs(squareXSide - squareYSide)) < playerPainDiagonal;
    }

    private void shoot() {
        if (isStandOnFireLine()) {

            BotsController controller = new BotsController(bot);

            Bullet bullet = controller.controlShooting();
            GameSpaceWindow.addBulletsView(bullet, this);

            String botsType = controller.controlGettingBotType();

            updateAnimation(botsType + "_attack");

            currentAnimation.lock();
            currentAnimation.play();
        }
    }

    private void moveToPlayer() {
        if (!isBoundsIntersect()) {
            move();
        }
    }

    private void moveToFireLine() {
        if (!isStandOnFireLine()) {
            move();
        }
    }

    private void move() {
        updateAnimation(characterName + "_move");

        currentAnimation.play();

        int movementX = 1;
        int movementY = 1;

        double currentMovementAngle = updateRotationAngle();
        int currentMovementAngleInt = (int) currentMovementAngle;

        switch (currentMovementAngleInt) {
            case 0:
                moveRight(movementX);
                break;
            case 180:
                moveLeft(movementX);
                break;
            case 270:
                moveUp(movementY);
                break;
            case 90:
                moveDown(movementY);
                break;
            case 225:
                moveUp(movementY);
                moveLeft(movementX);
                break;
            case 315:
                moveUp(movementY);
                moveRight(movementX);
                break;
            case 135:
                moveDown(movementY);
                moveLeft(movementX);
                break;
            case 45:
                moveDown(movementY);
                moveRight(movementX);
                break;
        }
    }

    private double updateRotationAngle() {
        double botsPainX = characterPane.getTranslateX();
        double botsPainY = characterPane.getTranslateY();

        Pane playersPane = playersView.getCharacterPane();
        double playerPainX = playersPane.getTranslateX();
        double playerPainY = playersPane.getTranslateY();

        boolean botStandsBelow = (botsPainY - playerPainY) > 0;
        boolean botStandsRight = (botsPainX - playerPainX) > 0;
        boolean botStandsAbove = (playerPainY - botsPainY) > 0;
        boolean botStandsLeft = (playerPainX - botsPainX) > 0;

        double rotationAngle;

        if (isStandHorizontal() && botStandsLeft) {
            rotationAngle = 0;
        } else if (isStandHorizontal() && botStandsRight) {
            rotationAngle = 180;
        } else if (isStandVertical() && botStandsBelow) {
            rotationAngle = 270;
        } else if (isStandVertical() && botStandsAbove) {
            rotationAngle = 90;
        } else if (botStandsBelow && botStandsRight) {
            rotationAngle = 225;
        } else if (botStandsBelow && botStandsLeft) {
            rotationAngle = 315;
        } else if (botStandsAbove && botStandsRight) {
            rotationAngle = 135;
        } else if (botStandsAbove && botStandsLeft) {
            rotationAngle = 45;
        } else {
            try {
                throw new IOException("Bot Angle Exception");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                rotationAngle = -1;
            }
        }

        rotate(rotationAngle);

        return rotationAngle;
    }

    private void idle() {
        updateAnimation(characterName + "_idle");

        currentAnimation.play();
    }
}
