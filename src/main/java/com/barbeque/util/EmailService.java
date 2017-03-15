package com.barbeque.util;

import com.barbeque.config.ConfigProperties;
import com.barbeque.dto.request.ReportDTO;

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

            message.setText("Hello " + name + ",\n\nYou have requested to  register your device for Barbeque Nation. Kindly" +
                    " see the otp mentioned below.\n\nOne Time Password: \""
                    + otp + "\"\n\nNote: If you haven't requested this, please contact us immediately.\n\nThanks,\nBBQ");

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

    public static Boolean sendReport(String to, ReportDTO dailyReportDTO, ReportDTO monthlyReportDTO) {
        Boolean isProcessed = Boolean.FALSE;

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(FROM));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            message.setSubject("Daily feedback report update.");
            message.setContent(message, "text/html; charset=utf-8");

            Float avgNegative= (float) dailyReportDTO.getNegativeCount() /(float)dailyReportDTO.getTotalCount()*100f;
            Float avgAddressed= (float) dailyReportDTO.getUnAddressedCount() /(float)dailyReportDTO.getNegativeCount()*100f;

            Float avgN= (float) monthlyReportDTO.getNegativeCount() /(float)monthlyReportDTO.getTotalCount()*100f;
            Float avgA= (float) monthlyReportDTO.getUnAddressedCount() /(float)monthlyReportDTO.getNegativeCount()*100f;

            String msg = "<div>Hi " + dailyReportDTO.getUserName() +",</div>" +
                    " <div>&nbsp; </div>" +
                    "<div>"+ "<b>" +"Feedback counts are:- " + "</b> " + "</div>" +
                    " <div>Total count:-&nbsp;" + dailyReportDTO.getTotalCount() + "</div>" +
                    " <div>Negative count:-&nbsp;" + dailyReportDTO.getNegativeCount() + "&nbsp;("+ avgNegative + "%)</div>" +
                    " <div>Addressed count:-&nbsp;" + dailyReportDTO.getUnAddressedCount() + "&nbsp;("+ avgAddressed + "%)</div>" +
                    " <div>&nbsp; </div>" +
                    "<div>Please login to your Barbeque Nation account for further details.</div>" +
                    " <div>&nbsp; </div>" +
                    "<div>Thanks,</div>" +
                    "<div>BBQ</div>";

            message.setContent(msg, "text/html; charset=utf-8");

            Transport.send(message);

            isProcessed = Boolean.TRUE;

        } catch (MessagingException e) {
            isProcessed = Boolean.FALSE;
            e.printStackTrace();
        }
        return isProcessed;
    }
}
