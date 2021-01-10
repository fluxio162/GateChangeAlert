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
        Map<String, Object> output = new HashMap<>();

        try {
            output.put("passenger", GetGPS.getGPS(passenger));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        output.put("delay", input.get("delay"));
        output.put("passengerId", passenger);
        output.put("newGate", input.get("newGate"));

        return output;
    }
}
