package bsuir.vlad.universityshooter.application;

public class BulletsController {
    Bullet bullet;

    public BulletsController(Bullet bullet) {
        this.bullet = bullet;
    }

    public boolean controlDistance(int movementX, int movementY) {
        return bullet.isDistancePassed(movementX, movementY);
    }
}
