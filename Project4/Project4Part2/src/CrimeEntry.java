/**
 * Object CrimeEntry, used to decode the CSV and convert it into a POJO
 *
 * @author Nick Ma, haoxuanm
 */
public class CrimeEntry {
    //Columns as fiedld of this object
    Coordinate coor;
    String time, street, offence, tract, lat, longi;
    String crimeDate;

    //constructor
    public CrimeEntry(String[] ipt) {
        coor = new Coordinate(Double.parseDouble(ipt[0]), Double.parseDouble(ipt[1]));
        time = ipt[2];
        street = ipt[3];
        offence = ipt[4];
        crimeDate = ipt[5];
        tract = ipt[6];
        lat = ipt[7];
        longi = ipt[8];
    }

    public double calcDistance(CrimeEntry ce) {
        return this.coor.getDistance(ce.coor);
    }

    public String toString() {
        String sb = coor.getX() + ", " +
                coor.getY() + ", " +
                time + ", " +
                street + ", " +
                offence + ", " +
                crimeDate + ", " +
                tract + ", " +
                lat + ", " +
                longi + "\n";
        return sb;
    }
}
