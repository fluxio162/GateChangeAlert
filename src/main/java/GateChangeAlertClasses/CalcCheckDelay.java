package GateChangeAlertClasses;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;

import java.util.List;

public class CalcCheckDelay {

    public static void main(String[] args) {
        processImage();
    }

    static public Double calcCheckDelay()
    {
        Double securityCheckDelay = null;

        
        return securityCheckDelay;
    }

    public static int processImage(){
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

        int avg_time = 2;

        String image = "security.jpg";

        String s3Bucket ="airportbucketibk";

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

            for(Label label: labels)
            {
                System.out.println("Label ::" + label.getName());
                System.out.println("Confidence ::" + label.getConfidence());

                if(label.getName().equals("Person")){
                    personCount = label.getInstances().size();

                }
            }
        }
        catch(AmazonRekognitionException e)
        {
            e.printStackTrace();
        }

        return personCount*avg_time;
    }
}


