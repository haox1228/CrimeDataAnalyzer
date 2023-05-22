
import java.util.LinkedList;

/**
 * The treenode used to build a tree for the pre order tree walk
 *
 * @author Nick Ma, haoxuanm
 */
public class TreeNode {
    // the parent node
    TreeNode parent;
    //the vertex of this node
    int vertex;
    //the distance between this and the parent node
    double distance;
    //children of this node
    LinkedList<TreeNode> children;

    //construcot
    public TreeNode(TreeNode parent, double distance, int vertex) {
        this.parent = parent;
        this.distance = distance;
        this.children = new LinkedList<>();
        this.vertex = vertex;
    }

    //getter
    public double getDistance() {
        return distance;
    }

    public TreeNode getParent() {
        return parent;
    }

    //add a children to the node
    public void addChildren(TreeNode child) {
        this.children.add(child);
    }
}
