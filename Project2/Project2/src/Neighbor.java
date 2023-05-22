/**
 * @author Nick Ma, haoxuanm
 * The neighbor object for part 7
 */

import java.util.Arrays;

public class Neighbor {
    /**
     * Objects of class Neighbor will
     * contain a distance field and a pointer into the 2-d tree.
     */
    double distance;
    TwoDTree.TreeNode node;
    int count;
    //getters and setters
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public TwoDTree.TreeNode getNode() {
        return node;
    }

    public void setNode(TwoDTree.TreeNode node) {
        this.node = node;
    }
    public String toString(){
        return "Looked at "+this.count+"in tree. Found the nearest crime at: \n"+Arrays.toString(this.node.line);
    }
    //constructor
    public Neighbor(TwoDTree.TreeNode node, double distance){
        this.node = node;
        this.distance = distance;
        this.count = 1;
    }
}
