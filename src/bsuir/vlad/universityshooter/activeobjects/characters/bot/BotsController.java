package bsuir.vlad.universityshooter.activeobjects.characters.bot;

import bsuir.vlad.universityshooter.weapons.bullet.Bullet;
import bsuir.vlad.universityshooter.weapons.Weapon;

public class BotsController {
    private Bot bot;

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

    public final void controlStatusReducing(int receivedDamage) {
        bot.reduceStatus(receivedDamage);
    }

    public final boolean controlIsDead() {
        return bot.isDead();
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
