package GateChangeAlertClasses;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * to check the class GetGPS for its function
 */
public class CheckCoordinates
{

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.out.println("return value: " + GetGPS.getGPS(1));


    }
}