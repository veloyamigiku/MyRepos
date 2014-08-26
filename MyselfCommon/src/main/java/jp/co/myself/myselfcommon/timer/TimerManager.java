package jp.co.myself.myselfcommon.timer;

import java.util.Timer;
import java.util.TimerTask;

import jp.co.myself.myselfcommon.exception.CommonException;

/**
 * タイマー管理クラスです。
 */
public class TimerManager {
	
	/**
	 * タイマーオブジェクトです。
	 */
	private Timer timer = null;
	
	/**
	 * タイマーを初期化します。
	 * @param timerTask
	 * @param interval
	 * @throws CommonException 
	 */
	public void initTimer(TimerTask timerTask, long delay, long period) {
		clearTimer();
		
		if (timerTask != null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(timerTask, delay, period);
		}
		
	}
	
	/**
	 * タイマーをキャンセルします。
	 */
	public void clearTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	
	/**
	 * タイマーの起動状態を返却します。
	 * @return boolean
	 */
	public boolean isTimerOn() {
		if (timer == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 以下のメソッドは、上記メンバー変数のゲッター・セッターです。
	 */
	public Timer getTimer() {
		return timer;
	}
	
}
