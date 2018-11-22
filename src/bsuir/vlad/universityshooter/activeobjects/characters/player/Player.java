package bsuir.vlad.universityshooter.activeobjects.characters.player;

import bsuir.vlad.universityshooter.activeobjects.characters.Character;
import bsuir.vlad.universityshooter.game.profile.Profile;
import bsuir.vlad.universityshooter.weapons.bullet.Bullet;
import bsuir.vlad.universityshooter.weapons.Gunslinger;
import bsuir.vlad.universityshooter.weapons.Weapon;
import bsuir.vlad.universityshooter.weapons.WeaponsFile;

import java.util.List;
import java.util.stream.Collectors;

public class Player extends Character {
    private List<Weapon> allWeaponsList;
    private Profile profile;

    public Profile getProfile() {
        return profile;
    }

    public Player(Profile profile) {
        this.profile = profile;

        health = 100;
        defence = 100;

        String playerWeaponsFilePath = "resources/configs/player_weapon_characteristics.xml";
        allWeaponsList = new WeaponsFile(
                playerWeaponsFilePath
        ).loadWeapons();

        changeWeapon("knife");
    }

    final void changeWeapon(String weaponsType) {
        int first = 0;

        List<Weapon> findingWeaponsList
                = allWeaponsList.stream().filter(weapon -> weapon.getType().equals(weaponsType))
                .collect(Collectors.toList());

        weaponInHands = findingWeaponsList.get(first);
    }

    final Bullet shootFromWeapon() {
        Bullet bullet;

        if (!weaponInHands.isHolderEmpty()) {
            Gunslinger gunslingerName = Gunslinger.PLAYER;
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
        } else {
            bullet = null;
        }

        return bullet;
    }
}
