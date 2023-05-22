/**
 * @authro Nick Ma, haoxuanm
 * The redblacknode implement according to the javadoc
 */
public class RedBlackNode {
    /**
     * Final static class variable represent two color category of the node
     */
    public static final int RED = 1;
    public static final int BLACK = 0;
    /*
    The assigned color
     */
    int color;
    /**
     * Name value pair
     */
    String name;
    int value;
    /**
     * Node connections
     */
    RedBlackNode parent;
    RedBlackNode lc;
    RedBlackNode rc;

    //constructor: Construct a RedBlackNode with data, color, parent pointer, left child pointer and right child pointer.
    public RedBlackNode(int color, String data, RedBlackNode p, RedBlackNode lc, RedBlackNode rc) {
        this.color = color;
        this.name = data;
        this.parent = p;
        this.lc = lc;
        this.rc = rc;
    }

    /**
     * Getters an setters
     */
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getData() {
        return name;
    }

    public void setData(String data) {
        this.name = data;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public RedBlackNode getParent() {
        return parent;
    }

    public void setParent(RedBlackNode parent) {
        this.parent = parent;
    }

    public RedBlackNode getLc() {
        return lc;
    }

    public void setLc(RedBlackNode lc) {
        this.lc = lc;
    }

    public RedBlackNode getRc() {
        return rc;
    }

    public void setRc(RedBlackNode rc) {
        this.rc = rc;
    }
}
