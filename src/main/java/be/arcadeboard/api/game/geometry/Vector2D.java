package be.arcadeboard.api.game.geometry;

import org.bukkit.util.NumberConversions;

import java.util.Random;

/**
 * Similar to Bukkit Vector class
 * be it with 2D floats
 */
public class Vector2D {
    private static Random random = new Random();

    /**
     * Threshold for fuzzy equals().
     */
    private static final double epsilon = 0.000001;

    protected float x;
    protected float y;

    /**
     * Construct the vector with all components as 0.
     */
    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(Vector2D copy) {
        this.x = copy.x;
        this.y = copy.y;
    }

    /**
     * Construct the vector with provided integer components.
     *
     * @param x X component
     * @param y Y component
     */
    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Construct the vector with provided float components.
     *
     * @param x X component
     * @param y Y component
     */
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds a vector to this one
     *
     * @param vec The other vector
     * @return the same vector
     */
    public Vector2D add(Vector2D vec) {
        x += vec.x;
        y += vec.y;
        return this;
    }

    /**
     * Subtracts a vector from this one.
     *
     * @param vec The other vector
     * @return the same vector
     */
    public Vector2D subtract(Vector2D vec) {
        x -= vec.x;
        y -= vec.y;
        return this;
    }

    /**
     * Multiplies the vector by another.
     *
     * @param vec The other vector
     * @return the same vector
     */
    public Vector2D multiply(Vector2D vec) {
        x *= vec.x;
        y *= vec.y;
        return this;
    }

    /**
     * Divides the vector by another.
     *
     * @param vec The other vector
     * @return the same vector
     */
    public Vector2D divide(Vector2D vec) {
        x /= vec.x;
        y /= vec.y;
        return this;
    }

    /**
     * Copies another vector
     *
     * @param vec The other vector
     * @return the same vector
     */
    public Vector2D copy(Vector2D vec) {
        x = vec.x;
        y = vec.y;
        return this;
    }

    /**
     * Gets the magnitude of the vector, defined as sqrt(x^2+y^2+z^2). The
     * value of this method is not cached and uses a costly square-root
     * function, so do not repeatedly call this method to get the vector's
     * magnitude. NaN will be returned if the inner result of the sqrt()
     * function overflows, which will be caused if the length is too long.
     *
     * @return the magnitude
     */
    public double length() {
        return Math.sqrt(NumberConversions.square(x) + NumberConversions.square(y));
    }

    /**
     * Gets the magnitude of the vector squared.
     *
     * @return the magnitude
     */
    public double lengthSquared() {
        return NumberConversions.square(x) + NumberConversions.square(y);
    }

    /**
     * Get the distance between this vector and another. The value of this
     * method is not cached and uses a costly square-root function, so do not
     * repeatedly call this method to get the vector's magnitude. NaN will be
     * returned if the inner result of the sqrt() function overflows, which
     * will be caused if the distance is too long.
     *
     * @param o The other vector
     * @return the distance
     */
    public double distance(Vector2D o) {
        return Math.sqrt(NumberConversions.square(x - o.x) + NumberConversions.square(y - o.y));
    }

    /**
     * Get the squared distance between this vector and another.
     *
     * @param o The other vector
     * @return the distance
     */
    public double distanceSquared(Vector2D o) {
        return NumberConversions.square(x - o.x) + NumberConversions.square(y - o.y);
    }

    /**
     * Gets the angle between this vector and another in radians.
     *
     * @param other The other vector
     * @return angle in radians
     */
    public float angle(Vector2D other) {
        double dot = dot(other) / (length() * other.length());

        return (float) Math.acos(dot);
    }

    /**
     * Convert the vector to a point
     *
     * @return 2D point
     */
    public Point2D toPoint() {
        return new Point2D(getX(), getY());
    }

    /**
     * Sets this vector to the midpoint between this vector and another.
     *
     * @param other The other vector
     * @return this same vector (now a midpoint)
     */
    public Vector2D midpoint(Vector2D other) {
        x = (x + other.x) / 2;
        y = (y + other.y) / 2;
        return this;
    }

    /**
     * Gets a new midpoint vector between this vector and another.
     *
     * @param other The other vector
     * @return a new midpoint vector
     */
    public Vector2D getMidpoint(Vector2D other) {
        float x = (this.x + other.x) / 2;
        float y = (this.y + other.y) / 2;
        return new Vector2D(x, y);
    }

    /**
     * Performs scalar multiplication, multiplying all components with a
     * scalar.
     *
     * @param m The factor
     * @return the same vector
     */
    public Vector2D multiply(int m) {
        x *= m;
        y *= m;
        return this;
    }

    /**
     * Performs scalar multiplication, multiplying all components with a
     * scalar.
     *
     * @param m The factor
     * @return the same vector
     */
    public Vector2D multiply(double m) {
        x *= m;
        y *= m;
        return this;
    }

