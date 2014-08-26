package jp.co.myself.myselfcommon.manager;

import java.util.ArrayList;

import android.app.Activity;

/**
 * アプリケーションのアクティビティを管理するクラスです。
 */
public class ActivityManager {
	
	/**
	 * アクティビティのリストです。
	 */
	ArrayList<Activity> activityList = null;
	
	/**
	 * コンストラクタです。
	 */
	public ActivityManager() {
		activityList = new ArrayList<Activity>();
	}
	
	/**
	 * 指定したクラスのアクティビティをリストから取得します。
	 * リストに存在しない場合はnullを返却します。
	 * @return　Activity
	 */
	public Activity getActivityByClass(Class<?> cls) {
		for (int i = 0; i < activityList.size(); i++) {
			Activity a = activityList.get(i);
			if (a != null && a.getClass() == cls) {
				return a;
			}
		}
		return null;
	}
	
	/**
	 * リストにアクティビティを追加します。
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		if (activityList.contains(activity)) {
			return;
		} else {
			activityList.add(activity);
		}
	}
	
	/**
	 * リストからアクティビティを削除します。
	 * @param activity
	 */
	public void removeActivity(Activity activity) {
		if (activityList.contains(activity)) {
			activityList.remove(activity);
		}
	}
	
}
