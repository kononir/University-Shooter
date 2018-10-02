package bsuir.vlad.universityshooter.game;

import bsuir.vlad.universityshooter.activeobjects.characters.Bot;
import bsuir.vlad.universityshooter.activeobjects.characters.Player;
import bsuir.vlad.universityshooter.weapons.Bullet;
import bsuir.vlad.universityshooter.weapons.Weapon;
import bsuir.vlad.universityshooter.weapons.WeaponsFile;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private String name;
    private Player player;
    private Profile profile;
    private List<Bot> botList;
    private static List<Weapon> playerWeaponList;
    private static List<Weapon> botWeaponList;
    private static List<Bullet> bulletList;
    private static GameSpace gameSpace;

    public GameSpace getGameSpace() {
        return gameSpace;
    }

    public static List<Weapon> getPlayerWeaponList() {
        return playerWeaponList;
    }

    public static List<Weapon> getBotWeaponList() {
        return botWeaponList;
    }

    public Level(String name, Profile profile) {
        this.name = name;
        this.profile = profile;

        String playerWeaponsFilePath = "src/bsuir/vlad/universityshooter/resources/configs/player_weapon_characteristics.xml";
        playerWeaponList = new WeaponsFile(playerWeaponsFilePath).loadWeapons();

        String botWeaponsFilePath = "src/bsuir/vlad/universityshooter/resources/configs/bot_weapon_characteristics.xml";
        botWeaponList = new WeaponsFile(botWeaponsFilePath).loadWeapons();



        botList = new ArrayList<>();
        bulletList = new ArrayList<>();

        gameSpace = new GameSpace();

        initialize();
    }

    private void initialize() {
        Pane pane = gameSpace.getPane();
        double paneWidth = pane.getPrefWidth();
        double paneHeight = pane.getPrefHeight();

        addPlayerWithHUD(0, 0);
        addBot("zombie_teacher", 300, 200, true, "meleeAttack");
        addBot("zombie_teacher", 400, 100, true, "meleeAttack");
        addBot("zombie_teacher", 600, 400, true, "meleeAttack");
    }

    private void addPlayerWithHUD(double playerX, double playerY) {
        player = new Player();

        gameSpace.addPlayersView(player, playerX, playerY);
        gameSpace.addHUD(player);
    }

    private void addBot(String type, double botX, double botY, boolean movable, String attackType) {
        Bot bot = new Bot(type, movable, attackType);

        gameSpace.addBotsView(bot, botX, botY);
    }

    public static void addBullet(Bullet bullet, double bulletsPaneAngle) {
        bulletList.add(bullet);
        gameSpace.addBulletsView(bullet, bulletsPaneAngle);
    }
}
