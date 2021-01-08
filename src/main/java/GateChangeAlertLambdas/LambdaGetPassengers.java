package GateChangeAlertLambdas;

import GateChangeAlertClasses.GetPassengers;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

public class LambdaGetPassengers implements RequestHandler<Map<String, Object>, Map<String, Object>>{
        @Override public Map<String, Object> handleRequest(Map<String, Object> input, Context context){
            System.out.println("Hello World from GetPassengers");

            System.out.println(GetPassengers.getPassenger(1));

        return null;
    }
}
