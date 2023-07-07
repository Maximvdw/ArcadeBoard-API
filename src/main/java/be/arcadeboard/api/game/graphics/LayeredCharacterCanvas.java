package be.arcadeboard.api.game.graphics;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Layered character based canvas
 * Can be used in any multiline text interface (Chat, Scoreboard, Hologram,...)
 * that support chat components
 */
public class LayeredCharacterCanvas extends Canvas {
    private final Map<Integer, CharacterCanvas> layers = new TreeMap<>();

    public LayeredCharacterCanvas(int width, int height) {
        super(width, height);
        clear();
    }

    public CharacterCanvas getLayer(int layer) {
        if (!layers.containsKey(layer)) {
            layers.put(layer, new CharacterCanvas(this.getWidth(), this.getHeight()));
        }
        return layers.get(layer);
    }

    public Collection<CharacterCanvas> getLayers() {
        return layers.values();
    }

    @Override
    public void clear() {
        layers.clear();
        layers.put(0, new CharacterCanvas(this.getWidth(), this.getHeight()));
    }
}
