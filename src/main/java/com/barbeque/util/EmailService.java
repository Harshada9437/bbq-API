package com.barbeque.util;

import com.barbeque.config.ConfigProperties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by System1 on 9/29/2016.
 */
public class EmailService {
    private static final String USERNAME = ConfigProperties.smtp_name;
    private static final String PASSWORD = ConfigProperties.smtp_password;
    private static final String HOST = ConfigProperties.smtp_host;
    private static final String FROM = ConfigProperties.smtp_from;
    private static final Session session = getSession();

    public static Boolean sendOtp(String to,String name, int otp) {
        Boolean isProcessed = Boolean.FALSE;

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(FROM));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            message.setSubject("Otp Verification");

            message.setText("Hello " + name + ",\n\nYou have requested to  register your device for Barbeque Nation. Kindly see the otp mentioned below.\n\nOne Time Password: \"" + otp + "\"\n\nNote: If you haven't requested this, please contact us immediately.\n\nThanks,\nBBQ");

            Transport.send(message);

            isProcessed = Boolean.TRUE;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return isProcessed;
    }

    private static Session getSession() {
        if (session == null) {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "false");
            props.put("mail.smtp.host", HOST);
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(USERNAME,
                                    PASSWORD);
                        }
                    });
            return session;
        } else {
            return session;
        }
    }
}
