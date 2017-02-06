package com.barbeque.util;

/**
 * Created by System-2 on 1/23/2017.
 */

import com.barbeque.config.ConfigProperties;
import com.barbeque.dao.FeedbackDAO;
import com.barbeque.dao.outlet.OutletDAO;
import com.barbeque.dto.UpdateSettingsDTO;
import com.barbeque.dto.request.FeedbackRequestDTO;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SendSms {
    //Your authentication key
    private static final String authkey = ConfigProperties.authkey;
    private static final String campaign = ConfigProperties.campaign;
    private static final String senderId = ConfigProperties.senderId;
    //Sender ID,While using route4 sender id should be 6 characters long.
    //define route
    private static final String route = "4";

    //Prepare Url
    private static URLConnection myURLConnection = null;
    private static URL myURL = null;
    private static BufferedReader reader = null;

    //Send SMS API
    private static String mainUrl = "https://control.msg91.com/api/sendhttp.php?";

    //Prepare parameter string
    private static final StringBuilder sbPostData = new StringBuilder(mainUrl);


    public static Boolean sendThresholdSms(int id, String msg) {
        Boolean isProcessed = Boolean.FALSE;
        try {
            FeedbackRequestDTO feedback = FeedbackDAO.getfeedbackById(id);

            UpdateSettingsDTO dto = OutletDAO.getSetting(feedback.getOutletId());
            //Your message to send, Add URL encoding here.

            String message = msg.replace("%cn%",feedback.getCustomerName());
            message=message.replace("%mn%",dto.getPocName());
            message=message.replace("%ce%",feedback.getEmail());
            message=message.replace("%cm%",feedback.getMobileNo());
            message=message.replace("%tn%",feedback.getTableNo());
            String date = format(feedback.getFeedbackDate(),"dd-MMM-yyyy HH:mm:ss");
            message=message.replace("%fd%",date);

            //encoding message
            String encoded_message = URLEncoder.encode(message);

            sbPostData.append("authkey=" + authkey);
            sbPostData.append("&mobiles=" + dto.getPocMobile());
            sbPostData.append("&message=" + encoded_message);
            sbPostData.append("&route=" + route);
            sbPostData.append("&sender=" + senderId);
            sbPostData.append("&campaign=" + campaign);
            try {
                //final string
                mainUrl = sbPostData.toString();
                //prepare connection
                myURL = new URL(mainUrl);
                myURLConnection = myURL.openConnection();

                myURLConnection.connect();

                reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

                //reading response
                String response;
                while ((response = reader.readLine()) != null)
                    //print response
                    System.out.println(response);

                //finally close connection
                reader.close();

                isProcessed = Boolean.TRUE;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isProcessed;
    }

    public static String format(Timestamp value, String format){
            SimpleDateFormat dateFormatter = new SimpleDateFormat(format, Locale.ENGLISH);
            return dateFormatter.format(value.getTime());
    }
}