package GateChangeAlertLambdas;

import GateChangeAlertClasses.GetPassengers;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LambdaGetPassengers implements RequestHandler<Map<String, Integer>, Map<String, Object>> {
        @Override public Map<String, Object> handleRequest(Map<String, Integer> input, Context context){

            Map<String, Object> output = new HashMap<>();

            List<String> passenger = new ArrayList<>();
            passenger.addAll(GetPassengers.getPassenger((Integer)input.get("flight_id")));

            List<Integer> passengerList = new ArrayList<>();

            for(String s : passenger){
                passengerList.add(Integer.valueOf(s));
            }

            output.put("passengerList", passengerList);
            output.put("passengerCount", passengerList.size());
            return output;
    }
}
