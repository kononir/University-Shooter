import java.util.ArrayList;
import java.util.List;

public class Player {
    private int health;
    private int defence;
    private List<Weapon> listOfWeapons;

    public Player(){
        health = 100;
        defence = 0;

        String weaponsType = "knife";
        int difficultyCoefficient = 1;
        int damage = 35;
        int distance = 0;
        int maxAmmo = 0;

        Weapon knife = new Weapon(weaponsType, difficultyCoefficient, damage, distance, maxAmmo);
        listOfWeapons = new ArrayList<>();
        listOfWeapons.add(knife);
    }

    public final void increaseHealth(int newHealth){
        health += newHealth;
    }

    public final void reduceHealth(int newHealth){
        health -= newHealth;
    }

    public final void increaseDefence(int newDefence){
        defence += newDefence;
    }

    public final void reduceDefence(int newDefence){
        defence -= newDefence;
    }
}
