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


public class maindeletthis {

    public static void main(String[] args) {

        //Authenticate RDS Client
        AmazonRDS awsRDS = AmazonRDSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

        //Let's fetch all the databases created in AWS Console
        DescribeDBInstancesResult dbInstResult = awsRDS.describeDBInstances();

        List<DBInstance> dbInstances = dbInstResult.getDBInstances();

        for (DBInstance dbInst : dbInstances) {
            System.out.println("DB Instance:: " + dbInst.getDBName());
        }

        //Let's Connect to our database
        String RDS_INSTANCE_HOSTNAME = "db-airport.cyw2qnj2xex2.us-east-1.rds.amazonaws.com";
        String RDS_INSTANCE_PORT = "3306";

        String JDBC_URL = "jdbc:oracle:thin:@" + RDS_INSTANCE_HOSTNAME + ":" + RDS_INSTANCE_PORT + ":orcl";

        try {

            Connection connection = DriverManager.getConnection(
                    JDBC_URL, "root", "password");

            //verify the connection is successful
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select gps from PASSENGER where passenger_id = 1");
            while (rs.next()) {
                String gps = rs.getString("gps");
                System.out.println(gps);
            }

            //close the connection
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    }