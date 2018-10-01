package bsuir.vlad.universityshooter.characters;

import javafx.scene.layout.Pane;

import java.util.Timer;
import java.util.TimerTask;

public class BotsView extends CharacterView {
    private Bot bot;
    private PlayersView playersView;

    public Bot getBot() {
        return bot;
    }

    public BotsView(Bot bot, String type, PlayersView playersView) {
        super(type);
        this.bot = bot;
        this.playersView = playersView;

        updateAnimation(type + "_idle");
    }

    public final void updateBotsView() {
        Pane playersPane = playersView.getCharacterPane();

        if (!currentAnimation.isLock()) {
            if (characterPane.getBoundsInParent().intersects(playersPane.getBoundsInParent())) {
                meleeAttack();
            } else {
                updateAnimation(characterName + "_idle");

                currentAnimation.play();
            }
        }
    }

    public final void meleeAttack() {
        Pane playersPane = playersView.getCharacterPane();

        updateAnimation(characterName + "_attack");

        currentAnimation.lock();
        currentAnimation.play();

        int cycleDuration = (int) currentAnimation.getCycleDuration().toMillis();

        Timer damageTimer = new Timer();
        damageTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (characterPane.getBoundsInParent().intersects(playersPane.getBoundsInParent())) {
                    BotsController botsController = new BotsController(bot);

                    int receivedDamage = botsController.controlMelee();

                    Player player = playersView.getPlayer();
                    PlayersController playersController = new PlayersController(player);

                    playersController.controlStatusReducing(receivedDamage);

                    damageTimer.cancel();
                }
            }
        }, cycleDuration);
    }

    public final void setLocation(double x, double y) {
        characterPane.setLayoutX(x);
        characterPane.setLayoutY(y);
    }
}
