package be.arcadeboard.api.game.menu;

import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.game.GameState;
import be.arcadeboard.api.game.menu.items.MenuItem;
import be.arcadeboard.api.game.menu.items.SelectableMenuItem;
import be.arcadeboard.api.player.GamePlayer;
import be.arcadeboard.api.player.events.KeyDownEvent;
import be.arcadeboard.api.player.events.KeyUpEvent;
import be.arcadeboard.api.player.events.MouseClickEvent;
import be.arcadeboard.api.player.events.MouseMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Menu implements Game.KeyListener, GameState, Game.MouseListener {
    private List<MenuItem> items = new ArrayList<MenuItem>();
    private Map<GamePlayer, MenuItem> selectedItem = new HashMap<GamePlayer, MenuItem>();
    private Map<GamePlayer, Map<String, Object>> menuStates = new HashMap<GamePlayer, Map<String, Object>>();
    private Game game = null;
    private float cursorX, cursorY = 0;

    public Menu(Game game) {
        setGame(game);
        game.addKeyListener(this);
        game.addMouseListener(this);
    }

    /**
     * Assign the menu to a player
     *
     * @param player Player to assign the menu to
     * @return Menu instance
     */
    public Menu addPlayer(GamePlayer player) {
        menuStates.put(player, new HashMap<String, Object>());
        getGame().setPlayerState(player, this);
        if (!getItems().isEmpty()) {
            setSelectedItem(player, getItems().get(0));
        }
        return this;
    }

    /**
     * Remove the player from the menu
     *
     * @param player Game player menu
     * @return Menu instance
     */
    public Menu removePlayer(GamePlayer player) {
        getGame().setPlayerState(player, null);
        menuStates.remove(player);
        return this;
    }

    public List<MenuItem> getItems() {
        return this.items;
    }

    public Menu addItem(MenuItem item) {
        item.setY(this.items.size());
        this.items.add(item);
        return this;
    }

    public Boolean getMenuStateBoolean(GamePlayer gamePlayer, String key) {
        Object state = getMenuState(gamePlayer, key);
        if (state == null) {
            return false;
        } else {
            return (Boolean) state;
        }
    }

    public Integer getMenuStateInteger(GamePlayer gamePlayer, String key) {
        Object state = getMenuState(gamePlayer, key);
        if (state == null) {
            return 0;
        } else {
            return (Integer) state;
        }
    }

    public Object getMenuState(GamePlayer gamePlayer, String key) {
        return menuStates.get(gamePlayer).get(key);
    }

    public void setMenuState(GamePlayer gamePlayer, String key, Object value) {
        menuStates.get(gamePlayer).put(key, value);
    }

    /**
     * When the player forcefully quits the menu
     *
     * @param gamePlayer game player quit
     */
    public abstract void onForceQuit(GamePlayer gamePlayer);

    public void onKeyDown(KeyDownEvent event) {
        GamePlayer gamePlayer = event.getGamePlayer();
        if (!menuStates.containsKey(gamePlayer))
            return;

        // Avoid other menu's triggering
        event.setCancelled(true);
        int currentIdx = getItems().indexOf(getSelectedItem(event.getGamePlayer()));
        switch (event.getKey()) {
            case UP:
                if (currentIdx != 0) {
                    // Move up
                    currentIdx--;
                    setSelectedItem(event.getGamePlayer(), getItems().get(currentIdx));
                }
                break;
            case DOWN:
                if (currentIdx != getItems().size() - 1) {
                    // Move down
                    currentIdx++;
                    setSelectedItem(event.getGamePlayer(), getItems().get(currentIdx));
                }
                break;
            case JUMP:
                if (getSelectedItem(gamePlayer) instanceof SelectableMenuItem) {
                    ((SelectableMenuItem) getSelectedItem(gamePlayer)).onClick(gamePlayer);
                }
                break;
            case SNEAK:
                onForceQuit(gamePlayer);
                removePlayer(gamePlayer);
                break;
        }
    }

    public void onKeyUp(KeyUpEvent event) {

    }

    public MenuItem getSelectedItem(GamePlayer gamePlayer) {
        return selectedItem.get(gamePlayer);
    }

    public void setSelectedItem(GamePlayer gamePlayer, MenuItem selectedItem) {
        this.selectedItem.put(gamePlayer, selectedItem);
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
        if (!menuStates.containsKey(gamePlayer))
            return;
        int currentIdx = getItems().indexOf(getSelectedItem(event.getGamePlayer()));

        float oldCursorY = cursorY;
        float oldCursorX = cursorX;

        cursorX += (int) (event.getDeltaX() / 15);
        cursorY += (int) (event.getDeltaY() / 15);

        int deltaY = (int) (cursorY - oldCursorY);
        if (deltaY > 0) {
            if (currentIdx != 0) {
                // Move up
                currentIdx--;
                setSelectedItem(event.getGamePlayer(), getItems().get(currentIdx));
            }
        } else if (deltaY < 0) {
            if (currentIdx != getItems().size() - 1) {
                // Move down
                currentIdx++;
                setSelectedItem(event.getGamePlayer(), getItems().get(currentIdx));
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
        if (!menuStates.containsKey(gamePlayer))
            return;

        if (event.getMouseKey().equals(MouseClickEvent.MouseKey.LEFT)) {
            if (getSelectedItem(gamePlayer) instanceof SelectableMenuItem) {
                ((SelectableMenuItem) getSelectedItem(gamePlayer)).onClick(gamePlayer);
            }
        }
    }
}
