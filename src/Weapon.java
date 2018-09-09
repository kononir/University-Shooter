import javafx.scene.image.ImageView;

public class Weapon {
    final String type;
    final int damage;
    final int distance;
    final int maxAmmo;
    int ammo;

    public Weapon(String type, int coefficient, int damage, int distance, int maxAmmo){
        this.type = type;
        this.damage = damage / coefficient;
        this.distance = distance;
        this.maxAmmo = maxAmmo;
        this.ammo = maxAmmo;
    }
}
