package GateChangeAlertLambdas;

import GateChangeAlertClasses.GetPassengers;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LambdaGetPassengers implements RequestHandler<String, Map<String, Integer>> {
        @Override public Map<String, Integer> handleRequest(String input, Context context){
            String[] splitInput = input.split(";;");

            int flight_id = Integer.parseInt(splitInput[0]);
            String newGate = "Gate " + splitInput[1];

            Map<String, Integer> output = new HashMap<>();
            output.put(newGate, 0);
            List<String> passenger = new ArrayList<>();
            passenger.addAll(GetPassengers.getPassenger(flight_id));
            for(String p : passenger){
                output.put(p, 0);
            }

            return output;
    }
}
