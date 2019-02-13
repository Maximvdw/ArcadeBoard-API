package be.arcadeboard.api.game.geometry;

public class Point2D {
    protected float x;
    protected float y;


    public Point2D() {

    }

    public Point2D(Point2D copy) {
        this.x = copy.x;
        this.y = copy.y;
    }

    public Point2D(float x, float y) {
        setX(x);
        setY(y);
    }

    public Point2D applyVector(Vector2D vector) {
        setX(getX() + vector.getX());
        setY(getY() + vector.getY());
        return this;
    }

    public int getPixelX() {
        return (int) this.x;
    }

    public int getPixelY() {
        return (int) this.y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public Point2D setX(float x) {
        this.x = x;
        return this;
    }

    public Point2D setY(float y) {
        this.y = y;
        return this;
    }

    public float distance(Point2D other){
        float deltaX = Math.abs(other.x - x);
        float deltaY = Math.abs(other.y - y);
        return deltaX + deltaY;
    }

    /**
     * Point to vector
     *
     * @return point to vector
     */
    public Vector2D toVector() {
        return new Vector2D(getX(), getY());
    }

    /**
     * Get a new point.
     *
     * @return point
     */
    @Override
    public Point2D clone() {
        return new Point2D(getX(), getY());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point2D point2D = (Point2D) o;

        if (Float.compare(point2D.x, x) != 0) return false;
        return Float.compare(point2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    /**
     * Returns this points's components as x,y
     */
    @Override
    public String toString() {
        return x + "," + y;
    }
}
