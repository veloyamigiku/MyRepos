package jp.co.myself.myselfcommon.listener;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * (共通)位置リスナーです。
 */
abstract public class DagLocationListener implements LocationListener {
	
	/**
	 * 位置情報取得状態です。
	 */
	private boolean getStatus = false;
	
	/**
	 * 位置情報取得時に実行します。
	 */
	private void execAtLast() {
		getStatus = true;	
	}
	
	/**
	 * 位置情報取得時に実行する処理です。
	 * @param location
	 */
	abstract public void execAtLocationChanged(Location location);
	
	/**
	 * プロバイダーの状態が変化したときに実行する処理です。
	 * @param provider
	 * @param status
	 * @param extras
	 */
	abstract public void execStatusChanged(String provider, int status, Bundle extras);
	
	/**
	 * プロバイダーが利用可能になったときに実行する処理です。
	 * @param provider
	 */
	abstract public void execProviderEnabled(String provider);
	
	/**
	 * プロバイダーが利用不可になったときに実行する処理です。
	 * @param provider
	 */
	abstract public void execProviderDisabled(String provider);
	
	public void onLocationChanged(Location location) {
		
		execAtLocationChanged(location);
		
		execAtLast();
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		execStatusChanged(provider, status, extras);
	}

	public void onProviderEnabled(String provider) {
		execProviderEnabled(provider);
	}

	public void onProviderDisabled(String provider) {
		execProviderDisabled(provider);
	}
	
	/**
	 * 以下のメソッドは、上記メンバー変数のゲッター・セッターです。
	 */
	public boolean isGetStatus() {
		return getStatus;
	}

	public void setGetStatus(boolean getStatus) {
		this.getStatus = getStatus;
	}
	
}
