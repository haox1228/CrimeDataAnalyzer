package edu.cmu.andrew.haoxuanm;

import edu.colorado.nodes.ObjectNode;

/**
 * Project 1 Task 1
 *
 * @author: Nick Ma, haoxuanm
 */
public class SinglyLinkedList {
    /**
     * size is the length of the linkedlist
     * head is the node that pointing to the start of the linkedlist
     * tail is the node that pointing to the end of the linkedlist
     * iterator is used as a copy of reference pointing to the node at the linkedlist for iteration purpose
     * iterPos represents the index
     */
    int size;
    ObjectNode head;
    ObjectNode tail;
    ObjectNode iterator;

    /**
     * Constructor: initialize head, tail, and size
     */
    public SinglyLinkedList() {
    }

    /**
     * reset the iterator pointing to the first node
     * big theta: f(1)
     * pre condition: head node is not pointing to null
     * post condition: iterator reset to the head
     */

    public void reset() {
        iterator = head;
    }

    /**
     * Check if the iterator reach to the end
     * big theta: f(1)
     * @return true if not then end, otherwise false
     */
    public boolean hasNext() {
        if (iterator != null) {
            return true;
        }
        return false;
    }

    /**
     * return the Object pointed to by the iterator and increment the iterator to the next node in the list.
     * This reference becomes null if the object returned is the last node on the list.
     * @return return the Object pointed to by the iterator
     * big theta: f(1)
     */
    public java.lang.Object next() {
        if(iterator == null){
            return null;
        }
        java.lang.Object data = iterator.getData();
        iterator = iterator.getLink();
        return data;
    }

    /**
     * count the nodes
     * @return the number of nodes
     */
    public int countNodes() {
        return size;
    }

    /**
     * Add a node containing the Object c to the head of the linked list.
     * @param c  a single Object This method increments a count of the number of nodes on the list.
     *           The count is returned by countNodes.
     * Big theta: f(1)
     * Pre: input is a java.lang.object
     * post: head node will point to the new node
     */
    public void addAtFrontNode(java.lang.Object c) {
        ObjectNode n = new ObjectNode(c, null);
        if (size == 0) {
            head=n;
            tail=n;
            ++size;
            iterator = head;
            return;
        }
        n.setLink(head);
        head=n;
        ++size;
    }

    /**
     * Add a node containing the Object c to the tail of the linked list.
     * @param c  a single Object This method increments a count of the number of nodes on the list.
     *           The count is returned by countNodes.
     * Big theta: f(1)
     * Pre: input is a java.lang.object
     * post: tail node will point to the new node
     */
    public void addAtEndNode(java.lang.Object c) {
        ObjectNode n = new ObjectNode(c, null);
        if (size == 0) {
            head=n;
            tail=n;
            ++size;
            iterator = head;
            return;
        }
        tail.setLink(n);
        tail=n;
        ++size;
    }

    /**
     * Big theta : f(n)
     * pre: int was passed in
     * post: return the object at that position
     * @param i the index
     * @return reference to an object with list index i. The first object in the list is at position 0.
     */
    public java.lang.Object getObjectAt(int i) {
        if (i < 0 || i > size - 1) {
            return null;
        }
        reset();
        for (int index = 0; index < i; index++) {
            iterator = iterator.getLink();
        }
        return iterator.getData();
    }

    /**
     * Get the last
     * @return the object at the last
     */
    public Object getLast() {
        return tail.getLink().getData();
    }

    /**
     * Format output as a string which copies all the value for each of the node
     * Big theta: o(n)
     * @return the string format linkedlist
     * post: a string format of linkedlist value were returned
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (head == null) {
            return "";
        }
        ObjectNode in = head;
        while (in != null) {
            sb.append(in.getData());
            in = in.getLink();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] str = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        SinglyLinkedList ll = new SinglyLinkedList();
        for (String s : str) {
            ll.addAtEndNode(s);
        }
        System.out.println("SinglyLinkedList ll has: "+ ll.toString());
        SinglyLinkedList revLL = new SinglyLinkedList();
        for (String s : str) {
            revLL.addAtFrontNode(s);
        }
        System.out.println("SinglyLinkedList revLL has: "+revLL.toString());
        ll.reset();
        System.out.println("iterator of ll is at head after reset: "+ll.iterator.getData().toString());
        System.out.println("The object at index 5 of ll is: "+ ll.getObjectAt(5));
        System.out.println("Traverse ll:");
        ll.reset();
        while (ll.hasNext()) {
            System.out.print(ll.next());
        }
    }
}
