package bsuir.vlad.universityshooter.activeobjects.characters;

import bsuir.vlad.universityshooter.weapons.Bullet;
import bsuir.vlad.universityshooter.game.Level;
import bsuir.vlad.universityshooter.weapons.Weapon;
import bsuir.vlad.universityshooter.weapons.WeaponsController;

import java.util.Iterator;
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

    public PlayersView(Player player, double playerX, double playerY, List<BotsView> botsViewList) {
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
        PlayersController playersController = new PlayersController(player);
        Weapon weaponInHands = playersController.controlGettingWeaponInHands();

        WeaponsController weaponsController = new WeaponsController(weaponInHands);
        String typeOfWeaponInHands = weaponsController.controlGettingWeaponType();

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
            PlayersController playersController = new PlayersController(player);
            Weapon weaponInHands = playersController.controlGettingWeaponInHands();

            WeaponsController weaponsController = new WeaponsController(weaponInHands);
            String typeOfWeaponInHands = weaponsController.controlGettingWeaponType();

            updateAnimation("student_idle_" + typeOfWeaponInHands);

            currentAnimation.play();
        }
    }

    public final void attack() {
        boolean animationIsNotLock = !currentAnimation.isLock();

        if (animationIsNotLock) {
            PlayersController playersController = new PlayersController(player);
            Weapon weaponInHands = playersController.controlGettingWeaponInHands();

            WeaponsController weaponsController = new WeaponsController(weaponInHands);
            String attackTypeOfWeaponInHands = weaponsController.controlGettingWeaponAttackType();

            if (attackTypeOfWeaponInHands.equals("punch")) {
                meleeAttack();
            } else if (attackTypeOfWeaponInHands.equals("shoot")) {
                shoot();
            }
        }
    }

    private void meleeAttack() {
        PlayersController playersController = new PlayersController(player);
        Weapon weaponInHands = playersController.controlGettingWeaponInHands();

        WeaponsController weaponsController = new WeaponsController(weaponInHands);
        String typeOfWeaponInHands = weaponsController.controlGettingWeaponType();

        updateAnimation("student_use_" + typeOfWeaponInHands);

        currentAnimation.lock();
        currentAnimation.play();

        int cycleDuration = (int) currentAnimation.getCycleDuration().toMillis();

        int corePoolSize = 1;

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(corePoolSize);
        executor.schedule(new TimerTask() {
            @Override
            public void run() {
                Iterator<BotsView> botsViewIterator = botsViewList.iterator();

                while(botsViewIterator.hasNext()) {
                    BotsView botsView = botsViewIterator.next();

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
                            botsViewIterator.remove();
                        }
                    }
                }

                executor.shutdown();
            }
        }, cycleDuration, TimeUnit.MILLISECONDS);
    }

    private void shoot() {
        PlayersController playersController = new PlayersController(player);

        Bullet bullet = playersController.controlShooting();

        if (bullet != null) {
            Weapon weaponInHands = playersController.controlGettingWeaponInHands();

            WeaponsController weaponsController = new WeaponsController(weaponInHands);
            String typeOfWeaponInHands = weaponsController.controlGettingWeaponType();

            updateAnimation("student_use_" + typeOfWeaponInHands);

            currentAnimation.lock();
            currentAnimation.play();

            Level.addBullet(bullet, this);
        } else {
            reload();
        }
    }

    public final void reload() {
        PlayersController playersController = new PlayersController(player);
        Weapon weaponInHands = playersController.controlGettingWeaponInHands();

        WeaponsController weaponsController = new WeaponsController(weaponInHands);
        String attackTypeOfWeaponInHands = weaponsController.controlGettingWeaponAttackType();
        String typeOfWeaponInHands = weaponsController.controlGettingWeaponType();

        boolean weaponHasEnoughHolders = playersController.controlReloading(),
                attackTypeIsNotPunch = !attackTypeOfWeaponInHands.equals("punch"),
                animationIsNotLock = !currentAnimation.isLock();

        if (animationIsNotLock && attackTypeIsNotPunch && weaponHasEnoughHolders) {
            updateAnimation("student_reload_" + typeOfWeaponInHands);

            currentAnimation.lock();
            currentAnimation.play();
        }
    }
}
