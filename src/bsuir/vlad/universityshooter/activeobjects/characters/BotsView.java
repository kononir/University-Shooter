package bsuir.vlad.universityshooter.activeobjects.characters;

import javafx.scene.layout.Pane;

import java.util.TimerTask;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class BotsView extends CharacterView {
    private Bot bot;
    private PlayersView playersView;

    public Bot getBot() {
        return bot;
    }

    public BotsView(Bot bot, double botX, double botY, String type, PlayersView playersView) {
        super(type, botX, botY);
        this.bot = bot;
        this.playersView = playersView;

        updateAnimation(type + "_idle");
    }

    public void updateBotsView() {
        BotsController controller = new BotsController(bot);

        Pane playersPane = playersView.getCharacterPane();

        String attackType = controller.controlGettingAttackType();
        boolean movable = controller.controlGettingMovable();

        if (!currentAnimation.isLock()) {
            if (characterPane.getBoundsInParent().intersects(playersPane.getBoundsInParent())
                    && attackType.equals("meleeAttack")
            ) {
                meleeAttack();
            } else if (attackType.equals("shoot")) {
                shoot();
            }
        }

        if (!currentAnimation.isLock()) {
            if (!characterPane.getBoundsInParent().intersects(playersPane.getBoundsInParent())
                    && movable
            ) {
                move();
            } else if (!movable) {
                updateAnimation(characterName + "_idle");

                currentAnimation.play();
            }
        }
    }

    @Override
    public final void meleeAttack() {
        Pane playersPane = playersView.getCharacterPane();

        updateAnimation(characterName + "_attack");

        currentAnimation.lock();
        currentAnimation.play();

        int cycleDuration = (int) currentAnimation.getCycleDuration().toMillis();

        int corePoolSize = 0;

        ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(corePoolSize);

        service.schedule(new TimerTask() {
            @Override
            public void run() {
                if (characterPane.getBoundsInParent().intersects(playersPane.getBoundsInParent())) {
                    BotsController botsController = new BotsController(bot);

                    int receivedDamage = botsController.controlMelee();

                    Player player = playersView.getPlayer();
                    PlayersController playersController = new PlayersController(player);

                    playersController.controlStatusReducing(receivedDamage);
                }


            }
        }, cycleDuration, TimeUnit.MILLISECONDS);
    }

    @Override
    public final void shoot() {

    }

    public final void move() {
        updateAnimation(characterName + "_move");

        currentAnimation.play();

        double botsPainX = characterPane.getTranslateX();
        double botsPainY = characterPane.getTranslateY();

        Pane playersPane = playersView.getCharacterPane();

        double playerPainX = playersPane.getTranslateX();
        double playerPainY = playersPane.getTranslateY();

        int movementX = 1;
        int movementY = 1;

        boolean botStandsHorizontal = (botsPainY == playerPainY);
        boolean botStandsVertical = (botsPainX == playerPainX);
        boolean botStandsBelow = (botsPainY - playerPainY) > 0;
        boolean botStandsRight = (botsPainX - playerPainX) > 0;
        boolean botStandsAbove = (playerPainY - botsPainY) > 0;
        boolean botStandsLeft = (playerPainX - botsPainX) > 0;

        if (botStandsHorizontal && botStandsLeft) {
            double currentMovementAngle = 0;
            updateMovementAngle(currentMovementAngle);

            moveRight(movementX);
        } else if (botStandsHorizontal && botStandsRight) {
            double currentMovementAngle = 180;
            updateMovementAngle(currentMovementAngle);

            moveLeft(movementX);
        } else if (botStandsVertical && botStandsBelow) {
            double currentMovementAngle = 270;
            updateMovementAngle(currentMovementAngle);

            moveUp(movementY);
        } else if (botStandsVertical && botStandsAbove) {
            double currentMovementAngle = 90;
            updateMovementAngle(currentMovementAngle);

            moveDown(movementY);
        }

        if (botStandsBelow && botStandsRight) {
            double currentMovementAngle = 225;
            updateMovementAngle(currentMovementAngle);

            moveUp(movementY);
            moveLeft(movementX);
        } else if (botStandsBelow && botStandsLeft) {
            double currentMovementAngle = 315;
            updateMovementAngle(currentMovementAngle);

            moveUp(movementY);
            moveRight(movementX);
        } else if (botStandsAbove && botStandsRight) {
            double currentMovementAngle = 135;
            updateMovementAngle(currentMovementAngle);

            moveDown(movementY);
            moveLeft(movementX);
        } else if (botStandsAbove && botStandsLeft) {
            double currentMovementAngle = 45;
            updateMovementAngle(currentMovementAngle);

            moveDown(movementY);
            moveRight(movementX);
        }
    }


}
