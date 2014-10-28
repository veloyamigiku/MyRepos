package jp.co.myself.myselfcommon.db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyselfOpenHelper extends SQLiteOpenHelper {
	
	private String initSqlFile;
	private Context context;
	
	public MyselfOpenHelper(Context context, String dbName, String initString) {
		super(context, dbName, null, 1);
		this.context = context;
		this.initSqlFile = initString;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		BufferedReader brInitSqlFile = null;
		try {
			AssetManager manager = this.context.getResources().getAssets();
			brInitSqlFile = new BufferedReader(new InputStreamReader(manager.open(this.initSqlFile), StandardCharsets.UTF_8));
			for (String sqlLine; (sqlLine = brInitSqlFile.readLine()) != null;) {
				db.execSQL(sqlLine);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (brInitSqlFile != null) {
				try {
					brInitSqlFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
