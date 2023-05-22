/**
 * @author Nick Ma, haoxuanm
 * This is the 2DTree class
 * All required methods from part 1-8 were implemented
 */

import javax.swing.tree.TreeNode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TwoDTree {
    /**
     * The 2dtree keep the root node the size of the tree
     */
    TreeNode root;
    int size;

    /**
     * Constructor of the 2DTree
     * pre-condition: The String crimeDataLocation contains the path to
     * a file formatted in the exact same way as CrimeLatLonXY.csv
     * post-condition: The 2d tree is constructed and may be printed or
     * queried.
     *
     * @param crimeDataLocation
     */
    public TwoDTree(String crimeDataLocation) {
        ArrayList<String[]> lines = readCSV(crimeDataLocation);
        boolean vertical = true;
        for (String[] str : lines) {
            insert(str);
        }

    }

    /**
     * Insert a new node to the tree
     * Pre: the input is a string array
     * Post: create a node for the string array and put it into the tree
     *
     * @param str string array
     */
    public void insert(String[] str) {
        if (this.root == null) {
            root = new TreeNode(str, true);
            return;
        }
        insertHelper(root, str, root.isVertical);

    }

    /**
     * Helper of the input function
     * Pre: All the parameter were properly changed
     * Post: return the node which was newly inserted
     *
     * @param root       current root
     * @param str        new data to insert
     * @param isVertical if the current level of the tree is checking vertical location
     * @return
     */
    private TreeNode insertHelper(TreeNode root, String[] str, boolean isVertical) {
        if (root == null) {
            root = new TreeNode(str, isVertical);
            return root;
        }
        if (isVertical) {
            if (root.c.getX() > Double.parseDouble(str[0])) {
                root.left = insertHelper(root.left, str, false);
            } else {
                root.right = insertHelper(root.right, str, false);
            }
        } else {
            if (root.c.getY() > Double.parseDouble(str[1])) {
                root.left = insertHelper(root.left, str, true);
            } else {
                root.right = insertHelper(root.right, str, true);
            }
        }
        return root;
    }

    /**
     * Read the CSV from the file
     * Pre: the input filename is proper
     * Post: Return an arraylist of string contains entries
     *
     * @param fileName the filename
     * @return as post
     */
    private ArrayList<String[]> readCSV(String fileName) {
        String prefix = "/Users/mahaoxuan/Documents/Spring 2023/Data Structure/Project2/Project2/src/";
        ArrayList<String[]> res = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(prefix + fileName));
            while (sc.hasNextLine()) {
                String thisLine = sc.nextLine();
                if (thisLine.startsWith("X,")) {
                    continue;
                }
                String[] arr = thisLine.split(",");
                res.add(arr);
            }
            this.size = res.size();
        } catch (FileNotFoundException e) {
            System.out.println("No such file");
        }
        return res;
    }

    /**
     * Print the tree in pre order way
     * pre-condition: The 2d tree has been constructed.
     * post-condition: The 2d tree is displayed with a pre-order
     * traversal.
     */
    public void preOrderPrint() {
        TreeNode n = root;
        preOrderHelper(n);
        System.out.print("\n");
    }


    /**
     * Recursion helper of the pre order print
     * Pre: the treeNode is proper
     * Post: print the node
     *
     * @param n
     */
    private void preOrderHelper(TreeNode n) {
        if (n == null) {
            return;
        }
        System.out.print(n.c.toString() + ", ");
        preOrderHelper(n.left);
        preOrderHelper(n.right);
    }

    /**
     * In order print
     * pre-condition: The 2d tree has been constructed.
     * post-condition: The 2d tree is displayed with an in-order
     * traversal.
     * Best case: theta 1, average case theta n
     */
    public void inOrderPrint() {
        TreeNode n = root;
        inOrderHelper(n);
        System.out.print("\n");
    }

    /**
     * In order print helper
     * Pre: inOrderPrint were called
     * Post: print this current node
     *
     * @param n
     */
    private void inOrderHelper(TreeNode n) {
        if (n == null) {
            return;
        }
        inOrderHelper(n.left);
        System.out.print(n.c.toString() + ", ");
        inOrderHelper(n.right);
    }

    /**
     * pre-condition: The 2d tree has been constructed.
     * post-condition: The 2d tree is displayed with a post-order
     * traversal.
     */
    public void postOrderPrint() {
        TreeNode n = root;
        postOrderHelper(n);
        System.out.print("\n");
    }

    /**
     * Recursion helper of post order
     * pre: above method was called
     * post: print current node
     *
     * @param n
     */
    private void postOrderHelper(TreeNode n) {
        if (n == null) {
            return;
        }
        postOrderHelper(n.left);
        postOrderHelper(n.right);
        System.out.print(n.c.toString() + ", ");
    }

    /**
     * pre-condition: The 2d tree has been constructed.
     * post-condition: The 2d tree is displayed with a level-order
     * traversal.
     * Theta N
     */
    public void levelOrderPrint() {
        Queue q = new Queue();
        q.enqueue(root);
        while (!q.isEmpty()) {
            TreeNode node = (TreeNode) q.dequeue();
            System.out.print(node + ", ");
            if (node.left != null) {
                q.enqueue(node.left);
            }
            if (node.right != null) {
                q.enqueue(node.right);
            }
        }
    }

    /**
     * pre-condition: The 2d tree has been constructed.
     * post-condition: The 2d tree is displayed with a reverse levelorder
     * traversal.
     */
    public void reverseLevelOrderPrint() {
        Queue q = new Queue();
        Stack stack = new Stack();
        q.enqueue(root);
        while (!q.isEmpty()) {
            TreeNode node = (TreeNode) q.dequeue();
            stack.push(node);
            if (node.left != null) {
                q.enqueue(node.left);
            }
            if (node.right != null) {
                q.enqueue(node.right);
            }
        }
        System.out.println();
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + ", ");
        }
    }

    /**
     * pre-condition: The 2d tree has been constructed
     * post-condition: A list of 0 or more crimes is returned.
     *
     * @param x1 coordinates
     * @param y1 --
     * @param x2 --
     * @param y2 --
     * @return return as post
     */
    public ListOfCrimes findPointsInRange(double x1, double y1, double x2, double y2) {

        Coordinate c1 = new Coordinate(x1, y1);
        Coordinate c2 = new Coordinate(x2, y2);
        System.out.println("Searching for points within " + c1 + " and " + c2);
        rangeHelper(c1, c2, this.root);
        System.out.println("Examined nodes: " + crimes.count + " times");
        System.out.println("Found " + crimes.getSize() + " crimes");
        crimes.toKML();
        System.out.println("The crime data has been written to PGHCrimes.KML. It is viewable\n" +
                "in Google Earth Pro.");
        return crimes;

    }

    ListOfCrimes crimes = new ListOfCrimes();

    /**
     * Recursive helper for the above method
     * Pre: parameter were proper
     * post: add crime or continue traverse
     * @param c1 coordinates
     * @param c2
     * @param root current visiting node
     */
    private void rangeHelper(Coordinate c1, Coordinate c2, TreeNode root) {
        //reach to leave node
        if (root == null) {
            return;
        }
        crimes.count++;
        String checker = root.c.insideOf(c1, c2, root.isVertical);

        if (checker.equals("inside")) { //if curr node is inside the rec
            crimes.insert(root.line);
            rangeHelper(c1, c2, root.left);
            rangeHelper(c1, c2, root.right);
        } else if (checker.equals("vLeft")) { //if curr node x coor is left to the rec
            rangeHelper(c1, c2, root.right);
        } else if (checker.equals("vRight")) { //right
            rangeHelper(c1, c2, root.left);
        } else if (checker.equals("vIn")) { //x inside, but y not
            rangeHelper(c1, c2, root.left);
            rangeHelper(c1, c2, root.right);
        } else if (checker.equals("hUp")) { //if y is at the above of the rec
            rangeHelper(c1, c2, root.left);
        } else if (checker.equals("hDown")) { // below
            rangeHelper(c1, c2, root.right);
        } else if (checker.equals("hIn")) { //y inside, but x not
            rangeHelper(c1, c2, root.left);
            rangeHelper(c1, c2, root.right);
        }

    }

    /**
     * pre-condition: the 2d tree has been constructed.
     * The (x1,y1) pair represents a point in space near Pittsburgh and
     * in the state plane coordinate system.
     * post-condition: the distance in feet to the nearest node is
     * returned in Neighbor. In addition, the Neighbor
     * @param x1 x
     * @param y1 y
     * @return as post
     */
    public Neighbor nearestNeighbor(double x1, double y1) {
        Coordinate target = new Coordinate(x1, y1);
        return neighborHelper(target, this.root, new Neighbor(null, Double.MAX_VALUE), 0);

    }

    /**
     * Helper of the above method
     * pre: parameters are proper
     * post: return a local min node
     * @param target the target coordinate
     * @param root the current visiting node
     * @param closest the global min
     * @param counter the count of the visiting
     * @return
     */
    public Neighbor neighborHelper(Coordinate target, TreeNode root, Neighbor closest, int counter) {
        if (root == null) {
            return closest;
        }
        closest.count++;
        double dist = root.c.checkDistance(target);
        if (dist < closest.distance) {
            closest.setDistance(dist);
            closest.setNode(root);
        }
        String checker = root.c.findLocation(target, root.isVertical);
        if (checker.equals("same")) { //if curr node is inside the rec
            return closest;
        } else if (checker.equals("vLeft")) { //if curr node x coor is left to the rec
            closest = neighborHelper(target, root.right, closest, counter);
        } else if (checker.equals("vRight")) { //right
            closest = neighborHelper(target, root.left, closest, counter);
        } else if (checker.equals("vIn")) { //x inside, but y not
            closest = neighborHelper(target, root.left, closest, counter);
            closest = neighborHelper(target, root.right, closest, counter);
        } else if (checker.equals("hUp")) { //if y is at the above of the rec
            closest = neighborHelper(target, root.left, closest, counter);
        } else if (checker.equals("hDown")) { // below
            closest = neighborHelper(target, root.right, closest, counter);
        } else if (checker.equals("hIn")) { //y inside, but x not
            closest = neighborHelper(target, root.left, closest, counter);
            closest = neighborHelper(target, root.right, closest, counter);
        }
        return closest;

    }

    /**
     * Nested class of treenode,
     */
    static class TreeNode {
        /**
         * the treenode have a coordinate, a String array store that line
         *  the left node , and the right node. And a boolean to check if the current is vertical
         */
        Coordinate c;
        String[] line;
        TreeNode left;
        TreeNode right;
        boolean isVertical;

        public TreeNode(String[] line, boolean isVertical) {
            this.line = line;
            this.c = new Coordinate(Double.parseDouble(line[0]), Double.parseDouble(line[1]));
            left = null;
            right = null;
            this.isVertical = isVertical;
        }

        public String[] line() {
            return line;
        }

        @Override
        public boolean equals(Object o) {
            TreeNode oo = (TreeNode) o;
            return this.c.equals(oo.c);
        }

        @Override
        public int hashCode() {
            return c.hashCode();
        }

        public String toString() {
            return c.toString();
        }
    }
}
