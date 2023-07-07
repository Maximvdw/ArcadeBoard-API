package be.arcadeboard.api.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ResourceIcon extends ResourceImage {
    private boolean allowRotation = false;
    private boolean borders = false;

    public ResourceIcon(String name, BufferedImage image) throws IOException {
        super(name, image);
    }

    public ResourceIcon(String name, InputStream inputStream) throws IOException {
        super(name, inputStream);
    }

    public ResourceIcon(String name, File image) throws IOException {
        super(name, image);
    }

    /**
     * Get character
     *
     * @return character
     */
    public char getCharacter() {
        if (getHex().isEmpty()) {
            return (char) 0;
        }
        return (char) Integer.parseInt(getHex(), 16);
    }

    /**
     * Get character with rotation
     *
     * @param iconRotation icon rotation
     * @return character
     */
    public char getCharacter(ResourceIcon.IconRotation iconRotation) {
        return getCharacter(iconRotation.ordinal());
    }

    /**
     * Get character with rotation
     *
     * @param rotation rotation
     * @return character
     */
    public char getCharacter(int rotation) {
        return (char) Integer.parseInt(getHex(rotation), 16);
    }

    @Override
    public String toString() {
        return String.valueOf(getCharacter());
    }

    public String getHex(ResourceIcon.IconRotation iconRotation) {
        return getHex(iconRotation.ordinal());
    }

    public String getHex(int rotation) {
        int hexNumber = Integer.parseInt(getHex(), 16) + rotation;
        return Integer.toHexString(hexNumber);
    }

    /**
     * Is the icon allowed to rotate
     *
     * @return allowed rotation
     */
    public boolean isAllowRotation() {
        return allowRotation;
    }

    public void setAllowRotation(boolean allowRotation) {
        this.allowRotation = allowRotation;
    }

    public String toString(int rotation) {
        if (rotation != 0 && !isAllowRotation())
            return toString();
        return String.valueOf(getCharacter(rotation));
    }


    public String toString(ResourceIcon.IconRotation iconRotation) {
        return toString(iconRotation.ordinal());
    }

    /**
     * Get resource icon width
     *
     * @return Icon width
     */
    @Override
    public int getHeight() {
        if (hasBorders()) {
            return super.getHeight();
        }
        return super.getHeight() + 1;
    }

    /**
     * Get resource icon width
     *
     * @return Icon width
     */
    @Override
    public int getWidth() {
        if (hasBorders()) {
            return super.getWidth();
        }
        return super.getWidth() + 1;
    }

    public boolean hasBorders() {
        return borders;
    }

    public void setBorders(boolean borders) {
        this.borders = borders;
    }
}
