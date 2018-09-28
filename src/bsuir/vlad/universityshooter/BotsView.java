package bsuir.vlad.universityshooter;

public class BotsView extends Character {
    private Bot bot;

    public BotsView(Bot bot, String type) {
        super("src/bsuir/vlad/universityshooter/resources/configs/" + type + "_animation_characteristics.xml");
        this.bot = bot;

        updateAnimation(type + "_idle");
    }
}
