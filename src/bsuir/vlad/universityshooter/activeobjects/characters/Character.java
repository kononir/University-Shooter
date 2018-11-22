package bsuir.vlad.universityshooter.activeobjects.characters;

import bsuir.vlad.universityshooter.weapons.Weapon;

public abstract class Character {
    protected int health;
    protected int defence;
    protected Weapon weaponInHands;
    protected boolean dead;

    public Weapon getWeaponInHands() {
        return weaponInHands;
    }

    public boolean isDead() {
        return dead;
    }

    public int getHealth() {
        return health;
    }

    public int getDefence() {
        return defence;
    }

    public final void reduceStatus(int receivedDamage) {
        if (defence < receivedDamage) {
            defence = 0;
            health += defence - receivedDamage;
        } else {
            defence -= receivedDamage;
        }

        dead = (health <= 0);
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
