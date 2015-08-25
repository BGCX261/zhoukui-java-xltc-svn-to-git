package org.sunney.help.util;

import java.text.*;
import java.util.*;

/**
 * @author fisher
 * @description 日期工具类
 */
public class DateUtil {

	private static Calendar calendar = Calendar.getInstance();

	private static SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat
			.getInstance();

	/**
	 * @description 解析日期 字符串
	 * @param date
	 *            日期字符串
	 * @param type
	 *            日期类型
	 * @return Date
	 */

	public static Date parse(String date, DateFormatType type) {
		simpleDateFormat.applyPattern(type.getValue());
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * @description 格式化日期字符串(默认类型 yyyy-MM-dd HH:mm:ss)
	 * @param date
	 *            日期
	 * @return String
	 */
	public static final String format(Date date) {
		simpleDateFormat.applyPattern(DateFormatType.DEFAULT_TYPE.getValue());
		return simpleDateFormat.format(date);

	}

	/**
	 * @description 通过格式化类型格式化日期
	 * @param date
	 *            日期
	 * @param type
	 *            格式化类型
	 * @return String
	 */

	public static String format(Date date, DateFormatType type) {
		simpleDateFormat.applyPattern(type.getValue());
		return simpleDateFormat.format(date);

	}

	/**
	 * @description 传入timeStamp格式化日期字符串
	 * @param longDate
	 *            timeStamp
	 * @param type
	 *            日期格式化类型
	 * @return String
	 */

	public static String format(long longDate, DateFormatType type) {
		return format(new Date(longDate), type);
	}

	/**
	 * @description 将当前日期字符处格式化
	 * @param type
	 *            日期格式化类型
	 * @return String
	 */

	public static String format(DateFormatType type) {
		return format(new Date(), type);
	}

	/**
	 * 得到时间格式例如 2002-03-15
	 */
	public static final String format(long dateLong) {
		return format(new Date(dateLong));
	}

	/**
	 * 得到日期时间格式例如 2002-03-15 02:32:25
	 */
	public static final String formatTime(java.util.Date date) {
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
	}

	public static final String formatTime(long lngDate) {
		if (0 >= lngDate) {
			return "";
		} else {
			return formatTime(new Date(lngDate));
		}
	}

	public static final String formatTime(Long lngObj) {
		return formatTime(lngObj.longValue());
	}

	/**
	 * 得到小时，分的格式例如 02:32
	 */
	public static final String getFormatTime(java.util.Date date) {
		String Tempstr;
		int i;
		SimpleDateFormat mydate = new SimpleDateFormat(" MMM dd H mm ss,yyyy");
		Tempstr = "";

		mydate.applyPattern("H");
		i = Integer.parseInt(mydate.format(date));
		if (i < 10) {
			Tempstr = Tempstr + "0" + String.valueOf(i) + ":";
		} else {
			Tempstr = Tempstr + String.valueOf(i) + ":";
		}

		mydate.applyPattern("mm");
		i = Integer.parseInt(mydate.format(date));
		if (i < 10) {
			Tempstr = Tempstr + "0" + String.valueOf(i);
		} else {
			Tempstr = Tempstr + String.valueOf(i);
		}

		return Tempstr;
	}

	/**
	 * 得到小时的格式例如 02
	 */
	public static final String getFormatHour(java.util.Date date) {
		String Tempstr;
		int i;
		SimpleDateFormat mydate = new SimpleDateFormat(" MMM dd H mm ss,yyyy");
		Tempstr = "";

		mydate.applyPattern("H");
		i = Integer.parseInt(mydate.format(date));
		Tempstr = Tempstr + String.valueOf(i);
		return Tempstr;
	}

	/**
	 * 
	 * 得到小时的格式例如 02
	 */
	public static final String getFormatMinute(Date date) {

		String Tempstr;
		int i;
		SimpleDateFormat mydate = new SimpleDateFormat(" MMM dd H mm ss,yyyy");
		Tempstr = "";

		mydate.applyPattern("mm");
		i = Integer.parseInt(mydate.format(date));
		Tempstr = Tempstr + String.valueOf(i);
		return Tempstr;
	}

	/**
	 * 根据日期得到年份字符串
	 */
	public static final String getYear(Date date) {
		String Tempstr;
		int i;
		SimpleDateFormat mydate = new SimpleDateFormat(" MMM dd H mm ss,yyyy");
		Tempstr = "";
		mydate.applyPattern("yyyy");
		i = Integer.parseInt(mydate.format(date));
		Tempstr = Tempstr + String.valueOf(i);
		return Tempstr;
	}

	/**
	 * 根据日期得到月份字符串
	 */
	public static final String getMonth(Date date) {
		String Tempstr;
		int i;
		SimpleDateFormat mydate = new SimpleDateFormat(" MM dd H mm ss,yyyy");
		Tempstr = "";
		mydate.applyPattern("MM");
		i = Integer.parseInt(mydate.format(date));
		Tempstr = Tempstr + String.valueOf(i);
		return Tempstr;
	}

	/**
	 * 根据日期得到日期字符串
	 */
	public static final String getDay(Date date) {
		String Tempstr;
		int i;
		SimpleDateFormat mydate = new SimpleDateFormat(" MM dd H mm ss,yyyy");
		Tempstr = "";
		mydate.applyPattern("dd");
		i = Integer.parseInt(mydate.format(date));
		Tempstr = Tempstr + String.valueOf(i);
		return Tempstr;
	}

	/**
	 * 根据日期得到小时字符串
	 */
	public static final String getHour(Date date) {
		String Tempstr;
		int i;
		SimpleDateFormat mydate = new SimpleDateFormat(" MM dd H mm ss,yyyy");
		Tempstr = "";
		mydate.applyPattern("H");
		i = Integer.parseInt(mydate.format(date));
		Tempstr = Tempstr + String.valueOf(i);
		return Tempstr;
	}

	/**
	 * 用于只输入年月日生成long型的时间格式
	 **/
	public static final long getTimeLong(int yy, int mm, int dd) {
		calendar.clear();
		calendar.set(yy, mm - 1, dd, 0, 0, 0);
		return calendar.getTimeInMillis();

	}

	/**
	 * 用于输入年月日小时分生成long型的时间格式
	 **/
	public static final long getTimeLong(int yy, int mm, int dd, int h, int m) {
		calendar.clear();
		calendar.set(yy, mm - 1, dd, h, m, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 用于只输入年，月生成long型的时间格式
	 **/
	public static final long getTimeLong(int yy, int mm) {
		calendar.clear();
		calendar.set(yy, mm - 1, 0, 0, 0, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 根据输入的初始日期和新增的月份到新增月份后的总时间
	 **/
	public static final long getTotalTime(Date startTime, long month) {
		calendar.setTime(startTime);
		calendar.add(Calendar.MONTH, (int) month);
		return calendar.getTimeInMillis();
	}

	/**
	 * 用于输入年月日小时分秒生成long型的时间格式
	 **/
	public static final long getTimeLong(int yy, int mm, int dd, int h, int m,
			int sec) {
		calendar.clear();
		calendar.set(yy, mm - 1, dd, h, m, sec);
		return calendar.getTimeInMillis();
	}

	/**
	 * 根据输入一个时间得到和现在的时间差
	 **/
	public static final String getLeaveTime(long tagTime) {
		long nowTime = System.currentTimeMillis();
		long leaveTime = 0;
		if (nowTime > tagTime)
			leaveTime = (nowTime - tagTime) / 1000;
		else
			leaveTime = (tagTime - nowTime) / 1000;
		long date = 0;
		long hour = 0;
		long minute = 0;
		// int second = 0;

		long dateTime = 0;
		long hourTime = 0;
		// long minuteTime = 0;

		String strDate = "";
		String strHour = "";
		String strMinute = "";
		// String strSecond = "";

		date = leaveTime / 86400;
		dateTime = date * 86400;
		hour = (leaveTime - dateTime) / 3600;
		hourTime = hour * 3600;
		minute = (leaveTime - dateTime - hourTime) / 60;
		// minuteTime = minute*60;

		// second = leaveTime - dateTime - hourTime-minuteTime;

		if (date > 0)
			strDate = date + "天";
		if (hour > 0 || (minute > 0 && date > 0))
			strHour = hour + "小时";
		if (minute > 0)
			strMinute = minute + "分";
		// if(second>0)
		// strSecond = second+"秒";

		return strDate + strHour + strMinute;
	}

	/**
	 * @description 日期格式化类型枚举
	 */
	public enum DateFormatType {
		/**
		 * 格式为：yyyy-MM-dd HH:mm:ss
		 */
		DEFAULT_TYPE("yyyy-MM-dd HH:mm:ss"),

		/**
		 * 格式为：yyyy-MM-dd
		 */
		SIMPLE_TYPE("yyyy-MM-dd"),

		/**
		 * 格式为：yyyyMMddHHmmss
		 */
		FULL_TYPE("yyyyMMddHHmmss"),

		/**
		 * 格式为：yyyy/MM/dd
		 */
		SIMPLE_VIRGULE_TYPE("yyyy/MM/dd"),

		/**
		 * 格式为：yyyy/MM/dd
		 */
		FULL_VIRGULE_TYPE("yyyy/MM/dd HH:mm:ss"),

		/**
		 * 格式为:yyyy年MM月dd日
		 */

		SIMPLE_CN_TYPE("yyyy年MM月dd日"),

		/**
		 * 格式为:yyyy年MM月dd日 HH时mm分ss秒
		 */

		FULL_CN_TYPE("yyyy年MM月dd日 HH时mm分ss秒"),

		/**
		 * 格式为：HH:mm:ss
		 */
		FUll_TIME_TYPE("HH:mm:ss"),

		/**
		 * 格式为：HH:mm
		 */
		HOUR_MINUTE_TIME_TYPE("HH:mm");

		private final String value;

		DateFormatType(String formatStr) {
			this.value = formatStr;
		}

		/**
		 * 
		 * @description 获取指定日期类型
		 * @return
		 */
		public String getValue() {
			return value;
		}

	}
	/**
	 * @description 获取所有的日期格式化类型
	 * @return String[]
	 */
	public static String[] getAllFormatTypes() {
		DateFormatType[] types = DateFormatType.values();
		List<String> values = new ArrayList<String>();
		for (DateFormatType type : types) {
			values.add(type.getValue());
		}
		return values.toArray(new String[values.size()]);
	}

}