package bsuir.vlad.universityshooter.activeobjects.characters.bot;

import bsuir.vlad.universityshooter.activeobjects.characters.CharacterView;
import bsuir.vlad.universityshooter.activeobjects.characters.player.Player;
import bsuir.vlad.universityshooter.activeobjects.characters.player.PlayersController;
import bsuir.vlad.universityshooter.activeobjects.characters.player.PlayersView;
import bsuir.vlad.universityshooter.game.windows.GameSpaceWindow;
import bsuir.vlad.universityshooter.weapons.bullet.Bullet;
import bsuir.vlad.universityshooter.weapons.Weapon;
import bsuir.vlad.universityshooter.weapons.WeaponsController;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.*;

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

        if (!currentAnimation.isLock()) {
            switch (attackType) {
                case "punch":
                case "explode":
                    meleeAttack(attackType);
                    break;
                case "shoot":
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

    private boolean isStandOfFireLine() {
        double botsPainX = characterPane.getTranslateX();
        double botsPainY = characterPane.getTranslateY();

        double botsPainWidth = characterPane.getWidth();
        double botsPainHeight = characterPane.getHeight();

        double botsPainXMiddle = botsPainX + botsPainWidth / 2;
        double botsPainYMiddle = botsPainY + botsPainHeight / 2;

        Pane playersPane = playersView.getCharacterPane();

        double playerPainX = playersPane.getTranslateX();
        double playerPainY = playersPane.getTranslateY();

        double playerPainWidth = playersPane.getWidth();
        double playerPainHeight = playersPane.getHeight();
        double playerPainDiagonal = Math.sqrt(Math.pow(playerPainWidth, 2) + Math.pow(playerPainHeight, 2));

        double playerPainXBorder = playerPainX + playerPainWidth;
        double playerPainYBorder = playerPainY + playerPainHeight;

        boolean botStandsHorizontal
                = (botsPainYMiddle >= playerPainY) && (botsPainYMiddle <= playerPainYBorder);
        boolean botStandsVertical
                = (botsPainXMiddle >= playerPainX) && (botsPainXMiddle <= playerPainXBorder);

        double squareYSide = Math.abs(botsPainY - playerPainY);
        double squareXSide = Math.abs(botsPainX - playerPainX);
        boolean botStandsDiagonal = (Math.abs(squareXSide - squareYSide)) > 0 && (Math.abs(squareXSide - squareYSide)) < playerPainDiagonal;

        return botStandsHorizontal || botStandsVertical || botStandsDiagonal;
    }

    private void shoot() {
        if (isStandOfFireLine()) {

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
        if (!isStandOfFireLine()) {
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

        double botsPainWidth = characterPane.getWidth();
        double botsPainHeight = characterPane.getHeight();

        double botsPainXMiddle = botsPainX + botsPainWidth / 2;
        double botsPainYMiddle = botsPainY + botsPainHeight / 2;

        Pane playersPane = playersView.getCharacterPane();

        double playerPainX = playersPane.getTranslateX();
        double playerPainY = playersPane.getTranslateY();

        double playerPainWidth = playersPane.getWidth();
        double playerPainHeight = playersPane.getHeight();

        double playerPainXBorder = playerPainX + playerPainWidth;
        double playerPainYBorder = playerPainY + playerPainHeight;

        boolean botStandsHorizontal
                = (botsPainYMiddle >= playerPainY) && (botsPainYMiddle <= playerPainYBorder);
        boolean botStandsVertical
                = (botsPainXMiddle >= playerPainX) && (botsPainXMiddle <= playerPainXBorder);

        boolean botStandsBelow = (botsPainY - playerPainY) > 0;
        boolean botStandsRight = (botsPainX - playerPainX) > 0;
        boolean botStandsAbove = (playerPainY - botsPainY) > 0;
        boolean botStandsLeft = (playerPainX - botsPainX) > 0;

        double rotationAngle;

        if (botStandsHorizontal && botStandsLeft) {
            rotationAngle = 0;
        } else if (botStandsHorizontal && botStandsRight) {
            rotationAngle = 180;
        } else if (botStandsVertical && botStandsBelow) {
            rotationAngle = 270;
        } else if (botStandsVertical && botStandsAbove) {
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
