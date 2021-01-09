package GateChangeAlertLambdas;

        import GateChangeAlertClasses.CalcCheckDelay;
        import com.amazonaws.services.lambda.runtime.Context;
        import com.amazonaws.services.lambda.runtime.RequestHandler;

        import java.util.Map;

public class LambdaCalcCheckDelay implements RequestHandler<Map<String, Integer>, Map<String, Integer>> {
    @Override
    public Map<String, Integer> handleRequest(Map<String, Integer> input, Context context) {
        Map<String, Integer> output = input;
        for(String s : output.keySet()){
            if(s.startsWith("Gate")){
                output.put(s, CalcCheckDelay.processImage());
            }
        }
        return output;
    }
}