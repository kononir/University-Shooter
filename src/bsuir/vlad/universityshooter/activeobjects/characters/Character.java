package bsuir.vlad.universityshooter.activeobjects.characters;

import bsuir.vlad.universityshooter.weapons.Weapon;

public abstract class Character {
    protected int health;
    protected int defence;
    protected Weapon weaponInHands;

    public Weapon getWeaponInHands() {
        return weaponInHands;
    }

    public int getHealth() {
        return health;
    }

    public int getDefence() {
        return defence;
    }

    public final boolean reduceStatus(int receivedDamage) {
        boolean isDead;

        if (defence < receivedDamage) {
            defence = 0;
            health += defence - receivedDamage;
        } else {
            defence -= receivedDamage;
        }

        if (health <= 0) {
            isDead = true;
        } else {
            isDead = false;
        }

        return isDead;
    }
}
