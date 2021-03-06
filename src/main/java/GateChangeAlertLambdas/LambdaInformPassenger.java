package GateChangeAlertLambdas;

import GateChangeAlertClasses.InformPassenger;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public class LambdaInformPassenger implements RequestHandler<Map<String, Integer>, Map<String, Object>> {

    @Override
    public Map<String, Object> handleRequest(Map<String, Integer> input, Context context) {

        int delay = input.get("delay");
        int passengerId = input.get("passengerId");
        int gps = input.get("passenger");
        int newGate = input.get("newGate");
        int checkDelay = input.get("checkDelay");

        InformPassenger.informPassenger(passengerId, gps, checkDelay, delay, newGate);

        Map<String, Object> output = new HashMap<>();
        output.put("output", "Notification sent successfully!");

        return output;
    }

}
