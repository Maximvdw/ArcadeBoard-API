package be.arcadeboard.api.game.graphics;

import be.arcadeboard.api.resources.ColorResource;
import be.arcadeboard.api.resources.ResourceFont;
import be.arcadeboard.api.resources.ResourceIcon;
import be.arcadeboard.api.resources.ResourceImage;
import org.bukkit.ChatColor;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public abstract class ResourceIconCanvas<P extends ResourceIconCanvas.ResourceIconPixel> extends Canvas {
    private P[][] pixels = null;
    private Constructor<P> pixelIconConstructor;
    private Constructor<P> pixelContentConstructor;
    private Class<P> pixelClass;

    public ResourceIconCanvas(int width, int height) {
        super(width, height);
        clear();
    }

    protected final Class<P> getPixelClass() {
        if (pixelClass == null) {
            pixelClass = (Class<P>)
                    ((ParameterizedType) getClass()
                            .getGenericSuperclass())
                            .getActualTypeArguments()[0];
        }
        return pixelClass;
    }

    protected final Constructor<P> getPixelIconConstructor() throws NoSuchMethodException {
        if (pixelIconConstructor == null) {
            pixelClass = getPixelClass();
            pixelIconConstructor = pixelClass.getConstructor(ResourceImage.class, int.class);
        }
        return pixelIconConstructor;
    }

    protected final Constructor<P> getPixelContentConstructor() throws NoSuchMethodException {
        if (pixelContentConstructor == null) {
            pixelClass = getPixelClass();
            pixelContentConstructor = pixelClass.getConstructor(String.class);
        }
        return pixelContentConstructor;
    }

    protected final P createPixel(String content) {
        try {
            return getPixelContentConstructor().newInstance(content);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected final P createPixel(ResourceIcon icon, int rotation) {
        try {
            return getPixelIconConstructor().newInstance(icon, rotation);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected final P[] createPixelArray(int size) {
        return (P[]) Array.newInstance(getPixelClass(), size);
    }

    protected final P[][] createPixelArray(int... dimensions) {
        return (P[][]) Array.newInstance(getPixelClass(), dimensions);
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
        drawPixel(x, y, createPixel(resourceIcon, 0));
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
        drawPixel(x, y, createPixel(resourceIcon, rotation));
    }

    public void drawRectangle(int x, int y, int width, int height, ResourceIcon icon) {
        drawRectangle(x, y, width, height, createPixel(icon, 0));
    }

    public void fillRectangle(int x, int y, int width, int height, ResourceIcon icon) {
        fillRectangle(x, y, width, height, createPixel(icon, 0));
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
        drawLine(x1, y1, x2, y2, createPixel(icon, 0));
    }

    /**
     * Write a string
     *
     * @param x       X start position
     * @param y       Y start position
     * @param content content
     */
    @Deprecated
    public void writeString(int x, int y, String content) {
        writeString(x, y, content, ResourceFont.getDefaultFont());
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
        P[] pixels = stringToPixels(content, font);
        System.arraycopy(pixels, 0, this.pixels[y], x, Math.min(pixels.length, getWidth()));
    }

    protected P[] stringToPixels(String content, ResourceFont font) {
        content = ChatColor.translateAlternateColorCodes('&', content);
        String colors = ChatColor.getLastColors(content);
        content = ChatColor.stripColor(content);
        P[] pixels = createPixelArray(content.length());
        boolean requiresColorReset = !colors.isEmpty();

        for (int i = 0; i < content.length(); i++) {
            char character = content.charAt(i);
            if (i == 0) {
                if (character == ' ') {
                    pixels[i] = createPixel(colors + ColorResource.TRANSPARENT.toString());
                } else {
                    ResourceIcon fontCharacter = font.getCharacter(character);
                    if (fontCharacter != null) {
                        pixels[i] = createPixel(colors + fontCharacter);
                    } else {
                        pixels[i] = createPixel(ColorResource.LIGHT_PURPLE, 0);
                    }
                }
            } else if (i == content.length() - 1 && requiresColorReset) {
                if (character == ' ') {
                    pixels[i] = createPixel(ColorResource.TRANSPARENT.toString() + ChatColor.RESET);
                } else {
                    ResourceIcon fontCharacter = font.getCharacter(character);
                    if (fontCharacter != null) {
                        pixels[i] = createPixel(String.valueOf(fontCharacter) + ChatColor.RESET);
                    } else {
                        pixels[i] = createPixel(ColorResource.LIGHT_PURPLE, 0);
                    }
                }
            } else if (character == ' ') {
                pixels[i] = createPixel(ColorResource.TRANSPARENT, 0);
            } else {
                pixels[i] = createPixel(font.getCharacter(character), 0);
            }
        }
        return pixels;
    }

    /**
     * Get pixel matrix
     *
     * @return characterPixels pixel matrix
     */
    public P[][] getPixels() {
        return pixels;
    }

    /**
     * Set pixel matrix
     *
     * @param characterPixels pixel matrix
     */
    protected void setPixels(P[][] characterPixels) {
        this.pixels = characterPixels;
    }

    /**
     * Clear the canvas
     */
    public void clear() {
        setPixels(createPixelArray(getHeight(), getWidth()));
        fillRectangle(0, 0, getWidth(), getHeight(), createPixel(null, 0));
    }

    /**
     * Draw a characterPixel
     *
     * @param x              X position
     * @param y              Y position
     * @param characterPixel CharacterPixel
     */
    public void drawPixel(int x, int y, P characterPixel) {
        if (x >= 0 && y >= 0 && x < getWidth() && y < getHeight() && characterPixel != null) {
            drawPixelUnsafe(x, y, characterPixel);
        } else if (characterPixel == null) {
            throw new IllegalArgumentException("Unable to draw null pixel!");
        } else {
            throw new IllegalArgumentException("Unable to draw pixel! Pixel is out of bounds!");
        }
    }

    /**
     * Draw a characterPixel
     *
     * @param x              X position
     * @param y              Y position
     * @param characterPixel CharacterPixel
     */
    protected void drawPixelUnsafe(int x, int y, P characterPixel) {
        pixels[y][x] = characterPixel;
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
    public void drawRectangle(int x, int y, int width, int height, P characterPixel) {
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
     * @param pixel          Pixel
     */
    public void fillRectangle(int x, int y, int width, int height, P pixel) {
        for (int i = x; i < (x + width); i++) {
            for (int z = y; z < (y + height); z++) {
                drawPixel(i, z, pixel);
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
     * @param pixel          Pixel
     */
    public void drawLine(int x1, int y1, int x2, int y2, P pixel) {
        float dl = 3.125e-3F;
        float l;
        float x = x1 + 0.5F;
        float y = y1 + 0.5F;
        float dx = Math.round(x2 - x1) * dl;
        float dy = Math.round(y2 - y1) * dl;

        for (l = 0.0F; l < 1.0; l += dl) {
            drawPixel((int) Math.floor(x),
                    (int) Math.floor(y), pixel);
            x += dx;
            y += dy;
        }
    }

    /**
     * Get pixel
     *
     * @param x X location
     * @param y Y location
     * @return Pixel if valid location is provided
     */
    public P getPixel(int x, int y) {
        if (x >= 0 && y >= 0 && x < getWidth() && y < getHeight()) {
            return pixels[y][x];
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
     * @param pixel CharacterPixel
     */
    public void drawEllipse(int originX, int originY, int width, int height, P pixel) {
        for (int y = -height; y <= height; y++) {
            for (int x = -width; x <= width; x++) {
                if (x * x * height * height + y * y * width * width <= height * height * width * width)
                    drawPixel(originX + x, originY + y, pixel);
            }
        }
    }

    public static abstract class ResourceIconPixel {
        private String content = "";
        private String font = "arcadeboard:default";
        private boolean borders = true;

        public ResourceIconPixel(String content) {
            setContent(content);
        }

        public ResourceIconPixel(ResourceImage resourceIcon) {
            this(resourceIcon, 0);
        }

        public ResourceIconPixel(ResourceImage resourceIcon, int rotation) {
            if (resourceIcon != null) {
                if (resourceIcon instanceof ResourceIcon) {
                    setContent(((ResourceIcon) resourceIcon).toString(rotation));
                    borders = ((ResourceIcon) resourceIcon).hasBorders();
                } else {
                    setContent(resourceIcon.getHex());
                }
            }
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

        @Override
        public String toString() {
            return content;
        }

        public boolean hasBorders() {
            return borders;
        }

        public String getFont() {
            return font;
        }

        public void setFont(String font) {
            this.font = font;
        }
    }
}
