package bsuir.vlad.universityshooter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {
    private int health;
    private int defence;
    private Weapon weaponInHands;
    private List<Weapon> currentWeaponsList;
    private List<Weapon> allWeaponsList;

    public Weapon getWeaponInHands() {
        return weaponInHands;
    }

    public Player() {
        health = 100;
        defence = 0;

        String weaponsFilePath = "src/bsuir/vlad/universityshooter/resources/configs/weapons_characteristics.xml";
        allWeaponsList = new WeaponsFile(weaponsFilePath).loadWeapons();

        currentWeaponsList = new ArrayList<>(allWeaponsList.size());
        addNewWeapon("knife");
        addNewWeapon("handgun");
        addNewWeapon("rifle");
        addNewWeapon("shotgun");
    }

    public final void increaseHealth(int newHealth) {
        health += newHealth;
    }

    public final void reduceHealth(int newHealth) {
        health -= newHealth;
    }

    public final void increaseDefence(int newDefence) {
        defence += newDefence;
    }

    public final void reduceDefence(int newDefence) {
        defence -= newDefence;
    }

    public final boolean changeWeapon(String weaponsType) {
        boolean weaponsExisting = false;

        Weapon findingWeapon = findWeapon(weaponsType, currentWeaponsList);

        if(findingWeapon != null){
            weaponInHands = findingWeapon;
            weaponsExisting = true;
        }

        return weaponsExisting;
    }

    private Weapon findWeapon(String weaponsType, List<Weapon> weaponsList) {
        Iterator<Weapon> iter = weaponsList.iterator();
        Weapon findingWeapon = null;

        while (iter.hasNext()) {
            Weapon currWeapon = iter.next();

            String currWeaponType = currWeapon.getType();
            if (weaponsType.equals(currWeaponType)) {
                findingWeapon = currWeapon;
                break;
            }
        }

        return findingWeapon;
    }

    public final void addNewWeapon(String weaponsType){
        Weapon newWeapon = findWeapon(weaponsType, allWeaponsList);
        weaponInHands = newWeapon;

        currentWeaponsList.add(newWeapon);
    }

    public final String getTypeOfWeaponInHands(){
        return weaponInHands.getType();
    }

    public final Bullet shootFromWeapon() {
        Bullet bullet = null;

        if(!weaponInHands.isHolderEmpty()) {
            int bulletDamage = weaponInHands.getDamage();
            int distanceDamage = weaponInHands.getDistance();

            bullet = new Bullet(bulletDamage, distanceDamage);

            weaponInHands.reduceHoldersAmmo();
        }

        return bullet;
    }

    public final void reloadWeapon() {
        weaponInHands.increaseHoldersAmmo();
    }
}
