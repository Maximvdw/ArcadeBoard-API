package be.arcadeboard.api.resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ResourceImage {
    private String hex = "";
    private String name = "";
    private File imageFile = null;
    protected BufferedImage image = null;
    private ResourceIcon[][] icons = new ResourceIcon[0][0];
    private int height = 16;
    private int width = 16;
    private int ascent = 16;
    private boolean split = true;

    public ResourceImage(String name, BufferedImage image) {
        setName(name);
        setImage(image);
    }

    public ResourceImage(String name, InputStream inputStream) throws IOException {
        setName(name);
        setImage(ImageIO.read(inputStream));
    }

    public ResourceImage(String name, File image) throws IOException {
        setName(name);
        setImageFile(image);
        setImage(ImageIO.read(image));
    }

    /**
     * Get name of icon
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of icon
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get image file
     *
     * @return image file
     */
    public File getImageFile() {
        return imageFile;
    }

    /**
     * Set image file
     *
     * @param image Image file
     */
    public void setImageFile(File image) {
        this.imageFile = image;
    }

    public BufferedImage getImage(ResourceIcon.IconRotation iconRotation) {
        return getImage(iconRotation.ordinal());
    }

    /**
     * Get image with rotation
     *
     * @param rotation rotation
     * @return rotated image
     */
    public BufferedImage getImage(int rotation) {
        BufferedImage src = getImage();
        if (rotation == 0) {
            return src;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage dest = new BufferedImage(width, height, src.getType());
        Graphics2D graphics = dest.createGraphics();
        graphics.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        AffineTransform at = new AffineTransform();
        at.translate(width, height);

        at.rotate((Math.PI / 2) * rotation, Math.round(height / 2.), Math.round(width / 2.));
        graphics.setTransform(at);
        graphics.drawImage(src, 0, 0, null);
        graphics.dispose();
        return dest;
    }

    /**
     * Get icon image
     *
     * @return icon image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Set icon image
     *
     * @param image icon image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
        setHeight(this.image.getHeight());
        setWidth(this.image.getWidth());

        // Make any transparent pixels less transparent
        // to ensure fixed width in Minecraft
        for (int x = 0; x < this.image.getWidth(); x++) {
            for (int y = 0; y < this.image.getHeight(); y++) {
                int argb = this.image.getRGB(x, y);
                int alpha = (argb >> 24) & 0xff;
                if (alpha == 0) {
                    argb |= (1 << 24);
                    this.image.setRGB(x, y, argb);
                }
            }
        }

        if (split && (this.image.getHeight() != 16 || this.image.getWidth() != 16)) {
            // Split image
            int heightDiff = this.image.getHeight() % 16;
            int widthDiff = this.image.getWidth() % 16;
            if (heightDiff == 0 && widthDiff == 0) {
                // Correct dimensions or scalable dimensions
                final int width = 16;
                final int height = 16;
                final int rows = this.image.getHeight() / 16;
                final int cols = this.image.getWidth() / 16;

                this.icons = new ResourceIcon[rows][cols];
                for (int i = 0; i < rows; i++){
                    for (int j = 0; j < cols; j++){
                        try {
                            icons[i][j] = new ResourceIcon(getName() + "_" + ((i * cols) + j), this.image.getSubimage(
                                    j * width,
                                    i * height,
                                    width,
                                    height
                            ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                // Pad image
                BufferedImage newImage = new BufferedImage(this.image.getWidth() + widthDiff, this.image.getHeight() + heightDiff, BufferedImage.TYPE_INT_ARGB);
                // Center image
                newImage.getGraphics().drawImage(image, widthDiff / 2, heightDiff / 2, null);
                setImage(newImage);
            }
        }
    }

    /**
     * Get individual resource icons that make up this image
     * @return list of resource icons
     */
    public ResourceIcon[][] getIcons() {
        return icons;
    }

    /**
     * Get resource icon height
     *
     * @return Icon height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set resource icon height
     *
     * @param height Resource icon height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    public int getAscent() {
        return ascent;
    }

    public void setAscent(int ascent) {
        this.ascent = ascent;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getHex() {
        return hex;
    }

    /**
     * Set hex
     *
     * @param hex Hex
     */
    public void setHex(String hex) {
        this.hex = hex;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    /**
     * Icon rotation
     */
    public enum IconRotation {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
}
