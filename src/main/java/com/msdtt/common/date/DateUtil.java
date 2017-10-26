package com.msdtt.common.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zhuxd@wjs.com
 * @date 2017/9/26 15:56
 */
public class DateUtil{

	public static Date integerFormatDate(Integer startInteger) {
		Date formatDate = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			formatDate = sdf.parse(Integer.toString(startInteger.intValue()));
		} catch (ParseException var3) {
			;
		}

		return formatDate;
	}

	public static Date getMinTimeForDateByInter(Integer intDate){
		Date date = integerFormatDate(intDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Date minTime = calendar.getTime();

		return minTime;
	}

	public static Date getMaxTimeForDateByInter(Integer intDate){
		Date date = integerFormatDate(intDate);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date maxTime = calendar.getTime();

		return maxTime;
	}

	public static Integer getIntByDate(Date date){
		SimpleDateFormat currentTimeFormat = new SimpleDateFormat("yyyyMMdd");
		return Integer.parseInt(currentTimeFormat.format(date));
	}

	public static void main(String[] args){
		System.out.println(getIntByDate(new Date()));
	}
}
