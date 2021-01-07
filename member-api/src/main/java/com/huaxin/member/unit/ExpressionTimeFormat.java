package com.huaxin.member.unit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ljj
 * Date: 14-11-28
 * Time: 上午10:25
 * To change this template use File | Settings | File Templates.
 */
public class ExpressionTimeFormat {
    private static final Log log = LogFactory.getLog(ExpressionTimeFormat.class);

    //获取当前时间
    public String getTime(String actionTime, String dataQueryTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        String[] dataTime = actionTime.split(" ");
        String[] date = null;
        String[] time = null;
        int year = 0, month = 0, day = 0, hour = 0, minute = 0, second = 0;
        String mm = null, dd = null, hh = null, mi = null, ss = null;
        if (dataTime.length > 1) {
            date = dataTime[0].split("-");
            time = dataTime[1].split(":");
            if (date.length == 3) {
                year = Integer.parseInt(date[0]);
                month = Integer.parseInt(date[1]);
                day = Integer.parseInt(date[2]);
                hour = Integer.parseInt(time[0]);
                minute = Integer.parseInt(time[1]);
                second = Integer.parseInt(time[2]);
            } else if (date.length == 2) {
                year = calendar.get(Calendar.YEAR);
                month = Integer.parseInt(date[0]);
                day = Integer.parseInt(date[1]);
                hour = Integer.parseInt(time[0]);
                minute = Integer.parseInt(time[1]);
                second = Integer.parseInt(time[2]);
            } else {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH) + 1;
                day = Integer.parseInt(date[0]);
                hour = Integer.parseInt(time[0]);
                minute = Integer.parseInt(time[1]);
                second = Integer.parseInt(time[2]);
            }
        } else {
            time = dataTime[0].split(":");
            if (time.length == 3) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH) + 1;
                day = calendar.get(Calendar.DAY_OF_MONTH);
                hour = Integer.parseInt(time[0]);
                minute = Integer.parseInt(time[1]);
                second = Integer.parseInt(time[2]);
            } else if (time.length == 2) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH) + 1;
                day = calendar.get(Calendar.DAY_OF_MONTH);
                hour = Integer.parseInt(time[0]);
                minute = Integer.parseInt(time[1]);
                second = 0;
            } else {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH) + 1;
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
                second = Integer.parseInt(time[0]);
            }
        }
