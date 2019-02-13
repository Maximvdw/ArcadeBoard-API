package be.arcadeboard.api.resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ResourceFont {
    private static ResourceFont defaultFont = null;

    /** Default font **/
    static {
        try {
            defaultFont = new ResourceFont("default", ResourceFont.class.getResourceAsStream("/fonts/default.ttf"), 20);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
    }

    private Map<Character, ResourceIcon> characters = new HashMap<Character, ResourceIcon>();
    private String name = "";
    private Font font = null;
    private int fontSize = 16;
    private Color fontColor = Color.WHITE;

    public ResourceFont(String name, InputStream is, float fontSize) throws IOException, FontFormatException {
        this(name, Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(fontSize));
    }

    public ResourceFont(String name, Font font) throws IOException {
        setName(name);
        this.font = font;
        this.fontSize = font.getSize();
        addAllCharacters();
    }

    /**
     * Get resource icon character by character
     *
     * @param character Text character
     * @return resource icon
     */
    public ResourceIcon getCharacter(Character character) {
        if (characters.containsKey(character)) {
            return characters.get(character);
        }
        return null;
    }

    /**
     * Convert a string to the new font
     *
     * @param original Original plain text
     * @return converted
     */
    public String convert(String original) {
        StringBuilder converted = new StringBuilder();
        for (char c : original.toCharArray()){
            converted.append(getCharacter(c).toString());
        }
        return converted.toString();
    }

    public void addAllCharacters() throws IOException {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789<>()[]{}?!/\\-_=+*&%^@!?.,;'".toCharArray();
        for (char c : chars) {
            if (font.canDisplay(c)) {
                addCharacter(c);
            }
        }
    }

    public void addCharacter(Character character) throws IOException {
        this.characters.put(character, new ResourceIcon("FONT_" + name + "_" + character, getCharacterAsImage(character)));
    }

    private BufferedImage getCharacterAsImage(Character character) {
        if (font == null)
            return null;
        BufferedImage newImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics g = newImage.getGraphics();
        g.setFont(font);
        g.setColor(fontColor);
        if (font.canDisplay(character)) {
            // Write character
            FontMetrics fontMetrics = g.getFontMetrics();
            int width = fontMetrics.charWidth(character);
            int height = fontMetrics.getHeight();
            int x = (int) (8 - Math.floor(width / 2));
            int y = 16 - ((16 - height) / 2) - 3;
            g.drawString(character.toString(), x, y);
        }
        return newImage;
    }

    /**
     * Get resource icon characters
     *
     * @return mapping between characters and resource icons
     */
    public Map<Character, ResourceIcon> getCharacters() {
        return characters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public Color getFontColor() {
        return fontColor;
    }

    /**
     * Get the default resource font
     *
     * @return default resource font
     */
    public static ResourceFont getDefaultFont() {
        return defaultFont;
    }
}
