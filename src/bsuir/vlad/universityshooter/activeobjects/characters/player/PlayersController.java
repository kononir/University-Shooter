package bsuir.vlad.universityshooter.activeobjects.characters.player;

import bsuir.vlad.universityshooter.game.profile.Profile;
import bsuir.vlad.universityshooter.weapons.bullet.Bullet;
import bsuir.vlad.universityshooter.weapons.Weapon;

public class PlayersController {
    private Player player;

    public PlayersController(Player player) {
        this.player = player;
    }

    public final Weapon controlGettingWeaponInHands() {
        return player.getWeaponInHands();
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

    public final void controlSetDead(boolean dead) {
        player.setDead(dead);
    }

    public final Profile controlGettingProfile() {
        return player.getProfile();
    }

    final void controlChangingWeapon(String weaponType) {
        player.changeWeapon(weaponType);
    }

    final Bullet controlShooting() {
        return player.shootFromWeapon();
    }
}
