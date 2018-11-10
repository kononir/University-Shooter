package bsuir.vlad.universityshooter.activeobjects.characters.bot;

import bsuir.vlad.universityshooter.game.Difficulty;
import bsuir.vlad.universityshooter.game.windows.GameSpaceWindow;
import bsuir.vlad.universityshooter.weapons.Weapon;
import bsuir.vlad.universityshooter.weapons.WeaponsFile;
import javafx.scene.layout.Pane;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BotsGenerator {
    private HashMap<String, Integer> scoreMap;
    private List<Weapon> botWeaponList;
    private Pane gameSpacePane;
    private int difficultyCoefficient;
    private ScheduledExecutorService executor;

    public BotsGenerator(Pane gameSpacePane, Difficulty difficulty) {
        this.gameSpacePane = gameSpacePane;
        this.difficultyCoefficient = difficulty.getCoefficient();

        String botWeaponsFilePath = "resources/configs/bot_weapon_characteristics.xml";
        botWeaponList = new WeaponsFile(botWeaponsFilePath).loadWeapons();

        String botScoreFilePath = "resources/configs/bot_score.xml";
        scoreMap = new BotScoreFile(botScoreFilePath).loadBotsScore();
    }

    void start(long generationSpeed, TimeUnit timeUnit) {
        long startingAt = 0;

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(new TimerTask() {
            @Override
            public void run() {
                String type = generateBotType();
                int botX = generateBotX();
                int botY = generateBotY();
                boolean movable = generateAbilityToMove();

                addBot(type, botX, botY, movable);
            }
        }, startingAt, generationSpeed, timeUnit);
    }

    void shutdown() {
        executor.shutdown();
    }

    private void addBot(String type, double botX, double botY, boolean movable) {
        Weapon weaponInHands = findMatchWeapon(type);
        int score = findMatchScore(type);

        Bot bot = new Bot(type, weaponInHands, score, movable, difficultyCoefficient);

        GameSpaceWindow.addBotsView(bot, botX, botY);
    }

    private Weapon findMatchWeapon(String type) {
        int first = 0;

        List<Weapon> findingWeaponsList
                = botWeaponList.stream().filter(weapon -> weapon.getType().startsWith(type))
                .collect(Collectors.toList());

        return findingWeaponsList.get(first);
    }

    private int findMatchScore(String type) {
        return scoreMap.get(type);
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

        int paneWidth = (int) gameSpacePane.getPrefWidth();
        int indent = 100;

        int min = 0, max = paneWidth - indent;

        return min + random.nextInt(max - min + 1);
    }

    private int generateBotY() {
        Random random = new Random(System.currentTimeMillis());

        int paneHeight = (int) gameSpacePane.getPrefHeight();
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
