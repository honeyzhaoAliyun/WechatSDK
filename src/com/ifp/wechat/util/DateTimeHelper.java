package com.ifp.wechat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 
 * Title: klcar Home
 * 
 * Author: zhaoguoqing
 * 
 * Date: 2014-6-22
 * 
 * Description: 对日期和时间进行格式转换帮助类
 * 
 */
public class DateTimeHelper {
	public static final String DEFAULT_DATE_TIME_FORMATE = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATE_FORMATE = "yyyy-MM-dd";
	public static final String DEFAULT_CN_DATE_FORMATE = "yyyy年MM月dd日";
	public static final String DEFAULT_DATE_YM_FORMATE = "yyyy-MM";
	public static final String DEFAULT_CN_DATE_YM_FORMATE = "yyyy年MM月";
	public static final String DEFAULT_DATE_YW_FORMATE = "yyyy-ww";
	public static final String DEFAULT_CN_DATE_YW_FORMATE = "yyyy年第ww周";
	public static final String DEFAULT_DATE_Y_FORMATE = "yyyy";
	public static final String DEFAULT_CN_DATE_Y_FORMATE = "yyyy年";
	public static final String DEFAULT_DATE_Y0101_FORMATE = "yyyy-01-01";
	public static final String DEFAULT_DATE_STAR_FORMATE = "yyyy-MM-dd 00:00:00";
	public static final String DEFAULT_DATE_END_FORMATE = "yyyy-MM-dd 23:59:59";
	public static final String DEFAULT_DATE_HM_FORMATE = "yyyy-MM-dd HH:mm";
	public static final String DEFAULT_DATE_MD_FORMATE = "yyyy-MM-DD";

	public static String dateTimeToStr(Date source, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(source);
	}

	public static Date strToDatetime(String source, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(source);
		} catch (ParseException e) {
		}
		return null;
	}

	public static String dateTimeFormatReplace(String source, String format) {
		StringBuffer sb = new StringBuffer();

		if ("yyyy年MM月dd日".equals(format))
			sb.append(source.substring(0, 4)).append("年").append(
					source.substring(5, 7)).append("月").append(
					source.subSequence(8, 10)).append("日");
		else if ("yyyy年第ww周".equals(format))
			sb.append(source.substring(0, 4)).append("年第").append(
					source.substring(5, 7)).append("周");
		else if ("yyyy年MM月".equals(format))
			sb.append(source.substring(0, 4)).append("年").append(
					source.substring(5, 7)).append("月");
		else if ("yyyy年".equals(format)) {
			sb.append(source.substring(0, 4)).append("年");
		}

		return sb.toString();
	}

	public static int getMonthDiff(Date beginTime, Date endTime) {
		Calendar beginCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginTime);
		endCalendar.setTime(endTime);

		int years = endCalendar.get(1) - beginCalendar.get(1);
		int months = endCalendar.get(2) - beginCalendar.get(2);

		return (12 * years + months);
	}

	public static long getDateDiff(Date beginTime, Date endTime) {
		return ((beginTime.getTime() - endTime.getTime()) / 1000L * 60L * 60L * 24L);
	}
	
	
}
