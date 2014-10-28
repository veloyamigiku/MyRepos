package jp.co.myself.myselfcommon.db.dao;

import jp.co.myself.myselfcommon.db.MyselfOpenHelper;
import jp.co.myself.myselfcommon.db.entity.BaseEntity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class BaseDao<T extends BaseEntity> {
	
	private String tableName;
	
	protected SQLiteDatabase readableDb;
	protected SQLiteDatabase writableDb;
	
	public BaseDao(MyselfOpenHelper moh, String tableName) {
		this.readableDb = moh.getReadableDatabase();
		this.writableDb = moh.getWritableDatabase();
		this.tableName = tableName;
	}
	
	public long insert(T e) {
		ContentValues values = getContentValues(e);
		return this.writableDb.insert(tableName, null, values);
	}
	
	public int update(T e) {
		ContentValues values = getContentValues(e);
		String whereClause = BaseEntity.ROWID + "=" + e.getRowId();
		return this.writableDb.update(tableName, values, whereClause, null);
	}
	
	public int delete(T e) {
		String whereClause = BaseEntity.ROWID + "=" + e.getRowId();
		return this.writableDb.delete(this.tableName, whereClause, null);
	}
	
	abstract protected ContentValues getContentValues(T e);
	
	abstract protected T fromCursorToEntity(Cursor c);
}
