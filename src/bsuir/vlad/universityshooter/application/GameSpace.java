package bsuir.vlad.universityshooter.application;

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

        Pane playersPane = playersView.getPlayersPane();
        pane.getChildren().add(playersPane);
    }

    public static void addNewBullet(Bullet bullet, double bulletsPaneAngle) {
        BulletsView bulletsView = new BulletsView(bullet, bulletsPaneAngle);
        bulletsViewList.add(bulletsView);

        Pane bulletsPane = bulletsView.getBulletsPane();
        pane.getChildren().add(bulletsPane);

        Pane playersPane = playersView.getPlayersPane();
        bulletsView.setRelativeLocation(playersPane);
    }

    private void updateScene() {
        playersView.updatePlayersView();

        for(BulletsView bulletsView: bulletsViewList) {
            bulletsView.updateBulletsView();
        }
    }
}
