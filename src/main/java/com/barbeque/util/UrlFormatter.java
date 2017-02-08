package com.barbeque.util;

/**
 * Created by System-2 on 2/7/2017.
 */
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

public  class UrlFormatter {
    private static final String GOOGLE_URL_SHORT_API = "https://www.googleapis.com/urlshortener/v1/url";
    private static final String GOOGLE_API_KEY = "AIzaSyCUBhr1cshE8K92fAD__ATlWFvzb0jumYQ";

    public static String shortenUrl(String longUrl){

        if (longUrl == null) {
            return longUrl;
        }else if(!longUrl.startsWith("http://") && !longUrl.startsWith("https://")){
            longUrl = "http://"+longUrl;
        }
        try {
            String json = "{\"longUrl\": \""+longUrl+"\"}";
            String apiURL = GOOGLE_URL_SHORT_API+"?key="+GOOGLE_API_KEY;

            HttpPost postRequest = new HttpPost(apiURL);
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.setEntity(new StringEntity(json, "UTF-8"));

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(postRequest);
            String responseText = EntityUtils.toString(response.getEntity());

            Gson gson = new Gson();
            @SuppressWarnings("unchecked")
            HashMap<String, String> res = gson.fromJson(responseText, HashMap.class);

            return res.get("id");

        } catch (MalformedURLException e) {
            return "error";
        } catch (IOException e) {
            return "error";
        }
    }
}
