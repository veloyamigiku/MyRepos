package jp.co.myself.myselfcommon.db.entity;

public class BaseEntity {
	
	private int rowId;
	public static final String ROWID = "rowid";
	
	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	
}
