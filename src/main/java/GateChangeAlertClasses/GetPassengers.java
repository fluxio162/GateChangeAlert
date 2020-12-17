package GateChangeAlertClasses;

import java.util.ArrayList;

public class GetPassengers {

    static public String getPassenger(int newGate)
    {
        String passenger = null;
        int passenger_id=2;
        ArrayList<String> valueList = new ArrayList<String>();

        valueList = DatabaseAccessClass.accessDatabase("Select * From PASSENGER Where PASSENGER.passenger_id ="+ passenger_id,"firstname");
        return valueList.get(0);
    }

    public static void main(String[] args) {
        System.out.println(getPassenger(4));
    }





}
