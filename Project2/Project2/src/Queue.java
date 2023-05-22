/**
 * @author Nick Ma, haoxuanm
 * Queue.java is built with a linked
 * list based queue – using front and rear pointers
 */
public class Queue {
    //underlined linkedlist with head and tail
    MyLinkedList ll;
    //constructor
    public Queue(){
        ll = new MyLinkedList();
    }

    /**
     * add a node to the queue
     * @param n data
     */
    public void enqueue(Object n){
        ll.insert(n);
    }

    /**
     * Remove the first node from the queue
     * @return the first node's data
     */
    public Object dequeue(){
        MyLinkedList.Node node =  ll.removeFirst();
        return node.data;
    }

    /**
     * Get the size of the queue
     * @return size
     */
    public int size(){
        return ll.size;
    }

    /**
     * Check if the queue is empty
     * @return
     */
    public boolean isEmpty(){
        if(size() > 0){

            return false;
        }
        return true;
    }
}
