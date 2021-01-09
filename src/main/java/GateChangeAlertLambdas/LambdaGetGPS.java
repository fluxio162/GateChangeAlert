package GateChangeAlertLambdas;

import GateChangeAlertClasses.GetGPS;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LambdaGetGPS implements RequestHandler<Map<String, Integer>, Map<String, Integer>>{
        @Override public Map<String, Integer> handleRequest(Map<String, Integer> input, Context context){

            Map<String, Integer> output = input;
            for (String pas : output.keySet()) {
                if(!pas.startsWith("Gate")){
                    try{
                        output.put(pas, GetGPS.getGPS(Integer.valueOf(pas)));
                    }catch (ClassNotFoundException e){
                        e.printStackTrace();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }
        return output;
    }
}
