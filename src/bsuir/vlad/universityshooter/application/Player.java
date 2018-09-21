package bsuir.vlad.universityshooter.application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {
    private int health;
    private int defence;
    private Weapon weaponInHands;
    private List<Weapon> currentWeaponsList;
    private List<Weapon> allWeaponsList;

    public Player() {
        health = 100;
        defence = 0;

        allWeaponsList = new WeaponsFile("src/bsuir/vlad/universityshooter/application/resources/weapons_characteristics.xml").loadWeapons();

        currentWeaponsList = new ArrayList<>(allWeaponsList.size());
        addNewWeapon("knife");
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
}
