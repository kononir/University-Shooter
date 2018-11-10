package bsuir.vlad.universityshooter.activeobjects.characters.player;

import bsuir.vlad.universityshooter.game.profile.Profile;
import bsuir.vlad.universityshooter.weapons.bullet.Bullet;
import bsuir.vlad.universityshooter.weapons.Weapon;

public class PlayersController {
    private Player player;

    public PlayersController(Player player) {
        this.player = player;
    }

    public final boolean controlChangingWeapon(String weaponType) {
        return player.changeWeapon(weaponType);
    }

    public final Weapon controlGettingWeaponInHands() {
        return player.getWeaponInHands();
    }

    public final Bullet controlShooting() {
        return player.shootFromWeapon();
    }

    public final int controlGettingHealthLevel() {
        return player.getHealth();
    }

    public final int controlGettingDefenceLevel() {
        return player.getDefence();
    }

    public final void controlStatusReducing(int receivedDamage) {
        player.reduceStatus(receivedDamage);
    }

    public final boolean controlIsDead() {
        return player.isDead();
    }

    public final int controlMelee() {
        return player.getWeaponInHands().getDamage();
    }

    public final Profile controlGettingProfile() {
        return player.getProfile();
    }
}
