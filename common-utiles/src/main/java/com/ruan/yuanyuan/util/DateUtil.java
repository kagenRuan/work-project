package com.ruan.yuanyuan.util;

import org.springframework.util.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
/**
 * User: ruanyuanyuan
 * Date: 2019-09-20
 * Time: 16:00
 * version:1.0
 * Description: 周报时间工具类
 */
public class DateUtil {

    public static  final String YYYY_MM_DD="yyyy/MM/dd";

    /**
     * 推算汇报日期
     * @param start 周报开始时间
     * @param end   周报结束时间
     * @param report 汇报时间
     * @return
     */
    public static LocalDate getDate(LocalDate start,LocalDate end,int report){
        while (start.isBefore(end) || start.compareTo(end) == 0){
           int week = start.getDayOfWeek().getValue();
            if(week == report){
                return start;
            }
            start = start.plusDays(1);
        }
        return null;
    }

    /**
     * 获取本周第一天
     */
    public static LocalDate getCurrentDate(){
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek();
        LocalDate current = now.with(fieldISO, 1);
        return current;
    }

    /**
     * 时间转String
     * @param date 时间
     * @param prent 格式
     * @return String
     */
    public static String getLocalDateStr(LocalDate date,String prent){
        return date.format(DateTimeFormatter.ofPattern(prent));
    }

    /**
     * @param date  Date时间转换为LocalDate
     */
    public static LocalDate getLocalDate(Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    /**
     * 时间转换LocalDate
     * @param date 时间
     * @param prent 格式
     * @return
     */
    public static LocalDate strToLocalDate(String date,String prent){
        if(StringUtils.isEmpty(prent)){
          return LocalDate.parse(date);
        }
        return LocalDate.parse(date,DateTimeFormatter.ofPattern(prent));
    }

    /**
     * 根据时间加减天数
     * @param date 时间
     * @param num  天数
     */
    public static String getPlusDays(LocalDate date,int num){
        LocalDate _date = date.plusDays(num);
        return getLocalDateStr(_date, DateUtil.YYYY_MM_DD);
    }

   /**
     * 获取星期中的指定周几
     * @param date 时间
     * @param dayOfWeek 周几枚举
     * @return
     */
    public static LocalDate getDayWithNum(LocalDate date, DayOfWeek dayOfWeek){
       return date.with(ChronoField.DAY_OF_WEEK, dayOfWeek.getValue());
    }

    /**
     * select subdate(curdate(),date_format(curdate(),'%w')-1)//获取当前日期在本周的周一 
     * select subdate(curdate(),date_format(curdate(),'%w')-2)//获取当前日期在本周的周二 
     * select subdate(curdate(),date_format(curdate(),'%w')-7)//获取当前日期在本周的周日 
     * %W 星期名字(Sunday……Saturday)    
     * %w 一个星期中的天数(0=Sunday   ……6=Saturday）
     * 
     * 获取星期中的指定周几
     * @param date 时间
     * @param weekName 周几英文名称
     * @return
     */
    public static LocalDate getDayWithNum(LocalDate date,String weekName){
        return date.with(ChronoField.DAY_OF_WEEK,DayOfWeek.valueOf(weekName).getValue());
    }
}
