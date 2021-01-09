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

public class LambdaInformPassenger implements RequestHandler<Map<String, Integer>, String>{

        @Override public String handleRequest(Map<String, Integer> input, Context context){

            int delay = 0;
            int timeToGate = 10;
            String newGate = "";

            for(String s : input.keySet()){
                if(s.startsWith("Gate")){
                    delay = input.get(s);
                    newGate = s;
                }

            }

            for(Map.Entry<String, Integer> entry : input.entrySet()){
                if(!entry.getKey().startsWith("Gate")){
                    InformPassenger.informPassenger(entry.getKey(), entry.getValue(), delay, newGate);
                }

            }
        return "All notifications sent successfully!";
    }

}
