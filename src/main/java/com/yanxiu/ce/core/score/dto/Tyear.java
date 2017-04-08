package com.yanxiu.ce.core.score.dto;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 教育周期
 * 十三五：2016-2020
 * 十四五：2021-2025
 * Created by tangmin on 2016.09.20.
 */
public class Tyear {
    private String startYear;
    private String endYear;

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public static Tyear getTyearObj(Date date) throws ParseException {
        Tyear tyear = new Tyear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int theYear = calendar.get(Calendar.YEAR);

        if (theYear >= 2001 && theYear <= 2005) {
//            tyear.setStartYear("2001-01-01 00:00:00");
//            tyear.setEndYear("2005-12-31 23:59:59");
            tyear.setStartYear("2001");
            tyear.setEndYear("2005");

        } else if (theYear >= 2006 && theYear <= 2010) {
//            tyear.setStartYear("2006-01-01 00:00:00");
//            tyear.setEndYear("2010-12-31 23:59:59");
            tyear.setStartYear("2006");
            tyear.setEndYear("2010");

        } else if (theYear >= 2011 && theYear <= 2015) {
//            tyear.setStartYear("2011-01-01 00:00:00");
//            tyear.setEndYear("2015-12-31 23:59:59");
            tyear.setStartYear("2011");
            tyear.setEndYear("2015");

        } else if (theYear >= 2016 && theYear <= 2020) {
//            tyear.setStartYear("2016-01-01 00:00:00");
//            tyear.setEndYear("2020-12-31 23:59:59");
            tyear.setStartYear("2016");
            tyear.setEndYear("2020");

        } else if (theYear >= 2021 && theYear <= 2025) {
//            tyear.setStartYear("2021-01-01 00:00:00");
//            tyear.setEndYear("2025-12-31 23:59:59");
            tyear.setStartYear("2021");
            tyear.setEndYear("2025");
        } else if (theYear >= 2026 && theYear <= 2030) {
//            tyear.setStartYear("2026-01-01 00:00:00");
//            tyear.setEndYear("2030-12-31 23:59:59");
            tyear.setStartYear("2026");
            tyear.setEndYear("2030");
        } else if (theYear >= 2031 && theYear <= 2035) {
//            tyear.setStartYear("2031-01-01 00:00:00");
//            tyear.setEndYear("2035-12-31 23:59:59");
            tyear.setStartYear("2031");
            tyear.setEndYear("2035");
        } else if (theYear >= 2036 && theYear <= 2040) {
//            tyear.setStartYear("2036-01-01 00:00:00");
//            tyear.setEndYear("2040-12-31 23:59:59");
            tyear.setStartYear("2036");
            tyear.setEndYear("2040");
        } else if (theYear >= 2041 && theYear <= 2045) {
//            tyear.setStartYear("2041-01-01 00:00:00");
//            tyear.setEndYear("2045-12-31 23:59:59");
            tyear.setStartYear("2041");
            tyear.setEndYear("2045");
        } else if (theYear >= 2046 && theYear <= 2050) {
//            tyear.setStartYear("2046-01-01 00:00:00");
//            tyear.setEndYear("2050-12-31 23:59:59");
            tyear.setStartYear("2046");
            tyear.setEndYear("2050");

        } else {
            tyear.setStartYear("2051");
            tyear.setEndYear("2055");
//            tyear.setStartYear("2051-01-01 00:00:00");
//            tyear.setEndYear("2055-12-31 23:59:59");
        }
        return tyear;
    }
}
