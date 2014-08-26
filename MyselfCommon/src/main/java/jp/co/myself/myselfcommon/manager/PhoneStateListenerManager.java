package jp.co.myself.myselfcommon.manager;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * 電話状態リスナーを管理するクラスです。
 */
public class PhoneStateListenerManager {
	
	/**
	 * コンテキストです。
	 */
	private Context applicationContext;
	
	/**
	 * 電話状態リスナーです。
	 */
	private PhoneStateListener phoneStateListener;
	
	/**
	 * リッスン番号です。
	 */
	private int listenValue;
	
	/**
	 * 電話状態監視のステータスです。
	 */
	private boolean isOnPhoneStateListener = false;
	
	/**
	 * コンストラクタです。
	 * @param applicationContext
	 */
	public PhoneStateListenerManager(Context applicationContext, PhoneStateListener phoneStateListener, int listenValue) {
		this.applicationContext = applicationContext;
		this.phoneStateListener = phoneStateListener;
		this.listenValue = listenValue;
	}
	
	/**
	 * 電話状態の監視を始めます。
	 */
	public void registerPhoneStateListener() {
		if (applicationContext != null && phoneStateListener != null) {
			TelephonyManager telephoneManager = (TelephonyManager) applicationContext.getSystemService(Context.TELEPHONY_SERVICE);
			telephoneManager.listen(phoneStateListener, listenValue);
			isOnPhoneStateListener = true;
		}
	}
	
	/**
	 * 電話状態の監視を停止します。
	 */
	public void unregisterPhoneStateListener() {
		if (applicationContext != null && phoneStateListener != null) {
			TelephonyManager telephoneManager = (TelephonyManager) applicationContext.getSystemService(Context.TELEPHONY_SERVICE);
			telephoneManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
			isOnPhoneStateListener = false;
		}
	}
	
	/**
	 * 以降のメソッドは、上記メンバ変数のゲッター・セッターです。
	 */
	public boolean isOnPhoneStateListener() {
		return isOnPhoneStateListener;
	}
	
}
