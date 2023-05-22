
import java.util.LinkedList;

/**
 * @author Nick Ma, haoxuanm
 * The key class for this project. It will take the CrimeEntries and build:
 * 1. Weighted Graph
 * 2. MST in Prims
 * 3. Tree format of MST with a root node
 * 4. a pre order tree walk to the MST
 * 5. Store the hamiltonian cycle
 * 6. distance of the tour
 * 7. run all permutation to find the optimal tour
 * 8. calculate distance of the optimal tour
 */

public class WeightedGraph {
    public double[][] adjacencyMatrix; //the 2d matrix represents the weighed graph
    public CrimeEntry[] entries;//the filtered entries
    /**
     * MST in array representation
     * For example
     * index  pair
     * 0      0 0.0
     * 1      0 1111
     * 2      1 2222
     * 3      2 4444
     * This means vertex 0 is 1's parent, and the distance is 1111
     * vertex 1 is 2's parent, and distance is 2222 and so on...
     */
    public KeyValPair[] MST;
    public LinkedList<Integer> poTreeWalk; //pre order tree walk tour
    public LinkedList<Integer> hamiltonianCycle; //hamiltonian cycle, which is poTreeWalk append 0
    public LinkedList<Integer> optimalRoute; //the optimal route
    TreeNode root; //the root node of the tree representation of the MST in Prim
    double minDist; //the minimum distance

    /**
     * Constructor of the class, basically is the background driver of the project
     * It will perform as the sequence suggest at the class doc
     *
     * @param ce
     */
    public WeightedGraph(CrimeEntry[] ce) {
        adjacencyMatrix = new double[ce.length][ce.length];
        entries = ce;
        buildGraph();
        MST = buildMST();
        buildTree();
        poTreeWalk = new LinkedList<>();
        preOrderTreeWalk(root);
        hamiltonianCycle = new LinkedList<>(poTreeWalk);
        hamiltonianCycle.add(0);
        findOptimal();
    }

    /**
     * Populate 2D graph
     * Pre-con: the entries should not be 0
     */
    private void buildGraph() {
        for (int i = 0; i < entries.length; i++) {
            for (int j = i; j < entries.length; j++) {
                double dist = entries[i].calcDistance(entries[j]);
                adjacencyMatrix[i][j] = dist;
                adjacencyMatrix[j][i] = dist;
            }
        }
    }

    /**
     * get the distance from the 2D array
     *
     * @param i start vertex
     * @param j end vertex
     * @return the distance
     */
    public double getDistance(int i, int j) {
        return adjacencyMatrix[i][j];
    }

    /**
     * Build the MST, which is the implementation of prim algo
     * pre con: the 2d array has been build
     *
     * @return the array representation of the MST as suggest in the field doc
     */
    public KeyValPair[] buildMST() {
        //initialize
        int v = adjacencyMatrix.length;
        boolean[] checked = new boolean[v];
        KeyValPair[] MST = new KeyValPair[v];
        MinHeap minHeap = new MinHeap(v);
        double dist;
        MST[0] = new KeyValPair(0, 0);
        //initialize the minheap with all defaults except for itself,
        //assume we pick vertex 0 as start
        minHeap.add(new KeyValPair(0, 0));
        for (int i = 1; i < v; i++) {
            minHeap.add(new KeyValPair(i, Double.MAX_VALUE));
        }

        //start to build the MST
        while (!minHeap.isEmpty()) {
            //get the min element
            KeyValPair curr = minHeap.delete();
            //find its vertex
            int currV = curr.adjIndex;
            //mark it as checked
            checked[currV] = true;
            //loop through all vertex and update the values in the heap
            for (int i = 0; i < v; i++) {
                dist = adjacencyMatrix[currV][i];
                if (!checked[i] && dist != 0) {
                    int inHeap = minHeap.getIndex(i);
                    if (dist < minHeap.get(inHeap).distance && inHeap != -1) {
                        MST[i] = new KeyValPair(currV, dist);
                        minHeap.updatePair(inHeap, dist);
                    }
                }
            }
        }
        return MST;
    }

