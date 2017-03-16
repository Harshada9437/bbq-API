package com.barbeque.util;

import com.barbeque.config.ConfigProperties;
import com.barbeque.dto.request.ReportDTO;
import com.barbeque.request.report.ReportData;

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

            String table = "",row="";
            for (int i = 1; i < monthlyReportDTO.getOutlets().size(); i++){
                ReportData dailyData =  dailyReportDTO.getOutlets().get(i);
                ReportData monthlyData = monthlyReportDTO.getOutlets().get(i);
                row = "<tr style=\"background-color: #eceaea;\">\n" +
                        "<td align=\"center\" valign=\"top\" class=\"textContent\">\n" +
                       dailyData.getStoreId() +
                        "</td>\n" +
                        "<td align=\"center\" valign=\"top\" class=\"textContent\">\n" +
                        "<b>"+dailyData.getTotalCount() +"</b> <small>("+monthlyData.getTotalCount()+")</small>\n" +
                        "</td>\n" +
                        "<td align=\"center\" valign=\"top\" class=\"textContent\">\n" +
                        "<b>"+dailyData.getNegativeCount() +"</b> <small>("+monthlyData.getNegativeCount()+")</small>\n" +
                        "</td>\n" +
                        "<td align=\"center\" valign=\"top\" class=\"textContent\">\n" +
                        "<b>"+dailyData.getUnAddressedCount()+"</b><small>("+monthlyData.getUnAddressedCount()+")</small>\n" +
                        "</td>\n" +
                        "<td align=\"center\" valign=\"top\" class=\"textContent\">\n" +
                        "<b>"+dailyData.getDailyBillCount()+" </b><small>("+monthlyData.getMonthlyBillCount()+")</small>\n" +
                        "</td>\n" +
                        "</tr>\n";
               table=table+row;
            }

            String msg="<!DOCTYPE html >\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n" +
                    "<meta name=\"format-detection\" content=\"telephone=no\" />\n" +
                    "<title>Feedback Report</title>\n" +
                    "<style type=\"text/css\">\n" +
                    "html { background-color:#E1E1E1; margin:0; padding:0; }\n" +
                    "body, #bodyTable, #bodyCell, #bodyCell{height:100% !important; margin:0; padding:0; width:100% !important;font-family:Helvetica, Arial, \"Lucida Grande\", sans-serif;}\n" +
                    "table{border-collapse:collapse;}\n" +
                    "table[id=bodyTable] {width:100%!important;margin:auto;max-width:500px!important;color:#7A7A7A;font-weight:normal;}\n" +
                    "img, a img{border:0; outline:none; text-decoration:none;height:auto; line-height:100%;}\n" +
                    "a {text-decoration:none !important;border-bottom: 1px solid;}\n" +
                    "h1, h2, h3, h4, h5, h6{color:#5F5F5F; font-weight:normal; font-family:Helvetica; font-size:20px; line-height:125%; text-align:Left; letter-spacing:normal;margin-top:0;margin-right:0;margin-bottom:10px;margin-left:0;padding-top:0;padding-bottom:0;padding-left:0;padding-right:0;}\n" +
                    ".ReadMsgBody{width:100%;} .ExternalClass{width:100%;} \n" +
                    ".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div{line-height:100%;}\n" +
                    "table, td{mso-table-lspace:0pt; mso-table-rspace:0pt;} \n" +
                    "#outlook a{padding:0;} \n" +
                    "img{-ms-interpolation-mode: bicubic;display:block;outline:none; text-decoration:none;} \n" +
                    "body, table, td, p, a, li, blockquote{-ms-text-size-adjust:100%; -webkit-text-size-adjust:100%; font-weight:normal!important;} \n" +
                    ".ExternalClass td[class=\"ecxflexibleContainerBox\"] h3 {padding-top: 10px !important;} \n" +
                    "h1{display:block;font-size:26px;font-style:normal;font-weight:normal;line-height:100%;}\n" +
                    "h2{display:block;font-size:20px;font-style:normal;font-weight:normal;line-height:120%;}\n" +
                    "h3{display:block;font-size:17px;font-style:normal;font-weight:normal;line-height:110%;}\n" +
                    "h4{display:block;font-size:18px;font-style:italic;font-weight:normal;line-height:100%;}\n" +
                    ".flexibleImage{height:auto;}\n" +
                    ".linkRemoveBorder{border-bottom:0 !important;}\n" +
                    "table[class=flexibleContainerCellDivider] {padding-bottom:0 !important;padding-top:0 !important;}\n" +
                    "body, #bodyTable{background-color:#E1E1E1;}\n" +
                    "#emailHeader{background-color:#E1E1E1;}\n" +
                    "#emailBody{background-color:#FFFFFF;}\n" +
                    "#emailFooter{background-color:#E1E1E1;}\n" +
                    ".nestedContainer{background-color:#F8F8F8; border:1px solid #CCCCCC;}\n" +
                    ".emailButton{background-color:#205478; border-collapse:separate;}\n" +
                    ".buttonContent{color:#FFFFFF; font-family:Helvetica; font-size:18px; font-weight:bold; line-height:100%; padding:15px; text-align:center;}\n" +
                    ".buttonContent a{color:#FFFFFF; display:block; text-decoration:none!important; border:0!important;}\n" +
                    ".emailCalendar{background-color:#FFFFFF; border:1px solid #CCCCCC;}\n" +
                    ".emailCalendarMonth{background-color:#205478; color:#FFFFFF; font-family:Helvetica, Arial, sans-serif; font-size:16px; font-weight:bold; padding-top:10px; padding-bottom:10px; text-align:center;}\n" +
                    ".emailCalendarDay{color:#205478; font-family:Helvetica, Arial, sans-serif; font-size:60px; font-weight:bold; line-height:100%; padding-top:20px; padding-bottom:20px; text-align:center;}\n" +
                    ".imageContentText {margin-top: 10px;line-height:0;}\n" +
                    ".imageContentText a {line-height:0;}\n" +
                    "#invisibleIntroduction {display:none !important;} \n" +
                    "span[class=ios-color-hack] a {color:#275100!important;text-decoration:none!important;} /* Remove all link colors in IOS (below are duplicates based on the color preference) */\n" +
                    "span[class=ios-color-hack2] a {color:#205478!important;text-decoration:none!important;}\n" +
                    "span[class=ios-color-hack3] a {color:#8B8B8B!important;text-decoration:none!important;}\n" +
                    ".a[href^=\"tel\"], a[href^=\"sms\"] {text-decoration:none!important;color:#606060!important;pointer-events:none!important;cursor:default!important;}\n" +
                    ".mobile_link a[href^=\"tel\"], .mobile_link a[href^=\"sms\"] {text-decoration:none!important;color:#606060!important;pointer-events:auto!important;cursor:default!important;}\n" +
                    "@media only screen and (max-width: 480px){\n" +
                    "body{width:100% !important; min-width:100% !important;}\n" +
                    "table[id=\"emailHeader\"],\n" +
                    "table[id=\"emailBody\"],\n" +
                    "table[id=\"emailFooter\"],\n" +
                    "table[class=\"flexibleContainer\"],\n" +
                    "td[class=\"flexibleContainerCell\"] {width:100% !important;}\n" +
                    "td[class=\"flexibleContainerBox\"], td[class=\"flexibleContainerBox\"] table {display: block;width: 100%;text-align: left;}\n" +
                    "td[class=\"imageContent\"] img {height:auto !important; width:100% !important; max-width:100% !important; }\n" +
                    "img[class=\"flexibleImage\"]{height:auto !important; width:100% !important;max-width:100% !important;}\n" +
                    "img[class=\"flexibleImageSmall\"]{height:auto !important; width:auto !important;}\n" +
                    "table[class=\"flexibleContainerBoxNext\"]{padding-top: 10px !important;}\n" +
                    "table[class=\"emailButton\"]{width:100% !important;}\n" +
                    "td[class=\"buttonContent\"]{padding:0 !important;}\n" +
                    "td[class=\"buttonContent\"] a{padding:15px !important;}\n" +
                    "}\n" +
                    "</style>" +
                    "</head>\n" +
                    "<body bgcolor=\"#E1E1E1\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\">\n" +
                    "<center style=\"background-color:#E1E1E1;\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" id=\"bodyTable\" style=\"table-layout: fixed;max-width:100% !important;width: 100% !important;min-width: 100% !important;\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\" id=\"bodyCell\">\n" +
                    "<table bgcolor=\"#FFFFFF\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" id=\"emailBody\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"color:#FFFFFF;\" bgcolor=\"#3498db\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" class=\"flexibleContainer\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\" width=\"500\" class=\"flexibleContainerCell\">\n" +
                    "<table border=\"0\" cellpadding=\"30\" cellspacing=\"0\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\" class=\"textContent\">\n" +
                    "<h1 style=\"color:#FFFFFF;line-height:100%;font-family:Helvetica,Arial,sans-serif;font-size:35px;font-weight:normal;margin-bottom:5px;text-align:center;\">Feedbacks Summary</h1>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\">\n" +
                    "<table border=\"0\" cellpadding=\"30\" cellspacing=\"0\" width=\"500\" class=\"flexibleContainer\">\n" +
                    "<tr>\n" +
                    "<td valign=\"top\" width=\"500\" class=\"flexibleContainerCell\">\n" +
                    "<table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td align=\"left\" valign=\"top\" class=\"flexibleContainerBox\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"210\" style=\"max-width: 100%;\">\n" +
                    "<tr>\n" +
                    "<td align=\"left\" class=\"textContent\">\n" +
                    "<h3 style=\"color:#5F5F5F;line-height:125%;font-family:Corbel;font-size:20px;font-weight:bold;margin-top:8px;margin-bottom:3px;text-align:left;\">Total Bills</h3>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<div style=\"margin-bottom: 15px;margin-top:5px;\">\n" +
                    "<label style=\"background-color:#9036d4;padding-top: 6px; padding-bottom: 6px;padding-left: 8px;padding-right: 8px;color: white;\" >" + dailyReportDTO.getDailyBillCount() + "</label>\n" +
                    "<small>Since Yesterday.</small>\n" +
                    "</div>\n" +
                    "<div style=\"\">\n" +
                    "<label style=\"background-color:#642b90;padding-top: 6px; padding-bottom: 6px;padding-left: 8px;padding-right: 8px;color: white;\">"+monthlyReportDTO.getMonthlyBillCount()+"</label>\n" +
                    "<small>Till Date This Month.</small>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "<td align=\"right\" valign=\"middle\" class=\"flexibleContainerBox\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"210\" style=\"max-width: 100%;\">\n" +
                    "<tr>\n" +
                    "<td align=\"left\" class=\"textContent\">\n" +
                    "<h3 style=\"color:#5F5F5F;line-height:125%;font-family:Corbel;font-size:20px;font-weight:bold;margin-top:8px;margin-bottom:3px;text-align:left;\">Total Feedbacks</h3>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<div style=\"margin-bottom: 15px;margin-top:5px;\">\n" +
                    "<label style=\"background-color:#40c740;padding-top: 6px; padding-bottom: 6px;padding-left: 8px;padding-right: 8px;color: white;\" >"+dailyReportDTO.getTotalCount()+"</label>\n" +
                    "<small>Since Yesterday.</small>\n" +
                    "</div>\n" +
                    "<div style=\"\">\n" +
                    "<label style=\"background-color:#2d9a2d;padding-top: 6px; padding-bottom: 6px;padding-left: 8px;padding-right: 8px;color: white;\">"+monthlyReportDTO.getTotalCount()+"</label>\n" +
                    "<small>Till Date This Month.</small>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" class=\"flexibleContainer\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\" width=\"500\" class=\"flexibleContainerCell\">\n" +
                    "<table class=\"flexibleContainerCellDivider\" border=\"0\" cellpadding=\"30\" cellspacing=\"0\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\" style=\"padding-top:0px;padding-bottom:0px;\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\" style=\"border-top:1px solid #C8C8C8;\"></td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\">\n" +
                    "<table border=\"0\" cellpadding=\"30\" cellspacing=\"0\" width=\"500\" class=\"flexibleContainer\">\n" +
                    "<tr>\n" +
                    "<td valign=\"top\" width=\"500\" class=\"flexibleContainerCell\">\n" +
                    "<table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td align=\"left\" valign=\"top\" class=\"flexibleContainerBox\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"210\" style=\"max-width: 100%;\">\n" +
                    "<tr>\n" +
                    "<td align=\"left\" class=\"textContent\">\n" +
                    "<h3 style=\"color:#5F5F5F;line-height:125%;font-family:Corbel;font-size:20px;font-weight:bold;margin-top:8px;margin-bottom:3px;text-align:left;\">Negative Feedbacks</h3>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<div style=\"margin-bottom: 15px;margin-top:8px;\">\n" +
                    "<label style=\"background-color:red;padding-top: 6px; padding-bottom: 6px;padding-left: 8px;padding-right: 8px;color: white;\" >"+dailyReportDTO.getNegativeCount()+"</label>\n" +
                    "<small>Since Yesterday.</small>\n" +
                    "</div>\n" +
                    "<div style=\"\">\n" +
                    "<label style=\"background-color:brown;padding-top: 6px; padding-bottom: 6px;padding-left: 8px;padding-right: 8px;color: white;\">"+monthlyReportDTO.getNegativeCount()+"</label>\n" +
                    "<small>Till Date This Month.</small>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "<td align=\"right\" valign=\"middle\" class=\"flexibleContainerBox\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"210\" style=\"max-width: 100%;\">\n" +
                    "<tr>\n" +
                    "<td align=\"left\" class=\"textContent\">\n" +
                    "<h3 style=\"color:#5F5F5F;line-height:125%;font-family:Corbel;font-size:20px;font-weight:bold;margin-top:8px;margin-bottom:3px;text-align:left;font-size: 18px;\">Unaddressed Feedbacks</h3>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<div style=\"margin-bottom: 15px;margin-top:8px;\">\n" +
                    "<label style=\"background-color:#2ba5f5;padding-top: 6px; padding-bottom: 6px;padding-left: 8px;padding-right: 8px;color: white;\" >"+dailyReportDTO.getUnAddressedCount()+"</label>\n" +
                    "<small>Since Yesterday.</small>\n" +
                    "</div>\n" +
                    "<div style=\"\">\n" +
                    "<label style=\"background-color:#146498;padding-top: 6px; padding-bottom: 6px;padding-left: 8px;padding-right: 8px;color: white;\">"+monthlyReportDTO.getUnAddressedCount()+"</label>\n" +
                    "<small>Till Date This Month.</small>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\">\n" +
                    "<table border=\"0\" cellpadding=\"8\" cellspacing=\"0\" width=\"500\" class=\"flexibleContainer\">\n" +
                    "<tr>\n" +
                    "<td align=\"left\" class=\"textContent\">\n" +
                    "<h3 style=\"color:#5F5F5F;font-family:Corbel;font-size:20px;font-weight:bold;margin-top:0;margin-bottom:2px;text-align:left;font-size: 20px;margin-bottom: -12px;\">Outlet-Wise Summary:</h3>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td valign=\"top\" width=\"500\" class=\"flexibleContainerCell\">\n" +
                    "<table border=\"1\" cellpadding=\"3\" cellspacing=\"0\" width=\"100%\">\n" +
                    "<tr style=\"background: #ea6314;color: white; font-size:14px\">\n" +
                    "<td align=\"center\" rowspan=\"2\"valign=\"top\" class=\"textContent\">\n" +
                    "Outlet\n" +
                    "</td>\n" +
                    "<td align=\"center\"  valign=\"top\" class=\"textContent\">\n" +
                    "Total\n" +
                    "</td>\n" +
                    "<td align=\"center\"  valign=\"top\" class=\"textContent\">\n" +
                    "Negative\n" +
                    "</td>\n" +
                    "<td align=\"center\"  valign=\"top\" class=\"textContent\">\n" +
                    "Unaddressed\n" +
                    "</td>\n" +
                    "<td align=\"center\"  valign=\"top\" class=\"textContent\">\n" +
                    "Bills\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr style=\"background: #ea6314;color: white; font-size:12px\">\n" +
                    "<td align=\"center\"  valign=\"top\" class=\"textContent\">\n" +
                    "Daily <small>( MTD )</small>\n" +
                    "</td>\n" +
                    "<td align=\"center\" valign=\"top\" class=\"textContent\">\n" +
                    "Daily <small>( MTD )</small>\n" +
                    "</td>\n" +
                    "<td align=\"center\"  valign=\"top\" class=\"textContent\">\n" +
                    "Daily <small>( MTD )</small>\n" +
                    "</td>\n" +
                    "<td align=\"center\"  valign=\"top\" class=\"textContent\">\n" +
                    "Daily <small>( MTD )</small>\n" +
                    "</td>\n" +
                    "</tr>\n" + table +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" class=\"flexibleContainer\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\" width=\"500\" class=\"flexibleContainerCell\">\n" +
                    "<table class=\"flexibleContainerCellDivider\" border=\"0\" cellpadding=\"30\" cellspacing=\"0\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" valign=\"top\" style=\"padding-top:0px;padding-bottom:0px;\">\n" +
                    "<table border=\"0\" cellpadding=\"30\" cellspacing=\"0\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td valign=\"top\">\n" +
                    "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:13px;color:#828282;text-align:center;line-height:120%;\">\n" +
                    "<div>Copyright &#169; 2017 <a href=\"http://theuniquemedia.in/\" target=\"_blank\" style=\"text-decoration:none;color:#828282;\">All&nbsp;rights&nbsp;reserved.</div>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</center>\n" +
                    "</body>\n" +
                    "</html>";

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
