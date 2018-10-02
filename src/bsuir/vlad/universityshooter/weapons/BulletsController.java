package bsuir.vlad.universityshooter.weapons;

import bsuir.vlad.universityshooter.weapons.Bullet;

public class BulletsController {
    Bullet bullet;

    public BulletsController(Bullet bullet) {
        this.bullet = bullet;
    }

    public final boolean controlDistance(int movementX, int movementY) {
        return bullet.isDistancePassed(movementX, movementY);
    }

    public final String controlGunslinger() {
        return bullet.getGunslingerName();
    }

    public final int controlGettingDamage() {
        return bullet.getDamage();
    }

    public final String controlGettingType() {
        return bullet.getType();
    }
}
