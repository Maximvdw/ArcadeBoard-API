package be.arcadeboard.api.game.menu;

import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.game.GamePlayerState;
import be.arcadeboard.api.game.menu.items.MenuItem;
import be.arcadeboard.api.game.menu.items.SelectableMenuItem;
import be.arcadeboard.api.player.GamePlayer;
import be.arcadeboard.api.player.events.KeyDownEvent;
import be.arcadeboard.api.player.events.KeyUpEvent;
import be.arcadeboard.api.player.events.MouseClickEvent;
import be.arcadeboard.api.player.events.MouseMoveEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu implements Game.KeyListener, GamePlayerState, Game.MouseListener {
    private List<MenuItem> items = new ArrayList<MenuItem>();
    private SelectableMenuItem selectedItem = null;
    private Game game = null;
    private GamePlayer gamePlayer = null;
    private float cursorX, cursorY = 0;

    public Menu(Game game, GamePlayer gamePlayer) {
        setGame(game);
        setGamePlayer(gamePlayer);

        game.addKeyListener(this);
        game.addMouseListener(this);
    }

    public List<MenuItem> getItems() {
        return this.items;
    }

    public Menu addItem(MenuItem item) {
        item.setY(this.items.size());
        this.items.add(item);
        if (selectedItem == null) {
            setSelectedItem((SelectableMenuItem) item);
        }
        return this;
    }

    /**
     * When the player forcefully quits the menu
     *
     * @param gamePlayer game player quit
     */
    public abstract void onForceQuit(GamePlayer gamePlayer);

    public void onKeyDown(KeyDownEvent event) {
        GamePlayer gamePlayer = event.getGamePlayer();
        if (!getGamePlayer().equals(gamePlayer)) {
            return;
        }
        if (getSelectedItem() == null) {
            return;
        }

        // Avoid other menu's triggering
        event.setCancelled(true);
        int currentIdx = getItems().indexOf(getSelectedItem());
        switch (event.getKey()) {
            case UP:
                if (currentIdx != 0) {
                    // Move up
                    currentIdx--;
                    setSelectedItem((SelectableMenuItem) getItems().get(currentIdx));
                }
                break;
            case DOWN:
                if (currentIdx != getItems().size() - 1) {
                    // Move down
                    currentIdx++;
                    setSelectedItem((SelectableMenuItem) getItems().get(currentIdx));
                }
                break;
            case JUMP:
                if (getSelectedItem() != null) {
                    getSelectedItem().onClick(gamePlayer);
                }
                break;
            case SNEAK:
                onForceQuit(gamePlayer);
                break;
        }
    }

    public void onKeyUp(KeyUpEvent event) {

    }

    public SelectableMenuItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(SelectableMenuItem selectedItem) {
        this.selectedItem = selectedItem;
    }

    /**
     * Handle menu for game player
     *
     * @param gamePlayer Game player
     */
    public abstract void handle(GamePlayer gamePlayer);

    /**
     * Get game
     *
     * @return game
     */
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * On mouse move
     *
     * @param event MouseMoveEvent
     */
    public void onMouseMove(MouseMoveEvent event) {
        GamePlayer gamePlayer = event.getGamePlayer();
        if (!getGamePlayer().equals(gamePlayer)) {
            return;
        }
        if (getSelectedItem() == null) {
            return;
        }

        int currentIdx = getItems().indexOf(getSelectedItem());

        float oldCursorY = cursorY;
        float oldCursorX = cursorX;

        cursorX += (int) (event.getDeltaX() / 15);
        cursorY += (int) (event.getDeltaY() / 15);

        int deltaY = (int) (cursorY - oldCursorY);
        if (deltaY > 0) {
            if (currentIdx != 0) {
                // Move up
                currentIdx--;
                setSelectedItem((SelectableMenuItem) getItems().get(currentIdx));
            }
        } else if (deltaY < 0) {
            if (currentIdx != getItems().size() - 1) {
                // Move down
                currentIdx++;
                setSelectedItem((SelectableMenuItem) getItems().get(currentIdx));
            }
        }
    }

    /**
     * On mouse click
     *
     * @param event MouseClickEvent
     */
    public void onMouseClick(MouseClickEvent event) {
        GamePlayer gamePlayer = event.getGamePlayer();
        if (!getGamePlayer().equals(gamePlayer)) {
            return;
        }

        if (event.getMouseKey().equals(MouseClickEvent.MouseKey.LEFT)) {
            getSelectedItem().onClick(gamePlayer);
        }
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    private final void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
}
