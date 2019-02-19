package be.arcadeboard.api.game.menu;

import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.game.graphics.CharacterCanvas;
import be.arcadeboard.api.player.GamePlayer;

public class DefaultMenu extends Menu {
    public DefaultMenu(Game game, GamePlayer gamePlayer) {
        super(game, gamePlayer);
    }

    public void onForceQuit(GamePlayer gamePlayer) {
        getGame().stop();
    }

    public void handle(GamePlayer gamePlayer) {

    }

}
