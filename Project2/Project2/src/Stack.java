/**
 * @author Nick Ma, haoxuanm
 * This is the stack implemented by only head linkedlist
 */
public class Stack {
    LinkedListWithOnlyHead ll;
    public Stack(){
        ll = new LinkedListWithOnlyHead();
    }

    /**
     * Push method, will insert to the last of the linkedlist
     * @param n the data
     */
    public void push(Object n){
        ll.insert(n);

    }

    /**
     * Pop, which will get the last element fom the linkedlist
     * @return the data of the last node
     */
    public Object pop(){

        return ll.removeLast();
    }

    /**
     * Check if the stack is empty
     * @return true if empty
     */
    public boolean isEmpty(){
       return ll.isEmpty();
    }
}
