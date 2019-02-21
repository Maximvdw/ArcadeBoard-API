package be.arcadeboard.api.game.graphics;

import be.arcadeboard.api.resources.ColorResource;
import be.arcadeboard.api.resources.ResourceFont;
import be.arcadeboard.api.resources.ResourceIcon;
import be.arcadeboard.api.resources.ResourceImage;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Character based canvas
 * Can be used in any multiline text interface (Chat, Scoreboard, Hologram,...)
 */
public class CharacterCanvas extends Canvas {
    private String title = "";
    private CharacterPixel[][] characterPixels = null;

    public CharacterCanvas() {

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
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     *
     * @param title Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get horizontal lines
     *
     * @return horizontal lines
     */
    public List<String> getHorizontalLines() {
        List<String> lines = new ArrayList<String>();
        for (int y = 0; y < getHeight(); y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < characterPixels.length; x++) {
                CharacterPixel characterPixel = characterPixels[x][y];
                line.append(characterPixel.getContent());
            }
            lines.add(line.toString());
        }
        return lines;
    }

    /**
     * Draw a resource image
     *
     * @param x             X position
     * @param y             Y position
     * @param resourceImage Resource image
     */
    public void drawImage(int x, int y, ResourceImage resourceImage) {
        for (int i = 0; i < resourceImage.getIcons().length; i++) {
            for (int j = 0; j < resourceImage.getIcons()[i].length; j++) {
                ResourceIcon icon = resourceImage.getIcons()[i][j];
                drawPixel(x + j, y + i, icon);
            }
        }
    }


    /**
     * Draw a resource image
     *
     * @param x             X position
     * @param y             Y position
     * @param resourceImage Resource image
     * @param rotation      rotation
     */
    public void drawPixel(int x, int y, ResourceImage resourceImage, int rotation) {
        // TODO: Rotate array
        for (int i = 0; i < resourceImage.getIcons().length; i++) {
            for (int j = 0; j < resourceImage.getIcons()[i].length; j++) {
                ResourceIcon icon = resourceImage.getIcons()[i][j];
                drawPixel(x + j, y + i, icon, rotation);
            }
        }
    }


    /**
     * Draw a pixel
     *
     * @param x            X position
     * @param y            Y position
     * @param resourceIcon Resource icon
     */
    public void drawPixel(int x, int y, ResourceIcon resourceIcon) {
        drawPixel(x, y, new CharacterPixel(resourceIcon));
    }

    /**
     * Draw a pixel
     *
     * @param x            X position
     * @param y            Y position
     * @param resourceIcon Resource icon
     * @param rotation     rotation
     */
    public void drawPixel(int x, int y, ResourceIcon resourceIcon, int rotation) {
        drawPixel(x, y, new CharacterPixel(resourceIcon, rotation));
    }

    public void drawRectangle(int x, int y, int width, int height, ResourceIcon icon) {
        drawRectangle(x, y, width, height, new CharacterPixel(icon));
    }

    public void fillRectangle(int x, int y, int width, int height, ResourceIcon icon) {
        fillRectangle(x, y, width, height, new CharacterPixel(icon));
    }

    /**
     * Draw a line
     *
     * @param x1   X position 1
     * @param y1   Y position 1
     * @param x2   X position 2
     * @param y2   Y position 2
     * @param icon ResourceIcon
     */
    public void drawLine(int x1, int y1, int x2, int y2, ResourceIcon icon) {
        drawLine(x1, y1, x2, y2, new CharacterPixel(icon));
    }

    /**
     * Write a string
     *
     * @param x       X start position
     * @param y       Y start position
     * @param content content
     */
    public void writeString(int x, int y, String content) {
        // Clear characterPixels on the right
        for (int i = x + 1; i < getWidth(); i++) {
            characterPixels[i][y].setContent("");
        }
        drawPixel(x, y, new CharacterPixel(content));
    }

    /**
     * Write a string with a resource font
     *
     * @param x       X start position
     * @param y       Y start position
     * @param content content
     * @param font    resource font
     */
    public void writeString(int x, int y, String content, ResourceFont font) {
        content = ChatColor.translateAlternateColorCodes('&', content);
        String colors = ChatColor.getLastColors(content);
        content = ChatColor.stripColor(content);
        boolean requiresColorReset = !colors.isEmpty();
        assert x + content.length() < characterPixels.length;

        for (int i = 0; i < content.length(); i++) {
            Character character = content.charAt(i);
            if (i == 0) {
                if (character == ' ') {
                    characterPixels[x + i][y] = new CharacterPixel(colors + ColorResource.TRANSPARENT.toString());
                } else {
                    ResourceIcon fontCharacter = font.getCharacter(character);
                    if (fontCharacter != null) {
                        characterPixels[x + i][y] = new CharacterPixel(colors + fontCharacter);
                    }
                }
            } else if (i == content.length() - 1 && requiresColorReset) {
                if (character == ' ') {
                    characterPixels[x + i][y] = new CharacterPixel(ColorResource.TRANSPARENT.toString()  + ChatColor.RESET);
                } else {
                    ResourceIcon fontCharacter = font.getCharacter(character);
                    if (fontCharacter != null) {
                        characterPixels[x + i][y] = new CharacterPixel(String.valueOf(fontCharacter) + ChatColor.RESET);
                    }
                }
            } else {
                if (character == ' ') {
                    drawPixel(x + i, y, ColorResource.TRANSPARENT);
                } else {
                    drawPixel(x + i, y, font.getCharacter(character));
                }
            }
        }
    }

