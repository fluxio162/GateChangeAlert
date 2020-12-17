package GateChangeAlertClasses;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class GetGPS {

    /**
     * receives the passenger_id and checks if the gps location is within the airport, if it is, it is checked if it is
     * within the security check of the airport.
     * return 0: passenger is not at the airport
     * return 1: passenger is at the airport but not in the security check
     * return 2: passenger is in the security check of the airport
     *
     * @param passenger_id id of the passenger
     * @return the gps location of the passenger according to the database
     */
    public static int getGPS(int passenger_id) throws ClassNotFoundException, SQLException {


        String gpsLocationFromDatabase = getGPSFromDatabase(1);

        int gpsLocation = checkCoordinates(gpsLocationFromDatabase);

        return gpsLocation;
    }

    private static String getGPSFromDatabase(int passenger_id) {
        // TODO: passenger_id add to the command string using a stringbuilder object

        /*

        /*
        StringBuilder sqlCommand = new StringBuilder("select gps from PASSENGER where passenger_id = ");
        sqlCommand.append(passenger_id);

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://db-airport.cyw2qnj2xex2.us-east-1.rds.amazonaws.com"
                      + "user=root&password=password");

        PreparedStatement gpsLocatoin = con.prepareStatement(String.valueOf(sqlCommand));
        ResultSet result = gpsLocatoin.executeQuery();

        return result.getString("gps");


        // Authenticate the RDS Client
        AmazonRDS awsRDS = AmazonRDSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

        // fetch the mySQL database created in the AWS Console
        DescribeDBInstancesResult dbInstResult = awsRDS.describeDBInstances();

        List<DBInstance> dbInstances = dbInstResult.getDBInstances();

        for (DBInstance dbInst : dbInstances) {
            System.out.println("DB Instance:: " + dbInst.getDBName());
        }

        // Endpoint of database
        String RDS_INSTANCE_HOSTNAME = "db-airport.cyw2qnj2xex2.us-east-1.rds.amazonaws.com";
        // Port of Database
        String RDS_INSTANCE_PORT = "3306";

        // name of database 'db-airport'
        String JDBC_URL = "jdbc:oracle:thin:@" + RDS_INSTANCE_HOSTNAME + ":" + RDS_INSTANCE_PORT + ":db-airport";

        try {
            // !!! ERROR: No suitable driver found for jdbc:oracle:thin:@db-airport.cyw2qnj2xex2.us-east-1.rds.amazonaws.com:3306:db-airport
            Connection connection = DriverManager.getConnection(
                    // access data of the database
                    JDBC_URL, "root", "password");

            // verify the connection is successful
            Statement stmt = connection.createStatement();

            // get the GPS Position of passenger with id 'passenger_id' from table 'passenger' in the database
            ResultSet rs = stmt.executeQuery("SELECT gps from PASSENGER WHERE passenger_id = 1");
            while (rs.next()) {
                // the gps position is stored as a string
                String gps = rs.getString("gps");
                System.out.println(gps);
            }

            // close the connection
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

         */

        // a coordinate outside of the airport
        // class returns 0 when this coordinate is used
        // return "47.258185426265165, 11.35069427743045";

        // a coordinate within the airport of innsbruck (but not within the security check)
        // class returns 1 when this coordinate is used
        // return "47.257789704052335, 11.351155464984213";

        // a coordinate within the security check
        // class returns 2 when this coordinate is used
        return "47.25785654273658, 11.351599611921941";
    }


    /**
     * determine if the passenger is at the airport or not
     * based on their gps location stored in the database on AWS RDS (mySQL)
     * the gps points of the airport of innsbruck are:
     * 47.25782045882951, 11.348523974233919
     * 47.25702686622257, 11.348638895473194
     * 47.25743507927046, 11.353708008079538
     * 47.25834795668141, 11.353295777347043
     * @param gpsLocation has the longitude (gpsLocation[0]) and
     *                    latitude (gpsLocation[1]) of the passengers gps location stored
     */
    private static int checkCoordinates(String gpsLocation) {
        {

            // split the String with the gpsLocation into a longitude and a latitude double
            Pattern pattern = Pattern.compile(", ");

            // latitude = coordinates[0]
            // longitude = coordinates[1]
            double[] coordinates = pattern.splitAsStream(gpsLocation)
                    .mapToDouble(Double::parseDouble)
                    .toArray();

            ArrayList<Double> lat_array = new ArrayList<Double>();
            ArrayList<Double> long_array = new ArrayList<Double>();

            /**
             * this is a polygon bounding box of the gps positions of an area within the 
             * airport of innsbruck that we defined as the check in area
             */
            ArrayList<String> securityCheck_lat_long_pairs = new ArrayList<String>();
            securityCheck_lat_long_pairs.add("47.25782045882951, 11.348523974233919");
            securityCheck_lat_long_pairs.add("47.25702686622257, 11.348638895473194");
            securityCheck_lat_long_pairs.add("47.25743507927046, 11.353708008079538");
            securityCheck_lat_long_pairs.add("47.25834795668141, 11.353295777347043");

            // Convert the strings to doubles.
            for (String s : securityCheck_lat_long_pairs) {
                lat_array.add(Double.parseDouble(s.split(",")[0]));
                long_array.add(Double.parseDouble(s.split(",")[1]));
            }

            /**
             * if the passenger is at the airport, the return value of the function coordinate_is_inside_polygon
             * will be TRUE
              */
            if(coordinate_is_inside_polygon(
                    coordinates[0], coordinates[1],
                    lat_array, long_array) == true){
                if (isInSecurityCheck(coordinates)){
                    // passenger is at the airport and in the security check
                    return 2;
                } else{
                    // passenger is at the airport but not in the security check
                    return 1;
                }

            }else{
                // if the passenger is not at the airport the integer 0 will be returned by the function
                return 0;
            }

        }

    }

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
    private static boolean isInSecurityCheck(double[] gpsLocation) {
        {
            ArrayList<Double> lat_array = new ArrayList<Double>();
            ArrayList<Double> long_array = new ArrayList<Double>();

            /**
             * this is a polygon bounding box of the gps positions of an area within the
             * airport of innsbruck that we defined as the check in area
             */
            ArrayList<String> securityCheck_lat_long_pairs = new ArrayList<String>();
            securityCheck_lat_long_pairs.add("47.257913806167274, 11.351468718394406");
            securityCheck_lat_long_pairs.add("47.257938381759864, 11.35168128399997");
            securityCheck_lat_long_pairs.add("47.257769537975584, 11.35172352833711");
            securityCheck_lat_long_pairs.add("47.25774769309808, 11.351510292846283");

            // Convert the strings to doubles.
            for (String s : securityCheck_lat_long_pairs) {
                lat_array.add(Double.parseDouble(s.split(",")[0]));
                long_array.add(Double.parseDouble(s.split(",")[1]));
            }

            /**
             * if the passenger is at the airport, the return value of the function coordinate_is_inside_polygon
             * will be one true
             */
            if(coordinate_is_inside_polygon(
                    gpsLocation[0], gpsLocation[1],
                    lat_array, long_array) == true) {
                    return true;
            } else {
                return false;
            }

        }
    }

    public static boolean coordinate_is_inside_polygon(
            double latitude, double longitude,
            ArrayList<Double> lat_array, ArrayList<Double> long_array)
    {
        double PI = 3.14159265;
        double TWOPI = 2 * PI;

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
        double PI = 3.14159265;
        double TWOPI = 2 * PI;
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

}