package be.arcadeboard.api.game.menu;

import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.game.GameLobby;
import be.arcadeboard.api.player.GamePlayer;

public abstract class MultiplayerMenu extends DefaultMenu{
    public MultiplayerMenu(Game game, GamePlayer gamePlayer) {
        super(game, gamePlayer);

        // Get existing or new lobby
        GameLobby lobby = game.getAvailableLobby();
        if (lobby == null){
            lobby = game.createLobby();
        }

        // Add player to the lobby
        lobby.addPlayer(gamePlayer);
    }

    public abstract void onLobbyFull();
}
