package be.arcadeboard.api.game.events;

import be.arcadeboard.api.game.GameLobby;

public class GameLobbyFullEvent extends GameEvent {
    private GameLobby lobby = null;

    public GameLobbyFullEvent(GameLobby gameLobby) {
        super(gameLobby.getGame());
        setLobby(gameLobby);
    }

    public GameLobby getLobby() {
        return lobby;
    }

    public void setLobby(GameLobby lobby) {
        this.lobby = lobby;
    }
}
