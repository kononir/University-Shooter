package bsuir.vlad.universityshooter.characters;

public class BotsController {
    Bot bot;

    public BotsController(Bot bot) {
        this.bot = bot;
    }

    public final String controlBotType() {
        return bot.getType();
    }

    public final int controlMelee() {
        return bot.getWeaponInHands().getDamage();
    }

    public final boolean controlStatusReducing(int receivedDamage) {
        return bot.reduceStatus(receivedDamage);
    }
}
