import java.util.Arrays;

/**
 * @authod Nick Ma, haoxuanm
 * The linkedlist with only a head pointer as requested to build the stack
 */
public class LinkedListWithOnlyHead {
    /**
     * Node class
     */
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
     * This linkedlist has only a head
     */
    Node head;

    public LinkedListWithOnlyHead(){
        head = null;
    }

    /**
     * Insert a new node to the linkedlist.
     * The best case would be theta 1 if the cur linkedlist is null;
     * The average case would be theta n since reach to the tail without a tail ref has to visit all the node
     * Pre: the input has to be java object
     * Post: the node has been added to the linkedlist with the data
     * @param o
     */
    public void insert(Object o){
        Node node = new Node(o);
        if(head == null){
            head = node;
            return;
        }
        Node h = head;
        while(h.next != null){
            h = h.next;
        }
        h.next = node;
    }

    /**
     * Remove the last element from the linkedlist
     * Pre condition: the linkedlist cannot be null;
     * Post condition: the last node has been successfully removed
     * @return the last node's data
     */
    public Object removeLast(){
        Node res;
        if(head.next == null){
            res = head;
            head = null;
            return res;
        }
        Node prev = null;
        Node h = head;
        while(h.next != null){
            prev = h;
            h = h.next;
        }
        res = h;
        prev.next = null;
        return  res.data;
    }

    /**
     * Check if the linkedlist is empty
     * @return
     */
    public boolean isEmpty(){
        return head == null;
    }
}
