package com.example.sanitarrate;

import java.util.Iterator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import android.content.ContentValues;

public class Excel2SQLiteHelper {
	public static void InsertExcelToSqlite(DataBaseHelper dbAdapter, Sheet sheet) {
		for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
			Row row = rit.next();
			ContentValues values = new ContentValues();
				values.put(DataBaseHelper.KEY_ROWID, row.getCell(0).getStringCellValue());
				values.put(DataBaseHelper.KEY_ROOM, row.getCell(1).getStringCellValue());
				values.put(DataBaseHelper.KEY_RATE, row.getCell(2).getStringCellValue());
			dbAdapter.Insert(DataBaseHelper.DATABASE_TABLE, values);
		}
	}
}
