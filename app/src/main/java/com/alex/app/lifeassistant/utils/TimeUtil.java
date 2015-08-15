package com.alex.app.lifeassistant.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * Created by alex.lee on 2015-07-19.
 */
public class TimeUtil {
	/**
	 * 修正时间
	 *
	 * @param date 原始时间
	 *
	 * @return 修正后的时间
	 */
	public static String modifyDate(String date) {
		DateTime dateTime = DateTimeFormat.forPattern("yyyyMMddhhmmss").parseDateTime(date);

		if (dateTime.toString("yyyyMMdd").equals(DateTime.now().toString("yyyyMMdd"))) {
			return dateTime.toString("hh:mm");
		} else if (dateTime.toString("yyyy").equals(DateTime.now().toString("yyyy"))) {
			return dateTime.toString("yyyy-MM-dd hh:mm");
		} else {
			return date;
		}
	}
}
