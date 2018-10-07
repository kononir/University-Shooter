package bsuir.vlad.universityshooter.activeobjects.characters;

import bsuir.vlad.universityshooter.weapons.Bullet;
import bsuir.vlad.universityshooter.game.Level;
import javafx.scene.Scene;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PlayersView extends CharacterView {
    private Player player;
    private List<BotsView> botsViewList;

    public Player getPlayer() {
        return player;
    }

    public PlayersView(Player player, double playerX, double playerY, Scene scene, List<BotsView> botsViewList) {
        super("student", playerX, playerY);

        this.player = player;
        this.botsViewList = botsViewList;

        updateAnimation("student_idle_knife");
    }

    public final void updateCurrentWeaponView(String weaponType) {
        PlayersController playersController = new PlayersController(player);
        boolean weaponExisting = playersController.controlChangingWeapon(weaponType);

        if (weaponExisting) {
            updateAnimation("student_idle_" + weaponType);
        }
    }

    public final void move(double currentMovementAngle) {
        PlayersController controller = new PlayersController(player);
        String typeOfWeaponInHands = controller.controlWeaponInHandsType();

        boolean animationIsNotLock = !currentAnimation.isLock();

        if (animationIsNotLock) {
            updateAnimation("student_move_" + typeOfWeaponInHands);

            currentAnimation.play();
        }

        rotate(currentMovementAngle);

        int movementX = 3;
        int movementY = 3;

        int currentMovementAngleInt = (int) currentMovementAngle;

        switch(currentMovementAngleInt) {
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
            case 270:
                moveUp(movementY);
                break;
            case 90:
                moveDown(movementY);
                break;
            case 180:
                moveLeft(movementX);
                break;
            case 0:
                moveRight(movementX);
                break;
        }
    }

    public final void idle() {
        boolean animationIsNotLock = !currentAnimation.isLock();

        if (animationIsNotLock) {
            PlayersController controller = new PlayersController(player);
            String typeOfWeaponInHands = controller.controlWeaponInHandsType();

            updateAnimation("student_idle_" + typeOfWeaponInHands);

            currentAnimation.play();
        }
    }

    public final void attack() {
        boolean animationIsNotLock = !currentAnimation.isLock();

        if (animationIsNotLock) {
            PlayersController controller = new PlayersController(player);
            String typeOfWeaponInHands = controller.controlWeaponInHandsType();

            if (typeOfWeaponInHands.equals("knife")) {
                meleeAttack();
            } else {
                shoot();
            }
        }
    }

    public final void meleeAttack() {
        PlayersController controller = new PlayersController(player);
        String typeOfWeaponInHands = controller.controlWeaponInHandsType();

        updateAnimation("student_use_" + typeOfWeaponInHands);

        currentAnimation.lock();
        currentAnimation.play();

        int cycleDuration = (int) currentAnimation.getCycleDuration().toMillis();

        int corePoolSize = 1;

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(corePoolSize);
        executor.schedule(new TimerTask() {
            @Override
            public void run() {
                int listSize = botsViewList.size();

                for (int index = 0; index < listSize; index++) {
                    BotsView botsView = botsViewList.get(index);

                    boolean boundsIntersect = botsView.getCharacterPane().getBoundsInParent().intersects(
                            characterPane.getBoundsInParent()
                    );

                    if (boundsIntersect) {
                        PlayersController playersController = new PlayersController(player);
                        int receivedDamage = playersController.controlMelee();

                        Bot bot = botsView.getBot();
                        BotsController botsController = new BotsController(bot);

                        boolean botIsDead = botsController.controlStatusReducing(receivedDamage);

                        if (botIsDead) {
                            botsView.getCharacterPane().setVisible(false);
                            botsViewList.remove(botsView);

                            index--;
                            listSize--;
                        }
                    }
                }

                executor.shutdown();
            }
        }, cycleDuration, TimeUnit.MILLISECONDS);
    }

    public final void shoot() {
        PlayersController controller = new PlayersController(player);

        Bullet bullet = controller.controlShooting();

        if (bullet != null) {
            String typeOfWeaponInHands = controller.controlWeaponInHandsType();

            updateAnimation("student_use_" + typeOfWeaponInHands);

            currentAnimation.lock();
            currentAnimation.play();

            Level.addBullet(bullet, this);
        } else {
            reload();
        }
    }

    public final void reload() {
        PlayersController controller = new PlayersController(player);
        String typeOfWeaponInHands = controller.controlWeaponInHandsType();

        boolean weaponHasEnoughHolders = controller.controlReloading(),
                weaponIsNotKnife = !typeOfWeaponInHands.equals("knife"),
                animationIsNotLock = !currentAnimation.isLock();

        if (animationIsNotLock && weaponIsNotKnife && weaponHasEnoughHolders) {
            updateAnimation("student_reload_" + typeOfWeaponInHands);

            currentAnimation.lock();
            currentAnimation.play();
        }
    }
}