    /**
     * Performs scalar multiplication, multiplying all components with a
     * scalar.
     *
     * @param m The factor
     * @return the same vector
     */
    public Vector2D multiply(float m) {
        x *= m;
        y *= m;
        return this;
    }

    /**
     * Calculates the dot product of this vector with another. The dot product
     * is defined as x1*x2+y1*y2+z1*z2. The returned value is a scalar.
     *
     * @param other The other vector
     * @return dot product
     */
    public double dot(Vector2D other) {
        return x * other.x + y * other.y;
    }

    /**
     * Converts this vector to a unit vector (a vector with length of 1).
     *
     * @return the same vector
     */
    public Vector2D normalize() {
        double length = length();

        x /= length;
        y /= length;

        return this;
    }

    /**
     * Zero this vector's components.
     *
     * @return the same vector
     */
    public Vector2D zero() {
        x = 0;
        y = 0;
        return this;
    }

    /**
     * Returns whether this vector is in an axis-aligned bounding box.
     * <p>
     * The minimum and maximum vectors given must be truly the minimum and
     * maximum X, Y and Z components.
     *
     * @param min Minimum vector
     * @param max Maximum vector
     * @return whether this vector is in the AABB
     */
    public boolean isInAABB(Vector2D min, Vector2D max) {
        return x >= min.x && x <= max.x && y >= min.y && y <= max.y;
    }

    /**
     * Returns whether this vector is within a sphere.
     *
     * @param origin Sphere origin.
     * @param radius Sphere radius
     * @return whether this vector is in the sphere
     */
    public boolean isInSphere(Vector2D origin, double radius) {
        return (NumberConversions.square(origin.x - x) + NumberConversions.square(origin.y - y)) <= NumberConversions.square(radius);
    }

    /**
     * Gets the X component.
     *
     * @return The X component.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the floored value of the X component, indicating the pixel that
     * this vector is contained with.
     *
     * @return pixel X
     */
    public int getPixelX() {
        return NumberConversions.floor(x);
    }

    /**
     * Gets the Y component.
     *
     * @return The Y component.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the floored value of the Y component, indicating the pixel that
     * this vector is contained with.
     *
     * @return pixel y
     */
    public int getPixelY() {
        return NumberConversions.floor(y);
    }

    /**
     * Set the X component.
     *
     * @param x The new X component.
     * @return This vector.
     */
    public Vector2D setX(int x) {
        this.x = x;
        return this;
    }

    /**
     * Set the X component.
     *
     * @param x The new X component.
     * @return This vector.
     */
    public Vector2D setX(float x) {
        this.x = x;
        return this;
    }

    /**
     * Set the Y component.
     *
     * @param y The new Y component.
     * @return This vector.
     */
    public Vector2D setY(int y) {
        this.y = y;
        return this;
    }

    /**
     * Set the Y component.
     *
     * @param y The new Y component.
     * @return This vector.
     */
    public Vector2D setY(float y) {
        this.y = y;
        return this;
    }

    /**
     * Checks to see if two objects are equal.
     * <p>
     * Only two Vectors can ever return true. This method uses a fuzzy match
     * to account for floating point errors. The epsilon can be retrieved
     * with epsilon.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector2D)) {
            return false;
        }

        Vector2D other = (Vector2D) obj;

        return Math.abs(x - other.x) < epsilon && Math.abs(y - other.y) < epsilon && (this.getClass().equals(obj.getClass()));
    }

    /**
     * Returns a hash code for this vector
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;

        hash = 79 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }

    /**
     * Get a new vector.
     *
     * @return vector
     */
    @Override
    public Vector2D clone() {
        return new Vector2D(getX(), getY());
    }

    /**
     * Returns this vector's components as x,y
     */
    @Override
    public String toString() {
        return x + "," + y;
    }

    /**
     * Check if each component of this Vector is finite.
     *
     * @throws IllegalArgumentException if any component is not finite
     */
    public void checkFinite() throws IllegalArgumentException {
        NumberConversions.checkFinite(x, "x not finite");
        NumberConversions.checkFinite(y, "y not finite");
    }

    /**
     * Get the threshold used for equals().
     *
     * @return The epsilon.
     */
    public static double getEpsilon() {
        return epsilon;
    }

    /**
     * Gets the minimum components of two vectors.
     *
     * @param v1 The first vector.
     * @param v2 The second vector.
     * @return minimum
     */
    public static Vector2D getMinimum(Vector2D v1, Vector2D v2) {
        return new Vector2D(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y));
    }

    /**
     * Gets the maximum components of two vectors.
     *
     * @param v1 The first vector.
     * @param v2 The second vector.
     * @return maximum
     */
    public static Vector2D getMaximum(Vector2D v1, Vector2D v2) {
        return new Vector2D((float) Math.max(v1.x, v2.x), (float) Math.max(v1.y, v2.y));
    }

    /**
     * Gets a random vector with components having a random value between 0
     * and 1.
     *
     * @return A random vector.
     */
    public static Vector2D getRandom() {
        return new Vector2D(random.nextFloat(), random.nextFloat());
    }
}
