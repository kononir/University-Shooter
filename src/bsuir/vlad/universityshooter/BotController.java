package bsuir.vlad.universityshooter;

public class BotController {
    Bot bot;

    public BotController(Bot bot) {
        this.bot = bot;
    }

    public final String controlBotType() {
        return bot.getType();
    }
}
