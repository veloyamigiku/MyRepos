package jp.co.myself.myselfcommon.listener;

import java.util.HashMap;

public interface LocationListenerManagerListener {
	
	/**
	 * マネージャで位置情報を取得した時の処理です。
	 * @param listenerList
	 */
	public void onGetLocation(HashMap<String, DagLocationListener> locationListenerMap);
	
}
