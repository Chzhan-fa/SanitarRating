package com.example.sanitarrate;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper {
	
	public static final String KEY_ROWID = "_id";              // id
	public static final String KEY_ROOM = "room";              // Номер комнаты
	public static final String KEY_RATE = "rate";              // Оценка
	
	public static final String DATABASE_NAME = "SanitarRate";  // Имя БД
	public static final String DATABASE_TABLE = "List";        // Вид БД
	public static final int DATABASE_VERSION = 1;              // Версия БД
	
	private final Context ourContext;
	private DBHelper ourHelper;
	private SQLiteDatabase ourDatabase;
	
	private static class DBHelper extends SQLiteOpenHelper {
		// Конструктор класса DBHelper
		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}
		// Функция запуска
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE table " + DATABASE_TABLE + " (" + KEY_ROWID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ROOM
				+ " TEXT NOT NULL, " + KEY_RATE + " Integer);"
		    );
		}
		// Функция обновления БД
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP table if exists " + DATABASE_TABLE);
			onCreate(db);
		}
	}
	// Конструкторы класса DataBaseHelper
	public DataBaseHelper(Context context) {
		ourContext = context;
	}
	// Функция открытия БД
	public DataBaseHelper open() throws SQLException {
		ourHelper = new DBHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	// Функция закрытия БД
	public void CloseDb() {
		ourHelper.close();
	}
	// Функция добавления строки в БД
	public long AddRow(String name, int rate) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_ROOM, name);
		cv.put(KEY_RATE, rate);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}
	// Список результатов
	public ArrayList<String> GetDBContent() {
		ArrayList<String> list = new ArrayList<String>();
		String[] columns = new String[] { KEY_ROWID, KEY_ROOM, KEY_RATE };
		Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns, null, null,
				null, null, KEY_RATE + " desc");

		int irow = cursor.getColumnIndex(KEY_ROWID);
		int iroom = cursor.getColumnIndex(KEY_ROOM);
		int irate = cursor.getColumnIndex(KEY_RATE);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			String id = cursor.getString(irow);
			String room = cursor.getString(iroom);
			String rate = cursor.getString(irate);
			list.add(id);
			list.add(room);
			list.add(rate);

		}
		cursor.close();
		return list;
	}
	// Функция обновления строки в БД
	public void UpdateRow(int key, String rate)  throws SQLException {
		ContentValues cvupdate = new ContentValues();
		cvupdate.put(KEY_RATE, rate);
		ourDatabase.update(DATABASE_TABLE, cvupdate, KEY_ROWID + "=" + key, null);	

	}
	// Функция удаления строки в БД
	public void DeleteRow(int key)  throws SQLException {
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + key, null);	
	}	
}