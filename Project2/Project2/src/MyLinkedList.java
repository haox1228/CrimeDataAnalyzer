import java.util.Arrays;

/**
 * @author Nick Ma, haoxuanm
 * The linkedlist that is used in other places except for the special required Stack implementation.
 * Same structure as the other linkedlist
 */
public class MyLinkedList {
    static class Node{
        Object data;
        Node next;
        public Node(String[] input){
            data = Arrays.toString(input);

        }
        public Node(Object data){
            this.data = data;
        }
        public String toString(){
            return data.toString();
        }
    }

    /**
     * Has a head, tail and size
     */
    Node head;
    Node tail;
    int size;

    /**
     * Constructors for different uses
     */
    public MyLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }
    public MyLinkedList(Node n){
        head = n;
        tail = n;
        size = 1;
    }

    /**
     * Insert a node in to the linkedlist
     * Pre: the node is passed in
     * Post: the node is added
     * @param n the node
     */
    public void insert(Node n){
        if(head == null){
            head = n;
            tail = head;
            size++;
            return;
        }
        tail.next = n;
        tail = tail.next;
        size++;
    }
    /**
     * Insert a node in to the linkedlist, also used as enqueue
     * Pre: a object
     * Post: the node of that object is added
     * @param n the node
     */
    public void insert(Object n){
        Node node = new Node(n);
        if(head == null){
            head = node;
            tail = head;
            size++;
            return;
        }
        tail.next = node;
        tail = tail.next;
        size++;
    }

    public String toString(){
        Node h = head;
        StringBuilder sb = new StringBuilder();
        while(h!=null){
            sb.append(h.data).append("\n");
            h = h.next;
        }
        return sb.toString();
    }

    /**
     * Implement features to implement a queue, this is dequeue method
     * Pre: the linklist have to have at least a node
     * Post: the head node is returned
     * @return return the first node
     */
    public Node removeFirst(){
        if(head == null){
            return null;
        }
        Node res = head;
        head = head.next;
        if(head == null){
            tail = null;
        }
        size--;
        return res;
    }
}
