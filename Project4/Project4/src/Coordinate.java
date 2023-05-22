import java.util.Objects;

/**
 * This class were derived from project 2: 2DTree, which will used to calculate distance and
 * other applicable features
 *
 * @author Nick Ma, haoxuanm
 */
public class Coordinate {
    //the x y coordinates
    double x, y;

    //getters and setters
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    //constructors
    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate() {
        this.x = 0;
        this.y = 0;
    }

    //equals and hashcode override
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * Check the distance from the curr coor to the target coor.
     *
     * @param target the target coordinate
     * @return the distance
     */
    public double getDistance(Coordinate target) {
        double height = Math.abs(this.y - target.y);
        double length = Math.abs(this.x - target.x);
        return Math.sqrt((height * height) + (length * length));
    }
}