    /**
     * Get pixel matrix
     *
     * @return characterPixels pixel matrix
     */
    public CharacterPixel[][] getCharacterPixels() {
        return characterPixels;
    }

    /**
     * Set pixel matrix
     *
     * @param characterPixels pixel matrix
     */
    public void setCharacterPixels(CharacterPixel[][] characterPixels) {
        this.characterPixels = characterPixels;
    }

    /**
     * Clear the canvas
     */
    public void clear() {
        setCharacterPixels(new CharacterPixel[getWidth()][getHeight()]);
        fillRectangle(0, 0, getWidth(), getHeight(), new CharacterPixel());
    }

    /**
     * Draw a characterPixel
     *
     * @param x              X position
     * @param y              Y position
     * @param characterPixel CharacterPixel
     */
    public void drawPixel(int x, int y, CharacterPixel characterPixel) {
        if (x >= 0 && y >= 0 && x < getWidth() && y < getHeight()) {
            characterPixels[x][y] = characterPixel;
        }
    }


    /**
     * Draw a rectangle
     *
     * @param x              X position 1
     * @param y              Y position 1
     * @param width          Width of rectangle
     * @param height         Height of rectangle
     * @param characterPixel CharacterPixel
     */
    public void drawRectangle(int x, int y, int width, int height, CharacterPixel characterPixel) {
        for (int i = x; i < (x + width); i++) {
            drawPixel(i, y, characterPixel);
            drawPixel(i, y + height - 1, characterPixel);
        }
        for (int i = y; i < (y + height); i++) {
            drawPixel(x, i, characterPixel);
            drawPixel(x + width - 1, i, characterPixel);
        }
    }

    /**
     * Draw a filled rectangle
     *
     * @param x              X position 1
     * @param y              Y position 1
     * @param width          Width of rectangle
     * @param height         Height of rectangle
     * @param characterPixel CharacterPixel
     */
    public void fillRectangle(int x, int y, int width, int height, CharacterPixel characterPixel) {
        for (int i = x; i < (x + width); i++) {
            for (int z = y; z < (y + height); z++) {
                drawPixel(i, z, characterPixel);
            }
        }
    }

    /**
     * Draw a line
     *
     * @param x1             X position 1
     * @param y1             Y position 1
     * @param x2             X position 2
     * @param y2             Y position 2
     * @param characterPixel CharacterPixel
     */
    public void drawLine(int x1, int y1, int x2, int y2, CharacterPixel characterPixel) {
        float dl = 3.125e-3F;
        float l;
        float x = x1 + 0.5F;
        float y = y1 + 0.5F;
        float dx = Math.round(x2 - x1) * dl;
        float dy = Math.round(y2 - y1) * dl;

        for (l = 0.0F; l < 1.0; l += dl) {
            drawPixel((int) Math.floor(x),
                    (int) Math.floor(y), characterPixel);
            x += dx;
            y += dy;
        }
    }

    /**
     * Get pixel
     *
     * @param x X location
     * @param y Y location
     * @return CharacterPixel if valid location is provided
     */
    public CharacterPixel getPixel(int x, int y) {
        if (x >= 0 && y >= 0 && x < getWidth() && y < getHeight()) {
            return characterPixels[x][y];
        }
        return null;
    }

    /**
     * Draw an ellipse
     *
     * @param originX        Origin X position
     * @param originY        Origin Y position
     * @param height         circle height
     * @param width          circle width
     * @param characterPixel CharacterPixel
     */
    public void drawEllipse(int originX, int originY, int width, int height, CharacterPixel characterPixel) {
        for (int y = -height; y <= height; y++) {
            for (int x = -width; x <= width; x++) {
                if (x * x * height * height + y * y * width * width <= height * height * width * width)
                    drawPixel(originX + x, originY + y, characterPixel);
            }
        }
    }

    public static class CharacterPixel {
        public final static ResourceIcon DEFAULT_ICON = ColorResource.TRANSPARENT;

        private String content = "";

        public CharacterPixel() {
            setContent(DEFAULT_ICON.toString());
        }

        public CharacterPixel(String content) {
            setContent(content);
        }

        public CharacterPixel(ResourceIcon resourceIcon) {
            if (resourceIcon == null) {
                resourceIcon = DEFAULT_ICON;
            }
            setContent(resourceIcon.toString());
        }

        public CharacterPixel(ResourceIcon resourceIcon, int rotation) {
            if (resourceIcon != null)
                setContent(resourceIcon.toString(rotation));
        }

        /**
         * Get pixel content
         *
         * @return content
         */
        public String getContent() {
            return content;
        }

        /**
         * Set pixel content
         *
         * @param content Content
         */
        public void setContent(String content) {
            this.content = content;
        }
    }

}
