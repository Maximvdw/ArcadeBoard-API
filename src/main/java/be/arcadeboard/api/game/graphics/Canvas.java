package be.arcadeboard.api.game.graphics;

public abstract class Canvas {
    private int width = 0;
    private int height = 0;

    public Canvas() {

    }

    public Canvas(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    /**
     * Get height
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set height
     *
     * @param height Height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Get width
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set width
     *
     * @param width Width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Get total pixel count
     *
     * @return pixel count
     */
    public int getPixelCount() {
        return width * height;
    }

    public abstract void clear();
}
