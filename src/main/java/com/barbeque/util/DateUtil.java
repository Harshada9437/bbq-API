package com.barbeque.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.DateTimeZone;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static Timestamp getTimeStampFromString(String dateStr) {
        return Timestamp.valueOf(dateStr);
    }

    public static String getDateStringFromTimeStamp(Timestamp timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    }

    public static String getCurrentServerTime() {
        SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfAmerica.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return sdfAmerica.format(new Date());
    }

    public static String getCurrentServerTimeByRemoteTimestamp(Timestamp timestamp) {
        SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfAmerica.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return sdfAmerica.format(timestamp);
    }

    public static String getCurrentServerDate() {
        SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd");
        sdfAmerica.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return sdfAmerica.format(new Date());
    }

    public static String getTimestampForReport(Timestamp timestamp){
        return new SimpleDateFormat("HH:mm:ss").format(timestamp);
    }

    public static int compareTimestampWithCurrentDate(Timestamp timestamp){
        DateTime dt = new DateTime(timestamp,
                DateTimeZone.forID("Asia/Kolkata"));
        DateTime currentDate = new DateTime(new Date(),
                DateTimeZone.forID("Asia/Kolkata"));
        DateTimeComparator comparator = DateTimeComparator.getInstance();
        return comparator.compare(dt,currentDate);
    }
}
