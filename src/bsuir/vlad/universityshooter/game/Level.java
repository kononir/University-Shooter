package bsuir.vlad.universityshooter.game;

import bsuir.vlad.universityshooter.activeobjects.characters.*;
import bsuir.vlad.universityshooter.weapons.Bullet;
import bsuir.vlad.universityshooter.weapons.Weapon;
import bsuir.vlad.universityshooter.weapons.WeaponsFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Level {
    private Player player;
    private Profile profile;
    private List<Bot> botList;
    private static BotsGenerator botsGenerator;
    private HashMap<String, Integer> scoreMap;
    private static List<Weapon> playerWeaponList;
    private List<Weapon> botWeaponList;
    private static List<Bullet> bulletList;
    private static GameSpace gameSpace;

    public Profile getProfile() {
        return profile;
    }

    public GameSpace getGameSpace() {
        return gameSpace;
    }

    public static BotsGenerator getBotsGenerator() {
        return botsGenerator;
    }

    public static List<Weapon> getPlayerWeaponList() {
        return playerWeaponList;
    }

    public Level(Menu menu, Profile profile) {
        this.profile = profile;

        String playerWeaponsFilePath = "../resources/configs/player_weapon_characteristics.xml";
        playerWeaponList = new WeaponsFile(
                getClass().getResourceAsStream(playerWeaponsFilePath)
        ).loadWeapons();

        String botWeaponsFilePath = "../resources/configs/bot_weapon_characteristics.xml";
        botWeaponList = new WeaponsFile(
                getClass().getResourceAsStream(botWeaponsFilePath)
        ).loadWeapons();

        String botScoreFilePath = "../resources/configs/bot_score.xml";
        scoreMap = new BotScoreFile(
                getClass().getResourceAsStream(botScoreFilePath)
        ).loadBotsScore();

        botList = new ArrayList<>();
        bulletList = new ArrayList<>();

        LevelsController levelsController = new LevelsController(menu);
        gameSpace = levelsController.controlGettingGameSpace();

        initialize();
    }

    private void initialize() {
        double playerX = 0;
        double playerY = 0;

        addPlayer(playerX, playerY);

        long generationSpeed = 5;

        botsGenerator = new BotsGenerator(this);
        botsGenerator.start(generationSpeed, TimeUnit.SECONDS);
    }

    private void addPlayer(double playerX, double playerY) {
        player = new Player(profile);

        LevelsController levelsController = new LevelsController(gameSpace);
        levelsController.controlAddingPlayersView(player, playerX, playerY);
    }

    public final void addBot(String type, double botX, double botY, boolean movable, int difficultyCoefficient) {
        Weapon weaponInHands = findMatchWeapon(type);
        int score = findMatchScore(type);

        Bot bot = new Bot(type, weaponInHands, score, movable, difficultyCoefficient);

        LevelsController levelsController = new LevelsController(gameSpace);
        levelsController.controlAddingBotsView(bot, botX, botY);
    }

    public static void addBullet(Bullet bullet, CharacterView gunslingersView) {
        bulletList.add(bullet);

        LevelsController levelsController = new LevelsController(gameSpace);

        levelsController.controlAddingBulletsView(bullet, gunslingersView);
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
}
