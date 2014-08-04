package jp.co.myself.myselfcommon.network;

import jp.co.myself.myselfcommon.entity.DataConnectivityType;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * データ通信接続に関するユーティリティクラスです。
 */
public class DataConnectivityUtility {
	
	/**
	 * データ通信接続の管理オブジェクトです。
	 */
	private static ConnectivityManager connectivityManager = null;
	
	/**
	 * データ通信接続の管理オブジェクトを返却します。
	 * @param applicationContext
	 * @return ConnectivityManager
	 */
	private static ConnectivityManager getConnectivityManager(Context applicationContext) {
		if (connectivityManager == null) {
			connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		return connectivityManager;
	}
	
	/**
	 * 本メソッド実行時のデータ通信接続タイプを返却します。
	 * 以下の場合に、接続タイプを初期値で返却します。
	 * ・不正な引数を指定した場合。
	 * ・データ通信ネットワークに接続していない場合。
	 * @param applicationContext
	 * @return ConnectivityType
	 */
	public static DataConnectivityType getDataConnectivityType(Context applicationContext) {
		
		DataConnectivityType type = new DataConnectivityType();
		
		// メソッドのパラメータが不正な場合の処理です。
		if (applicationContext == null) {
			return type;
		}
		
		ConnectivityManager manager = getConnectivityManager(applicationContext);
		
		if (manager != null) {
			
			NetworkInfo networkInfo = manager.getActiveNetworkInfo();
			
			// データ通信用ネットワークに接続している場合の処理です。
			if (networkInfo != null && networkInfo.isConnected()) {
				type.setType(networkInfo.getType());
				type.setSubType(networkInfo.getSubtype());
			}
			
		}
		
		return type;
	}
	
}
