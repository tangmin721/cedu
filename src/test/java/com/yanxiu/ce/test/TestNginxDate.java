package com.yanxiu.ce.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Description:
 * @author: tangmin
 * @date: 2017年02月21日 17:27
 * @version: 1.0
 */
public class TestNginxDate {
    public static void main(String[] args) throws ParseException {
        String timeLocal = "[17/Feb/2017:00:03:13 +0800]";
        SimpleDateFormat formatter = new SimpleDateFormat("[dd/MMM/yyyy:hh:mm:ss Z]", Locale.ENGLISH);
        Date date = formatter.parse(timeLocal);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("转换后的日期格式："+format.format(date));
    }
}
