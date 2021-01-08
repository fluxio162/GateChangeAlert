package GateChangeAlertClasses;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class InformPassenger {

    static void informPassenger(String passenger, int location, double delay, double timeToGate)
    {
        final String fromEmail = "innsbruckairportservice@gmail.com";
        final String password = "Innsbru1!1";
        List<String> emailList = DatabaseAccessClass.accessDatabase("SELECT mail from PASSENGER WHERE passenger_id = " + passenger, "mail");
        final String toEmail = emailList.get(0);


        /*
        Setup Email properties
         */
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
        double totalTime = delay+timeToGate+10.0;


        switch(location){
            case 0:
                sendEmail(session, toEmail,"Airport Innsbruck Alert", "Unfortunately, you are still not at the airport. As soon as you enter the airport, the waiting time at the security check-in is " + (int)totalTime + " minutes.");
                break;
            //not at airport

            case 1:
                sendEmail(session, toEmail,"Airport Innsbruck Alert", "Welcome to Innsbruck Airport! Please go to the security check in. The current waiting time is " + (int)totalTime + " minutes.");
                break;
            //at airport

            case 2:
                sendEmail(session, toEmail,"Airport Innsbruck Notification", "We are pleased to welcome you at Innsbruck Airport! We wish you a pleasant flight.");
                break;
            //in security area
        }

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
            Transport.send(msg);

            System.out.println("EMail Sent Successfully! " + toEmail);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
