package com.barbeque.util;

/**
 * Created by System-2 on 1/23/2017.
 */

import com.barbeque.config.ConfigProperties;
import com.barbeque.dao.FeedbackDAO;
import com.barbeque.dao.outlet.OutletDAO;
import com.barbeque.dto.request.UpdateSettingsDTO;
import com.barbeque.dto.request.FeedbackRequestDTO;
import com.barbeque.dto.request.SmsSettingDTO;
import com.barbeque.exceptions.FeedbackNotFoundException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SendSms {

    private String route = "4";

    //Prepare Url
    private URLConnection myURLConnection = null;
    private URL myURL = null;
    private BufferedReader reader = null;

    //Send SMS API
    private String mainUrl = "https://control.msg91.com/api/sendhttp.php?";

    public Boolean sendThresholdSms(int id, String msg, SmsSettingDTO smsSettingDTO) throws FeedbackNotFoundException {
        Boolean isProcessed = Boolean.FALSE;

        String authkey = smsSettingDTO.getApi();
        String campaign = smsSettingDTO.getCampaign();
        String senderId = smsSettingDTO.getSenderId();
        String countryCode = smsSettingDTO.getCountryCode();

        String url = UrlFormatter.shortenUrl(ConfigProperties.url + "/" + id + "?SC=1") ;
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        FeedbackRequestDTO feedback = feedbackDAO.getfeedbackById(id);

        UpdateSettingsDTO dto = OutletDAO.getSetting(feedback.getOutletId());
        //Your message to send, Add URL encoding here.

        String message = msg.replace("%cn%", feedback.getCustomerName());
        message = message.replace("%mn%", dto.getMgrName());
        message = message.replace("%ce%", feedback.getEmail());
        message = message.replace("%cm%", feedback.getMobileNo());
        message = message.replace("%tn%", feedback.getTableNo());
        message = message.replace("%url%", url);
        String date = format(DateUtil.getTimeStampFromString(feedback.getFeedbackDate()), "dd-MMM-yyyy HH:mm:ss");
        message = message.replace("%fd%", date);

        //encoding message
        String encoded_message = URLEncoder.encode(message);

        //Prepare parameter string
        StringBuilder sbPostData = new StringBuilder(mainUrl);

        sbPostData.append("authkey=" + authkey);
        sbPostData.append("&mobiles=" + dto.getMgrMobile());
        sbPostData.append("&message=" + encoded_message);
        sbPostData.append("&route=" + route);
        sbPostData.append("&sender=" + senderId);
        sbPostData.append("&campaign=" + campaign);
        sbPostData.append("&country=" + countryCode);
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
                isProcessed = Boolean.TRUE;

            //finally close connection
            reader.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return isProcessed;
    }

    public String format(Timestamp value, String format) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format, Locale.ENGLISH);
        return dateFormatter.format(value.getTime());
    }

    public Boolean sendReferSms(int id, String positiveSmsTemplate, SmsSettingDTO smsSettingDTO) throws FeedbackNotFoundException {
        Boolean isProcessed = Boolean.FALSE;

        String authkey = smsSettingDTO.getApi();
        String campaign = smsSettingDTO.getCampaign();
        String senderId = smsSettingDTO.getSenderId();
        String countryCode = smsSettingDTO.getCountryCode();

        FeedbackDAO feedbackDAO = new FeedbackDAO();
        FeedbackRequestDTO feedback = feedbackDAO.getfeedbackById(id);
        UpdateSettingsDTO dto = OutletDAO.getSetting(feedback.getOutletId());

        String url = UrlFormatter.shortenUrl(ConfigProperties.referurl + "&mobileno=" + feedback.getMobileNo() + "&name=" + feedback.getCustomerName()+ "&programmeId=" + dto.getProgramId()) ;

        //Your message to send, Add URL encoding here.

        String message = positiveSmsTemplate.replace("%cn%", feedback.getCustomerName());
        message = message.replace("%url%", url);


        //encoding message
        String encoded_message = URLEncoder.encode(message);

        //Prepare parameter string
        StringBuilder sbPostData = new StringBuilder(mainUrl);

        sbPostData.append("authkey=" + authkey);
        sbPostData.append("&mobiles=" + feedback.getMobileNo());
        sbPostData.append("&message=" + encoded_message);
        sbPostData.append("&route=" + route);
        sbPostData.append("&sender=" + senderId);
        sbPostData.append("&campaign=" + campaign);
        sbPostData.append("&country=" + countryCode);
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
                isProcessed = Boolean.TRUE;

            //finally close connection
            reader.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return isProcessed;
    }
}