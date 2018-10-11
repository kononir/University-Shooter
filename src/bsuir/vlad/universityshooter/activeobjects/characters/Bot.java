package bsuir.vlad.universityshooter.activeobjects.characters;

import bsuir.vlad.universityshooter.weapons.Bullet;
import bsuir.vlad.universityshooter.weapons.Gunslinger;
import bsuir.vlad.universityshooter.weapons.Weapon;

public class Bot extends Character {
    private String type;
    private boolean movable;
    private int score;

    public boolean isMovable() {
        return movable;
    }

    public String getType() {
        return type;
    }

    public int getScore() {
        return score;
    }

    public Bot(String type, Weapon weaponInHands, int score, boolean movable, int difficultyCoefficient) {
        this.type = type;
        this.score = score;
        this.movable = movable;
        this.weaponInHands = weaponInHands;

        health = 100 / difficultyCoefficient;
        defence = 0;
    }

    public final Bullet shootFromWeapon() {
        Gunslinger gunslingerName = Gunslinger.BOT;
        int bulletDamage = weaponInHands.getDamage();
        int distanceDamage = weaponInHands.getDistance();
        String bulletsType = "bullet";

        return new Bullet(bulletsType, gunslingerName, bulletDamage, distanceDamage);
    }
}
