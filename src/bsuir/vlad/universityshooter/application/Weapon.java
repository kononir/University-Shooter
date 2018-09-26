package bsuir.vlad.universityshooter.application;

public class Weapon {
    private final String type;
    private final int damage;
    private final int distance;
    private final int maxAmmo;
    private int ammo;

    public String getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }

    public int getDistance() {
        return distance;
    }

    public Weapon(String type, int damage, int distance, int maxAmmo) {
        this.type = type;
        this.damage = damage;
        this.distance = distance;
        this.maxAmmo = maxAmmo;
        this.ammo = maxAmmo;
    }

    public void reduceDamage() {
        int difficultyCoefficient = 1; //находим в файле необходимые данные по типу оружия
    }
}
