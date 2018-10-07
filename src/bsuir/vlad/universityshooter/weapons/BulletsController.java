package bsuir.vlad.universityshooter.weapons;

public class BulletsController {
    Bullet bullet;

    public BulletsController(Bullet bullet) {
        this.bullet = bullet;
    }

    public final boolean controlDistancePassing() {
        return bullet.isDistancePassed();
    }

    public final void controlReducingDistance(int movementX, int movementY) {
        bullet.reduceDistance(movementX, movementY);
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
