package com.joker.myfw.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author Joker
 *
 */
public class DateUtil {
	
	public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss"; // 24小时常用日期时间格式

	public static final String PATTERN_DATE = "yyyy-MM-dd";

	public static DateFormat dateTimeFormat = new SimpleDateFormat(PATTERN_DATETIME);

	public static DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);

	public static DateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	public static DateFormat yyyyMM = new SimpleDateFormat("yyyyMM");

	public static DateFormat yyyy = new SimpleDateFormat("yyyy");

	public static DateFormat MM_dd_yyyy = new SimpleDateFormat("MM-dd-yyyy");

	public static DateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

	public static DateFormat yyyy_MM_dd_mm_ss = new SimpleDateFormat("yyyy-MM-dd-mm-ss");

	public static DateFormat EEEE_MMMM_dd_yyyy = new SimpleDateFormat("EEEE-MMMM-dd-yyyy");

	public static DateFormat yyyy_MM_dd = dateFormat;

	public static DateFormat yyyy_MM = new SimpleDateFormat("yyyy-MM");
	
	public static DateFormat HH_mm_ss = new SimpleDateFormat("HH:mm:ss");
	
	
	/**
	 * 根据制定格式将日期转为日期字符串 再转为日期
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateByDate(Date date, String pattern) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(sdf.format(date));
	}

	/**
	 * 根据制定格式将日期转为日期字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getStringByDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 根据格式(yyyy-MM-dd HH:mm:ss)将日期转为日期字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getStringByDateTime(Date date) {
		return dateTimeFormat.format(date);
	}

	/**
	 * 根据格式(yyyy-MM-dd)将日期转为日期字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getStringByDate(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * 将指定格式的日期时间字符串转换为日期对象
	 * 
	 * @param datetime
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateByString(String datetime, String pattern)
			throws ParseException {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.parse(datetime);
	}

	/**
	 * 给出一个表示日期的字符串，返回一个日期类型的object
	 * 
	 * @param src 表示日期的字串
	 * @param df 字串对应的日期格式
	 * @return Date
	 */
	public static Date getDateByString(String src, DateFormat df){
		try{
			return df.parse(src);
		} catch (Exception e){
			return null;
		}
	}
	
	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的日期时间字符串转换为日期对象
	 * 
	 * @param datetime
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateTimeByString(String datetime) throws ParseException {
		return dateTimeFormat.parse(datetime);
	}
	
	/**
	 * 将yyyy-MM-dd格式的日期字符串转换为日期对象
	 * 
	 * @param cal
	 * @return
	 * @throws ParseException 
	 */
	public static Date getDateByString(String date) throws ParseException {
		return dateFormat.parse(date);
	}

	/**
	 * 转换为零点的时间(比如2013-03-18 14:53:36 转换为2013-03-18 00:00:00)
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseTimeToZero(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * 是否今天
	 * 
	 * @param day
	 * @return
	 * @throws ParseException 
	 */
	public static boolean isToday(Date date) throws ParseException {
		Date today = new Date();
		long time1 = getDateByDate(date, PATTERN_DATE).getTime();
		long time2 = getDateByDate(today, PATTERN_DATE).getTime();
		return time1 == time2;
	}
	
	/**
	 * 计算两个日期间相隔的天数 (date2-date1)
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException 
	 */
	public static Integer getDifDay(Date date1, Date date2) throws ParseException{
		date1 = getDateByDate(date1, PATTERN_DATE);
		date2 = getDateByDate(date2, PATTERN_DATE);
		
		long dif = date2.getTime() - date1.getTime();
		
		return (int)(dif / (24 * 3600 * 1000));
	}

	
	
	/**
	 * 判断两个时间相差是否一分钟
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException 
	 */
	public static boolean isDifOneMin(Date date1, Date date2) throws ParseException{
		date1 = HH_mm_ss.parse(HH_mm_ss.format(date1));
		date2 = HH_mm_ss.parse(HH_mm_ss.format(date2));
		
		long dif = date1.getTime() - date2.getTime();
		if(Math.abs(dif) <= 60 * 1000){
			return true;
		}
		return false;
	}
	
	/**
	   * 计算两个日期的差，返回X天X时X分X秒
	   * defDate
	   * @param date1
	   * @param date2
	   * @return
	   */
	public static String defDate(Date date1, Date date2) {
		if(null != date1 && null != date2){
			String result = "";
			long d = date1.getTime() - date2.getTime();
			long day = d / (1000*60*60*24L);
			long hour = (d%(1000*60*60*24L)) / (1000*60*60L);
			long min = ((d%(1000*60*60*24L)) % (1000*60*60L)) / (1000*60L);
			long sec = (((d%(1000*60*60*24L)) % (1000*60*60L)) % (1000*60L)) / (1000L);
			if(day>0){
				result+=day + "天";
			}
			if(hour>0){
				result +=hour+ "时";
			}
			if(min>0){
				result += min + "分";
			}
			if(sec>0){
				result +=  sec + "秒";
			}
			return result;
		}else{
			return "NULL";
		}
	}
}
