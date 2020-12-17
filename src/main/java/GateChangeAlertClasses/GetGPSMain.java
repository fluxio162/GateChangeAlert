package GateChangeAlertClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;

/**
 * SOURCE: https://github.com/PrimaryInput/Examples/blob/master/ExampleOracle.java
 * Public accessibility is set to Yes
 * ojdbc6.jar (MySQL Connector) is added to the project structure
 */
public class GetGPSMain {

    public static void main(String[] args) {

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
                ResultSet rs = stmt.executeQuery("SELECT gps from PASSENGER WHERE passenger_id = 1");
                while (rs.next()) {
                    // the gps position is stored as a string
                    String gps = rs.getString("gps");
                    System.out.println(gps);
                }

                // close the connection
                stmt.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}