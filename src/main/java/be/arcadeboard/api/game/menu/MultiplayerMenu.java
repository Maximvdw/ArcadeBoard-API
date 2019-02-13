package be.arcadeboard.api.game.menu;

import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.game.GameLobby;
import be.arcadeboard.api.game.graphics.CharacterCanvas;
import be.arcadeboard.api.game.menu.items.MenuItem;
import be.arcadeboard.api.game.menu.items.SelectableMenuItem;
import be.arcadeboard.api.player.GamePlayer;
import be.arcadeboard.api.resources.ColorResource;
import be.arcadeboard.api.resources.ResourceFont;

import java.util.List;

/**
 * UNDER DEVELOPMENT
 */
public class MultiplayerMenu extends Menu {
    private final double WAITING_DOTS_DELAY = 0.5;

    private boolean flash = false;
    private Menu previousState = null;

    public MultiplayerMenu(final Game game, Menu previousMenu) {
        super(game);
        this.previousState = previousMenu;

        addItem(new SelectableMenuItem("Cancel") {
            @Override
            public void onClick(GamePlayer gamePlayer) {
                game.getPlugin().getGameManager().getGameLobbyByGame(game).removePlayer(gamePlayer);
                removePlayer(gamePlayer);
                previousState.addPlayer(gamePlayer);
                game.debug("Cancelling multiplayer menu for player: " + gamePlayer.getName());
            }
        });
    }

    @Override
    public Menu addPlayer(GamePlayer gamePlayer) {
        super.addPlayer(gamePlayer);
        Game game = getGame();
        GameLobby lobby = game.getPlugin().getGameManager().getGameLobbyByGame(game);
        if (lobby == null) {
            game.info(gamePlayer.getName() + " created a new lobby");
            game.getPlugin().getGameManager().createGameLobby(game, gamePlayer);
        } else {
            // Join lobby
            game.removePlayer(gamePlayer);
            removePlayer(gamePlayer);
            game.info(gamePlayer.getName() + " joined existing lobby");

            lobby.addPlayer(gamePlayer);
            lobby.getGame().addPlayer(gamePlayer);
            List<GamePlayer> players = lobby.getGame().getPlayers();
            for (GamePlayer gp : players) {
                previousState.removePlayer(gp);
            }
        }
        return this;
    }

    public void onForceQuit(GamePlayer gamePlayer) {

    }

    public void handle(GamePlayer gamePlayer) {
        CharacterCanvas canvas = (CharacterCanvas) getGame().getCanvas(gamePlayer);
        if (getGame().getTicks() % 2 == 0) {
            flash = !flash;
        }

        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.drawPixel(x, y, ColorResource.TRANSPARENT);
            }
        }

        if (getGame().getTicks() % WAITING_DOTS_DELAY == 0) {
            if (getMenuStateInteger(gamePlayer, "waiting") == 3) {
                setMenuState(gamePlayer, "waiting", 1);
            } else {
                setMenuState(gamePlayer, "waiting", getMenuStateInteger(gamePlayer, "waiting") + 1);
            }
        }

        String waitingDots = "";
        for (int i = 0; i < getMenuStateInteger(gamePlayer, "waiting"); i++) {
            waitingDots += ".";
        }
        canvas.writeString(3, 1, "Waiting for players " + waitingDots, ResourceFont.getDefaultFont());

        canvas.setTitle("&f&lMultiplayer");
        int i = 0;
        for (MenuItem item : getItems()) {
            if (item.equals(getSelectedItem(gamePlayer))) {
                canvas.writeString(2, 7 + i, (flash ? "&7" : "") + "> " + item.getName() + " <", ResourceFont.getDefaultFont());
            } else {
                canvas.writeString(4, 7 + i, item.getName(), ResourceFont.getDefaultFont());
            }
            i++;
        }

    }
}
