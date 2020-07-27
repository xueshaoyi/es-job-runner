package com.xsy.job.service.util;

/**
 * @author xueshaoyi
 * @Date 2020/7/26 下午9:47
 **/
public class TimeCronUtil {

	private static final String END_CRON = "0 %minute %hour * * ? *";
	private static final String MINUTE = "%minute";
	private static final String HOUR = "%hour";
	private static final String HEAD = "0/";

	public static void main(String[] args) {
		String result = intervalTimeToCron(20, 23, 15);
		System.out.println(result);
	}


	public static String intervalTimeToCron(int intervalTime, int startTime, int endTime) {
		String minute = minuteCron(intervalTime);
		String hour = hourCron(intervalTime, startTime, endTime);
		String cron = END_CRON;
		cron = cron.replace(MINUTE, minute).replace(HOUR, hour);
		return cron;
	}

	private static String minuteCron(int intervalTime) {
		if (intervalTime < 60) {
			return HEAD + intervalTime;
		} else {
			return "0";
		}
	}

	private static String hourCron(int intervalTime, int startTime, int endTime) {
		String resultHour = "0/1";
		int intervalHour = 0;
		if (intervalTime > 60) {
			intervalHour = intervalTime / 60;
		}
		if (intervalHour == 0) {
			if (startTime < endTime) {
				resultHour = startTime + "-" + endTime;
			} else if (startTime == endTime) {
				resultHour = startTime + "";
			} else {
				resultHour = intervalHour(false, 1, startTime, endTime);
			}
		} else {
			boolean endGtStart = true;
			if (startTime > endTime) {
				endGtStart = false;
			}
			resultHour = intervalHour(endGtStart, intervalHour, startTime, endTime);
		}

		return resultHour;
	}

	private static String intervalHour(boolean endGtStart, int intervalHour, int startTime, int endTime) {
		if (endGtStart) {
			int interval = startTime + intervalHour;
			if (endTime < interval) {
				return String.valueOf(startTime);
			} else {
				String result = intervalHour(endGtStart, intervalHour, interval, endTime);
				return startTime +","+ result;
			}
		} else {
			int interval = startTime + intervalHour;
			if (interval >= 24) {
				endGtStart = true;
				interval = interval % 24;
			}
			if (endGtStart) {
				if (endTime < interval) {
					return String.valueOf(startTime);
				} else {
					String result = intervalHour(endGtStart, intervalHour, interval, endTime);
					return startTime +","+ result;
				}
			} else {
				String result = intervalHour(endGtStart, intervalHour, interval, endTime);
				return startTime +","+ result;
			}
		}

	}

}
