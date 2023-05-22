/**
 * @author Nick Ma, haoxuanm
 * Objects of class RedBlackTree hold four private members.
 * RedBlackNode tree is a reference to the root of the tree or to the nil node
 * if the tree is empty. RedBlackNode nil is a reference to the sentinel node.
 * int size counts the number of insertions into the tree.
 * int recentCompares counts the number of comparisons in the last search of the tree.
 * String[] courses for indexing
 */
public class RedBlackTree {
    RedBlackNode root;
    int size;
    static RedBlackNode nil;
    String[] courses;
    int recentCompares;

    /**
     * This constructor creates an empty RedBlackTree.
     * It creates a RedBlackNode that represents null.
     * It sets the internal variable tree to point to this
     * node. This node that an empty tree points to will be
     * used as a sentinel node. That is, all pointers in the
     * tree that would normally contain the value null, will instead
     * point to this sentinel node.
     */
    public RedBlackTree() {
        nil = new RedBlackNode(0, null, null, null, null);
        this.root = nil;
        this.courses = new String[40];
        this.size = 0;
        this.recentCompares = 0;
    }

    public String getCourse(int index) {
        return this.courses[index];
    }

    /**
     * The boolean contains() returns true if the String v is in the
     * RedBlackTree and false otherwise.
     * It counts each comparison it makes in the variable recentCompares.
     *
     * @param v check this value
     * @return true if the String v is in RedBlackTree and false otherwise.
     */
    public boolean contains(String v) {
        this.recentCompares = 0;
        RedBlackNode x = this.root;
        while (x != nil) {
            if (v.compareTo(x.getData()) < 0) {
                x = x.lc;
            } else if (v.compareTo(x.getData()) > 0) {
                x = x.rc;
            } else {
                return true;
            }
            this.recentCompares++;
        }
        return false;
    }

    public int getSize() {
        return this.size;
    }

    /**
     * Get the index of the course searched
     * pre: input is a string
     * post:  return the index of the string
     * theta logn
     *
     * @param course the course that used to search
     * @return the index of the course, -1 if nothing found
     */
    public int getIndex(String course) {
        RedBlackNode x = this.root;
        while (x != nil) {
            if (course.compareTo(x.getData()) < 0) {
                x = x.lc;
            } else if (course.compareTo(x.getData()) > 0) {
                x = x.rc;
            } else {
                return x.value;
            }
            this.recentCompares++;
        }
        return -1;
    }

    /**
     * The insert() method places a data item into the tree
     * pre: the value has to be a string
     * post: the value inserted to the redblack tree and the rules of rbt were maintained
     * theta log n
     *
     * @param value the value tobe inserted
     */

    public void insert(String value) {
        RedBlackNode y = nil;
        RedBlackNode x = this.root;
        RedBlackNode z = new RedBlackNode(1, value, nil, nil, nil);
        while (x != nil) {
            y = x;
            if (value.compareTo(x.getData()) < 0) {
                x = x.lc;
            } else if (value.compareTo(x.getData()) > 0) {
                x = x.rc;
            } else {
                return;
            }
        }
        z.setParent(y);
        z.setValue(this.size);
        if (y == nil) {
            this.root = z;
        } else {
            if (value.compareTo(y.getData()) < 0) {
                y.lc = z;
            } else {
                y.rc = z;
            }
        }
        z.lc = nil;
        z.rc = nil;
        courses[size] = value;
        size++;
        z.setColor(RedBlackNode.RED);
        RBInsertFixup(z);

    }

    /**
     * leftRotate() performs a single left rotation.
     *
     * @param x
     */
    public void leftRotate(RedBlackNode x) {
        RedBlackNode y = x.getRc();
        x.setRc(y.getLc());
        y.lc.parent = x;
        y.parent = x.parent;
        if (x.parent == nil) {
            this.root = y;
        } else {
            if (x == x.getParent().getLc()) {
                x.parent.lc = y;
            } else {
                x.parent.rc = y;
            }
        }
        y.setLc(x);
        x.parent = y;

    }

    /**
     * Fixing up the tree so that Red Black Properties are preserved.
     * pre: a rbt passed in
     * post: the passed in rbt has preserved its property
     * thata log n
     *
     * @param z
     */
    public void RBInsertFixup(RedBlackNode z) {
        RedBlackNode y;
        while (z.getParent().getColor() == RedBlackNode.RED) {
            if (z.getParent() == z.getParent().getParent().getLc()) {
                y = z.getParent().getParent().getRc();
                if (y.getColor() == RedBlackNode.RED) {
                    z.getParent().setColor(RedBlackNode.BLACK);
                    y.setColor(RedBlackNode.BLACK);
                    z.getParent().getParent().setColor(RedBlackNode.RED);
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getRc()) {
                        z = z.getParent();
                        leftRotate(z);
                    }
                    z.getParent().setColor(RedBlackNode.BLACK);
                    z.getParent().getParent().setColor(RedBlackNode.RED);
                    rightRotate(z.getParent().getParent());
                }
            } else {
                y = z.getParent().getParent().getLc();
                if (y.getColor() == RedBlackNode.RED) {
                    z.getParent().setColor(RedBlackNode.BLACK);
                    y.setColor(RedBlackNode.BLACK);
                    z.getParent().getParent().setColor(RedBlackNode.RED);
                    z = z.getParent().getParent();

                } else {
                    if (z == z.getParent().getLc()) {
                        z = z.getParent();
                        rightRotate(z);
                    }
                    z.getParent().setColor(RedBlackNode.BLACK);
                    z.getParent().getParent().setColor(RedBlackNode.RED);
                    leftRotate(z.getParent().getParent());
                }
            }
        }
        this.root.setColor(RedBlackNode.BLACK);
    }

    /**
     * rightRotate() performs a single right rotation
     *
     * @param x the node
     */
    public void rightRotate(RedBlackNode x) {
        RedBlackNode y = x.getLc();
        x.lc = y.rc;
        y.rc.parent = x;
        y.parent = x.parent;
        if (x.parent == nil) {
            this.root = y;
        } else {
            if (x == x.parent.lc) {
                x.parent.lc = y;
            } else {
                x.parent.rc = y;
            }
        }
        y.rc = x;
        x.parent = y;
    }

}
