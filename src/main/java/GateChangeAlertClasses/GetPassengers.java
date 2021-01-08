package GateChangeAlertClasses;

import java.util.ArrayList;

public class GetPassengers {

    static public ArrayList<String> getPassenger(int flight_id)
    {
        String passenger = null;
        int passenger_id=2;
        ArrayList<String> valueList = new ArrayList<String>();

        valueList = DatabaseAccessClass.accessDatabase("SELECT passenger_id from PASSENGER WHERE flight_id = " + flight_id, "passenger_id");
        return valueList;
    }

    public static void main(String[] args) {
        System.out.println(getPassenger(1));
    }





}
