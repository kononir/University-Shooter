package bsuir.vlad.universityshooter.weapons;

public class Bullet {
    private final String type;
    private final String gunslingerName;
    private final int damage;
    private int distance;

    public String getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }

    public String getGunslingerName() {
        return gunslingerName;
    }

    public Bullet(String type, String gunslingerName, int damage, int distance) {
        this.type = type;
        this.gunslingerName = gunslingerName;
        this.damage = damage;
        this.distance = distance;
    }

    public boolean isDistancePassed(int movementX, int movementY) {
        boolean distancePassed;

        if (distance == 0) {
            distancePassed = true;
        }
        else {
            reduceDistance(movementX, movementY);
            distancePassed = false;
        }

        return distancePassed;
    }

    private void reduceDistance(int movementX, int movementY) {
        int powMovementX = (int) Math.pow(movementX, 2);
        int powMovementY = (int) Math.pow(movementY, 2);
        int delta = (int) Math.sqrt(powMovementX + powMovementY);

        distance = distance - delta;
    }
}
