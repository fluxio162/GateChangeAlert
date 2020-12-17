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

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.out.println(GetGPS.getGPS(4));
    }

}