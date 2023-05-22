/**
 * This is the linkedlist used for implement teh hashmap in a chain hash fashion.
 *
 * @author Nick Ma, haoxuanm
 */
public class MyLinkedList {
    //head, tail, size, keep track a tail help increase performance when add a node
    private ListNode head;
    private ListNode tail;
    private int size;
    //getters and setters

    public ListNode getHead() {
        return head;
    }

    public void setHead(ListNode head) {
        this.head = head;
    }

    public ListNode getTail() {
        return tail;
    }

    public void setTail(ListNode tail) {
        this.tail = tail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    //constructor
    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public MyLinkedList(ListNode node) {
        this.head = node;
        this.tail = node;
        this.size = 1;
    }

    /**
     * Add a node to the end of the link
     *
     * @param node a node from ListNode
     */
    public void addLast(ListNode node) {
        if (isEmpty()) {
            this.head = node;
            this.tail = node;
            this.size = 1;
            return;
        }
        tail.setNext(node);
        this.tail = node;
        this.size++;
    }
    //get the node by indexing

    public ListNode get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        ListNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current;
    }

    public boolean isEmpty() {
        return this.getSize() == 0;
    }


}
