/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  org.cpframework.cp_utils.CP_DateUtilsTest.java 													       
 * 功能: CP_DateUtils日期工具类												   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014年1月6日 下午3:19:15 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2014年1月6日    |    孙成启     |     Created 
 */
package org.pyf.developer.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/** 
 *Description: <CP_DateUtils>. <br>
 *<p>
	<使用说明>
 </p>
 *Makedate:2014年1月6日 下午3:19:15 
 * @author 孙成启  
 * @version V1.0                             
 */
public class CP_DateUtils {

	public final static String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String DEFAULT_DATE_FORMAT = "yyyyMMdd";
	public final static String DEFAULT_DATE_FORMAT_S = "yyyy-MM-dd";

	public static void main(String[] args) {
		
	}

	/**
	 * 
	 * 描述 : <转换称sql.date>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date changeToSQLDate(Date date) {
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	/**
	 * 
	 * 描述 : <将指定字符串格式化并转换称sqldate>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param date
	 *            日期字符串
	 * @param format
	 * @return
	 */
	public static java.sql.Date changeToSQLDate(String date, String format) {
		return new java.sql.Date(parseDate(format, date).getTime());
	}

	/**
	 * 
	 * 描述 : <指定日期转换成sqlTimestamp>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp changeToSQLTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 
	 * 描述 : <指定日期字符串格式化后转换成sqlTimestamp>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Timestamp changeToSQLTimestamp(String date,
			String format) {
		return new Timestamp(parseDate(format, date).getTime());
	}

	/**
	 * 
	 * 描述 : <格式化当前日期>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param fmt
	 * @return
	 */
	public static String formatCurrentDate(String fmt) {
		return formatDate(new Date(), fmt);
	}

	/**
	 * 
	 * 描述 : <用默认的格式格式化指定日期,不带时分秒>. <br>
	 * <p>
	 * <默认的格式化格式:yyyy-MM-dd>
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT_S);
		return sdf.format(date);
	}

	/**
	 * 
	 * 描述 : <格式化指定日期>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param date
	 *            日期
	 * @param fmt
	 *            指定的格式
	 * @return
	 */
	public static String formatDate(Date date, String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(date);
	}

