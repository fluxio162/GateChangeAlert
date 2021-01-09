package GateChangeAlertLambdas;

import GateChangeAlertClasses.CalcCheckDelay;
import GateChangeAlertClasses.CalcTimeCheckToGate;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

public class LambdaCalcTimeCheckToGate implements RequestHandler<Map<String, Integer>, Map<String, Integer>>{
        @Override public Map<String, Integer> handleRequest(Map<String, Integer> input, Context context){
            Map<String, Integer> output = input;
            for(String s : output.keySet()){
                if(s.startsWith("Gate")){
                    int delay = output.get(s);
                    output.put(s, delay + CalcTimeCheckToGate.calcTimeCheckToGate());
                }
            }
            return output;
    }
}
