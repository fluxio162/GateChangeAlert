package GateChangeAlertLambdas;

import GateChangeAlertClasses.InformPassenger;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LambdaInformPassenger implements RequestHandler<Map<String, Integer>, Map<String, Object>> {

    @Override
    public Map<String, Object> handleRequest(Map<String, Integer> input, Context context) {


        int delay = input.get("delay");
        int passengerId = input.get("passengerId");
        int gps = input.get("passenger");
        int newGate = input.get("newGate");

        InformPassenger.informPassenger(passengerId, gps, delay, newGate);

        Map<String, Object> output = new HashMap<>();
        output.put("output", "Notification sent successfully!");

        return output;
    }

}
