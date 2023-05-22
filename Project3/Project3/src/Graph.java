/**
 * The graph that used to represent the connection of courses
 *
 * @author Nick Ma, haoxuanm
 */
public class Graph {
    /**
     * 2D array to represent the adjacency matrix
     */
    int[][] edges;
    /**
     * An array sized with unique courses represent the color of each vertex
     */
    int[] labels;

    public Graph(int size) {
        edges = new int[size][size];
        labels = new int[size];
    }

    /**
     * Add a new edge between two vertex
     * pre: two vertex index is within the range
     * post: build edge
     *
     * @param s vertex 1
     * @param t vertex 2
     */
    public void addEdge(int s, int t) {
        edges[s][t] = 1;
        edges[t][s] = 1;
    }

    /**
     * Check if there is an edge between two vertex
     * pre: two vertexes are within range
     * post: boolean
     *
     * @param s vertex 1
     * @param t vertex 2
     * @return if there is a edge between s and t
     */
    public boolean isEdge(int s, int t) {
        return edges[s][t] == 1 && edges[t][s] == 1;
    }

    //getter and setter
    public void setLabel(int v, int color) {
        labels[v] = color;
    }

    public int getLabel(int v) {
        return labels[v];
    }

    /**
     * Helper method for greedy color: Get the first uncolored vertex
     * pre: the graph has been constructed
     * post: the return the vertex that is the first uncolored
     * theta n avg case
     *
     * @return the vertex that is the first uncolored
     */
    public int firstUncoloredVertex() {
        for (int i = 0; i < labels.length; i++) {
            if (labels[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Helper method for greedy color: get the next uncolored vertex
     * pre: the graph is constructed, v is within range
     * post: retrun the vertex
     * theta n avg case
     *
     * @param v where to start examine
     * @return the vertex
     */
    public int nextUncoloredVertex(int v) {
        v = v + 1;
        for (int i = v; i < labels.length; i++) {
            if (labels[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * toString method for the graph, in the style of adjacency matrix
     * pre: the graph is constructed
     * post: A string has its adjacnecy matrix
     * theta n2 avg case
     *
     * @return A string has its adjacnecy matrix
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges[i].length; j++) {
                sb.append(edges[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