	/**
	 * 
	 * 描述 : <用默认的格式格式化指定日期,带时分秒>. <br>
	 * <p>
	 * <默认的格式化格式:DEFAULT_TIME_FORMAT>
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
		return sdf.format(date);
	}

	/**
	 * 
	 * 描述 : <获得当前月的日期,从1开始>. <br>
	 * <p>
	 * 
	 * @return
	 */
	public static Integer getCurrentDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DATE);
	}

	/**
	 * 
	 * 描述 : <获得当前星期几,从1开始,>. <br>
	 * <p>
	 * 
	 * @return
	 */
	public static Integer getCurrentDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 
	 * 描述 : <获得当前日期在当前月中是第几个星期>. <br>
	 * <p>
	 * 
	 * @return
	 */
	public static Integer getCurrentDayOfWeekInMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}

	/**
	 * 
	 * 描述 : <获得当前小时数(0-11) 12小时制>. <br>
	 * <p>
	 * 
	 * @return
	 */
	public static Integer getCurrentHourOfDay_12() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR);
	}

	/**
	 * 
	 * 描述 : <获得当前小时数(0-23) 24小时制>. <br>
	 * <p>
	 * 
	 * @return
	 */
	public static Integer getCurrentHourOfDay_24() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 
	 * 描述 : <获得当前月份,从0开始>. <br>
	 * <p>
	 * 
	 * @return
	 */
	public static Integer getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH);
	}

	/**
	 * 
	 * 描述 : <获得当前是第几季度>. <br>
	 * <p>
	 * <一共四个季度,从1开始>
	 * </p>
	 * 
	 * @return
	 */
	public static Integer getCurrentSeasonOfYear() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		// int array[][] = {{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		return season;
	}

	/**
	 * 
	 * 描述 : <获得当前月中的星期数>. <br>
	 * <p>
	 * 
	 * @return
	 */
	public static Integer getCurrentWeekOfMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 
	 * 描述 : <获得当前年到现在的星期数>. <br>
	 * <p>
	 * 
	 * @return
	 */
	public static Integer getCurrentWeekOfYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 
	 * 描述 : <获得当前年>. <br>
	 * <p>
	 * 
	 * @return
	 */
	public static Integer getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 
	 * 描述 : <获得上个季度的第一天>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 */
	public static Date getFirstDayOFLastSeason() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		int season = getCurrentSeasonOfYear();
		if (season == 1) {
			year--;
			season = 4;
		} else {
			season--;
		}
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		return getMonthFirstDay(year, array[season - 1][0] - 1);
	}

	/**
	 * 
	 * 描述 : <获得上个季度的最后一天>. <br>
	 * <p>
	 * <时分秒初始化为>
	 * </p>
	 * 
	 * @return
	 */
	public static Date getLastDayOFLastSeason() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int season = getCurrentSeasonOfYear();
		if (season == 1) {
			year--;
			season = 4;
		} else {
			season--;
		}
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		return getMonthLastDay(year, array[season - 1][2] - 1);
	}

	/**
	 * 
	 * 描述 : <获得某年某月的最后一天 的格式化日期表示>. <br>
	 * <p>
	 * <月份从0开始,格式为:yyyy-MM-dd>
	 * </p>
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 年
		cal.set(Calendar.YEAR, year);
		// 日先设置为1 防止天数超过当月最大值而产生bug
		cal.set(Calendar.DATE, 1);
		// 月，因为Calendar里的月是从0开始，所以要-1
		cal.set(Calendar.MONTH, month);
		// 月份加一，得到下个月的一号
		cal.add(Calendar.MONTH, 1);
		// 下一个月减一为本月最后一天
		cal.add(Calendar.DATE, -1);

		return formatDate(cal.getTime(), DEFAULT_DATE_FORMAT_S);
	}

	/**
	 * 
	 * 描述 : <获得某年某月的第一天>. <br>
	 * <p>
	 * <月份从0开始,时分秒初始化为0>
	 * </p>
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getMonthFirstDay(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		// 日，设为一号
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.MONTH, month);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * 
	 * 描述 : <获得某年某月的最后一天,不带时分秒>. <br>
	 * <p>
	 * <月份从0开始>
	 * </p>
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getMonthLastDay(int year, int month) {

		Calendar cal = Calendar.getInstance();
		// 年
		cal.set(Calendar.YEAR, year);
		// 日，设为一号
		cal.set(Calendar.DATE, 1);
		// 月，因为Calendar里的月是从0开始，所以要-1
		cal.set(Calendar.MONTH, month + 1);

		// 下一个月减一为本月最后一天
		cal.add(Calendar.DATE, -1);

		return cal.getTime();
	}

	/**
	 * 
	 * 描述 : <获得某年某月的最后一天,带时分秒>. <br>
	 * <p>
	 * <月份从0开始>
	 * </p>
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getMonthLastFullDay(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 年
		cal.set(Calendar.YEAR, year);
		// 日，设为一号
		cal.set(Calendar.DATE, 1);
		// 月，因为Calendar里的月是从0开始，所以要-1
		cal.set(Calendar.MONTH, month + 1);

		// 下一个月减一为本月最后一天
		cal.add(Calendar.DATE, -1);

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return cal.getTime();
	}

	/**
	 * 
	 * 描述 : <获得昨天日期>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 */
	public static Date getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * 
	 * 描述 : <获得昨天日期的字符串>. <br>
	 * <p>
	 * <默认的格式为:yyyy-MM-dd>
	 * </p>
	 * 
	 * @return
	 */
	public static String getYesterdayStr() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return formatDate(cal.getTime(), DEFAULT_DATE_FORMAT_S);
	}

	/**
	 * 
	 * 描述 : <获得昨天日期的字符串>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param fmt
	 *            指定格式
	 * @return
	 */
	public static String getYesterdayStr(String fmt) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return formatDate(cal.getTime(), fmt);
	}

	/**
	 * 
	 * 描述 : <获得上个月的第一天>. <br>
	 * <p>
	 * <时分秒初始化为0>
	 * </p>
	 * 
	 * @return
	 */
	public static Date lastMonthFirstDay() {
		Calendar cal = Calendar.getInstance();
		// 日，设为一号
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * 
	 * 描述 : <上个月的最后一天>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 */
	public static Date lastMonthLastDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.DATE, -1);

		return cal.getTime();
	}

	/**
	 * 
	 * 描述 : <上个月最后一天，最后一秒>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 */
	public static Timestamp lastMonthLastFullDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.DATE, -1);

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 
	 * 描述 : <指定字符串转换成日期>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param fmt
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String fmt, String dateStr) {
		return parseDate(fmt, dateStr, null);
	}

	/**
	 * 
	 * 描述 : <指定字符串转换成日期>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param fmt
	 * @param dateStr
	 * @param locale
	 * @return
	 */
	public static Date parseDate(String fmt, String dateStr, Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(fmt, locale);
			return sdf.parse(dateStr);
		} catch (Exception e) {
			throw new RuntimeException(String.format(
					"格式化日期异常,fmt[%s],locale[%s],dateStr[%s]", fmt,
					locale, dateStr), e);
		}
	}

	/**
	 * 
	 * 描述 : <本月第一天，第一秒>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * @return
	 */
	public static Timestamp thisMonthFirstFullDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 
	 * 描述 : <本月最后一天，最后一秒>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 */
	public static Timestamp thisMonthLastFullDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 
	 * 描述 : <将给定date的时分秒毫秒初始化为0>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param original
	 *            给定日期
	 * @return
	 */
	public static Date toDateBegin(Date original) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(original);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 
	 * 描述 : <将给定date的时分秒毫秒初始化为:23时59分59秒999毫秒>. <br>
	 * <p>
	 * 
	 * @param original
	 * @return
	 */
	public static Date toDateEnd(Date original) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(original);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
}
