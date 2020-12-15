package com.web.account_book.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
    static public String this_month;
    static public String last_month;
    public DateUtil(){
        Calendar cal = Calendar.getInstance();
        String format = "yyyy-MM";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        this_month = sdf.format(cal.getTime())+"%";

        cal.add(cal.MONTH, -1);
        last_month = sdf.format(cal.getTime())+"%";
    }
}
