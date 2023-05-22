import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * This object used to generate the KML file
 *
 * @author Nick Ma, haoxuanm
 */
public class KML {
    //route1 is the TSP, route2 is the optimal
    LinkedList<Integer> route1, route2;
    //The input entries
    CrimeEntry[] vertices;

    //constructor
    public KML(LinkedList<Integer> route1, LinkedList<Integer> route2, CrimeEntry[] vertices) {
        this.route1 = route1;
        this.route2 = route2;
        this.vertices = vertices;

    }

    /**
     * Generate the KML
     */
    public void generateKML() {
        String[] coors = getCoor();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                        "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n" +
                        "<Document>\n" +
                        "<name>Pittsburgh TSP</name><description>TSP on Crime</description><Style id=\"style6\">\n" +
                        "<LineStyle>\n" +
                        "<color>73FF0000</color>\n" +
                        "<width>5</width>\n" +
                        "</LineStyle>\n")
                .append("</Style>\n" +
                        "<Style id=\"style5\">\n" +
                        "<LineStyle>\n" +
                        "<color>507800F0</color>\n" +
                        "<width>5</width>\n" +
                        "</LineStyle>\n" +
                        "</Style>\n" +
                        "<Placemark>\n" +
                        "<name>TSP Path</name>\n" +
                        "<description>TSP Path</description>\n" +
                        "<styleUrl>#style6</styleUrl>\n" +
                        "<LineString>\n" +
                        "<tessellate>1</tessellate>\n" +
                        "<coordinates>\n")
                .append(getRoute(route1, coors))
                .append("</coordinates>\n" +
                        "</LineString>\n" +
                        "</Placemark>\n" +
                        "<Placemark>\n" +
                        "<name>Optimal Path</name>\n" +
                        "<description>Optimal Path</description>\n" +
                        "<styleUrl>#style5</styleUrl>\n" +
                        "<LineString>\n" +
                        "<tessellate>1</tessellate>\n" +
                        "<coordinates>\n")
                .append(getRoute(route2, coors))
                .append("</coordinates>\n" +
                        "</LineString>\n" +
                        "</Placemark>\n" +
                        "</Document>\n" +
                        "</kml>");
        try {
            File file = new File("src/PGHCrimes.kml");
            PrintWriter pw = new PrintWriter("src/PGHCrimes.kml");
            pw.println(sb.toString());
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    /**
     * get the coordinate
     *
     * @return the coordinates with format
     */
    private String[] getCoor() {
        String[] res = new String[vertices.length];
        String coor;
        for (int i = 0; i < vertices.length; i++) {
            coor = vertices[i].longi + "," + vertices[i].lat;
            res[i] = coor;
        }
        return res;
    }

    /**
     * get the route
     *
     * @param route input route
     * @param coors input coordinates
     * @return the formatted lat,longi pair ready for KML
     */
    private static String getRoute(LinkedList<Integer> route, String[] coors) {
        StringBuilder sb = new StringBuilder();
        String suffix = ",0.000000\n";
        for (int i = 0; i < route.size(); i++) {
            sb.append(coors[route.get(i)]).append(suffix);
        }
        return sb.toString();
    }
}
