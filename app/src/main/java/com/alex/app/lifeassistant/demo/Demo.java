package com.alex.app.lifeassistant.demo;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

/**
 * Created by alex.lee on 2015-07-06.
 */
public class Demo {
	public static void main(String[] args) {
		DateTime dateTime = DateTime.parse("2015-07-06");
			switch (dateTime.getDayOfWeek()) {
				case DateTimeConstants.SUNDAY:
					System.out.println("星期日");
					break;
				case DateTimeConstants.MONDAY:
					System.out.println("星期一");
					break;
				case DateTimeConstants.TUESDAY:
					System.out.println("星期二");
					break;
				case DateTimeConstants.WEDNESDAY:
					System.out.println("星期三");
					break;
				case DateTimeConstants.THURSDAY:
					System.out.println("星期四");
					break;
				case DateTimeConstants.FRIDAY:
					System.out.println("星期五");
					break;
				case DateTimeConstants.SATURDAY:
					System.out.println("星期六");
					break;
		}
	}
}
