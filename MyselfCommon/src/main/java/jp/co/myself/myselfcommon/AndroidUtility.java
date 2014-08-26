package jp.co.myself.myselfcommon;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * Androidアプリケーションに関するユーティリティクラスです。
 */
public class AndroidUtility {
	
	/**
	 * AndroidManifest.xmlのandroid.debuggable属性の値をboolean型で返却します。
	 * 以下の条件を満たす場合に、falseを返却します。
	 * ・不正な引数を指定した場合。
	 * ・android.debuggable属性がfalseの場合。
	 * @param applicationContext
	 * @return boolean
	 */
	public static boolean isDebuggable(Context applicationContext) {
		
		// 不正な引数を受け取った場合の処理です。
		if (applicationContext == null) {
			return false;
		}
		
		final PackageManager packageManager = applicationContext.getPackageManager();
		ApplicationInfo applicationInfo = null;
		
		try {
			// android.debuggable属性の値を含む情報を取得します。
			applicationInfo = packageManager.getApplicationInfo(applicationContext.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			return false;
		}
		
		if ((applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 開発アクティビティを呼び出します。
	 * @param applicationContext
	 */
	public static void startActivityOfDevelopmentSettings(Activity activity) {
		
		// 引数が不正な場合は、途中終了します。
		if (activity == null) {
			return;
		}
		
		final Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
		intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings"));
		
		// 開発アクティビティを起動します。
		activity.startActivity(intent);
		
	}
	
}
