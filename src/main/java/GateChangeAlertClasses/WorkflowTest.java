package GateChangeAlertClasses;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.*;

public class WorkflowTest {

    public static void main(String[] args) {
        String input = "1;;3";
        String[] splitInput = input.split(";;");

        int flight_id = Integer.parseInt(splitInput[0]);
        int newGate = Integer.parseInt(splitInput[1]);

        /*
        List<String> passenger = new ArrayList<>();
        GetPassengers.getPassenger(flight_id);
         */

        List<String> passenger = new ArrayList<>();
        passenger = DatabaseAccessClass.accessDatabase("SELECT passenger_id from PASSENGER WHERE flight_id = " + flight_id, "passenger_id");
        System.out.println("Passenger" + passenger);

        double delay = CalcCheckDelay.processImage();
        System.out.println("Delay:" + delay);

        double timeCheckToGate = CalcTimeCheckToGate.calcTimeCheckToGate();
        System.out.println("CheckToGate: " + timeCheckToGate);


        Map<String, Integer> passengerPosition = new HashMap<>();
        for (String pas : passenger) {
            try {
                passengerPosition.put(pas, GetGPS.getGPS(Integer.valueOf(pas)));
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println(passengerPosition);

        for(Map.Entry<String, Integer> entry : passengerPosition.entrySet()){
            InformPassenger.informPassenger(entry.getKey(), entry.getValue(), delay, "Gate");
        }
    }
}
