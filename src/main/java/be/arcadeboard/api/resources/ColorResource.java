package be.arcadeboard.api.resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * PresetColor
 * Created by Maxim on 18/01/2018.
 */
public class ColorResource extends ResourceIcon {
    private static List<ColorResource> values = new ArrayList<ColorResource>();

    public final static ColorResource BLACK = addFromAWTColor("BLACK", new Color(0, 0, 0));
    public final static ColorResource DARK_BLUE = addFromAWTColor("DARK_BLUE", new Color(0, 0, 170));
    public final static ColorResource DARK_BLUE_SHADE = addFromAWTColor("DARK_BLUE_SHADE", new Color(0, 0, 42));
    public final static ColorResource DARK_GREEN = addFromAWTColor("DARK_GREEN", new Color(0, 170, 0));
    public final static ColorResource DARK_GREEN_SHADE = addFromAWTColor("DARK_GREEN_SHADE", new Color(0, 42, 0));
    public final static ColorResource DARK_AQUA = addFromAWTColor("DARK_AQUA", new Color(0, 170, 170));
    public final static ColorResource DARK_AQUA_SHADE = addFromAWTColor("DARK_AQUA_SHADE", new Color(0, 42, 42));
    public final static ColorResource DARK_RED = addFromAWTColor("DARK_RED", new Color(170, 0, 0));
    public final static ColorResource DARK_RED_SHADE = addFromAWTColor("DARK_RED_SHADE", new Color(42, 0, 0));
    public final static ColorResource DARK_PURPLE = addFromAWTColor("DARK_PURPLE", new Color(170, 0, 170));
    public final static ColorResource DARK_PURPLE_SHADE = addFromAWTColor("DARK_PURPLE_SHADE", new Color(42, 0, 42));
    public final static ColorResource GOLD = addFromAWTColor("GOLD", new Color(255, 170, 0));
    public final static ColorResource GOLD_SHADE = addFromAWTColor("GOLD_SHADE", new Color(42, 42, 0));
    public final static ColorResource GRAY = addFromAWTColor("GRAY", new Color(170, 170, 170));
    public final static ColorResource GRAY_SHADE = addFromAWTColor("GRAY_SHADE", new Color(42, 42, 42));
    public final static ColorResource DARK_GRAY = addFromAWTColor("DARK_GRAY", new Color(85, 85, 85));
    public final static ColorResource DARK_GRAY_SHADE = addFromAWTColor("DARK_GRAY_SHADE", new Color(21, 21, 21));
    public final static ColorResource BLUE = addFromAWTColor("BLUE", new Color(85, 85, 255));
    public final static ColorResource BLUE_SHADE = addFromAWTColor("BLUE", new Color(21, 21, 63));
    public final static ColorResource GREEN = addFromAWTColor("GREEN", new Color(85, 255, 85));
    public final static ColorResource GREEN_SHADE = addFromAWTColor("GREEN_SHADE", new Color(21, 63, 21));
    public final static ColorResource AQUA = addFromAWTColor("AQUA", new Color(85, 255, 255));
    public final static ColorResource AQUA_SHADE = addFromAWTColor("AQUA_SHADE", new Color(21, 63, 63));
    public final static ColorResource RED = addFromAWTColor("RED", new Color(255, 85, 85));
    public final static ColorResource RED_SHADE = addFromAWTColor("RED_SHADE", new Color(63, 21, 21));
    public final static ColorResource LIGHT_PURPLE = addFromAWTColor("LIGHT_PURPLE", new Color(255, 85, 255));
    public final static ColorResource LIGHT_PURPLE_SHADE = addFromAWTColor("LIGHT_PURPLE_SHADE", new Color(63, 21, 63));
    public final static ColorResource YELLOW = addFromAWTColor("YELLOW", new Color(255, 255, 85));
    public final static ColorResource YELLOW_SHADE = addFromAWTColor("YELLOW_SHADE", new Color(63, 63, 21));
    public final static ColorResource WHITE = addFromAWTColor("WHITE", new Color(255, 255, 255));
    public final static ColorResource WHITE_SHADE = addFromAWTColor("WHITE_SHADE", new Color(63, 63, 63));
    public final static ColorResource TRANSPARENT = addFromAWTColor("TRANSPARENT", new Color(0, 0, 0, 0));

    private ColorResource(String name, BufferedImage image) throws IOException {
        super(name, image);
    }

    /**
     * Create new resource icon from AWT color
     *
     * @param name  Resource icon name
     * @param color AWT color
     * @throws IOException
     */
    public ColorResource(String name, Color color) throws IOException {
        this(name, getPixelImage(color));
    }

    /**
     * Add a preset color
     *
     * @param name  Preset color name
     * @param color Color name
     * @return Preset color
     */
    private static ColorResource addFromAWTColor(String name, Color color) {
        try {
            ColorResource icon = new ColorResource(name, getPixelImage(color));
            values.add(icon);
            return icon;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BufferedImage getPixelImage(Color color) {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, 16, 16);
        g.dispose();
        return image;
    }

    /**
     * Get all preset colors
     * @return preset colors
     */
    public static List<ColorResource> values() {
        return values;
    }
}