    /**
     * Recursive build the MST into tree representation
     * pre: the array MST has been built
     */
    public void buildTree() {
        root = buildSubtree(null, 0);
    }

    //helper, its a DFS
    private TreeNode buildSubtree(TreeNode parent, int index) {
        //get the current pair from MST by index
        KeyValPair currPair = MST[index];
        //Create a new node for it
        TreeNode cur = new TreeNode(parent, currPair.distance, index);
        //Get all the child vertices by searching throught the MST
        LinkedList<Integer> children = searchNext(index);
        //base case: current stack node have no child, then means reached to the leaf
        if (children.isEmpty()) {
            return cur;
        }
        //corner case: skip case when cur is the root node, index 0 of the vertexÒ that parent itself
        if (cur.parent == null) {
            children.removeFirst();
        }
        //loop through the children of the current parent node
        for (int child : children) {
            TreeNode childNode = buildSubtree(cur, child);
            cur.addChildren(childNode);

        }

        return cur;
    }

    /**
     * helper for build the MST tree, used to search the location of the vertex in the MST array
     *
     * @param target vertex
     * @return list of targets
     */
    private LinkedList<Integer> searchNext(int target) {
        LinkedList<Integer> res = new LinkedList<>();
        for (int i = 0; i < MST.length; i++) {
            if (MST[i].adjIndex == target) {
                res.add(i);
            }
        }
        return res;

    }

    /**
     * Perform a preorder tree walk in recursion
     *
     * @param node the node, default will be the root
     */
    public void preOrderTreeWalk(TreeNode node) {
        if (node == null) {
            return;
        }
        poTreeWalk.add(node.vertex);
        for (TreeNode child : node.children) {
            preOrderTreeWalk(child);
        }
    }

    /**
     * Get the distance in miles for a tour
     *
     * @param path the tour
     * @return the distance in double
     */
    public double pathSumMiles(LinkedList<Integer> path) {
        int vStart, vEnd;
        double thisDist, sum = 0;
        for (int i = 1; i < path.size(); i++) {
            vStart = path.get(i - 1);
            vEnd = path.get(i);
            thisDist = getDistance(vStart, vEnd);
            sum += thisDist;
        }
        return sum * 0.00018939;
    }

    /**
     * find the optimal by all permutation in DFS
     */
    public void findOptimal() {
        minDist = Double.MAX_VALUE;
        int[] route = new int[entries.length - 1];
        for (int i = 0; i < route.length; i++) {
            route[i] = i + 1;
        }
        dfs(route, 0);

    }

    /**
     * Use memoization for permutation problem to save space
     * this is swap helper
     *
     * @param i    i
     * @param j    j
     * @param nums input
     */
    private void swap(int i, int j, int[] nums) {
        int val = nums[j];
        nums[j] = nums[i];
        nums[i] = val;
    }

    /**
     * DFS helper
     *
     * @param nums  the input array
     * @param index the current looking index
     */
    private void dfs(int[] nums, int index) {
        //when all swaps are complete, add 0 to front and all 0 to end
        if (index == nums.length) {
            LinkedList<Integer> thisRes = new LinkedList<>();
            thisRes.add(0);
            for (int i : nums) {
                thisRes.add(i);
            }
            thisRes.add(0);
            //calculate the distance
            double thisDist = pathSumMiles(thisRes);
            //if it is smaller than the global min, update the global min and set this as the local optimum
            if (thisDist < minDist) {
                minDist = thisDist;
                optimalRoute = thisRes;
            }
            return;
        }
        //do the dfs
        for (int i = index; i < nums.length; i++) {
            swap(index, i, nums);
            dfs(nums, index + 1);
            swap(index, i, nums);
        }
    }


}

