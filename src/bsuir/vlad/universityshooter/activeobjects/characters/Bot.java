package bsuir.vlad.universityshooter.activeobjects.characters;

import bsuir.vlad.universityshooter.game.Level;
import bsuir.vlad.universityshooter.weapons.Bullet;
import bsuir.vlad.universityshooter.weapons.Weapon;

import java.util.List;
import java.util.stream.Collectors;

public class Bot extends Character {
    private String type;
    private boolean movable;
    private String attackType;

    public boolean isMovable() {
        return movable;
    }

    public String getAttackType() {
        return attackType;
    }

    public String getType() {
        return type;
    }

    public Bot(String type, boolean movable, String attackType) {
        this.type = type;
        this.movable = movable;
        this.attackType = attackType;

        weaponInHands = findMatchWeapon();

        health = 100;
        defence = 0;
    }

    private Weapon findMatchWeapon() {
        int first = 0;

        List<Weapon> weaponList = Level.getBotWeaponList();

        List<Weapon> findingWeaponsList
                = weaponList.stream().filter(weapon -> weapon.getType().startsWith(type))
                            .collect(Collectors.toList());

        return findingWeaponsList.get(first);
    }

    public final Bullet shootFromWeapon() {
        String gunslingerName = "bot";
        int bulletDamage = weaponInHands.getDamage();
        int distanceDamage = weaponInHands.getDistance();
        String bulletsType = "bullet";

        return new Bullet(bulletsType, gunslingerName, bulletDamage, distanceDamage);
    }
}
