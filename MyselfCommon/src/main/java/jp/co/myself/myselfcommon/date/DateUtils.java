package jp.co.myself.myselfcommon.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * (共通)日付クラスです。
 */
public class DateUtils {
	
	/**
	 * 現在の日付文字列(yyyy_MM_dd)を返却します。
	 * @return String
	 */
	public static String getCurrentDateString() {
		Date date = new Date();
		return (new SimpleDateFormat("yyyy_MM_dd").format(date));
	}
	
	/**
	 * 現在の日付文字列(yyyy_MM_dd HH:mm:ss)を返却します。
	 * @return String
	 */
	public static String getLogDateString() {
		Date date = new Date();
		return new SimpleDateFormat("yyyy_MM_dd HH:mm:ss").format(date);
	}
	
	/**
	 * ミリ秒を日時文字列(yyyy_MM_dd HH:mm:ss)に変換して返却します。
	 * @param msec
	 * @return String
	 */
	public static String convertMsecToDateString(long msec) {
		Date date = new Date(msec);
		return new SimpleDateFormat("yyyy_MM_dd HH:mm:ss").format(date);
	}
	
}
