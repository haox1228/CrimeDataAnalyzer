import java.io.*;
import java.util.StringTokenizer;

/**
 * @author Nick Ma, haoxuanm
 * This is the scedule class that takes the role of build tree and graph, color graph and IO.
 */

public class Schedule {
    RedBlackTree tree;
    Graph graph;
    EdgeList edgeList;
    /**
     * courses: all the courses with duplicate entries
     */
    String[] courses;
    /**
     * current number of courses
     */
    int courseIndex;
    /**
     * All the student, element is the number courses the student take
     */
    int[] students;
    /**
     * current number of students
     */
    int studentIndex;

    /**
     * initilaize the scedule, with new redblakc tree, new edgelist, new course and student array
     */
    public Schedule() {
        tree = new RedBlackTree();
        //graph = new Graph(40);
        edgeList = new EdgeList();
        courses = new String[200];
        courseIndex = 0;
        students = new int[40];
        studentIndex = 0;
    }

    /**
     * a greedy heuristic to color a graph
     * pre: graph is built
     * post: color all the vertex that is ok to be in a same color in the graph
     *
     * @param color: the current color
     */
    public void greedy(int color) {
        boolean found;
        int v;
        ColorList.Node w;
        ColorList newclr = new ColorList();
        v = graph.firstUncoloredVertex();
        while (v != -1) {
            found = false;
            w = newclr.getHeadNode();
            while (w != null) {
                if (graph.isEdge(v, w.getW())) {
                    found = true;

                }
                w = w.next;
            }
            if (found == false) {
                graph.setLabel(v, color);
                newclr.addFirst(v);
            }
            v = graph.nextUncoloredVertex(v);
        }
        edgeList.add(newclr);
    }

    /**
     * Caller of the greedy algorhtm
     * pre: graph is built
     * post: all the vertex of the graph is colored
     */
    public void colorGraph() {
        int color = 1;
        while (graph.firstUncoloredVertex() != -1) {
            greedy(color);
            color++;
        }
    }

    /**
     * Used to print the schedule
     * Pre: the graph is build, the edgelist is build
     * post: return a formatted string of final exam schedule
     *
     * @return a formatted string of final exam schedule
     */
    public String printSchedule() {
        String prefix = "Final Exam Period ";
        String pointer = " => ";
        StringBuilder sb = new StringBuilder();
        ColorList cl;
        for (int i = 0; i < edgeList.nextAvailSpot; i++) {
            cl = edgeList.list[i];
            ColorList.Node iter = cl.getHeadNode();
            sb.append(prefix).append(i).append(pointer);
            while (iter != null) {
                sb.append(tree.getCourse(iter.getW())).append(" ");
                iter = iter.next;
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String args[]) {
        try {
            //Build two bufferreader for two scedules
            BufferedReader in =
                    new BufferedReader(
                            new FileReader(new File("src/input0"))
                    );
            //initiliaze the schedule and collect data
            Schedule s0 = new Schedule();
            readFile(s0, in);

            BufferedReader in2 =
                    new BufferedReader(
                            new FileReader(new File("src/input"))
                    );
            Schedule s = new Schedule();
            readFile(s, in2);
            //Format output
            StringBuilder sb = new StringBuilder();
            sb.append("haouxanm").append("\n");
            sb.append(s0.printGraph()).append("\n");
            sb.append(s0.printSchedule()).append("\n");
            sb.append(s.printGraph()).append("\n");
            sb.append(s.printSchedule());

            File file = new File("src/result.txt");
            PrintWriter pw = new PrintWriter("src/result.txt");
            pw.println(sb.toString());
            pw.close();

        } catch (IOException e) {
            System.out.println("IO Exception");
        }

    }

    /**
     * File reader
     *
     * @param s0 schedule
     * @param in buffer
     * @throws IOException
     */
    private static void readFile(Schedule s0, BufferedReader in) throws IOException {
        String line0;
        line0 = in.readLine();
        while (line0 != null) {
            s0.processLine(line0);
            line0 = in.readLine();
        }
        s0.buildTree();
        s0.buildGraph();
        s0.colorGraph();
    }

    /**
     * Process line, just like sample code
     *
     * @param line
     */
    public void processLine(String line) {
        StringTokenizer st;
        // use space, and tab for delimeters
        st = new StringTokenizer(line, " \t"); // use split if you prefer
        int count = 0;
        while (st.hasMoreTokens()) {
            if (count == 1) {
                students[studentIndex] = Integer.parseInt(st.nextToken());
                studentIndex++;
            } else if (count > 1) {
                courses[courseIndex] = st.nextToken();
                //System.out.println(st.nextToken());
                courseIndex++;
            } else {
                st.nextToken();
            }
            count++;
        }
    }

    /**
     * Build the rbt, based on the course array
     * pre: all the courses from the file is recorded
     * post: the rbt with non dup elements is built
     */
    public void buildTree() {
        int index = 0;
        while (courses[index] != null) {
            tree.insert(courses[index]);
            index++;
        }

    }

    /**
     * Build a graph by the help of array courses and students
     * pre: the tree is built, so the number of unique element is know
     * post: the graph is built
     * theta number of entries * unique courses^2 * log(unique courses)
     */
    public void buildGraph() {
        graph = new Graph(tree.getSize());
        int sI = 0;
        int numCourse;
        int curr = 0;
        while (students[sI] != 0) {
            numCourse = students[sI];
            for (int i = curr; i < curr + numCourse - 1; i++) {
                for (int j = i + 1; j < curr + numCourse; j++) {
                    graph.addEdge(tree.getIndex(courses[i]), tree.getIndex(courses[j]));
                    graph.addEdge(tree.getIndex(courses[j]), tree.getIndex(courses[i]));
                }
            }
            curr += numCourse;
            sI++;
        }
    }

    /**
     * Print the graph
     *
     * @return the adjacency matrix
     */
    public String printGraph() {
        return graph.toString();
    }


}
