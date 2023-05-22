/**
 * @author Nick Ma, haoxuanm
 * This is the linkedlist that used to store a node and its neighor.
 */
public class ColorList {
    /**
     * Node class, has a next pointer and the vertex value
     */
    static class Node {
        Node next;
        int w;

        public Node() {
            next = null;
            w = -1;
        }

        public Node(int w) {
            next = null;
            this.w = w;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return this.next;
        }

        public void setW(int w) {
            this.w = w;
        }

        public int getW() {
            return w;
        }
    }

    /**
     * Head and tail of the linkedlist
     */
    Node head;
    Node tail;

    /**
     * Constructor
     */
    public ColorList() {
        head = null;
        tail = null;
    }

    /**
     * Add to the end of the list
     * Pre condition: input has to be an integer
     * Post: new node has been add to the end of the linkedlist
     * Theta 1
     *
     * @param v: input
     */
    public void add(int v) {
        Node n = new Node(v);
        if (head == null) {
            head = n;
            tail = head;

            return;
        }
        tail.next = n;
        tail = tail.next;
    }

    /**
     * Return the head value of the linkedlist
     *
     * @return head value of the linkedlist
     */
    public int getHead() {
        return head == null ? -1 : head.getW();
    }

    /**
     * Return the head node
     *
     * @return head node
     */
    public Node getHeadNode() {
        return head;
    }

    /**
     * Return the tail value
     *
     * @return tail value
     */
    public int getTail() {
        return tail == null ? -1 : head.getW();
    }

    /**
     * Add to the start of the linkedlist
     * Pre: input has to be a integer
     * post: new node add as the new head
     * theta 1
     *
     * @param v input
     */
    public void addFirst(int v) {
        Node n = new Node(v);
        if (head == null) {
            head = n;
            tail = head;
            return;
        }
        n.next = head;
        head = n;
    }
}
