package bsuir.vlad.universityshooter.activeobjects.characters;

import bsuir.vlad.universityshooter.game.Level;
import bsuir.vlad.universityshooter.game.Profile;

import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BotsGenerator {
    private Level level;
    private ScheduledExecutorService executor;

    public BotsGenerator(Level level) {
        this.level = level;
    }

    public void start(long generationSpeed, TimeUnit timeUnit) {
        long startingAt = 0;

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(new TimerTask() {
            @Override
            public void run() {
                String botType = generateBotType();
                int botX = generateBotX();
                int botY = generateBotY();
                boolean movable = generateAbilityToMove();

                Profile profile = level.getProfile();
                int coefficient = profile.createCoefficient();

                level.addBot(botType, botX, botY, movable, coefficient);
            }
        }, startingAt, generationSpeed, timeUnit);
    }

    public void shutdown() {
        executor.shutdown();
    }

    private String generateBotType() {
        Random random = new Random(System.currentTimeMillis());

        int min = 0, max = 9, bound = 4;

        int randomNumber = min + random.nextInt(max - min + 1);

        String botType;

        if (randomNumber >= min && randomNumber < bound) {
            botType = "bomber";
        } else if (randomNumber >= bound && randomNumber < max) {
            botType = "zombie_teacher";
        } else if (randomNumber == max) {
            botType = "soldier";
        } else {
            botType = "bomber";

            try {
                throw new IllegalArgumentException("Bots Generator Exception");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        return botType;
    }

    private int generateBotX() {
        Random random = new Random(System.currentTimeMillis());

        int paneWidth = (int) level.getGameSpace().getPane().getPrefWidth();
        int indent = 100;

        int min = 0, max = paneWidth - indent;

        return min + random.nextInt(max - min + 1);
    }

    private int generateBotY() {
        Random random = new Random(System.currentTimeMillis());

        int paneHeight = (int) level.getGameSpace().getPane().getPrefHeight();
        int indent = 150;

        int min = 0, max = paneHeight - indent;

        return min + random.nextInt(max - min + 1);
    }

    private boolean generateAbilityToMove() {
        Random random = new Random(System.currentTimeMillis());

        int min = 0, max = 9, bound = 7;

        int randomNumber = min + random.nextInt(max - min + 1);

        boolean movable;

        if (randomNumber >= min && randomNumber < bound) {
            movable = true;
        } else if (randomNumber >= bound && randomNumber <= max) {
            movable = false;
        } else {
            movable = true;

            try {
                throw new IllegalArgumentException("Bots Generator Exception");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        return movable;
    }
}
