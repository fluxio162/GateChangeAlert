package GateChangeAlertLambdas;

        import com.amazonaws.services.lambda.runtime.Context;
        import com.amazonaws.services.lambda.runtime.RequestHandler;
        import com.amazonaws.services.rekognition.AmazonRekognition;
        import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
        import com.amazonaws.services.rekognition.model.*;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class LambdaCalcCheckDelay implements RequestHandler<Map<String, Object>, Map<String, Object>> {
    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        Map<String, Object> output = new HashMap<>();
        output.put("output", processImage());
        return output;
    }

    private static int processImage() {
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

        String image = "security queue.jpg";

        String s3Bucket = "wittibucket";

        S3Object s3Obj = new S3Object();
        s3Obj.withBucket(s3Bucket);
        s3Obj.withName(image);

        Image img = new Image();
        img.withS3Object(s3Obj);

        DetectLabelsRequest request = new DetectLabelsRequest();
        request.withImage(img);
        request.withMaxLabels(10);
        request.withMinConfidence(90F);

        int personCount = 0;

        try {

            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            List<Label> labels = result.getLabels();

            for (Label label : labels) {
                System.out.println("Label ::" + label.getName());
                System.out.println("Confidence ::" + label.getConfidence());

                if (label.getName().equals("Person")) {
                    personCount = label.getInstances().size();

                }
            }
        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }

        System.out.println("Total persons: " + personCount);
        return personCount;
    }
}