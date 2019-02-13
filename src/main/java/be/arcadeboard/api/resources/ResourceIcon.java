package be.arcadeboard.api.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ResourceIcon extends ResourceImage {
    private String hex = "";
    private byte startIdx = 0;
    private byte endIdx = 15;
    private boolean allowRotation = false;

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
     * Set icon image
     *
     * @param image icon image
     */
    @Override
    public void setImage(BufferedImage image) {
        if (image.getHeight() != 16 || image.getWidth() != 16) {
            // Not correct size, resize
        } else {
            this.image = image;
        }
    }

    /**
     * Get character
     *
     * @return character
     */
    public char getCharacter() {
        return (char) Integer.parseInt(hex, 16);
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
     * Get the end index position of the icon
     * Default: 15
     *
     * @return end index position
     */
    public byte getEndIndex() {
        return endIdx;
    }

    /**
     * Set the end index position of the icon
     * Default: 15
     *
     * @param endIdx end index position
     */
    public void setEndIndex(byte endIdx) {
        this.endIdx = endIdx;
    }

    /**
     * Get the start index position of the icon
     * Default: 0
     *
     * @return start index position
     */
    public byte getStartIndex() {
        return startIdx;
    }

    /**
     * Set the start index position of the icon
     * Default: 0
     *
     * @param startIdx start index position
     */
    public void setStartIndex(byte startIdx) {
        this.startIdx = startIdx;
    }
}
