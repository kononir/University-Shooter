package bsuir.vlad.universityshooter.game;

import bsuir.vlad.universityshooter.activeobjects.characters.Bot;
import bsuir.vlad.universityshooter.activeobjects.characters.CharacterView;
import bsuir.vlad.universityshooter.activeobjects.characters.Player;
import bsuir.vlad.universityshooter.weapons.Bullet;

class LevelsController {
    private GameSpace gameSpace;

    LevelsController(GameSpace gameSpace) {
        this.gameSpace = gameSpace;
    }

    LevelsController(Menu menu) {
        gameSpace = new GameSpace(menu);
    }

    GameSpace controlGettingGameSpace() {
        return gameSpace;
    }

    void controlAddingBulletsView(Bullet bullet, CharacterView gunslingersView) {
        gameSpace.addBulletsView(bullet, gunslingersView);
    }

    void controlAddingPlayersView(Player player, double playerX, double playerY) {
        gameSpace.addPlayersView(player, playerX, playerY);
    }

    void controlAddingBotsView(Bot bot, double botX, double botY) {
        gameSpace.addBotsView(bot, botX, botY);
    }
}
