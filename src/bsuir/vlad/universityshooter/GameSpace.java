package bsuir.vlad.universityshooter;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameSpace {
    private static Pane pane;
    private static Scene scene;
    private static PlayersView playersView;
    private static List<BulletsView> bulletsViewList;
    private static List<BotsView> botsViewList;

    public Scene getScene() {
        return scene;
    }

    public GameSpace(Profile profile) {
        pane = new Pane();
        int PaneWidth = 600;
        int PaneHeight = 400;
        pane.setPrefSize(PaneWidth, PaneHeight);

        scene = new Scene(pane);
        addNewPlayer(profile);

        bulletsViewList = new ArrayList();

        AnimationTimer animTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateScene();
            }
        };
        animTimer.start();
    }

    public static void addNewPlayer(Profile profile) {
        Player player = profile.getProfilePlayer();
        playersView = new PlayersView(player, scene);

        Pane playersPane = playersView.getCharacterPane();
        pane.getChildren().add(playersPane);
    }

    public static void addNewBullet(Bullet bullet, double bulletsPaneAngle) {
        BulletsView bulletsView = new BulletsView(bullet, bulletsPaneAngle);
        bulletsViewList.add(bulletsView);

        Pane bulletsPane = bulletsView.getBulletsPane();
        pane.getChildren().add(bulletsPane);

        Pane playersPane = playersView.getCharacterPane();
        bulletsView.setRelativeLocation(playersPane);
    }

    public static void addNewBot(Bot bot) {
        String botType = bot.getType();

        BotsView botsView = new BotsView(bot, botType);
        botsViewList.add(botsView);

        Pane botsPane = botsView.getCharacterPane();
        pane.getChildren().add(botsPane);


    }

    private void updateScene() {
        playersView.updatePlayersView();

        for(BulletsView bulletsView: bulletsViewList) {
            bulletsView.updateBulletsView();
        }
    }
}
