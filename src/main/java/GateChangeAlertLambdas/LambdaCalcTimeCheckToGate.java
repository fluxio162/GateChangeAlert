package GateChangeAlertLambdas;

import GateChangeAlertClasses.CalcCheckDelay;
import GateChangeAlertClasses.CalcTimeCheckToGate;
import GateChangeAlertClasses.GetPassengers;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public class LambdaCalcTimeCheckToGate implements RequestHandler<Map<String, Integer>, Map<String, Object>>{
        @Override public Map<String, Object> handleRequest(Map<String, Integer> input, Context context){
            Map<String, Object> output = new HashMap<>();
            output.put("checkDelay", input.get("delay"));
            output.put("delay", (input.get("delay") + CalcTimeCheckToGate.calcTimeCheckToGate(input.get("newGate"))));
            output.put("newGate", input.get("newGate"));
            return output;
    }
}
