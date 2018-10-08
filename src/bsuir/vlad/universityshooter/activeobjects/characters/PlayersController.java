package bsuir.vlad.universityshooter.activeobjects.characters;

import bsuir.vlad.universityshooter.game.Profile;
import bsuir.vlad.universityshooter.weapons.Bullet;
import bsuir.vlad.universityshooter.weapons.Weapon;

public class PlayersController {
    Player player;

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

    public final int controlGettingWeaponInHandsHoldersNumber() {
        return player.getWeaponInHands().getHoldersNumber();
    }

    public final int controlGettingWeaponInHandsHoldersAmmo() {
        return  player.getWeaponInHands().getHoldersAmmo();
    }

    public final boolean controlReloading() {
        return player.reloadWeapon();
    }

    public final int controlGettingHealthLevel() {
        return player.getHealth();
    }

    public final int controlGettingDefenceLevel() {
        return player.getDefence();
    }

    public final boolean controlStatusReducing(int receivedDamage) {
        return player.reduceStatus(receivedDamage);
    }

    public final int controlMelee() {
        return player.getWeaponInHands().getDamage();
    }

    public final Profile controlGettingProfile() {
        return player.getProfile();
    }
}
