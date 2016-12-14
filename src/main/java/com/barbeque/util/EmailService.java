package com.barbeque.util;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * Created by System1 on 9/29/2016.
 */
public class EmailService {
    private static final String USERNAME = "support@theuniquemedia.in";
    private static final String PASSWORD = "supums0L@fp";
    private static final String HOST = "bh-in-5.webhostbox.net";
    private static final String FROM = "support@theuniquemedia.in";
    private static final Session session = getSession();

    public static void sendNewStudentLogin(String to, String  name,String loginId, String password,int userId) {
        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(FROM));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            message.setSubject("Please check your login details to the institute");

            String msg = "<div>Hello " + name + ",</div>" +
            " <div>&nbsp; </div>" +
                    "<div>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Your Login has been created with our institute.Note the loginId</div>" +
                    "<div> and password given below and use them to login your account and to get </div>" +
                    " <div>updates regarding your profile.</div>" +
                    " <div>&nbsp; </div>" +
                    " <div>&nbsp; </div>" +
                    "<div>" + "<b>" + "Login Details:-" + "</b>" + "</div>" +
                    " <div>&nbsp; </div>" +
                    " <div>" + "<b>" + "LoginId:" + "</b>" + loginId + "</div>" +
                    " <div>" + "<b>Password:</b>" + password + "</div>";

            message.setContent(msg, "text/html; charset=utf-8");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static Session getSession() {
        if (session == null) {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "false");
            props.put("mail.smtp.host", HOST);
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
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
