package GateChangeAlertClasses;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Source: https://stackoverflow.com/questions/4287780/detecting-whether-a-gps-coordinate-falls-within-a-polygon-on-a-map (selected answer)
 * determine if the passenger is already in the security check in area or not
 * based on their gps location stored in the database on AWS RDS (mySQL)
 * the gps points of the security check in area are: 
 * 47.257913806167274, 11.351468718394406
 * 47.257938381759864, 11.35168128399997
 * 47.257769537975584, 11.35172352833711
 * 47.25774769309808, 11.351510292846283
 */
public class CheckCoordinates
{
    public static double PI = 3.14159265;
    public static double TWOPI = 2*PI;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.out.println("return value: " + GetGPS.getGPS(1));

        ArrayList<Double> lat_array = new ArrayList<Double>();
        ArrayList<Double> long_array = new ArrayList<Double>();

        /**
         * this is a polygon bounding box of the gps positions of an area within the 
         * airport of innsbruck that we defined as the check in area
         */
        ArrayList<String> polygon_lat_long_pairs = new ArrayList<String>();
        polygon_lat_long_pairs.add("47.257913806167274, 11.351468718394406");
        polygon_lat_long_pairs.add("47.257938381759864, 11.35168128399997");
        polygon_lat_long_pairs.add("47.257769537975584, 11.35172352833711");
        polygon_lat_long_pairs.add("47.25774769309808, 11.351510292846283");

        // Convert the strings to doubles.
        for(String s : polygon_lat_long_pairs){
            lat_array.add(Double.parseDouble(s.split(",")[0]));
            long_array.add(Double.parseDouble(s.split(",")[1]));
        }

        // prints TRUE true because the lat/long passed in is
        // inside the bounding box.
        System.out.println(coordinate_is_inside_polygon(
                47.25777637682968, 11.351271259978814,
                lat_array, long_array));

        // prints FALSE because the lat/long passed in
        // is Not inside the bounding box.
        System.out.println(coordinate_is_inside_polygon(
                47.25777637682968, 11.351271259978814,
                lat_array, long_array));

    }
    public static boolean coordinate_is_inside_polygon(
            double latitude, double longitude,
            ArrayList<Double> lat_array, ArrayList<Double> long_array)
    {
        int i;
        double angle=0;
        double point1_lat;
        double point1_long;
        double point2_lat;
        double point2_long;
        int n = lat_array.size();

        for (i=0;i<n;i++) {
            point1_lat = lat_array.get(i) - latitude;
            point1_long = long_array.get(i) - longitude;
            point2_lat = lat_array.get((i+1)%n) - latitude;
            point2_long = long_array.get((i+1)%n) - longitude;
            angle += Angle2D(point1_lat,point1_long,point2_lat,point2_long);
        }

        if (Math.abs(angle) < PI)
            return false;
        else
            return true;
    }

    public static double Angle2D(double y1, double x1, double y2, double x2)
    {
        double dtheta,theta1,theta2;

        theta1 = Math.atan2(y1,x1);
        theta2 = Math.atan2(y2,x2);
        dtheta = theta2 - theta1;
        while (dtheta > PI)
            dtheta -= TWOPI;
        while (dtheta < -PI)
            dtheta += TWOPI;

        return(dtheta);
    }

    public static boolean is_valid_gps_coordinate(double latitude,
                                                  double longitude)
    {
        // This is a bonus function, it's unused, to reject invalid lat/longs.
        if (latitude > -90 && latitude < 90 &&
                longitude > -180 && longitude < 180)
        {
            return true;
        }
        return false;
    }
}