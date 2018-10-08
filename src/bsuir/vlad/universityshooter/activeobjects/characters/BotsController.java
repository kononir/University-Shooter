package bsuir.vlad.universityshooter.activeobjects.characters;

import bsuir.vlad.universityshooter.weapons.Bullet;
import bsuir.vlad.universityshooter.weapons.Weapon;

public class BotsController {
    Bot bot;

    public BotsController(Bot bot) {
        this.bot = bot;
    }

    public final String controlGettingBotType() {
        return bot.getType();
    }

    public final int controlMelee() {
        return bot.getWeaponInHands().getDamage();
    }

    public final Bullet controlShooting() {
        return bot.shootFromWeapon();
    }

    public final boolean controlStatusReducing(int receivedDamage) {
        return bot.reduceStatus(receivedDamage);
    }

    public final boolean controlGettingMovable() {
        return bot.isMovable();
    }

    public final Weapon controlGettingWeaponInHands() {
        return bot.getWeaponInHands();
    }

    public final int controlGettingScore() {
        return bot.getScore();
    }
}
