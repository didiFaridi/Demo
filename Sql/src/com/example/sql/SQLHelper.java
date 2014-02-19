package com.example.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_BAR = "barcode";
	public static final String KEY_NAME = "items";
	public static final String KEY_PRICE = "price";

	private static final String DATABASE_NAME = "dummy";
	private static final String DATABASE_TABLE = "dummy";
	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private SQLiteDatabase ourDatabase;
	private final Context ourContext;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_BAR
					+ " TEXT NOT NULL, " + KEY_NAME + " TEXT NOT NULL, "
					+ KEY_PRICE + " TEXT NOT NULL);");
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public SQLHelper(Context c) {
		ourContext = c;
	}

	public SQLHelper open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public long createEntry(String bar, String name, String price) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_BAR, bar);
		cv.put(KEY_NAME, name);
		cv.put(KEY_PRICE, price);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public String getData() {
		String[] columns = new String[] { KEY_ROWID, KEY_BAR, KEY_NAME,
				KEY_PRICE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		String result = "";

		int id = c.getColumnIndex(KEY_ROWID);
		int bar = c.getColumnIndex(KEY_BAR);
		int name = c.getColumnIndex(KEY_NAME);
		int price = c.getColumnIndex(KEY_PRICE);
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(id) + " " + c.getString(bar)
					+ " " + c.getString(name) + " " + c.getString(price) + "\n";
		}

		return result;
	}
}
