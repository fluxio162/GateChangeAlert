package GateChangeAlertClasses;

import java.util.ArrayList;
import java.util.Map;

public class GetPassengers {

    static public ArrayList<String> getPassenger(int flight_id)
    {
        ArrayList<String> valueList = new ArrayList<String>();
        valueList = DatabaseAccessClass.accessDatabase("SELECT passenger_id from PASSENGER WHERE flight_id = " + flight_id, "passenger_id");
        return valueList;
    }
}
