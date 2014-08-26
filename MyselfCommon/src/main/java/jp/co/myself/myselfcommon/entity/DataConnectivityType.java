package jp.co.myself.myselfcommon.entity;

/**
 * データ通信の接続タイプクラスです。
 */
public class DataConnectivityType {
	
	/**
	 * 各プロパティの初期値です。
	 */
	private static final int INITIAL_VALUE = -1;
	
	/**
	 * 回線接続のタイプです。
	 */
	private int type;
	
	/**
	 * 回線接続のサブタイプです。
	 */
	private int subType;
	
	/**
	 * コンストラクターです。
	 */
	public DataConnectivityType() {
		type = INITIAL_VALUE;
		subType = INITIAL_VALUE;
	}
	
	/**
	 * 以降のメソッドは、上記メンバー変数のゲッター・セッターです。
	 * @return
	 */
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}
	
}
