/**
 * The ListNode impelemntation for the linkedlist. It has getters and setters
 * Nick Ma, haoxuanm
 */
public class ListNode {
    //the value holds in the node is the int, represent its position at the hashmap
    private int val;
    //the string is the pattern and it is the key
    private String key;
    //holds the next
    private ListNode next;
    //getters and setters

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    //constructors
    public ListNode() {
        this.key = null;
        this.next = null;
        this.val = 0;
    }

    public ListNode(String key, Integer val) {
        this.val = val;
        this.key = key;
    }

    public ListNode(String key, Integer val, ListNode next) {
        this.val = val;
        this.key = key;
        this.next = next;
    }
}
