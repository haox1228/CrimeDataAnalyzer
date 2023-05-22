/**
 * @author Nick Ma, haoxuanm
 * The object to store key value pair used for the MST
 */
public class KeyValPair implements Comparable<KeyValPair> {
    //parent vertex
    int adjIndex;
    //distance from the index vertex to parent vertex
    double distance;

    //constructor
    public KeyValPair(int ind, double dist) {
        this.adjIndex = ind;
        this.distance = dist;
    }

    @Override
    public int compareTo(KeyValPair o) {
        return Double.compare(this.distance, o.distance);
    }

    @Override
    public String toString() {
        return adjIndex + " " + distance;
    }
}