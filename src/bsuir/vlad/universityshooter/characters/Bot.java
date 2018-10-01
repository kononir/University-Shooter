package bsuir.vlad.universityshooter.characters;

import bsuir.vlad.universityshooter.game.Level;
import bsuir.vlad.universityshooter.weapons.Weapon;

import java.util.List;
import java.util.stream.Collectors;

public class Bot extends Character {
    private String type;

    public String getType() {
        return type;
    }

    public Bot(String type) {
        this.type = type;

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
}
