package com.shopping.unit;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public final class DateUtils implements Serializable {

	private static final long serialVersionUID = -3098985139095632110L;
	public static final String YYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYMMDD = "yyyy-MM-dd";
	public static final String YYYMM = "yyyy-MM";
	public static final String YYYMMDD_NOT = "yyyyMMdd";
	public static final String DAY_START = " 00:00:01";
	public static final String DAY_END = " 23:59:59";
	public static final String YYYMMDDHHMMSSSTR = "yyyyMMddHHmmss";

	private DateUtils() {
	}

	/**
	 * Timestamp转化为String:
	 */
	public static String convertTimestampToStrOfDate(long longtime) {
		SimpleDateFormat df = new SimpleDateFormat(YYYMMDDHHMMSS);
		Timestamp now = new Timestamp(longtime);
		String str = df.format(now);
		return str;
	}

	/**
	 * Timestamp转化为String:
	 */
	public static String convertTimestampToStrOfDate(long longtime, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Timestamp now = new Timestamp(longtime);
		String str = df.format(now);
		return str;
	}

	/**
	 * 上个月的最开始一天到 秒
	 * 
	 * @return
	 */
	public static String getLastMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date strDateTo = calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat(YYYMMDD);
		return df.format(strDateTo) + DAY_START;
	}

	/**
	 * 上个月的最后一天到 秒
	 * 
	 * @return
	 */
	public static String getLastMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date strDateTo = calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat(YYYMMDD);
		return df.format(strDateTo) + DAY_END;
	}

	/**
	 * 当前月的第一天
	 * 
	 * @return
	 */
	public static String getCurrentMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date strDateTo = calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat(YYYMMDD);
		return df.format(strDateTo) + DAY_START;
	}

	/**
	 * 当前月的最后一天
	 * 
	 * @return
	 */
	public static String getCurrentMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date strDateTo = calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat(YYYMMDD);
		return df.format(strDateTo) + DAY_END;
	}

	/**
	 * 计算两个时间中间的间隔天数.
	 * 
	 * @param startday
	 * @param endday
	 */
	public static int getIntervalDays(Date startday, Date endday) {
		if (startday.after(endday)) {
			Date cal = startday;
			startday = endday;
			endday = cal;
		}
		long startdays = startday.getTime();
		long enddays = endday.getTime();
		long result = enddays - startdays;
		return (int) (result / (1000 * 60 * 60 * 24));
	}

	/**
	 * 格式化日期显示格式
	 * 
	 * @param sdate
	 *            原始日期格式 s - 表示 "yyyy-mm-dd" 形式的日期的 String 对象
	 * @param format
	 *            格式化后日期格式
	 * @return 格式化后的日期显示
	 */

	public static String dateFormat(String sdate, String format) {

		SimpleDateFormat formatter = new SimpleDateFormat(format);

		java.sql.Date date = java.sql.Date.valueOf(sdate);

		String dateString = formatter.format(date);

		return dateString;

	}

	/**
	 * 求两个日期相差天数
	 * 
	 * @param sd
	 *            起始日期，格式yyyy-MM-dd
	 * @param ed
	 *            终止日期，格式yyyy-MM-dd
	 * @return 两个日期相差天数
	 */

	public static long getIntervalDays(String sd, String ed) {

		return ((java.sql.Date.valueOf(ed)).getTime() - (java.sql.Date

				.valueOf(sd)).getTime()) / (3600 * 24 * 1000);

	}

	/**
	 * 起始年月yyyy-MM与终止月yyyy-MM之间跨度的月数。
	 * 
	 * @param beginMonth
	 *            格式为yyyy-MM
	 * @param endMonth
	 *            格式为yyyy-MM
	 * @return 整数。
	 */

	public static int getInterval(String beginMonth, String endMonth) {

		int intBeginYear = Integer.parseInt(beginMonth.substring(0, 4));

		int intBeginMonth = Integer.parseInt(beginMonth.substring(beginMonth

				.indexOf("-") + 1));

		int intEndYear = Integer.parseInt(endMonth.substring(0, 4));

		int intEndMonth = Integer.parseInt(endMonth.substring(endMonth

				.indexOf("-") + 1));

		return ((intEndYear - intBeginYear) * 12)

				+ (intEndMonth - intBeginMonth) + 1;

	}

	/**
	 * 根据给定的分析位置开始分析日期/时间字符串。例如，时间文本 "07/10/96 4:5 PM, PDT" 会分析成等同于
	 * <p/>
	 * Date(837039928046) 的 Date。
	 * 
	 * @param sDate
	 * @param dateFormat
	 * @return
	 */

	public static Date getDate(String sDate, String dateFormat) {

		SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);

		ParsePosition pos = new ParsePosition(0);

		return fmt.parse(sDate, pos);

	}

	/**
	 * 取得当前日期的年份，以yyyy格式返回.
	 * 
	 * @return 当年 yyyy
	 */

	public static String getCurrentYear() {
		return getFormatCurrentTime("yyyy");
	}

	public static String getCurrentYearMonth() {
		return getFormatCurrentTime("yyyy-MM");
	}

	/**
	 * 自动返回上一年。例如当前年份是2007年，那么就自动返回2006
	 * 
	 * @return 返回结果的格式为 yyyy
	 */

	public static String getBeforeYear() {

		String currentYear = getFormatCurrentTime("yyyy");

		int beforeYear = Integer.parseInt(currentYear) - 1;

		return "" + beforeYear;

	}

	/**
	 * 取得当前日期的月份，以MM格式返回.
	 * 
	 * @return 当前月份 MM
	 */

	public static String getCurrentMonth() {

		return getFormatCurrentTime("MM");

	}

	/**
	 * 取得当前日期的上一个月，以MM格式返回.
	 * 
	 * @return 当前月份 MM
	 */

	public static String getLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return sdf.format(cal.getTime());
	}

	/**
	 * 取得当前日期的天数，以格式"dd"返回.
	 * 
	 * @return 当前月中的某天dd
	 */

	public static String getCurrentDay() {

		return getFormatCurrentTime("dd");

	}

	/**
	 * 返回当前时间字符串。
	 * <p/>
	 * <p/>
	 * <p/>
	 * 格式：yyyy-MM-dd
	 * 
	 * @return String 指定格式的日期字符串.
	 */

	public static String getCurrentDate() {

		return getFormatDateTime(new Date(), "yyyy-MM-dd");

	}

	public static String getCurrentDate(String format) {

		return getFormatDateTime(new Date(), format);

	}

	/**
	 * 返回当前指定的时间戳。格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 格式为yyyy-MM-dd HH:mm:ss，总共19位。
	 */

	public static String getCurrentDateTime() {

		return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");

	}

	public static Long getCurrentSysTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 返回给定时间字符串。
	 * <p/>
	 * <p/>
	 * <p/>
	 * 格式：yyyy-MM-dd
	 * 
	 * @param date
	 *            日期
	 * @return String 指定格式的日期字符串.
	 */

	public static String getFormatDate(Date date) {

		return getFormatDateTime(date, "yyyy-MM-dd");

	}

	/**
	 * 根据制定的格式，返回日期字符串。例如2007-05-05
	 * 
	 * @param format
	 *            "yyyy-MM-dd" 或者 "yyyy/MM/dd",当然也可以是别的形式。
	 * @return 指定格式的日期字符串。
	 */

	public static String getFormatDate(String format) {

		return getFormatDateTime(new Date(), format);

	}

	/**
	 * 返回当前时间字符串。
	 * <p/>
	 * <p/>
	 * <p/>
	 * 格式：HH:mm:ss
	 * 
	 * @return String 指定格式的日期字符串.
	 */

	public static String getCurrentTime() {

		return getFormatDateTime(new Date(), "HH:mm:ss");

	}

	/**
	 * 返回给定时间字符串。
	 * <p/>
	 * <p/>
	 * <p/>
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            日期
	 * @return String 指定格式的日期字符串.
	 */

	public static String getFormatTime(Date date) {

		return getFormatDateTime(date, "yyyy-MM-dd HH:mm:ss");

	}

	public static String getFormatTime(Date date, String format) {

		return getFormatDateTime(date, format);

	}

	/**
	 * 根据给定的格式，返回时间字符串。
	 * <p/>
	 * <p/>
	 * <p/>
	 * 格式参照类描绘中说明.和方法getFormatDate是一样的。
	 * 
	 * @param format
	 *            日期格式字符串
	 * @return String 指定格式的日期字符串.
	 */

	public static String getFormatCurrentTime(String format) {

		return getFormatDateTime(new Date(), format);

	}

	/**
	 * 根据给定的格式与时间(Date类型的)，返回时间字符串。最为通用。<br>
	 * 
	 * @param date
	 *            指定的日期
	 * @param format
	 *            日期格式字符串
	 * @return String 指定格式的日期字符串.
	 */

	public static String getFormatDateTime(Date date, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		return sdf.format(date);

	}

	/**
	 * 取得指定年月日的日期对象.
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月注意是从1到12
	 * @param day
	 *            日
	 * @return 一个java.util.Date()类型的对象
	 */

	public static Date getDateObj(int year, int month, int day) {

		Calendar c = new GregorianCalendar();

		c.set(year, month - 1, day);

		return c.getTime();

	}

	/**
	 * 获取指定日期的下一天。
	 * 
	 * @param date
	 *            yyyy/MM/dd
	 * @return yyyy/MM/dd
	 */

	public static String getDateTomorrow(String date) {

		Date tempDate = null;

		if (date.indexOf("/") > 0)

			tempDate = getDateObj(date, "[/]");

		if (date.indexOf("-") > 0)

			tempDate = getDateObj(date, "[-]");

		tempDate = getDateAdd(tempDate, 1);

		return getFormatDateTime(tempDate, "yyyy/MM/dd");

	}

	/**
	 * 获取与指定日期相差指定天数的日期。
	 * 
	 * @param date
	 *            yyyy/MM/dd
	 * @param offset
	 *            正整数
	 * @return yyyy/MM/dd
	 */

	public static String getDateOffset(String date, int offset) {

		// Date tempDate = getDateObj(date, "[/]");

		Date tempDate = null;

		if (date.indexOf("/") > 0)

			tempDate = getDateObj(date, "[/]");

		if (date.indexOf("-") > 0)

			tempDate = getDateObj(date, "[-]");

		tempDate = getDateAdd(tempDate, offset);

		return getFormatDateTime(tempDate, "yyyy/MM/dd");

	}

	/**
	 * 取得指定分隔符分割的年月日的日期对象.
	 * 
	 * @param argsDate
	 *            格式为"yyyy-MM-dd"
	 * @param split
	 *            时间格式的间隔符，例如“-”，“/”，要和时间一致起来。
	 * @return 一个java.util.Date()类型的对象
	 */

	public static Date getDateObj(String argsDate, String split) {

		String[] temp = argsDate.split(split);

		int year = new Integer(temp[0]).intValue();

		int month = new Integer(temp[1]).intValue();

		int day = new Integer(temp[2]).intValue();

		return getDateObj(year, month, day);

	}

	/**
	 * 取得给定字符串描述的日期对象，描述模式采用pattern指定的格式.
	 * 
	 * @param dateStr
	 *            日期描述 从给定字符串的开始分析文本，以生成一个日期。该方法不使用给定字符串的整个文本。 有关日期分析的更多信息，请参阅
	 *            <p/>
	 *            parse(String, ParsePosition) 方法。一个 String，应从其开始处进行分析
	 * @param pattern
	 *            日期模式
	 * @return 给定字符串描述的日期对象。
	 */

	public static Date getDateFromString(String dateStr, String pattern) {

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		Date resDate = null;

		try {

			resDate = sdf.parse(dateStr);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return resDate;

	}

	/**
	 * 取得当前Date对象.
	 * 
	 * @return Date 当前Date对象.
	 */

	public static Date getDateObj() {

		Calendar c = new GregorianCalendar();

		return c.getTime();

	}

	/**
	 * @return 当前月份有多少天；
	 */

	public static int getDaysOfCurMonth() {

		int curyear = new Integer(getCurrentYear()).intValue(); // 当前年份

		int curMonth = new Integer(getCurrentMonth()).intValue();// 当前月份

		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,

				31 };

		// 判断闰年的情况 ，2月份有29天；

		if ((curyear % 400 == 0)

				|| ((curyear % 100 != 0) && (curyear % 4 == 0))) {

			mArray[1] = 29;

		}

		return mArray[curMonth - 1];

		// 如果要返回下个月的天数，注意处理月份12的情况，防止数组越界；

		// 如果要返回上个月的天数，注意处理月份1的情况，防止数组越界；

	}

	/**
	 * 根据指定的年月 返回指定月份（yyyy-MM）有多少天。
	 * 
	 * @param time
	 *            yyyy-MM
	 * @return 天数，指定月份的天数。
	 */

	public static int getDaysOfCurMonth(final String time) {

		if (time.length() != 7) {

			throw new NullPointerException("参数的格式必须是yyyy-MM");

		}

		String[] timeArray = time.split("-");

		int curyear = new Integer(timeArray[0]).intValue(); // 当前年份

		int curMonth = new Integer(timeArray[1]).intValue();// 当前月份

		if (curMonth > 12) {

			throw new NullPointerException("参数的格式必须是yyyy-MM，而且月份必须小于等于12。");

		}

		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,

				31 };

		// 判断闰年的情况 ，2月份有29天；

		if ((curyear % 400 == 0)

				|| ((curyear % 100 != 0) && (curyear % 4 == 0))) {

			mArray[1] = 29;

		}

		if (curMonth == 12) {

			return mArray[0];

		}

		return mArray[curMonth - 1];

		// 如果要返回下个月的天数，注意处理月份12的情况，防止数组越界；

		// 如果要返回上个月的天数，注意处理月份1的情况，防止数组越界；

	}

	/**
	 * 返回指定为年度为year月度month的月份内，第weekOfMonth个星期的第dayOfWeek天是当月的几号。<br>
	 * <p/>
	 * 00 00 00 01 02 03 04 <br>
	 * <p/>
	 * 05 06 07 08 09 10 11<br>
	 * <p/>
	 * 12 13 14 15 16 17 18<br>
	 * <p/>
	 * 19 20 21 22 23 24 25<br>
	 * <p/>
	 * 26 27 28 29 30 31 <br>
	 * <p/>
	 * 2006年的第一个周的1到7天为：05 06 07 01 02 03 04 <br>
	 * <p/>
	 * 2006年的第二个周的1到7天为：12 13 14 08 09 10 11 <br>
	 * <p/>
	 * 2006年的第三个周的1到7天为：19 20 21 15 16 17 18 <br>
	 * <p/>
	 * 2006年的第四个周的1到7天为：26 27 28 22 23 24 25 <br>
	 * <p/>
	 * 2006年的第五个周的1到7天为：02 03 04 29 30 31 01 。本月没有就自动转到下个月了。
	 * 
	 * @param year
	 *            形式为yyyy <br>
	 * @param month
	 *            形式为MM,参数值在[1-12]。<br>
	 * @param weekOfMonth
	 *            在[1-6],因为一个月最多有6个周。<br>
	 * @param dayOfWeek
	 *            数字在1到7之间，包括1和7。1表示星期天，7表示星期六<br>
	 *            <p/>
	 *            -6为星期日-1为星期五，0为星期六 <br>
	 * @return <type>int</type>
	 */

	public static int getDayofWeekInMonth(String year, String month,

			String weekOfMonth, String dayOfWeek) {

		Calendar cal = new GregorianCalendar();

		// 在具有默认语言环境的默认时区内使用当前时间构造一个默认的 GregorianCalendar。

		int y = new Integer(year).intValue();

		int m = new Integer(month).intValue();

		cal.clear();// 不保留以前的设置

		cal.set(y, m - 1, 1);// 将日期设置为本月的第一天。

		cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, new Integer(weekOfMonth)

				.intValue());

		cal.set(Calendar.DAY_OF_WEEK, new Integer(dayOfWeek).intValue());

		// WEEK_OF_MONTH表示当天在本月的第几个周。不管1号是星期几，都表示在本月的第一个周

		return cal.get(Calendar.DAY_OF_MONTH);

	}

	/**
	 * 根据指定的年月日小时分秒，返回一个java.Util.Date对象。
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月 0-11
	 * @param date
	 *            日
	 * @param hourOfDay
	 *            小时 0-23
	 * @param minute
	 *            分 0-59
	 * @param second
	 *            秒 0-59
	 * @return 一个Date对象。
	 */

	public static Date getDate(int year, int month, int date, int hourOfDay,

			int minute, int second) {

		Calendar cal = new GregorianCalendar();

		cal.set(year, month, date, hourOfDay, minute, second);

		return cal.getTime();

	}

	/**
	 * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
	 * 
	 * @param year
	 * @param month
	 *            month是从1开始的12结束
	 * @param day
	 * @return 返回一个代表当期日期是星期几的数字。1表示星期天、2表示星期一、7表示星期六。
	 */

	public static int getDayOfWeek(String year, String month, String day) {

		Calendar cal = new GregorianCalendar(new Integer(year).intValue(),

				new Integer(month).intValue() - 1, new Integer(day).intValue());

		return cal.get(Calendar.DAY_OF_WEEK);

	}

	/**
	 * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
	 * 
	 * @param date
	 *            "yyyy/MM/dd",或者"yyyy-MM-dd"
	 * @return 返回一个代表当期日期是星期几的数字。1表示星期天、2表示星期一、7表示星期六。
	 */

	public static int getDayOfWeek(String date) {

		String[] temp = null;

		if (date.indexOf("/") > 0) {

			temp = date.split("/");

		}

		if (date.indexOf("-") > 0) {

			temp = date.split("-");

		}

		return getDayOfWeek(temp[0], temp[1], temp[2]);

	}

	/**
	 * 返回当前日期是星期几。例如：星期日、星期一、星期六等等。
	 * 
	 * @param date
	 *            格式为 yyyy/MM/dd 或者 yyyy-MM-dd
	 * @return 返回当前日期是星期几
	 */

	public static String getChinaDayOfWeek(String date) {

		String[] weeks = new String[] { "星期日", "星期一", "星期二", "星期三", "星期四",

				"星期五", "星期六" };

		int week = getDayOfWeek(date);

		return weeks[week - 1];

	}

	/**
	 * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
	 * 
	 * @param date
	 * @return 返回一个代表当期日期是星期几的数字。1表示星期天、2表示星期一、7表示星期六。
	 */

	public static int getDayOfWeek(Date date) {

		Calendar cal = new GregorianCalendar();

		cal.setTime(date);

		return cal.get(Calendar.DAY_OF_WEEK);

	}

	/**
	 * 返回制定日期所在的周是一年中的第几个周。<br>
	 * <p/>
	 * created by wangmj at 20060324.<br>
	 * 
	 * @param year
	 * @param month
	 *            范围1-12<br>
	 * @param day
	 * @return int
	 */

	public static int getWeekOfYear(String year, String month, String day) {

		Calendar cal = new GregorianCalendar();

		cal.clear();

		cal.set(new Integer(year).intValue(),

				new Integer(month).intValue() - 1, new Integer(day).intValue());

		return cal.get(Calendar.WEEK_OF_YEAR);

	}

	/**
	 * 取得给定日期加上一定天数后的日期对象.
	 * 
	 * @param date
	 *            给定的日期对象
	 * @param amount
	 *            需要添加的天数，如果是向前的天数，使用负数就可以.
	 * @return Date 加上一定天数以后的Date对象.
	 */

	public static Date getDateAdd(Date date, int amount) {

		Calendar cal = new GregorianCalendar();

		cal.setTime(date);

		cal.add(GregorianCalendar.DATE, amount);

		return cal.getTime();

	}

	/**
	 * 获取给定日期加上一定天数的时间戳对象
	 * 
	 * @return
	 */
	public static Timestamp getTimestampAdd(int amount) {
		Date date = getDateAdd(new Date(), amount);
		return new Timestamp(date.getTime());
	}

	/**
	 * 获取给定日期 加上一定的分钟的时间戳
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Timestamp getTimestampAddMinute(Date date, int hour) {
		Calendar cal = new GregorianCalendar();

		cal.setTime(date);

		cal.add(GregorianCalendar.MINUTE, hour);

		return new Timestamp(cal.getTime().getTime());
	}

	/**
	 * 获取给定日期 减去一定的分钟的时间戳
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Timestamp getTimestampReduceMinute(Date date, int hour) {
		Calendar cal = new GregorianCalendar();

		cal.setTime(date);

		cal.set(GregorianCalendar.MINUTE, cal.get(Calendar.HOUR) - hour);

		return new Timestamp(cal.getTime().getTime());
	}

	/**
	 * 取得给定日期加上一定天数后的日期对象.
	 * <p/>
	 * <p/>
	 * <p/>
	 * , @param date
	 * <p/>
	 * 给定的日期对象
	 * 
	 * @param amount
	 *            需要添加的天数，如果是向前的天数，使用负数就可以.
	 * @param format
	 *            输出格式.
	 * @return Date 加上一定天数以后的Date对象.
	 */

	public static String getFormatDateAdd(Date date, int amount, String format) {

		Calendar cal = new GregorianCalendar();

		cal.setTime(date);

		cal.add(GregorianCalendar.DATE, amount);

		return getFormatDateTime(cal.getTime(), format);

	}

	/**
	 * 获得当前日期固定间隔天数的日期，如前60天dateAdd(-60)
	 * 
	 * @param amount
	 *            距今天的间隔日期长度，向前为负，向后为正
	 * @param format
	 *            输出日期的格式.
	 * @return java.lang.String 按照格式输出的间隔的日期字符串.
	 */

	public static String getFormatCurrentAdd(int amount, String format) {

		Date d = getDateAdd(new Date(), amount);

		return getFormatDateTime(d, format);

	}

	/**
	 * 取得给定格式的昨天的日期输出
	 * 
	 * @param format
	 *            日期输出的格式
	 * @return String 给定格式的日期字符串.
	 */

	public static String getFormatYestoday(String format) {

		return getFormatCurrentAdd(-1, format);

	}

	/**
	 * 返回指定日期的前一天。<br>
	 * 
	 * @param sourceDate
	 * @param format
	 *            yyyy MM dd hh mm ss
	 * @return 返回日期字符串，形式和formcat一致。
	 */

	public static String getYestoday(String sourceDate, String format) {

		return getFormatDateAdd(getDateFromString(sourceDate, format), -1,

				format);

	}

	/**
	 * 返回明天的日期，<br>
	 * 
	 * @param format
	 * @return 返回日期字符串，形式和formcat一致。
	 */

	public static String getFormatTomorrow(String format) {

		return getFormatCurrentAdd(1, format);

	}

	/**
	 * 返回指定日期的后一天。<br>
	 * 
	 * @param sourceDate
	 * @param format
	 * @return 返回日期字符串，形式和formcat一致。
	 */

	public static String getFormatDateTommorrow(String sourceDate, String format) {

		return getFormatDateAdd(getDateFromString(sourceDate, format), 1,

				format);

	}

	/**
	 * 根据主机的默认 TimeZone，来获得指定形式的时间字符串。
	 * 
	 * @param dateFormat
	 * @return 返回日期字符串，形式和formcat一致。
	 */

	public static String getCurrentDateString(String dateFormat) {

		Calendar cal = Calendar.getInstance(TimeZone.getDefault());

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		sdf.setTimeZone(TimeZone.getDefault());

		return sdf.format(cal.getTime());

	}

	/**
	 * 获取指定时间的那天 23:59:59.999 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayEnd(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 获取指定时间的那天 00:00:00.001 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayBegin(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 001);
		return c.getTime();
	}

	public static Date toDate(String dateStr, String format) {
		DateFormat dataFormat = new SimpleDateFormat(format);
		try {
			return dataFormat.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}

	}

	public static Timestamp toTimestamp(String timeStr) {
		Timestamp tp = new Timestamp(System.currentTimeMillis());
		tp = Timestamp.valueOf(timeStr);
		return tp;

	}

	public static String toString(Date date, String format) {
		DateFormat dataFormat = new SimpleDateFormat(format);
		return dataFormat.format(date);
	}

	public static Date toDate(Timestamp date) {
		return new Date(date.getTime());
	}

	public static Timestamp getDBDate() {
		return new Timestamp(new Date().getTime());
	}

	public static Timestamp longToTimestamp(long longTime) {
		Timestamp now = new Timestamp(longTime * 1000L);
		return now;
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}

	/**
	 * 将时间unix转换为int类型
	 * 
	 * @param timeString
	 * @param format
	 * @return
	 */
	public static int dateToInt(Date date) {
		String strTime = String.valueOf(date.getTime());
		strTime = strTime.substring(0, 10);
		return Integer.parseInt(strTime);
	}

	public static String getAfter(int timeinterval, Date d) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return bartDateFormat.format(getAfterDateDay(timeinterval, d));
	}

	public static Date getAfterDate(int timeinterval, Date d) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		ca.add(Calendar.DATE, timeinterval);
		return ca.getTime();
	}

	public static Date getAfterDateMonth(int month) {
		Date dNow = new Date();
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dNow);
		calendar.add(Calendar.MONTH, month);
		dBefore = calendar.getTime();
		return dBefore;
	}

	public static Date getAfterDateDay(int timeinterval, Date d) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		ca.add(Calendar.DATE, timeinterval);
		return ca.getTime();
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int getDistanceDays(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static Date getAfterOneHour() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
		return calendar.getTime();
	}

	/**
	 * 按年加减日期
	 *
	 * @param date：日期
	 * @param num：要加减的年数
	 * @return：成功，则返回加减后的日期；失败，则返回null
	 */
	public static Date addYears(Date date, int num) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, num);
		return c.getTime();
	}

	/**
	 * 按月加减日期
	 *
	 * @param date：日期
	 * @param num：要加减的月数
	 * @return：成功，则返回加减后的日期；失败，则返回null
	 */
	public static Date addMonths(Date date, int num) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, num);
		return c.getTime();
	}

	/**
	 * 按日加减日期
	 *
	 * @param date：日期
	 * @param num：要加减的日数
	 * @return：成功，则返回加减后的日期；失败，则返回null
	 */
	public static Date addDays(Date date, int num) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, num);
		return c.getTime();
	}

	/**
	 * 按小时 加减日期
	 * Calendar.HOUR_OF_DAY是24小时制    
	 * Calendar.HOUR是12小时制
	 * @param date：日期
	 * @param num：要加减的小时
	 * @return：成功，则返回加减后的日期；失败，则返回null
	 */
	public static Date addHour(Date date, int num) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, num);
		return c.getTime();
	}

	/**
	 * 按分钟 加减日期
	 *
	 * @param date：日期
	 * @param num：要加减的分钟
	 * @return：成功，则返回加减后的日期；失败，则返回null
	 */
	public static Date addMinutes(Date date, int num) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, num);
		return c.getTime();
	}

	/**
	 * 按秒 加减日期
	 *
	 * @param date：日期
	 * @param num：要加减的秒
	 * @return：成功，则返回加减后的日期；失败，则返回null
	 */
	public static Date addSeconds(Date date, int num) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, num);
		return c.getTime();
	}

    // 获得今天0点
    public static long getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}
