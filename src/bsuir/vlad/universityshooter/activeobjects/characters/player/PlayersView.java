package bsuir.vlad.universityshooter.activeobjects.characters.player;

import bsuir.vlad.universityshooter.activeobjects.characters.CharacterView;
import bsuir.vlad.universityshooter.activeobjects.characters.bot.Bot;
import bsuir.vlad.universityshooter.activeobjects.characters.bot.BotsController;
import bsuir.vlad.universityshooter.activeobjects.characters.bot.BotsView;
import bsuir.vlad.universityshooter.game.profile.Profile;
import bsuir.vlad.universityshooter.game.profile.ProfileController;
import bsuir.vlad.universityshooter.game.windows.GameSpaceWindow;
import bsuir.vlad.universityshooter.activeobjects.bullet.Bullet;
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
        boolean animationIsNotLock = !currentAnimation.isLock();

        if (animationIsNotLock) {
            PlayersController playersController = new PlayersController(player);
            playersController.controlChangingWeapon(weaponType);

            updateAnimation("student_idle_" + weaponType);
        }
    }

    public final void updateMovementAnimation() {
        boolean animationIsNotLock = !currentAnimation.isLock();

        if (animationIsNotLock) {
            PlayersController playersController = new PlayersController(player);
            Weapon weaponInHands = playersController.controlGettingWeaponInHands();

            WeaponsController weaponsController = new WeaponsController(weaponInHands);
            String typeOfWeaponInHands = weaponsController.controlGettingType();

            updateAnimation("student_move_" + typeOfWeaponInHands);

            currentAnimation.play();
        }
    }

    public final void idle() {
        boolean animationIsNotLock = !currentAnimation.isLock();

        if (animationIsNotLock) {
            PlayersController playersController = new PlayersController(player);
            Weapon weaponInHands = playersController.controlGettingWeaponInHands();

            WeaponsController weaponsController = new WeaponsController(weaponInHands);
            String typeOfWeaponInHands = weaponsController.controlGettingType();

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
            String attackTypeOfWeaponInHands = weaponsController.controlGettingAttackType();

            if (attackTypeOfWeaponInHands.equals("punch")) {
                meleeAttack(weaponInHands);
            } else if (attackTypeOfWeaponInHands.equals("shoot")) {
                shoot(weaponInHands);
            }
        }
    }

    private void meleeAttack(Weapon weaponInHands) {
        WeaponsController weaponsController = new WeaponsController(weaponInHands);
        String typeOfWeaponInHands = weaponsController.controlGettingType();

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

                while (botsViewIterator.hasNext()) {
                    BotsView botsView = botsViewIterator.next();

                    boolean boundsIntersect = botsView.getCharacterPane().getBoundsInParent().intersects(
                            characterPane.getBoundsInParent()
                    );

                    if (boundsIntersect) {
                        PlayersController playersController = new PlayersController(player);
                        Weapon weaponInHands = playersController.controlGettingWeaponInHands();

                        WeaponsController weaponsController = new WeaponsController(weaponInHands);
                        int receivedDamage = weaponsController.controlGettingDamage();

                        Bot bot = botsView.getBot();

                        BotsController botsController = new BotsController(bot);
                        botsController.controlStatusReducing(receivedDamage);
                        boolean botIsDead = botsController.controlIsDead();

                        if (botIsDead) {
                            botsView.getCharacterPane().setVisible(false);
                            botsViewIterator.remove();

                            Profile profile = playersController.controlGettingProfile();

                            int newScore = botsController.controlGettingScore();

                            ProfileController profileController = new ProfileController(profile);
                            profileController.controlIncreasingScore(newScore);
                        }
                    }
                }

                executor.shutdown();
            }
        }, cycleDuration, TimeUnit.MILLISECONDS);
    }

    private void shoot(Weapon weaponInHands) {
        PlayersController playersController = new PlayersController(player);

        Bullet bullet = playersController.controlShooting();

        if (bullet != null) {
            GameSpaceWindow.addBulletsView(bullet, this);

            WeaponsController weaponsController = new WeaponsController(weaponInHands);
            String typeOfWeaponInHands = weaponsController.controlGettingType();

            updateAnimation("student_use_" + typeOfWeaponInHands);

            currentAnimation.lock();
            currentAnimation.play();
        }
    }

    public final void reload() {
        PlayersController playersController = new PlayersController(player);
        Weapon weaponInHands = playersController.controlGettingWeaponInHands();

        WeaponsController weaponsController = new WeaponsController(weaponInHands);
        String attackTypeOfWeaponInHands = weaponsController.controlGettingAttackType();

        boolean weaponHasEnoughHolders = weaponsController.controlHasEnoughHolders(),
                attackTypeIsNotPunch = !attackTypeOfWeaponInHands.equals("punch"),
                animationIsNotLock = !currentAnimation.isLock();

        if (animationIsNotLock && attackTypeIsNotPunch && weaponHasEnoughHolders) {
            weaponsController.controlReloading();

            String typeOfWeaponInHands = weaponsController.controlGettingType();
            updateAnimation("student_reload_" + typeOfWeaponInHands);

            currentAnimation.lock();
            currentAnimation.play();
        }
    }
}
