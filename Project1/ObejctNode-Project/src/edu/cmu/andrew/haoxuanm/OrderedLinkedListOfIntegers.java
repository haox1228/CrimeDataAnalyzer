package edu.cmu.andrew.haoxuanm;

import edu.colorado.nodes.ObjectNode;

import java.util.Arrays;
import java.util.Random;

/**
 * Project 1 Task 1
 *
 * @author: Nick Ma, haoxuanm
 */
public class OrderedLinkedListOfIntegers {
    /**
     * size is the length of the linkedlist
     * head is the node that pointing to the start of the linkedlist
     * tail is the node that pointing to the end of the linkedlist
     * iterator is used as a copy of reference pointing to the node at the linkedlist for iteration purpose
     */
    int size;
    ObjectNode head;
    ObjectNode tail;
    ObjectNode iterator;
    /**
     * Constructor
     */
    public OrderedLinkedListOfIntegers() {
    }
    /**
     * Constructor: initialize head, tail, and size with info provided
     */
    public OrderedLinkedListOfIntegers(ObjectNode start, ObjectNode end, int size) {
        head = start;
        tail = end;
        this.size = size;
    }
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
    //same as singly linkedlist
    public int countNodes() {
        return size;
    }

    /**
     * Add the a objectnode with the input number as where it suppose to be
     * @param num the num input
     * Big theta: f(n)
     * pre: input has to be a int or Integer
     * post: a new node is added to the linkedlist
     */
    public void sortedAdd(Integer num) {
        ObjectNode n = new ObjectNode(num, null);
        if (size == 0) {
            head=n;
            tail=n;
            ++size;
            iterator = head;
            return;
        }
        if(num < (Integer) head.getData()){
            n.setLink(head);
            head = n;
            size++;
            return;
        }
        if(num > (Integer) tail.getData()){
            tail.setLink(n);
            tail = tail.getLink();
            size++;
            return;

        }
        ObjectNode cur = head.getLink();
        ObjectNode prev = head;
        while (cur != null &&
                !((num >= (Integer) prev.getData()) && (num <= (Integer) cur.getData()))) {
            prev = cur;
            cur = cur.getLink();
        }
        if (cur != null) {
            n.setLink(cur);
            prev.setLink(n);
        } else {
            prev.setLink(n);
            tail.setLink(n);
        }
        size++;


    }

    /**
     * Merge two linkedlist, and maintain ordered
     * @param o another ordered linkedlist
     * @return a merged and sorted linkedlist
     * Big theta: f(m+n)
     * Precondition: the input and the itself have to hold Integer value as its data.
     * and both are sorted by the same order
     * Post: a merged and sorted linkedlist returned
     */
    public OrderedLinkedListOfIntegers merge(OrderedLinkedListOfIntegers o) {
        if (size == 0 && o.size == 0) {
            return new OrderedLinkedListOfIntegers();
        } else if (size != 0 && o.size == 0) {
            return this;
        } else if (size == 0 && o.size != 0) {
            return o;
        } else {
            ObjectNode dum = new ObjectNode(Integer.MIN_VALUE, null);
            ObjectNode h = dum;
            reset();
            o.reset();

            while (hasNext() && o.hasNext()) {
                if ((Integer) iterator.getData() < (Integer) o.iterator.getData()) {
                    dum.setLink(this.iterator);
                    next();
                } else {
                    dum.setLink(o.iterator);
                    o.next();
                }
                dum = dum.getLink();
            }
            if (hasNext()) {
                dum.setLink(iterator);
                return new OrderedLinkedListOfIntegers(h.getLink(), tail, size + o.size);
            }
            if (o.hasNext()) {
                dum.setLink(o.iterator);
                return new OrderedLinkedListOfIntegers(h.getLink(), o.tail, size + o.size);
            }
            return new OrderedLinkedListOfIntegers();
        }
    }
    //same
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (head == null) {
            return "";
        }
        ObjectNode in = head;
        while (in != null) {
            sb.append(in.getData() + " ");
            in = in.getLink();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int[] rand1 = new int[20];
        int[] rand2 = new int[20];
        Random rd = new Random();
        for (int i = 0; i < 20; i++) {
            rand1[i] = rd.nextInt(10000);
            rand2[i] = rd.nextInt(10000);
        }
        System.out.println("Randomly Generated int array 1:" + Arrays.toString(rand1));
        System.out.println("Randomly Generated int array 2:" + Arrays.toString(rand2));
        OrderedLinkedListOfIntegers ll = new OrderedLinkedListOfIntegers();
        for (int i : rand1) {
            ll.sortedAdd(i);
        }
        System.out.println("rand1 were added to the linkedlist ll: " + ll);
        ll.reset();
        System.out.print("traverse ll: ");
        while (ll.hasNext()) {
            System.out.print(ll.next() + " ");
        }
        System.out.println("\nsize of ll: " + ll.size);
        OrderedLinkedListOfIntegers ll2 = new OrderedLinkedListOfIntegers();
        for (int i : rand2) {
            ll2.sortedAdd(i);
        }
        System.out.println("rand1 were added to the linkedlist ll2: " + ll2);
        OrderedLinkedListOfIntegers mergedLL = ll.merge(ll2);
        System.out.println("ll and ll2 were merged: " + mergedLL);
        System.out.println("size of merged linkedlist: " + mergedLL.size);

    }

}
