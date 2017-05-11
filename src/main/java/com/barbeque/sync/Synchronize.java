package com.barbeque.sync;

import com.barbeque.request.report.BillData;
import com.barbeque.request.report.ReportData;
import com.barbeque.util.DateUtil;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.xml.ws.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by System-2 on 1/7/2017.
 */
public class Synchronize {
    public static Data callURL(String myURL) throws JSONException {
        Data data = new Data();
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        List<Cluster> clusters = new ArrayList<Cluster>();
        List<Group> groups = new ArrayList<Group>();
        List<Outlet> outlets = new ArrayList<Outlet>();
        List<Regions> regions = new ArrayList<Regions>();
        List<Company> companies = new ArrayList<Company>();
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:" + myURL, e);
        }

        if (sb != null) {
            JSONObject jsonObj = new JSONObject(sb.toString());
            JSONArray clusterJSONList = jsonObj.getJSONArray("cluster");

            for (int i = 0; i < clusterJSONList.length(); i++) {
                JSONObject object = clusterJSONList.getJSONObject(i);
                Cluster newObject = new Cluster();
                newObject.setRegionId(object.getInt("region_id"));
                newObject.setId(object.getInt("id"));
                newObject.setShortDesc(object.getString("clusters_desc"));
                newObject.setDesc(object.getString("cluster_desc"));
                clusters.add(newObject);
            }

            JSONArray groupsJSONList = jsonObj.getJSONArray("groups");

            for (int i = 0; i < groupsJSONList.length(); i++) {
                JSONObject object = groupsJSONList.getJSONObject(i);
                Group newObject = new Group();
                newObject.setId(object.getInt("id"));
                newObject.setShortDesc(object.getString("groups_desc"));
                newObject.setDesc(object.getString("group_desc"));
                groups.add(newObject);
            }

            JSONArray outletJSONList = jsonObj.getJSONArray("outlet");

            for (int i = 0; i < outletJSONList.length(); i++) {
                JSONObject object = outletJSONList.getJSONObject(i);
                Outlet newObject = new Outlet();
                newObject.setId(object.getInt("id"));
                newObject.setShortDesc(object.getString("short_desc"));
                newObject.setOutletDesc(object.getString("outlet_desc"));
                newObject.setGroupId(object.getInt("group_id"));
                newObject.setCompanyId(object.getInt("company_id"));
                newObject.setClusterId(object.getInt("cluster_id"));
                newObject.setRegionId(object.getInt("region_id"));
                newObject.setPosStoreId(object.getString("pos_store_id"));
                outlets.add(newObject);
            }

            JSONArray regionJSONList = jsonObj.getJSONArray("region");

            for (int i = 0; i < regionJSONList.length(); i++) {
                JSONObject object = regionJSONList.getJSONObject(i);
                Regions newObject = new Regions();
                newObject.setId(object.getInt("id"));
                newObject.setCompanyId(object.getInt("company_id"));
                newObject.setShortDesc(object.getString("regions_desc"));
                newObject.setDesc(object.getString("region_desc"));
                regions.add(newObject);
            }

            JSONArray companyJSONList = jsonObj.getJSONArray("company");

            for (int i = 0; i < companyJSONList.length(); i++) {
                JSONObject object = companyJSONList.getJSONObject(i);
                Company newObject = new Company();
                newObject.setId(object.getInt("id"));
                newObject.setGroupId(object.getInt("group_id"));
                newObject.setShortDesc(object.getString("company_short_desc"));
                newObject.setDesc(object.getString("company_desc"));
                companies.add(newObject);
            }
        }

        data.setClusters(clusters);
        data.setGroups(groups);
        data.setCompanies(companies);
        data.setRegions(regions);
        data.setOutlets(outlets);
        return data;
    }

    public static BillData callData(String myURL) throws JSONException {
        BillData data = new BillData();
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        List<ReportData> reportDatas = new ArrayList<ReportData>();


        Date date1 = new Date();

        final Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.DATE, -1);
        Date date2 = cal.getTime();
        Timestamp t2 = new Timestamp(date2.getTime());
        String currentDate = DateUtil.format(t2,"yyyy.MM.dd");

        try {
            URL url = new URL(myURL + currentDate);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:" + myURL, e);
        }

        if (sb != null) {
            JSONObject jsonObj = new JSONObject(sb.toString());
            JSONArray clusterJSONList = jsonObj.getJSONArray("storebill");

            for (int i = 0; i < clusterJSONList.length(); i++) {
                JSONObject object = clusterJSONList.getJSONObject(i);
                ReportData newObject = new ReportData();
                newObject.setStoreId(object.getString("StoreId"));
                newObject.setMonthlyBillCount(object.getInt("Monthly"));
                newObject.setDailyBillCount(object.getInt("Daily"));
                reportDatas.add(newObject);
            }


            data.setOutlets(reportDatas);
        }
        return data;
    }

    public static JSONObject callCustomer(String myURL) throws JSONException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader in = null;
        JSONObject jsonObj = null;

        try {
            URL url = new URL(myURL );
            URLConnection urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:" + myURL, e);
        }

        if (sb != null) {
             jsonObj = new JSONObject(sb.toString());
        }
        return jsonObj;
    }
}

