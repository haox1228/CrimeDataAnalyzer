/**
 * @author: Nick Ma, haoxuanm
 * The object ListOfCrimes for part 6. It has a linkedlist underlined and a count
 * It will provide methods for adding crimes to the list and retrieving crimes from the list.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ListOfCrimes {
    /**
     * The under lined linkedlist
     */
    MyLinkedList ll;
    /**
     * The count
     */
    int count;

    /**
     * Constructor
     */
    public ListOfCrimes(){
        ll = new MyLinkedList();
    }
    public ListOfCrimes(MyLinkedList.Node n){
        ll = new MyLinkedList(n);
    }

    /**
     * Insert new node
     * pre: The object should be a string array
     * post: added to the data structure
     * @param data  crime entry which were splitted
     */
    public void insert(String[] data){
        ll.insert(data);
    }

    /**
     * Get the size
     * @return the size;
     */
    public int getSize(){
        return ll.size;
    }

    /**
     * The tostring method followed the request as the sample output
     * @return the output
     */
    public String toString(){
        MyLinkedList.Node h = ll.head;
        ArrayList<String[]> arr = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        while(h != null){
            arr.add((String[]) h.data);
            h = h.next;
        }
        for(String[] str: arr){
            sb.append(Arrays.toString(str)).append('\n');
        }
        return sb.toString();
    }

    /**
     * return a KML representation of the list
     */
    public void toKML(){
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n" +
                "<Document>\n" +
                "<Style id=\"style1\">\n" +
                "<IconStyle>\n" +
                "<Icon>\n" +
                "<href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/blue\n" +
                "-dot.png</href>\n" +
                "</Icon>\n" +
                "</IconStyle>\n" +
                "</Style>\n");

        MyLinkedList.Node node = ll.head;
        String[] data;
        while(node!=null){
            data = (String[]) node.data;
            sb.append(kmlHelper(data[4],data[3],data[8],data[7]));
            node = node.next;
        }
        sb.append("</Document>\n" +
                "</kml>");
        try{
            File crimes = new File("/Users/mahaoxuan/Documents/Spring 2023/Data Structure/Project2/Project2/src/PGHCrimes.KML");
            crimes.createNewFile();
            BufferedWriter write = new BufferedWriter(new FileWriter("/Users/mahaoxuan/Documents/Spring 2023/Data Structure/Project2/Project2/src/PGHCrimes.KML"));
            write.write(sb.toString());
            write.close();
        }catch(IOException e){
            System.out.println("Something went wrong");
            e.printStackTrace();
        }finally {
            System.out.println("file created and written");
        }
    }

    /**
     * Helper to format a single place mark
     * @param name Crime type
     * @param des location
     * @param longi longitude
     * @param lat latitude
     * @return a string formatted as a placemark
     */
    private String kmlHelper(String name, String des,String longi, String lat){
        return "<Placemark>\n" +
                "   <name>"+name+"</name>\n"+
                "   <description>"+ des+ "</description>\n"+
                "   <Point>\n" +
                "       <coordinates>"+ longi+","+lat+"</coordinates>\n"+
                "   </Point>\n"+
                "</Placemark>\n";
    }

}
