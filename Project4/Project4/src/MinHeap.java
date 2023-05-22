/**
 * MinHeap implementation, studied from https://faculty.cs.niu.edu/~freedman/340/340notes/340heap.htm
 * @author Nick Ma, haoxuanm
 */

public class MinHeap {
    //Store the key val pair
    KeyValPair[] heap;
    //size of the heap, max size of the heap
    int size, max;
    //constructor
    public MinHeap(int max){
        this.max = max;
        size = 0;
        heap = new KeyValPair[++max];

    }
    //getters
    private int getParent(int ind){
        return ind/2;
    }
    private int getLc(int ind){
        return ind*2;
    }

    private int getRc(int ind){
        return (ind*2)+1;
    }
    //swap two heap nodes at the array
    private void swap(int a, int b){
        KeyValPair aC = this.heap[a];
        this.heap[a] = this.heap[b];
        this.heap[b] = aC;
    }

    /**
     * Percolate up
     * @param ind the index of the heap node to do this
     */
    private void percolateUp(int ind){
        while(ind > 0){
            int parent = getParent(ind);
            if(heap[ind].compareTo(heap[parent]) < 0){
                swap(ind, parent);
                ind = parent;
            }else{
                break;
            }
        }
    }

    /**
     * Percolate down
     * @param ind index of the heap node to do this
     */
    private void percolateDown(int ind){
        while(true){
            int lc = getLc(ind);
            int rc = getRc(ind);
            int min = ind;

            if(lc < size && heap[lc].compareTo(heap[min]) < 0){
                min = lc;
            }

            if(rc < size && heap[rc].compareTo(heap[min]) < 0){
                min = rc;
            }

            if(min != ind){
                swap(min, ind);
                ind = min;
            }else{
                break;
            }
        }
    }

    /**
     * Add a node to the heap
     * @param kvp the input
     */
    public void add(KeyValPair kvp){
        heap[size] = kvp;
        size++;
        percolateUp(size -1);
    }

    /**
     * Delete the minimum node from the heap
     * @return the min node
     */
    public KeyValPair delete(){
        KeyValPair res = heap[0];
        size--;
        heap[0] = heap[size];
        percolateDown(0);
        return res;
    }

    /**
     * get the node by the index, like an array
     * @param ind index
     * @return kvp
     */
    public KeyValPair get(int ind){
        return heap[ind];
    }

    /**
     * get a specific index of a heap node, by calling its vertex number
     * @param vertex the vertex number
     * @return the index where it stored in the underlying heap array
     */
    public int getIndex(int vertex){
        for(int i = 0; i< size; i++){
            if(heap[i].adjIndex == vertex){
                return i;
            }
        }
        return -1;
    }

    /**
     * update the distance of a KVP to newer value
     * @param ind the index where the node is located at in the array
     * @param dist the new distance
     */
    public void updatePair(int ind, double dist){
        heap[ind].distance = dist;
        percolateUp(ind);
    }

    /**
     * Check if there is any elements in the heap
     * @return true is nothing, false if there is something
     */
    public boolean isEmpty(){
        return size == 0;
    }

}
