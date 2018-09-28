package bsuir.vlad.universityshooter;

public class BulletsController {
    Bullet bullet;

    public BulletsController(Bullet bullet) {
        this.bullet = bullet;
    }

    public final boolean controlDistance(int movementX, int movementY) {
        return bullet.isDistancePassed(movementX, movementY);
    }
}
