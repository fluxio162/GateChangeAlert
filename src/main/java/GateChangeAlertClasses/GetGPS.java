package GateChangeAlertClasses;

import java.sql.*;

public class GetGPS {

    /**
     * receives the passenger_id, gets it's gps location stored in the database and returns it
     * @param passenger_id id of the passenger
     * @return the gps location of the passenger according to the database
     */
    public static String getGPS(int passenger_id) throws ClassNotFoundException, SQLException
    {
        StringBuilder sqlCommand = new StringBuilder("select gps from PASSENGER where passenger_id = ");
        sqlCommand.append(passenger_id);
         
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://db-airport.cyw2qnj2xex2.us-east-1.rds.amazonaws.com"
                      + "user=root&password=password");

        PreparedStatement gpsLocatoin = con.prepareStatement(String.valueOf(sqlCommand));
        ResultSet result = gpsLocatoin.executeQuery();

        return result.getString("gps");
  
    }

}
