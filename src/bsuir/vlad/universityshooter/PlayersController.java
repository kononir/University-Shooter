package bsuir.vlad.universityshooter;

public class PlayersController {
    Player player;

    public PlayersController(Player player) {
        this.player = player;
    }

    public final boolean controlChangingWeapon(String weaponType) {
        return player.changeWeapon(weaponType);
    }

    public final String controlTypeOfWeaponInHands(){
        return player.getTypeOfWeaponInHands();
    }

    public final Bullet controlShooting() {
        return player.shootFromWeapon();
    }

    public final void controlReloading() {
        player.reloadWeapon();
    }
}
