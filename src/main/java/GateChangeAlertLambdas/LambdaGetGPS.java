package GateChangeAlertLambdas;

import GateChangeAlertClasses.GetGPS;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LambdaGetGPS implements RequestHandler<Map<String, Integer>, Map<String, Object>> {
    @Override
    public Map<String, Object> handleRequest(Map<String, Integer> input, Context context) {

        int passenger = input.get("passengerList");
        String gpsLocationData = "";
        int gpsLocation = 0;
        int delay = input.get("delay");
        Map<String, Object> output = new HashMap<>();

        try {
            gpsLocationData = GetGPS.getGPS(passenger);

            gpsLocation = GetGPS.checkGPSCoordinates(gpsLocationData);
            output.put("passenger", gpsLocation);

            if(gpsLocation == 0){
                output.put("delay", delay+10);
            }
            else if(gpsLocation == 1){
                output.put("delay", delay+GetGPS.calculateWalkingTime(gpsLocationData));
            }
            else{
                output.put("delay", input.get("delay"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        output.put("checkDelay", input.get("checkDelay"));
        output.put("passengerId", passenger);
        output.put("newGate", input.get("newGate"));

        return output;
    }
}
