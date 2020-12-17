package GateChangeAlertClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * SOURCE: https://github.com/PrimaryInput/Examples/blob/master/ExampleOracle.java
 * Public accessibility is set to Yes
 * ojdbc6.jar (MySQL Connector) is added to the project structure
 */
public class GetGPSMain {

    public static void main(String[] args) {

        // list of values returned by database
        ArrayList<String> valueList = new ArrayList<String>();

        valueList = DatabaseAccessClass.accessDatabase("SELECT gps from PASSENGER WHERE passenger_id = 1", "gps");

        System.out.println(valueList);
    }

}