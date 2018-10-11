package bsuir.vlad.universityshooter.weapons;

public class WeaponsController {
    private Weapon weapon;

    public WeaponsController(Weapon weapon) {
        this.weapon = weapon;
    }

    public final String controlGettingType() {
        return weapon.getType();
    }

    public final String controlGettingAttackType() {
        return weapon.getAttackType();
    }

    public final int controlGettingHoldersNumber() {
        return weapon.getHoldersNumber();
    }

    public final int controlGettingHoldersAmmo() {
        return weapon.getHoldersAmmo();
    }

    public final boolean controlHasEnoughHolders() {
        return weapon.hasEnoughHolders();
    }

    public final void controlReloading() {
        weapon.reload();
    }
}
