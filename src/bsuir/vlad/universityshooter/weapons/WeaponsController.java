package bsuir.vlad.universityshooter.weapons;

public class WeaponsController {
    private Weapon weapon;

    public WeaponsController(Weapon weapon) {
        this.weapon = weapon;
    }

    public final String controlGettingWeaponType(){
        return weapon.getType();
    }

    public final String controlGettingWeaponAttackType(){
        return weapon.getAttackType();
    }
}
