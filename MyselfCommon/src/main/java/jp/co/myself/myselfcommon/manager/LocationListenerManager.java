package jp.co.myself.myselfcommon.manager;

import java.util.HashMap;
import java.util.Map.Entry;

import jp.co.myself.myselfcommon.listener.DagLocationListener;
import jp.co.myself.myselfcommon.listener.LocationListenerManagerListener;
import android.content.Context;
import android.location.GpsStatus;
import android.location.LocationManager;

/**
 * 位置リスナーの管理クラスです。
 */
public class LocationListenerManager {
	
	/**
	 * 位置情報取得ステータスです。
	 */
	private boolean isOn = false;
	
	/**
	 * コンテキストです。
	 */
	private Context applicationContext;
	
	/**
	 * ロケーションリスナのリストです。
	 */
	private HashMap<String, DagLocationListener> locationListenerMap = null;
	
	/**
	 * GPS状態リスナーです。
	 */
	private GpsStatus.Listener gpsStatusListener = null;
	
	/**
	 * 本マネージャで位置情報取得時もしくは、タイムアウト時の委譲先です。
	 */
	private LocationListenerManagerListener locationListenerManagerListener = null;
	
	/**
	 * タイムアウトまでにスリープする回数です。
	 */
	private static final int SLEEP_COUNT = 300;
	
	/**
	 * スリープのミリ秒です。
	 */
	private static final int SLEEP_TIME = 100;
	
	/**
	 * コンストラクタです。
	 * 
	 * @param locationListener
	 */
	public LocationListenerManager(Context applicationContext) {
		this.applicationContext = applicationContext;
		this.locationListenerMap = new HashMap<String, DagLocationListener>();
	}
	
	/**
	 * ロケーションリスナのリストに新しいリスナーを追加します。
	 * @param locationListener
	 */
	public void addLocationListener(String locationProvider, DagLocationListener locationListener) {
		// リストでは「各プロバイダー」につき、「1つのリスナー」だけ追加します。
		if (!locationListenerMap.containsKey(locationProvider)) {
			locationListenerMap.put(locationProvider, locationListener);
		}
	}
	
	/**
	 * ロケーションリスナのリストから指定のリスナを削除します。
	 * @param locationListener
	 */
	public void removeLocationListener(String locationProvider) {
		locationListenerMap.remove(locationProvider);
	}
	
	/**
	 * 位置情報取得を開始します。
	 */
	public void startGetLocation() {
		
		// リストにリスナーが一つもない場合は、ここで終了します。
		if (locationListenerMap.size() == 0) {
			return;
		}
		
		LocationManager locationManager = (LocationManager) applicationContext.getSystemService(Context.LOCATION_SERVICE);
		
		for (Entry<String, DagLocationListener> e : locationListenerMap.entrySet()) {
			String locationProvider = e.getKey();
			DagLocationListener locationListener = e.getValue();
			
			if (locationProvider != null && locationListener != null) {
				
				locationListener.setGetStatus(false);
				locationManager.requestSingleUpdate(locationProvider, locationListener, null);
				
				if (LocationManager.GPS_PROVIDER.equals(locationProvider)) {
					if (gpsStatusListener != null) {
						locationManager.addGpsStatusListener(gpsStatusListener);
					}
				}
			}
			
		}
		
		isOn = true;
		
		watchLocationGetStatusForTimeout();
	}
	
	/**
	 * 位置情報取得を停止します。
	 */
	public void stopGetLocation() {
		
		LocationManager locationManager = (LocationManager) applicationContext.getSystemService(Context.LOCATION_SERVICE);
		
		for (Entry<String, DagLocationListener> e : locationListenerMap.entrySet()) {
			String locationProvider = e.getKey();
			DagLocationListener locationListener = e.getValue();
			
			if (locationProvider != null && locationListener != null) {
				
				locationManager.removeUpdates(locationListener);
				
				if (LocationManager.GPS_PROVIDER.equals(locationProvider)) {
					if (gpsStatusListener != null) {
						locationManager.removeGpsStatusListener(gpsStatusListener);
					}
				}
			}
			
		}
		
		isOn = false;
	}
	
	/**
	 * 一定時間、各リスナーの位置情報取得フラグを監視する。
	 * タイムアウトもしくは、位置情報取得した場合は、後処理後に終了。
	 */
	private void watchLocationGetStatusForTimeout() {
		
		new Thread(new Runnable()  {
			
			public void run() {
				for (int i = 0; i < SLEEP_COUNT; i++) {
					try {
						boolean getStatus = true;
						for (Entry<String, DagLocationListener> e : locationListenerMap.entrySet()) {
							DagLocationListener listener = e.getValue();
							String provider = e.getKey();
							if (listener.isGetStatus()) {
								// GPSで位置情報を取得した場合の処理です。
								if (LocationManager.GPS_PROVIDER.equals(provider) && listener.isGetStatus()) {
									break;
								}
							} else {
								getStatus = false;
							}
						}
						// 全部リスナで位置情報を取得済みの場合の処理です。
						if (getStatus) {
							break;
						}
						Thread.sleep(SLEEP_TIME);
					} catch (InterruptedException e) {
					}
				}
				
				// 本クラスで位置情報取得した後の処理を委譲します。
				if (locationListenerManagerListener != null) {
					locationListenerManagerListener.onGetLocation(locationListenerMap);
				}
				
				// 位置情報取得を停止します。
				stopGetLocation();
				
			}
		}).start();

	}
	
	/**
	 * 以下のメソッドは、上記メンバ変数のゲッター・セッターです。
	 */
	public boolean isOn() {
		return isOn;
	}

	public void setGpsStatusListener(GpsStatus.Listener gpsStatusListener) {
		this.gpsStatusListener = gpsStatusListener;
	}

	public void setLocationListenerManagerListener(
			LocationListenerManagerListener locationListenerManagerListener) {
		this.locationListenerManagerListener = locationListenerManagerListener;
	}
	
}
