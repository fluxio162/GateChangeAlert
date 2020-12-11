package GateChangeAlertLambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public class LambdaInformPassenger implements RequestHandler<Map<String, Object>, Map<String, Object>>{
        @Override public Map<String, Object> handleRequest(Map<String, Object> input, Context context){
            final String fromEmail = "innsbruckairportservice@gmail.com";
            final String password = "Innsbru1!1";
            final String toEmail = "david.arnold@student.uibk.ac.at";

        /*
        Setup Email properties
         */
            System.out.println("SSLEmail Start");
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
            props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
            props.put("mail.smtp.port", "465"); //SMTP Port

        /*
        Authentication of sender email
         */
            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };

        /*
        Start session
         */
            Session session = Session.getDefaultInstance(props, auth);
            System.out.println("Session created");
            sendEmail(session, toEmail,"Alert", "You are receiving a test email from Innsbruck Airport!");
        return null;
    }
    public static void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            // Set email header
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("noreply@ibk.at", "Innsbruck Airport"));

            msg.setReplyTo(InternetAddress.parse("noreply@ibk.at", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
