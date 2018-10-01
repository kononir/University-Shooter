package bsuir.vlad.universityshooter.characters;

import bsuir.vlad.universityshooter.weapons.Bullet;

public class PlayersController {
    Player player;

    public PlayersController(Player player) {
        this.player = player;
    }

    public final boolean controlChangingWeapon(String weaponType) {
        return player.changeWeapon(weaponType);
    }

    public final String controlWeaponInHandsType(){
        return player.getWeaponInHands().getType();
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

    public final int controlHealthLevel() {
        return player.getHealth();
    }

    public final int controlDefenceLevel() {
        return player.getDefence();
    }

    public final boolean controlStatusReducing(int receivedDamage) {
        return player.reduceStatus(receivedDamage);
    }

    public final int controlMelee() {
        return player.getWeaponInHands().getDamage();
    }
}
