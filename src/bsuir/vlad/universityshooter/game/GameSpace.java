package bsuir.vlad.universityshooter.game;

import bsuir.vlad.universityshooter.characters.*;
import bsuir.vlad.universityshooter.weapons.Bullet;
import bsuir.vlad.universityshooter.weapons.BulletsView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameSpace {
    private Pane pane;
    private Scene scene;
    private HUD hud;
    private PlayersView playersView;
    private List<BulletsView> bulletsViewList;
    private List<BotsView> botsViewList;

    public Scene getScene() {
        return scene;
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

    public void addPlayersView(Player player) {
        playersView = new PlayersView(player, scene, botsViewList);

        Pane playersPane = playersView.getCharacterPane();
        pane.getChildren().add(playersPane);
        playersPane.setLayoutX(0);
        playersPane.setLayoutY(0);
    }

    public void addBulletsView(Bullet bullet, double bulletsPaneAngle) {
        BulletsView bulletsView = new BulletsView(bullet, bulletsPaneAngle, playersView, botsViewList);
        bulletsViewList.add(bulletsView);

        Pane bulletsPane = bulletsView.getBulletsPane();
        pane.getChildren().add(bulletsPane);

        Pane playersPane = playersView.getCharacterPane();
        bulletsView.setRelativeLocation(playersPane);
    }

    public void addBotsView(Bot bot) {
        BotsController controller = new BotsController(bot);
        String botType = controller.controlBotType();

        BotsView botsView = new BotsView(bot, botType, playersView);
        botsViewList.add(botsView);

        Pane botsPane = botsView.getCharacterPane();
        pane.getChildren().add(botsPane);

        double paneWidth = pane.getPrefWidth();
        double paneHeight = pane.getPrefHeight();

        double x = paneWidth / 2;
        double y = paneHeight / 2;

        botsView.setLocation(x, y);
    }

    private void updateScene() {
        hud.updateHUD();

        playersView.updatePlayersView();

        int listSize = bulletsViewList.size();

        for (int index = 0; index < listSize; index++) {
            BulletsView bulletsView = bulletsViewList.get(index);

            bulletsView.updateBulletsView();
            if (!bulletsView.getBulletsPane().isVisible()) {
                bulletsViewList.remove(bulletsView);

                index--;
                listSize--;
            }
        }

        botsViewList.forEach(BotsView::updateBotsView);
    }
}
