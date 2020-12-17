package GateChangeAlertClasses;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseAccessClass {

    public static ArrayList<String> accessDatabase(String sqlCommand, String columnLabel){

        // list of values returned by database
        ArrayList<String> valueList = new ArrayList<String>();

        // next value returned
        String value;

        // Endpoint of database
        String RDS_INSTANCE_HOSTNAME = "db-airport.cyw2qnj2xex2.us-east-1.rds.amazonaws.com";
        // Port of Database
        String RDS_INSTANCE_PORT = "3306";

        // name of database 'db-airport'
        String JDBC_URL = "jdbc:mysql://" + RDS_INSTANCE_HOSTNAME + ":" + RDS_INSTANCE_PORT + "/Airport?useSSL=false";

        try {
            try (Connection connection = DriverManager.getConnection(JDBC_URL, "root", "password")) {

                // verify the connection is successful
                Statement stmt = connection.createStatement();

                // get the GPS Position of passenger with id 'passenger_id' from table 'passenger' in the database
                ResultSet rs = stmt.executeQuery(sqlCommand);
                while (rs.next()) {
                    // the gps position is stored as a string
                    value = rs.getString(columnLabel);
                    valueList.add(value);
                }

                // close the connection
                stmt.close();
                connection.close();
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return valueList;

    }

}
