package jp.co.myself.jcommon.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * (蜈ｱ騾?)譌･莉倥け繝ｩ繧ｹ縺ｧ縺吶??
 */
public class DateUtils {
	
	/**
	 * 迴ｾ蝨ｨ縺ｮ譌･莉俶枚蟄怜??(yyyy_MM_dd)繧定ｿ泌唆縺励∪縺吶??
	 * @return String
	 */
	public static String getCurrentDateString() {
		Date date = new Date();
		return (new SimpleDateFormat("yyyy_MM_dd").format(date));
	}
	
	/**
	 * 迴ｾ蝨ｨ縺ｮ譌･莉俶枚蟄怜??(yyyy_MM_dd HH:mm:ss)繧定ｿ泌唆縺励∪縺吶??
	 * @return String
	 */
	public static String getLogDateString() {
		Date date = new Date();
		return new SimpleDateFormat("yyyy_MM_dd HH:mm:ss").format(date);
	}
	
	/**
	 * 繝溘Μ遘偵ｒ譌･譎よ枚蟄怜??(yyyy_MM_dd HH:mm:ss)縺ｫ螟画鋤縺励※霑泌唆縺励∪縺吶??
	 * @param msec
	 * @return String
	 */
	public static String convertMsecToDateString(long msec) {
		Date date = new Date(msec);
		return new SimpleDateFormat("yyyy_MM_dd HH:mm:ss").format(date);
	}
	
}
