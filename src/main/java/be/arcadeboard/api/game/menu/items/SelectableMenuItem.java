package be.arcadeboard.api.game.menu.items;

import be.arcadeboard.api.player.GamePlayer;

public abstract class SelectableMenuItem extends MenuItem{
    public SelectableMenuItem(String name) {
        super(name);
    }

    public abstract void onClick(GamePlayer gamePlayer);
}
