package bsuir.vlad.universityshooter.game;

import bsuir.vlad.universityshooter.activeobjects.characters.player.Player;
import bsuir.vlad.universityshooter.activeobjects.characters.player.PlayersController;
import bsuir.vlad.universityshooter.game.profile.Profile;
import bsuir.vlad.universityshooter.game.profile.ProfileController;
import bsuir.vlad.universityshooter.weapons.Weapon;
import bsuir.vlad.universityshooter.weapons.WeaponsController;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class HUD {
    private Player player;
    private VBox statusBarsVBox;
    private Label ammoLabel;
    private Label scoreLabel;

    public HUD(Player player, Pane pane) {
        this.player = player;

        int rectWidth = 100, rectHeight = 10;

        Color healthRectColor = Color.RED;
        Rectangle healthBar = createStatusBar(rectWidth, rectHeight, healthRectColor);

        Color defenceRectColor = Color.GREY;
        Rectangle defenceBar = createStatusBar(rectWidth, rectHeight, defenceRectColor);

        statusBarsVBox = new VBox(healthBar, defenceBar);

        int vBoxSpace = 3, insetSize = 5;
        double hudOpacity = 0.5;

        statusBarsVBox.setSpacing(vBoxSpace);
        statusBarsVBox.setPadding(new Insets(insetSize));
        statusBarsVBox.setOpacity(hudOpacity);

        String beginScoreString = "0";
        int labelMaxWidth = 90;
        int fontSize = 15;
        Color textColor = Color.BLUE;

        scoreLabel = new Label(beginScoreString);
        scoreLabel.setOpacity(hudOpacity);
        scoreLabel.setTextAlignment(TextAlignment.RIGHT);
        scoreLabel.setMaxWidth(labelMaxWidth);
        scoreLabel.setFont(Font.font(fontSize));
        scoreLabel.setPadding(new Insets(insetSize));
        scoreLabel.setTextFill(textColor);

        int labelMaxHeight = 25;
        ammoLabel = new Label();
        ammoLabel.setOpacity(hudOpacity);
        ammoLabel.setMaxHeight(labelMaxHeight);
        ammoLabel.setFont(Font.font(fontSize));
        ammoLabel.setPadding(new Insets(insetSize));
        ammoLabel.setTextFill(textColor);

        pane.getChildren().addAll(statusBarsVBox, scoreLabel, ammoLabel);

        scoreLabel.setLayoutX(pane.getPrefWidth() - scoreLabel.getMaxWidth());
        ammoLabel.setLayoutY(pane.getPrefHeight() - labelMaxHeight);
    }

    private Rectangle createStatusBar(int rectWidth, int rectHeight, Color rectColor) {
        Rectangle statusBar = new Rectangle(rectWidth, rectHeight);
        statusBar.setStroke(Color.BLACK);
        statusBar.setFill(rectColor);

        return statusBar;
    }

    public void updateHUD() {
        updateStatusBars();
        updateScoreLabel();
        updateAmmoLabel();
    }

    private void updateStatusBars() {
        statusBarsVBox.toFront();

        PlayersController controller = new PlayersController(player);

        int health = controller.controlGettingHealthLevel();

        Rectangle healthBar = (Rectangle) statusBarsVBox.getChildren().get(0);
        int displayedHealth = (int) healthBar.getWidth();

        if (health != displayedHealth) {
            healthBar.setWidth(health);
        }

        int defence = controller.controlGettingDefenceLevel();

        Rectangle defenceBar = (Rectangle) statusBarsVBox.getChildren().get(1);
        int displayedDefence = (int) defenceBar.getWidth();

        if (defence != displayedDefence) {
            defenceBar.setWidth(defence);
        }
    }

    private void updateScoreLabel() {
        scoreLabel.toFront();

        PlayersController playersController = new PlayersController(player);
        Profile profile = playersController.controlGettingProfile();

        ProfileController profileController = new ProfileController(profile);
        long score = profileController.controlGettingScore();

        scoreLabel.setText(String.valueOf(score));
    }

    private void updateAmmoLabel() {
        ammoLabel.toFront();

        PlayersController playersController = new PlayersController(player);
        Weapon weaponInHands = playersController.controlGettingWeaponInHands();

        WeaponsController weaponsController = new WeaponsController(weaponInHands);
        int holdersNumber = weaponsController.controlGettingHoldersNumber();
        int holdersAmmo = weaponsController.controlGettingHoldersAmmo();

        ammoLabel.setText(holdersAmmo + "/" + holdersNumber);
    }
}
