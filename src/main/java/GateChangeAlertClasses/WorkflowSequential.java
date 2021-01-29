package GateChangeAlertClasses;

import java.sql.SQLException;
import java.util.*;

public class WorkflowSequential {

    public static void main(String[] args) {

        long startTime = System.nanoTime();

        // input
        int flight_id = 1;
        int newGate = 3;

        // getPassenger
        List<String> passenger = new ArrayList<>(GetPassengers.getPassenger(flight_id));
        List<Integer> passengerList = new ArrayList<>();

        for(String s : passenger){
            passengerList.add(Integer.valueOf(s));
        }

        int passengerCount = passengerList.size();

        // calcCheckDelay
        int checkDelay = CalcCheckDelay.processImage();

        // calcTimeCheckToGate
        int delay = checkDelay + CalcTimeCheckToGate.calcTimeCheckToGate(flight_id);

        // getGps
        Map<Integer, List<Integer>> passengerMap = new HashMap<>();
        for(Integer pas : passengerList){
            try {
                int passengerDelay = 0;
                String gpsLocationData = GetGPS.getGPS(pas);
                int gpsLocation = GetGPS.checkGPSCoordinates(gpsLocationData);

                if(gpsLocation == 0){
                    passengerDelay = delay + 10;
                }
                else if(gpsLocation == 1){
                    passengerDelay = delay + GetGPS.calculateWalkingTime(gpsLocationData);
                }
                else{
                    passengerDelay = delay;
                }

                List<Integer> valueList = new ArrayList<>();
                valueList.add(gpsLocation);
                valueList.add(passengerDelay);

                passengerMap.put(pas, valueList);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // informPassenger
        for(Map.Entry<Integer, List<Integer>> entry : passengerMap.entrySet()){

            InformPassenger.informPassenger(entry.getKey(), entry.getValue().get(0), checkDelay, entry.getValue().get(1), newGate);
            Map<String, Object> output = new HashMap<>();
            output.put("output", "Notification sent successfully!");
        }

        long stopTime = System.nanoTime();
        double elapsedTimeInSecond = stopTime - startTime;
        System.out.println("Execution time: " + elapsedTimeInSecond  / 1_000_000 + " ms");
    }
}
