package jp.co.myself.myselfcommon.db.dao;

import java.util.ArrayList;

import jp.co.myself.myselfcommon.db.entity.BaseEntity;
import jp.co.myself.myselfcommon.db.entity.SampleEntity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SampleDao extends BaseDao<SampleEntity> {
	
	private static final String FIND_ALL_SQL = "select " + BaseEntity.ROWID + ",* from " + SampleEntity.TABLE_NAME + ";";
	
	private SQLiteDatabase db;
	
	public SampleDao(SQLiteDatabase db) {
		super(db, SampleEntity.TABLE_NAME);
		this.db = db;
	}
	
	public ArrayList<SampleEntity> findAll() {
		
		ArrayList<SampleEntity> result = new ArrayList<SampleEntity>();
		Cursor c = db.rawQuery(FIND_ALL_SQL, null);
		while (c.moveToNext()) {
			result.add(fromCursorToEntity(c));
		}
		return result;
	}
	
	@Override
	protected ContentValues getContentValues(SampleEntity e) {
		ContentValues values = new ContentValues();
		values.put(SampleEntity.ID, e.getId());
		values.put(SampleEntity.NAME, e.getName());
		return values;
	}

	@Override
	protected SampleEntity fromCursorToEntity(Cursor c) {
		
		SampleEntity e = new SampleEntity();
		e.setId(c.getInt(c.getColumnIndex(SampleEntity.ID)));
		e.setName(c.getString(c.getColumnIndex(SampleEntity.NAME)));
		e.setRowId(c.getInt(c.getColumnIndex(SampleEntity.ROWID)));
		return e;
	}
	
}
