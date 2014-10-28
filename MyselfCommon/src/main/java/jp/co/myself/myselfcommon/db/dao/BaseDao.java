package jp.co.myself.myselfcommon.db.dao;

import jp.co.myself.myselfcommon.db.entity.BaseEntity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class BaseDao<T extends BaseEntity> {
	
	private String tableName;
	
	private SQLiteDatabase db;
	
	public BaseDao(SQLiteDatabase db, String tableName) {
		this.db = db;
		this.tableName = tableName;
	}
	
	public long insert(T e) {
		ContentValues values = getContentValues(e);
		return this.db.insert(tableName, null, values);
	}
	
	public int update(T e) {
		ContentValues values = getContentValues(e);
		String whereClause = BaseEntity.ROWID + "=" + e.getRowId();
		return this.db.update(tableName, values, whereClause, null);
	}
	
	public int delete(T e) {
		String whereClause = BaseEntity.ROWID + "=" + e.getRowId();
		return this.db.delete(this.tableName, whereClause, null);
	}
	
	abstract protected ContentValues getContentValues(T e);
	
	abstract protected T fromCursorToEntity(Cursor c);
}
