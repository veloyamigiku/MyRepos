package jp.co.myself.myselfcommon.manager;

import jp.co.myself.myselfcommon.exception.CommonException;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;

/**
 * ブルートゥースアダプタを管理するクラスです。
 */
public class BluetoothAdapterManager {
	
	/**
	 * ブルートゥースアダプタのオブジェクトです。
	 */
	private BluetoothAdapter bluetoothAdapter = null;
	
	/**
	 * ビーコン検知時のコールバック実装です。
	 */
	private BluetoothAdapter.LeScanCallback callback = null;
	
	/**
	 * コンテキストです。
	 */
	private Context applicationContext = null;
	
	/**
	 * ビーコン監視のステータスです。
	 * true:監視中、false:待機中
	 */
	private boolean adapterOn = false;
	
	/**
	 * コンストラクタです。
	 */
	public BluetoothAdapterManager(Context applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * ブルートゥースアダプタを初期化します。
	 * @throws CommonException
	 */
	public void init(BluetoothAdapter.LeScanCallback callback) throws CommonException {
		
		if (applicationContext == null) { 
			throw new CommonException(new Exception());
		}
		
		if (callback == null) {
			throw new CommonException(new Exception());
		}
		
		BluetoothManager bluetoothManager = (BluetoothManager) applicationContext.getSystemService(Context.BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();
		
		adapterOn = false;
	}
	
	/**
	 * ビーコン監視を開始します。
	 * @throws CommonException
	 */
	public void start() throws CommonException {
		
		// 既にビーコン監視中の場合は、スキップします。
		if (adapterOn) {
			return;
		}
		
		if (bluetoothAdapter == null || callback == null) {
			throw new CommonException(new Exception());
		}
		if (!bluetoothAdapter.startLeScan(callback)) {
			throw new CommonException(new Exception());
		}
		
		adapterOn = true;
	}
	
	/**
	 * ビーコン監視を停止します。
	 * @throws CommonException
	 */
	public void stop() throws CommonException {
		
		if (!adapterOn) {
			return;
		}
		
		if (bluetoothAdapter == null || callback == null) {
			throw new CommonException(new Exception());
		}
		
		bluetoothAdapter.stopLeScan(callback);
		
		adapterOn = false;
		
	}
	
	/**
	 * 以降のメソッドは、上記メンバー変数のゲッター・セッターです。
	 */
	public void setCallback(BluetoothAdapter.LeScanCallback callback) {
		this.callback = callback;
	}
	
}
