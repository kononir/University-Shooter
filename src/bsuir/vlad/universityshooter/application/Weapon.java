package bsuir.vlad.universityshooter.application;

public class Weapon {
    private final String type;
    private final int damage;
    private final int distance;
    private final int maxHoldersNumber;
    private int holdersNumber;
    private final int maxHoldersAmmo;
    private int holdersAmmo;

    public String getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }

    public int getDistance() {
        return distance;
    }

    public Weapon(String type, int damage, int distance, int maxHoldersNumber, int maxHoldersAmmo) {
        this.type = type;
        this.damage = damage;
        this.distance = distance;
        this.maxHoldersNumber = maxHoldersNumber;
        this.holdersNumber = maxHoldersNumber;
        this.maxHoldersAmmo = maxHoldersAmmo;
        this.holdersAmmo = maxHoldersAmmo;
    }

    public void reduceDamage() {
        int difficultyCoefficient = 1; //находим в файле необходимые данные по типу оружия
    }

    public void reduceHoldersAmmo() {
        --holdersAmmo;
    }

    public void increaseHoldersAmmo() {
        holdersAmmo = maxHoldersAmmo;
    }

    public final boolean isHolderEmpty() {
        return holdersAmmo == 0;
    }
}
