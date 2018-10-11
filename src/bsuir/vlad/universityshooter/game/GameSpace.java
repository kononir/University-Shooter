package bsuir.vlad.universityshooter.game;

import bsuir.vlad.universityshooter.activeobjects.characters.*;
import bsuir.vlad.universityshooter.game.keyboard.Keyboard;
import bsuir.vlad.universityshooter.weapons.Bullet;
import bsuir.vlad.universityshooter.weapons.BulletsView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameSpace {
    private final Pane pane;
    private Scene scene;
    private Keyboard keyboard;
    private HUD hud;
    private Menu menu;
    private PlayersView playersView;
    private List<BulletsView> bulletsViewList;
    private final List<BotsView> botsViewList;
    private AnimationTimer updatingTimer;

    public Scene getScene() {
        return scene;
    }

    public Pane getPane() {
        return pane;
    }

    public Menu getMenu() {
        return menu;
    }

    public AnimationTimer getUpdatingTimer() {
        return updatingTimer;
    }

    public PlayersView getPlayersView() {
        return playersView;
    }

    public GameSpace(Menu menu) {
        this.menu = menu;

        pane = new Pane();
        int PaneWidth = 600;
        int PaneHeight = 400;
        pane.setPrefSize(PaneWidth, PaneHeight);

        scene = new Scene(pane);

        bulletsViewList = new ArrayList<>();
        botsViewList = new ArrayList<>();

        updatingTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateScene();
            }
        };
        updatingTimer.start();
    }

    public void addHUD(Player player) {
        hud = new HUD(player, pane);
    }

    public void addKeyboard() {
        keyboard = new Keyboard(this);
    }

    public void addPlayersView(Player player, double playerX, double playerY) {
        playersView = new PlayersView(player, playerX, playerY, botsViewList);

        addKeyboard();

        Pane playersPane = playersView.getCharacterPane();
        pane.getChildren().add(playersPane);
    }

    public void addBulletsView(Bullet bullet, CharacterView gunslingerView) {
        BulletsView bulletsView = new BulletsView(bullet, gunslingerView, playersView, botsViewList);
        bulletsViewList.add(bulletsView);

        Pane bulletsPane = bulletsView.getBulletsPane();
        pane.getChildren().add(bulletsPane);
    }

    public void addBotsView(Bot bot, double botX, double botY) {
        BotsController controller = new BotsController(bot);
        String botType = controller.controlGettingBotType();

        BotsView botsView = new BotsView(bot, botX, botY, botType, playersView);

        synchronized (botsViewList) {
            botsViewList.add(botsView);
        }
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

        synchronized (botsViewList) {
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
        }

        if (!playersView.getCharacterPane().isVisible()) {
            updatingTimer.stop();
            Level.getBotsGenerator().shutdown();

            Player player = playersView.getPlayer();
            PlayersController playersController = new PlayersController(player);
            Profile profile = playersController.controlGettingProfile();

            menu.createGameOverScene(profile);
        }

        hud.updateHUD();
    }
}
