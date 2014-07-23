package jp.co.myself.myselfcommon;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 強制終了をハンドリングするクラスです。
 */
public class CrashReportUtility {
	
	/**
	 * 強制終了時の情報を保管するプリファレンスの名前です。
	 */
	private static final String PREF_NAME = "crashLog";
	
	/**
	 * 強制終了時の情報を保管するプリファレンスのキー名です。
	 */
	private static final String PREF_KEY_NAME = "crashLog";
	
	/**
	 * 強制終了時のデフォルト例外ハンドラーです。
	 */
	private static final UncaughtExceptionHandler DEFAULT_HANDLER = Thread.getDefaultUncaughtExceptionHandler();
	
	/**
	 * 強制終了時の例外ハンドラーに、強制終了時の情報を記録するハンドラーを設定します。
	 * @param context
	 */
	public static void setCrashReportHandler(final Context context) {
		Thread.setDefaultUncaughtExceptionHandler(new CrashReportHandler(context));
	}
	
	/**
	 * 強制終了時のデフォルト例外ハンドラーの拡張版です。
	 */
	private static final class CrashReportHandler implements UncaughtExceptionHandler {
		
		/**
		 * コンテキストです。
		 */
		private final Context applicationContext;
		
		private CrashReportHandler(final Context applicationContext) {
			this.applicationContext = applicationContext;
		}
		
		/**
		 * 強制終了時に実行する処理です。
		 */
		public void uncaughtException(Thread thread, Throwable ex) {
			// 強制終了時のスタックトレースを取得します。
			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			
			// 取得したスタックトレースを、プリファレンスに保存します。
			final SharedPreferences preference = applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
			final Editor editor = preference.edit();
			editor.putString(PREF_KEY_NAME, sw.toString());
			editor.commit();
			
			DEFAULT_HANDLER.uncaughtException(thread, ex);
		}
		
	}
	
}
