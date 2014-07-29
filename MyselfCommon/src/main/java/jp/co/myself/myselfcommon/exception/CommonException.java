package jp.co.myself.myselfcommon.exception;

/**
 * (共通)例外クラスです。
 */
public class CommonException extends Exception {

	/**
	 * シリアルバージョンです。
	 */
	private static final long serialVersionUID = -5530186977295622558L;
	
	private Exception e;
	
	public CommonException(Exception e) {
		this.setE(e);
	}

	public Exception getE() {
		return e;
	}

	public void setE(Exception e) {
		this.e = e;
	}
	
}
