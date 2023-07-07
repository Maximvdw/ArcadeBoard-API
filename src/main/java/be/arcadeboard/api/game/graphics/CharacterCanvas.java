package be.arcadeboard.api.game.graphics;

import be.arcadeboard.api.resources.*;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Character based canvas
 * Can be used in any multiline text interface (Chat, Scoreboard, Hologram,...)
 * that only support the default Minecraft font
 */
public class CharacterCanvas extends ResourceIconCanvas<CharacterCanvas.CharacterPixel> {
    private CharacterPixel[] title = null;

    @Deprecated
    public CharacterCanvas() {
        this(0, 0);
    }

    public CharacterCanvas(int width, int height) {
        super(width, height);
        clear();
    }

    /**
     * Get title
     *
     * @return title
     */
    @Deprecated
    public String getTitle() {
        if (this.title == null || this.title.length == 0) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes(
                '&',
                String.join("", (CharSequence) Arrays.stream(this.title).map(CharacterPixel::getContent)));
    }

    public CharacterPixel[] getTitlePixels() {
        return this.title;
    }

    /**
     * Set title
     *
     * @param title Title
     */
    @Deprecated
    public void setTitle(String title) {
        setTitle(title, ResourceFont.getDefaultFont());
    }

    /**
     * Set title
     *
     * @param title Title
     * @param font Resource font
     */
    public void setTitle(String title, ResourceFont font) {
        setTitle(stringToPixels(title, font));
    }

    /**
     * Set title
     *
     * @param pixels Character pixels
     */
    public void setTitle(CharacterPixel[] pixels) {
        this.title = pixels;
    }

    /**
     * Get horizontal lines
     *
     * @return horizontal lines
     */
    @Deprecated
    public List<String> getHorizontalLines() {
        List<String> lines = new ArrayList<String>();
        for (int y = 0; y < getHeight(); y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0 ; x < getWidth() ; x++) {
                CharacterPixel pixel = getPixel(x, y);
                line.append(pixel.getContent());
            }
            lines.add(line.toString());
        }
        return lines;
    }

    @Deprecated
    public CharacterPixel[][] getCharacterPixels() {
        return this.getPixels();
    }

    /**
     * Character pixel is a single pixel in the character canvas
     */
    public static class CharacterPixel extends ResourceIconPixel {
        public final static ResourceIcon DEFAULT_ICON = ColorResource.TRANSPARENT;

        public CharacterPixel() {
            this(DEFAULT_ICON);
        }

        public CharacterPixel(String content) {
            super(content);
        }

        public CharacterPixel(ResourceImage resourceIcon) {
            this(resourceIcon, 0);
        }

        public CharacterPixel(ResourceImage resourceIcon, int rotation) {
            super(resourceIcon != null ? resourceIcon : DEFAULT_ICON, rotation);
        }
    }
}
