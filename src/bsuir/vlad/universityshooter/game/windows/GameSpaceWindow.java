package bsuir.vlad.universityshooter.game.windows;

import bsuir.vlad.universityshooter.activeobjects.characters.*;
import bsuir.vlad.universityshooter.activeobjects.characters.bot.*;
import bsuir.vlad.universityshooter.activeobjects.characters.player.Player;
import bsuir.vlad.universityshooter.activeobjects.characters.player.PlayersController;
import bsuir.vlad.universityshooter.activeobjects.characters.player.PlayersView;
import bsuir.vlad.universityshooter.game.Difficulty;
import bsuir.vlad.universityshooter.game.HUD;
import bsuir.vlad.universityshooter.game.profile.Profile;
import bsuir.vlad.universityshooter.game.keyboard.Keyboard;
import bsuir.vlad.universityshooter.weapons.bullet.Bullet;
import bsuir.vlad.universityshooter.weapons.bullet.BulletsView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameSpaceWindow {
    private BotsGenerator botsGenerator;
    private Stage stage;
    private static Pane pane;
    private Keyboard keyboard;
    private HUD hud;
    private static PlayersView playersView;
    private static List<BulletsView> bulletsViewList;
    private static List<BotsView> botsViewList;
    private AnimationTimer updatingTimer;

    GameSpaceWindow(Stage stage, String difficultyString, String profileName) {
        Difficulty difficulty = Difficulty.valueOf(difficultyString.toUpperCase());
        Profile profile = new Profile(profileName, difficulty);
        Player player = new Player(profile);

        pane = new Pane();
        int PaneWidth = 600;
        int PaneHeight = 400;
        pane.setPrefSize(PaneWidth, PaneHeight);

        Scene scene = new Scene(pane);

        this.stage = stage;
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(closeEvent -> {
            Pane playersPane = playersView.getCharacterPane();
            playersPane.setVisible(false);
        });

        bulletsViewList = new ArrayList<>();
        botsViewList = new ArrayList<>();

        addPlayersView(player);
        addKeyboard();
        addHUD(player);

        initializeBotsGenerator(difficulty);

        updatingTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateScene();
            }
        };
        updatingTimer.start();
    }

    private void initializeBotsGenerator(Difficulty difficulty) {
        botsGenerator = new BotsGenerator(pane, difficulty);

        BotsGeneratorController controller = new BotsGeneratorController(botsGenerator);
        controller.controlStartingBotsGeneration();
    }

    private void addHUD(Player player) {
        hud = new HUD(player, pane);
    }

    private void addKeyboard() {
        keyboard = new Keyboard(stage.getScene(), playersView);
    }

    private void addPlayersView(Player player) {
        double playerX = 0;
        double playerY = 0;

        playersView = new PlayersView(player, playerX, playerY, botsViewList);

        Pane playersPane = playersView.getCharacterPane();
        pane.getChildren().add(playersPane);
    }

    public static void addBulletsView(Bullet bullet, CharacterView gunslingersView) {
        BulletsView bulletsView = new BulletsView(bullet, gunslingersView, playersView, botsViewList);
        bulletsViewList.add(bulletsView);

        Pane bulletsPane = bulletsView.getBulletsPane();
        pane.getChildren().add(bulletsPane);
    }

    public static void addBotsView(Bot bot, double botX, double botY) {
        BotsController controller = new BotsController(bot);
        String botType = controller.controlGettingBotType();

        BotsView botsView = new BotsView(bot, botX, botY, botType, playersView);

        botsViewList.add(botsView);
    }

    private void updateScene() {
        keyboard.updateKeyboard();

        Iterator<BulletsView> bulletsViewIterator = bulletsViewList.iterator();

        while (bulletsViewIterator.hasNext()) {
            BulletsView bulletsView = bulletsViewIterator.next();

            if (!bulletsView.getBulletsPane().isVisible()) {
                bulletsViewIterator.remove();
            } else {
                bulletsView.updateBulletsView();
            }
        }

        Iterator<BotsView> botsViewIterator = botsViewList.iterator();

        while (botsViewIterator.hasNext()) {
            BotsView botsView = botsViewIterator.next();

            Pane botsPane = botsView.getCharacterPane();

            if (!botsPane.isVisible()) {
                botsViewIterator.remove();
            } else {
                pane.getChildren().remove(botsPane);
                pane.getChildren().add(botsPane);

                botsView.updateBotsView();
            }
        }

        if (!playersView.getCharacterPane().isVisible()) {
            updatingTimer.stop();

            BotsGeneratorController botsGeneratorController = new BotsGeneratorController(botsGenerator);
            botsGeneratorController.controlStoppingBotsGeneration();

            Player player = playersView.getPlayer();
            PlayersController playersController = new PlayersController(player);
            Profile profile = playersController.controlGettingProfile();

            stage.close();
            new GameOverWindow(stage, profile);
        }

        hud.updateHUD();
    }
}
