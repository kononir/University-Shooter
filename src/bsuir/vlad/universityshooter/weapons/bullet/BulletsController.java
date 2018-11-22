package bsuir.vlad.universityshooter.weapons.bullet;

import bsuir.vlad.universityshooter.weapons.Gunslinger;

public class BulletsController {
    private Bullet bullet;

    public BulletsController(Bullet bullet) {
        this.bullet = bullet;
    }

    public final boolean controlIsDisappeared() {
        return bullet.isDisappeared();
    }

    public final void controlSetDisappeared(boolean disappeared) {
        bullet.setDisappeared(disappeared);
    }

    public final boolean controlDistancePassing() {
        return bullet.isDistancePassed();
    }

    public final void controlReducingDistance(int movementX, int movementY) {
        bullet.reduceDistance(movementX, movementY);
    }

    public final Gunslinger controlGunslinger() {
        return bullet.getGunslingerName();
    }

    public final int controlGettingDamage() {
        return bullet.getDamage();
    }

    public final String controlGettingType() {
        return bullet.getType();
    }
}
