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
    private String name = "";
    private File imageFile = null;
    protected BufferedImage image = null;
    private ResourceIcon[][] icons = new ResourceIcon[0][0];

    public ResourceImage(String name, BufferedImage image) throws IOException {
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
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage dest = new BufferedImage(width, height, src.getType());
        Graphics2D graphics2D = dest.createGraphics();
        graphics2D.translate((height - width) / 2, (height - width) / 2);
        graphics2D.rotate((Math.PI / 2) * rotation, height / 2, width / 2);
        graphics2D.drawRenderedImage(src, null);
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
        // Split image
        int heightDiff = this.image.getHeight() % 16;
        int widthDiff = this.image.getWidth() % 16;
        if (heightDiff == 0 && widthDiff % 16 == 0){
            // Correct dimensions
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
        }else{
            // Pad image
            BufferedImage newImage = new BufferedImage(this.image.getWidth() + widthDiff, this.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            // Center image
            newImage.getGraphics().drawImage(image, widthDiff / 2, heightDiff / 2, null);
            setImage(newImage);
        }
    }

    /**
     * Get individual resource icons that make up this image
     * @return list of resource icons
     */
    public ResourceIcon[][] getIcons() {
        return icons;
    }

    public enum IconRotation {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
}
