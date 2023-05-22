/**
 * @author Nick Ma, haoxuanm
 * Simple version of arraylist for storing the edgelist. Only necessary methods were
 * implemented. There will be no ensure capacity. Based on the assumption that there
 * will only be 40 vertices at max.
 */
public class EdgeList {
    /**
     * Underling array of coloredlist for the neighbors of a vertex
     */
    ColorList[] list;
    /**
     * Ensure the add method is theta 1 time
     */
    int nextAvailSpot;

    public EdgeList() {
        list = new ColorList[40];
        nextAvailSpot = 0;
    }

    /**
     * Add a new element in to the arraylist
     * Pre: input has to be a coloredlist, and there is available spot
     * post: new element added
     *
     * @param c new element
     */

    public void add(ColorList c) {
        list[nextAvailSpot] = c;
        nextAvailSpot++;
    }

    public ColorList get(int index) {
        return list[index];
    }


}
