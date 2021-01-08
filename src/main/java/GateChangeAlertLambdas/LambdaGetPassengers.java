package GateChangeAlertLambdas;

import GateChangeAlertClasses.DatabaseAccessClass;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;
import java.util.List;

public class LambdaGetPassengers implements RequestHandler<String, List<String>> {
        @Override public List<String> handleRequest(String input, Context context){
            String[] splitInput = input.split(";;");

            int flight_id = Integer.parseInt(splitInput[0]);
            int newGate = Integer.parseInt(splitInput[1]);

            List<String> passenger = new ArrayList<>();
            passenger = DatabaseAccessClass.accessDatabase("SELECT passenger_id from PASSENGER WHERE flight_id = " + flight_id, "passenger_id");

            return passenger;
    }
}
