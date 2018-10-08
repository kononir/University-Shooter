package bsuir.vlad.universityshooter.activeobjects.characters;

import bsuir.vlad.universityshooter.game.Profile;
import bsuir.vlad.universityshooter.weapons.Bullet;
import bsuir.vlad.universityshooter.game.Level;
import bsuir.vlad.universityshooter.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player extends Character {
    private List<Weapon> currentWeaponsList;
    private Profile profile;

    public Profile getProfile() {
        return profile;
    }

    public Player(Profile profile) {
        this.profile = profile;

        health = 100;
        defence = 100;

        currentWeaponsList = new ArrayList<>();
        addNewWeapon("knife");
        addNewWeapon("handgun");
        addNewWeapon("rifle");
        addNewWeapon("shotgun");
    }

    public final void increaseHealth(int receivedHealth) {
        health += receivedHealth;
    }

    public final void increaseDefence(int receivedDefence) {
        defence += receivedDefence;
    }

    public final boolean changeWeapon(String weaponsType) {
        boolean weaponsExisting;

        Weapon findingWeapon = findWeapon(weaponsType, currentWeaponsList);

        if(findingWeapon != null){
            weaponInHands = findingWeapon;
            weaponsExisting = true;
        }
        else {
            weaponsExisting = false;
        }

        return weaponsExisting;
    }

    private Weapon findWeapon(String weaponsType, List<Weapon> weaponsList) {
        int first = 0;

        List<Weapon> findingWeaponsList
                = weaponsList.stream().filter(weapon -> weapon.getType().equals(weaponsType))
                .collect(Collectors.toList());

        return findingWeaponsList.get(first);
    }

    public final void addNewWeapon(String weaponsType){
        List<Weapon> allWeaponsList = Level.getPlayerWeaponList();

        Weapon newWeapon = findWeapon(weaponsType, allWeaponsList);
        weaponInHands = newWeapon;

        currentWeaponsList.add(newWeapon);
    }

    public final Bullet shootFromWeapon() {
        Bullet bullet = null;

        if(!weaponInHands.isHolderEmpty()) {
            String gunslingerName = "player";
            int bulletDamage = weaponInHands.getDamage();
            int distanceDamage = weaponInHands.getDistance();
            String bulletsType;

            if (weaponInHands.getType().equals("shotgun")) {
                bulletsType = "buckshot";
            } else {
                bulletsType = "bullet";
            }

            bullet = new Bullet(bulletsType, gunslingerName, bulletDamage, distanceDamage);

            weaponInHands.reduceHoldersAmmo();
        }

        return bullet;
    }

    public final boolean reloadWeapon() {
        return weaponInHands.reload();
    }
}
