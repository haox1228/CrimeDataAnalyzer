/**
 * @author Nick Ma, haoxanm
 * <p>
 * This is the coordinate project, it stores two double as the coordinates, and provides necessary method
 * for part 6 and 7.
 */

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

public class Coordinate {
    //the x y coordinates
    double x, y;


    /**
     * This method helps to check if the currnet coordinate is inside of the given rectangle
     * There is no cases to consider and it will be straight up theta 1
     * Pre: the current coordinates must be effectively constructed, and input leftBottom and toRight have to
     * follow its physical meaning
     * Post: return a string describe the current coordinate's location regarding the box. It have seven
     * senarios: 1-3. vertically to the: left; right; vertically in the range, but horizontally not
     *           4-6: horizontally to the: up, down, in the range, but vertially not
     *           7: in the rectangle
     * @param leftBottom the left botoom corner coordinate of the rec
     * @param topRight the right top
     * @param isVertical whether the current not is checking vertical or not
     * @return return like the post condition
     */
    public String insideOf(Coordinate leftBottom, Coordinate topRight, boolean isVertical) {
        if (this.x >= leftBottom.x && this.x <= topRight.x && this.y <= topRight.y && this.y >= leftBottom.y) {
            return "inside";
        }
        if (isVertical) {
            if (this.x < leftBottom.x) {
                return "vLeft";
            } else if (this.x > topRight.x) {
                return "vRight";
            } else {
                return "vIn";
            }
        } else {
            if (this.y < leftBottom.y) {
                return "hDown";
            } else if (this.y > topRight.y) {
                return "hUp";
            } else {
                return "hIn";
            }
        }
    }

    /**
     * This method help to check the location of the currnet coordinate regarding the target for the nearest neighbor
     * Pre: target has to be valid
     * Post: return the location reference
     * @param target target coordinate
     * @param isVertical whether the current node is checking vertical
     * @return as the post conditiopn
     */
    public String findLocation(Coordinate target, boolean isVertical) {
        if (this.equals(target)) {
            return "same";
        }
        if (isVertical) {
            if (this.x < target.x) {
                return "vLeft";
            } else if (this.x > target.x) {
                return "vRight";
            } else {
                return "vIn";
            }
        } else {
            if (this.y < target.y) {
                return "hDown";
            } else if (this.y > target.y) {
                return "hUp";
            } else {
                return "hIn";
            }
        }
    }

    /**
     * Check the distance from the curr coor to the target coor.
     * @param target the target coordinate
     * @return the distance
     */
    public double checkDistance(Coordinate target) {
        double height = Math.abs(this.y - target.y);
        double length = Math.abs(this.x - target.x);
        return Math.sqrt((height * height) + (length * length));
    }

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
}