//        String[] express = dataQueryTime.split("\\|");
//        //秒
//        if(express[5].indexOf("$") == -1){
//            second = second+Integer.parseInt(express[5].split(":")[1]);
//            if(second >= 60){
//                second = second - 60;
//                minute = minute+1;
//            }
//            if(second < 0){
//                second = second + 60;
//                minute = minute -1;
//            }
//        }else{
//            second = Integer.parseInt(express[5].split(":")[1].replace("$","0"));
//        }
//        //分
//        if(express[4].indexOf("$") == -1){
//            minute = minute+Integer.parseInt(express[4].split(":")[1]);
//            if(minute >= 60){
//                minute = minute - 60;
//                hour = hour + 1;
//            }
//            if(minute < 0){
//                minute = minute + 60;
//                hour = hour - 1;
//            }
//        }else{
//            minute = Integer.parseInt(express[4].split(":")[1].replace("$","0"));
//        }
//        //小时
//        if(express[3].indexOf("$") == -1){
//            hour = hour+Integer.parseInt(express[3].split(":")[1]);
//            if(hour >= 24){
//                hour = hour - 24;
//                day = day + 1;
//            }if(hour < 0){
//                hour = hour + 24;
//                day = day - 1;
//            }
//        }else{
//            hour = Integer.parseInt(express[3].split(":")[1].replace("$","0"));
//        }
//        //日
//        if(express[2].indexOf("$") == -1){
//            day = day+Integer.parseInt(express[2].split(":")[1]);
//            int MM = 0,yy=0;
//            if(express[1].indexOf("$") == -1){
//                MM = month+Integer.parseInt(express[1].split(":")[1]);
//            }else {
//                MM = Integer.parseInt(express[1].split(":")[1].replace("$","0"));
//            }
//            if(express[0].indexOf("$") == -1){
//                yy = year+Integer.parseInt(express[0].split(":")[1]);
//            } else{
//                yy = Integer.parseInt(express[0].split(":")[1].replace("$","0"));
//            }
//
//            if(MM == 1 || MM == 3 || MM == 5 || MM ==7 || MM==8 ||MM==10 || MM==12){
//                if(day > 31){
//                    day = day - 31;
//                    month = month + 1;
//                }if(day <= 0){
//                    day = day + 31;
//                    month = month - 1;
//                }
//            }
//            else if(MM==4 || MM==6 || MM==9 || MM==11){
//                if(day > 30){
//                    day = day - 30;
//                    month = month + 1;
//                }if(day <= 0){
//                    day = day + 30;
//                    month = month - 1;
//                }
//            }else{
//                if(yy%4==0&&yy%100!=0||yy%400==0){
//                    if(day > 29){
//                        day = day - 29;
//                        month = month + 1;
//                    }
//                    if(day <= 0){
//                        day = day + 29;
//                        month = month - 1;
//                    }
//                }else{
//                    if(day > 28){
//                        day = day - 28;
//                        month = month + 1;
//                    }
//                    if(day <= 0){
//                        day = day + 28;
//                        month = month - 1;
//                    }
//                }
//            }
//        }else{
//            day = Integer.parseInt(express[2].split(":")[1].replace("$","0"));
//        }
//        //月
//        if(express[1].indexOf("$") == -1){
//            month = month+Integer.parseInt(express[1].split(":")[1]);
//            if(month > 12){
//                month = month - 12;
//                year = year + 1;
//            }if(month <= 0){
//                month = month + 12;
//                year = year - 1;
//            }
//        }else{
//            month = Integer.parseInt(express[1].split(":")[1].replace("$","0"));
//        }
//        //年
//        if(express[0].indexOf("$") == -1){
//            year = year+Integer.parseInt(express[0].split(":")[1]);
//        }else{
//            year = Integer.parseInt(express[0].split(":")[1].replace("$","0"));
//        }
//
//
//        if(month<10){
//            mm = "0"+month;
//        }else{
//            mm = String.valueOf(month);
//        }
//        if(day < 10){
//            dd = "0"+day;
//        }else{
//            dd = String.valueOf(day);
//        }
//        if(hour < 10){
//            hh = "0"+hour;
//        }else{
//            hh = String.valueOf(hour);
//        }
//        if(minute < 10){
//            mi = "0"+minute;
//        }else{
//            mi = String.valueOf(minute);
//        }
//        if(second < 10){
//            ss = "0"+second;
//        }else{
//            ss = String.valueOf(second);
//        }
//
//        return year+"-"+mm+"-"+dd+" "+hh+":"+mi+":"+ss;


        String sadd = "";
        String madd = "";
        String hadd = "";
        String dadd = "";
        String monadd = "";
        String yadd = "";

        String[] express = dataQueryTime.split("\\|");
        //秒
        if (!express[5].contains("$")) {
            sadd = express[5].split(":")[1];
        } else {

            second = Integer.parseInt(express[5].split(":")[1].replace("$", "0"));
        }

        //分
        if (!express[4].contains("$")) {
            madd = express[4].split(":")[1];
        } else {

            minute = Integer.parseInt(express[4].split(":")[1].replace("$", "0"));
        }
        //小时
        if (!express[3].contains("$")) {
            hadd = express[3].split(":")[1];
        } else {

            hour = Integer.parseInt(express[3].split(":")[1].replace("$", "0"));
        }
        //日
        if (!express[2].contains("$")) {
            dadd = express[2].split(":")[1];
        } else {

            day = Integer.parseInt(express[2].split(":")[1].replace("$", "0"));
        }
        //月
        if (!express[1].contains("$")) {
            monadd = express[1].split(":")[1];
        } else {
            month = Integer.parseInt(express[1].split(":")[1].replace("$", "0"));
        }
        //年
        if (!express[0].contains("$")) {
            yadd = express[0].split(":")[1];
        } else {
            year = Integer.parseInt(express[0].split(":")[1].replace("$", "0"));
        }
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sim.parse(nowTime));
        } catch (Exception e) {
            log.error(e);
        }
        if (StringUtils.isNotEmpty(yadd)) {
            c.add(Calendar.YEAR, Integer.valueOf(yadd));
        }
        if (StringUtils.isNotEmpty(monadd)) {
            c.add(Calendar.MONTH, Integer.valueOf(monadd));
        }
        if (StringUtils.isNotEmpty(dadd)) {
            c.add(Calendar.DATE, Integer.valueOf(dadd));
        }
        if (StringUtils.isNotEmpty(hadd)) {
            c.add(Calendar.HOUR_OF_DAY, Integer.valueOf(hadd));
        }
        if (StringUtils.isNotEmpty(madd)) {
            c.add(Calendar.MINUTE, Integer.valueOf(madd));
        }
        if (StringUtils.isNotEmpty(sadd)) {
            c.add(Calendar.SECOND, Integer.valueOf(sadd));
        }
        return sim.format(c.getTime());

    }

    //时间格式化
    public String format(String dateTime) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateTime = dateTime.substring(0, 19);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sf.parse(dateTime));
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DATE);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            if (minute % 5 != 0) {
                minute = minute - (minute % 5);
            }

            return year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day) + " " + (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + ":" + "00";
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }


    public void main(String[] args) throws Exception {


//         String de="Y:0|M:0|D:0|HH:0|MM:-5|SS:0";
//
//         System.out.println(getTime("2019-01-01 00:01:59",de));


        String d1 = "Y:0|M:0|D:0|HH:0|MM:-5|SS:$00";
        String d2 = "Y:0|M:0|D:0|HH:0|MM:-1|SS:$59";
        String d3 = "Y:0|M:0|D:0|HH:1|MM:0|SS:0";
        String d4 = "Y:0|M:0|D:0|HH:0|MM:12|SS:0";
        String d5 = "Y:0|M:0|D:0|HH:-1|MM:-15|SS:$00";
        String d6 = "Y:0|M:0|D:0|HH:0|MM:-5|SS:$00";
        String d7 = "Y:0|M:0|D:0|HH:0|MM:-10|SS:0";


        System.out.println(getTime("2017-09-16 15:30:00", d1));
        System.out.println(getTime("2017-09-15 15:45:00", d2));
        System.out.println(getTime("2017-09-02 13:20:00", d3));
        System.out.println(getTime("2017-09-05 07:05:00", d4));
        System.out.println(getTime("2017-09-04 06:05:00", d5));
        System.out.println(getTime("2017-09-22 03:20:00", d6));
        System.out.println(getTime("2017-09-25 04:05:00", d7));


//          String actionTime="2017-09-16 15:30:00";
//        String[] dataTime = actionTime.split(" ");
//        System.out.println(dataTime.length);
//
//        System.out.println(getTime("2019-01-01 00:00:00",d1));
//        System.out.println(getTime("2019-01-01 00:00:00",d2));
//        System.out.println(getTime("2019-01-01 00:00:00",d3));
//        System.out.println(getTime("2019-01-01 00:00:00",d4));
//        System.out.println(getTime("2019-01-01 00:00:00",d5));
//        System.out.println(getTime("2019-01-01 00:00:00",d6));
//        System.out.println(getTime("2019-01-01 00:00:00",d7));


//        System.out.println(getTime("2019-03-01 00:00:00",d1));
//        System.out.println(getTime("2019-03-01 00:00:00",d2));
//        System.out.println(getTime("2019-03-01 00:00:00",d3));
//        System.out.println(getTime("2019-03-01 00:00:00",d4));
//        System.out.println(getTime("2019-03-01 00:00:00",d5));
//        System.out.println(getTime("2019-03-01 00:00:00",d6));
//        System.out.println(getTime("2019-03-01 00:00:00",d7));
//


//
//        System.out.println(getTime("2019-02-01 00:00:00",dataQuerySTime));
//
//        System.out.println(getTime("2019-03-01 00:00:00",dataQuerySTime));
//
//        System.out.println(getTime("2019-04-01 00:00:00",dataQuerySTime));
//
//        System.out.println(getTime("2019-05-01 00:00:00",dataQuerySTime));
//
//        System.out.println(getTime("2019-06-01 00:00:00",dataQuerySTime));
//
//        System.out.println(getTime("2019-07-01 00:00:00",dataQuerySTime));
//
//        System.out.println(getTime("2019-08-01 00:00:00",dataQuerySTime));
//
//        System.out.println(getTime("2019-09-01 00:00:00",dataQuerySTime));
//
//        System.out.println(getTime("2019-10-01 00:00:00",dataQuerySTime));
//
//        System.out.println(getTime("2019-11-01 00:00:00",dataQuerySTime));
//
//        System.out.println(getTime("2019-12-01 00:00:00",dataQuerySTime));


    }


}
