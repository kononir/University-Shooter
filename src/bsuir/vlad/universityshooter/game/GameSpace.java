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
    private Pane pane;
    private Scene scene;
    private Keyboard keyboard;
    private HUD hud;
    private PlayersView playersView;
    private List<BulletsView> bulletsViewList;
    private List<BotsView> botsViewList;

    public Scene getScene() {
        return scene;
    }

    public Pane getPane() {
        return pane;
    }

    public GameSpace() {
        pane = new Pane();
        int PaneWidth = 600;
        int PaneHeight = 400;
        pane.setPrefSize(PaneWidth, PaneHeight);

        scene = new Scene(pane);

        bulletsViewList = new ArrayList<>();
        botsViewList = new ArrayList<>();

        AnimationTimer animTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateScene();
            }
        };
        animTimer.start();
    }

    public void addHUD(Player player) {
        hud = new HUD(player, pane);
    }

    public void addKeyboard() {
        keyboard = new Keyboard(scene, playersView);
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
        botsViewList.add(botsView);

        Pane botsPane = botsView.getCharacterPane();
        pane.getChildren().add(botsPane);

        double paneWidth = pane.getPrefWidth();
        double paneHeight = pane.getPrefHeight();

        double x = paneWidth / 2;
        double y = paneHeight / 2;

        botsPane.setTranslateX(botX);
        botsPane.setTranslateY(botY);
    }

    private void updateScene() {
        hud.updateHUD();

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

            if (!botsView.getCharacterPane().isVisible()) {
                botsViewIterator.remove();
            } else {
                botsView.updateBotsView();
            }
        }
    }
}
