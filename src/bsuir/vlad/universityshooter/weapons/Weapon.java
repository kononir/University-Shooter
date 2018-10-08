package bsuir.vlad.universityshooter.weapons;

public class Weapon {
    private final String type;
    private final String attackType;
    private final int damage;
    private final int distance;
    private final int maxHoldersNumber;
    private int holdersNumber;
    private final int maxHoldersAmmo;
    private int holdersAmmo;

    public String getType() {
        return type;
    }

    public String getAttackType() {
        return attackType;
    }

    public int getDamage() {
        return damage;
    }

    public int getDistance() {
        return distance;
    }

    public int getHoldersNumber() {
        return holdersNumber;
    }

    public int getHoldersAmmo() {
        return holdersAmmo;
    }

    public Weapon(String type, String attackType, int damage, int distance, int maxHoldersNumber, int maxHoldersAmmo) {
        this.type = type;
        this.attackType = attackType;
        this.damage = damage;
        this.distance = distance;
        this.maxHoldersNumber = maxHoldersNumber;
        this.holdersNumber = 1;
        this.maxHoldersAmmo = maxHoldersAmmo;
        this.holdersAmmo = maxHoldersAmmo;
    }

    public void reduceDamage() {
        int difficultyCoefficient = 1; //находим в файле необходимые данные по типу оружия
    }

    public void reduceHoldersAmmo() {
        --holdersAmmo;
    }

    public boolean reload() {
        boolean isEnoughHolders;

        if (holdersNumber != 0) {
            holdersAmmo = maxHoldersAmmo;
            --holdersNumber;

            isEnoughHolders = true;
        } else {
            isEnoughHolders = false;
        }

        return isEnoughHolders;
    }

    public void increaseHoldersNumber() {
        ++holdersNumber;
    }

    public final boolean isHolderEmpty() {
        return holdersAmmo == 0;
    }
}
