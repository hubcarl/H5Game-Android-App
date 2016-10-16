package com.blue.sky.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime
{
	public static String getNowDate()
	{
		return format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss");

	}

	public static String format(Calendar c, String pattern)
	{
		Calendar calendar = null;
		if (c != null)
		{
			calendar = c;
		}
		else
		{
			calendar = Calendar.getInstance();
		}
		if (pattern == null || pattern.equals(""))
		{
			pattern = "yyyy年MM月dd日 HH:mm:ss";
		}

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		return sdf.format(calendar.getTime());
	}

	public static long getCompareDays(String dateTime1, String dateTime2)
	{
		long days = -1;

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try

		{
			Date d1 = df.parse(dateTime1);

			Date d2 = df.parse(dateTime2);

			long diff = d1.getTime() - d2.getTime();

			days = diff / (1000 * 60 * 60 * 24);
		}

		catch (Exception e)

		{

		}
		return days;
	}

}
