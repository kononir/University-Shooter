package bsuir.vlad.universityshooter;

public class Bot {
    private String type;
    private int health;
    private int defence;
    private Weapon weapon;

    public String getType() {
        return type;
    }

    public Bot(Weapon weapon) {
        health = 100;
        defence = 0;

        this.weapon = weapon;
    }
}
