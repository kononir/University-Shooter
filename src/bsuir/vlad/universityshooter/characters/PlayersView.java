package bsuir.vlad.universityshooter.characters;

import bsuir.vlad.universityshooter.activeobjects.Gunslinger;
import bsuir.vlad.universityshooter.activeobjects.Militant;
import bsuir.vlad.universityshooter.weapons.Bullet;
import bsuir.vlad.universityshooter.game.keyboard.KeysController;
import bsuir.vlad.universityshooter.game.keyboard.KeysMap;
import bsuir.vlad.universityshooter.game.Level;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlayersView extends CharacterView implements Militant, Gunslinger {
    private Player player;
    private KeysMap keysMap;
    private List<BotsView> botsViewList;

    public Player getPlayer() {
        return player;
    }

    public PlayersView(Player player, Scene scene, List<BotsView> botsViewList) {
        super("student");

        this.player = player;
        this.botsViewList = botsViewList;

        updateAnimation("student_idle_knife");

        keysMap = new KeysMap();
        KeysController keysController = new KeysController(keysMap);
        keysController.controlOnScene(scene);
    }

    public final void updatePlayersView() {
        if (keysMap.isPressed(KeyCode.DIGIT1)) {
            String weaponType = "knife";
            updateCurrentWeaponView(weaponType);
        } else if (keysMap.isPressed(KeyCode.DIGIT2)) {
            String weaponType = "handgun";
            updateCurrentWeaponView(weaponType);
        } else if (keysMap.isPressed(KeyCode.DIGIT3)) {
            String weaponType = "rifle";
            updateCurrentWeaponView(weaponType);
        } else if (keysMap.isPressed(KeyCode.DIGIT4)) {
            String weaponType = "shotgun";
            updateCurrentWeaponView(weaponType);
        }

        PlayersController controller = new PlayersController(player);
        String typeOfWeaponInHands = controller.controlWeaponInHandsType();

        int movementX = 3;
        int movementY = 3;

        if (keysMap.isPressed(KeyCode.UP) && keysMap.isPressed(KeyCode.LEFT)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 225;
            updateMovementAngle(currentMovementAngle);

            moveUp(movementY);
            moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.UP) && keysMap.isPressed(KeyCode.RIGHT)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 315;
            updateMovementAngle(currentMovementAngle);

            moveUp(movementY);
            moveRight(movementX);
        } else if (keysMap.isPressed(KeyCode.DOWN) && keysMap.isPressed(KeyCode.LEFT)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 135;
            updateMovementAngle(currentMovementAngle);

            moveDown(movementY);
            moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.DOWN) && keysMap.isPressed(KeyCode.RIGHT)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 45;
            updateMovementAngle(currentMovementAngle);

            moveDown(movementY);
            moveRight(movementX);
        } else if (keysMap.isPressed(KeyCode.UP)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 270;
            updateMovementAngle(currentMovementAngle);

            moveUp(movementY);
        } else if (keysMap.isPressed(KeyCode.DOWN)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 90;
            updateMovementAngle(currentMovementAngle);

            moveDown(movementY);
        } else if (keysMap.isPressed(KeyCode.LEFT)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 180;
            updateMovementAngle(currentMovementAngle);

            moveLeft(movementX);
        } else if (keysMap.isPressed(KeyCode.RIGHT)) {
            if (!currentAnimation.isLock()) {
                updateAnimation("student_move_" + typeOfWeaponInHands);

                currentAnimation.play();
            }

            double currentMovementAngle = 0;
            updateMovementAngle(currentMovementAngle);

            moveRight(movementX);
        } else if (!currentAnimation.isLock()) {
            updateAnimation("student_idle_" + typeOfWeaponInHands);

            currentAnimation.play();
        }

        if (keysMap.isPressed(KeyCode.E) && !currentAnimation.isLock()) {
            if (typeOfWeaponInHands.equals("knife")) {
                meleeAttack();
            } else {
                shoot();
            }
        }

        if (keysMap.isPressed(KeyCode.R)
                && !currentAnimation.isLock()
                && !typeOfWeaponInHands.equals("knife")
        ) {
            reload();
        }
    }

    private void updateCurrentWeaponView(String weaponType) {
        PlayersController playersController = new PlayersController(player);
        boolean weaponExisting = playersController.controlChangingWeapon(weaponType);

        if (weaponExisting) {
            updateAnimation("student_idle_" + weaponType);
        }
    }

    @Override
    public void meleeAttack() {
        PlayersController controller = new PlayersController(player);

        String typeOfWeaponInHands = controller.controlWeaponInHandsType();

        updateAnimation("student_use_" + typeOfWeaponInHands);

        currentAnimation.lock();
        currentAnimation.play();

        int cycleDuration = (int) currentAnimation.getCycleDuration().toMillis();

        Timer damageTimer = new Timer();
        damageTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                int listSize = botsViewList.size();

                for (int index = 0; index < listSize; index++) {
                    BotsView botsView = botsViewList.get(index);

                    if (botsView.getCharacterPane().getBoundsInParent().intersects(
                            characterPane.getBoundsInParent()
                    )) {
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

                damageTimer.cancel();
            }
        }, cycleDuration);
    }

    @Override
    public void shoot() {
        PlayersController controller = new PlayersController(player);

        String typeOfWeaponInHands = controller.controlWeaponInHandsType();

        Bullet bullet = controller.controlShooting();

        if (bullet != null) {
            updateAnimation("student_use_" + typeOfWeaponInHands);

            currentAnimation.lock();
            currentAnimation.play();

            double bulletsPaneAngle = characterPaneAngle;
            Level.addBullet(bullet, bulletsPaneAngle);
        } else {
            reload();
        }
    }

    @Override
    public void reload() {
        PlayersController controller = new PlayersController(player);

        String typeOfWeaponInHands = controller.controlWeaponInHandsType();

        boolean isEnoughHolders = controller.controlReloading();

        if (isEnoughHolders) {
            updateAnimation("student_reload_" + typeOfWeaponInHands);

            currentAnimation.lock();
            currentAnimation.play();
        }
    }
}
