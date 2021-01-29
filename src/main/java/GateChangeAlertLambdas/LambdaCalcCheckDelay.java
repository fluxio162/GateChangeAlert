package GateChangeAlertLambdas;

        import GateChangeAlertClasses.CalcCheckDelay;
        import com.amazonaws.services.lambda.runtime.Context;
        import com.amazonaws.services.lambda.runtime.RequestHandler;
        import java.util.HashMap;
        import java.util.Map;

public class LambdaCalcCheckDelay implements RequestHandler<Map<String, Integer>, Map<String, Object>> {
    @Override
    public Map<String, Object> handleRequest(Map<String, Integer> input, Context context) {
        Map<String, Object> output = new HashMap<>();
        output.put("delay", CalcCheckDelay.processImage());
        output.put("newGate", input.get("newGate"));
        return output;
    }
}