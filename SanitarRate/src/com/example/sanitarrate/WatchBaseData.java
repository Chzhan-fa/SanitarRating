package com.example.sanitarrate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class WatchBaseData extends Activity implements OnClickListener {
	// Value of text size
	int TEXT_SIZE = 23;              
	// Describe menu
	public static final int MENU_ADD = Menu.FIRST;
	public static final int MENU_DELETE = Menu.FIRST + 1;
	// Describe db
	DataBaseHelper dbconnect;
	OpenFileDialog cur;
	ArrayList<String> globalArycolm = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_watch);	
		Button importButton = (Button) findViewById(R.id.import_but);
		importButton.setOnClickListener(this);
		Button exportButton = (Button) findViewById(R.id.export_but);
		exportButton.setOnClickListener(this);
	}

	private void collectData() {                                                       // List of rooms with rating
		dbconnect = new DataBaseHelper(this);
		dbconnect.Open();
		ArrayList<String> list = dbconnect.GetDBContent();
		int leng = list.size();
		dbconnect.CloseDb();
		int it = 0;
		TableLayout rate_table = (TableLayout) findViewById(R.id.rate_table);
		rate_table.removeAllViews();
		while (it < leng) {
			TableRow tr = new TableRow(this);
			//tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 80));
			int id = Integer.parseInt(list.get(it));
			tr.setId(id);
			tr.setOnClickListener(this);
			
			
			TextView key = new TextView(this);
			key.setText(list.get(it++));
			key.setTextSize(TEXT_SIZE);
			//Str.addView(key);
			
			
			TextView room = new TextView(this);
			room.setText(list.get(it++));
			room.setTextSize(TEXT_SIZE);
			tr.addView(room);
			
			
			TextView rate = new TextView(this);
			rate.setText(list.get(it++));
			rate.setTextSize(TEXT_SIZE);
			rate.setGravity(Gravity.RIGHT);
			tr.addView(rate);
			
			
			View v = new View(this);
			v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
			v.setBackgroundColor(Color.rgb(0, 0, 0));
			rate_table.addView(tr);
			rate_table.addView(v);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		collectData();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {                             // Menu in action bar
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
	    menu.add(Menu.NONE, MENU_ADD, Menu.NONE, "Добавить комнату");
	    menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Удалить данные");
	    return true;
	}
	
	public void OnOpenFileClick() {
		// TODO: FILE DIALOG
		OpenFileDialog fileDialog = new OpenFileDialog(this)
		.setFilter(".*\\.xls")
        .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
            @Override
            public void OnSelectedFile(String fileName) {
                //Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_LONG).show();
            	importExcel2Sqlite(fileName);
            }
        });
		fileDialog.show();
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {                        // Click handler
		switch(item.getItemId()) {
        	case MENU_ADD:
        		Intent add = new Intent(getBaseContext(), AddRowToBaseData.class);
        		startActivity(add);
        		finish();
        		return true;
        	case MENU_DELETE:
        		DeleteListOfRooms();
        		Intent watch3 = getIntent();
            	finish();
				startActivity(watch3);
        	default:
        		return super.onOptionsItemSelected(item);
        }
    }

	@Override
	public void onClick(View arg0) {	                                // Click handler
		switch(arg0.getId()){
		case R.id.import_but: {
			//importExcel2Sqlite();
        	//Intent watch = getIntent();
        	//finish();
			//startActivity(watch);
			OnOpenFileClick();
			break;
		}
		case R.id.export_but: {
			exportSqlite2Excel();
    		Intent watch2 = getIntent();
    		finish();
    		startActivity(watch2);
			break;
		}
		default:
			int id = arg0.getId();
			TableRow tr = (TableRow) arg0;
			TextView room = (TextView) tr.getChildAt(0);
			String sroom = room.getText().toString();
			TextView rate = (TextView) tr.getChildAt(1);
			String srate=rate.getText().toString();
			Intent i = new Intent(this, UpdateBaseData.class);
			i.putExtra("id", id);
			i.putExtra("room", sroom);
			i.putExtra("rate", srate);
			startActivity(i);	
			break;
		}
			
	}
	
	
	private void showDialog(String s) {
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
	}
	
	
	// ----------- Import from xls ----------//
	private void importExcel2Sqlite(String fileName) {
		
		Workbook wb = null;                                              // Create a workbook
		try {
			//inStream = openFileInput(fileName);                          // Name of table
			FileInputStream inStream = new FileInputStream (new File(fileName));
			wb = new HSSFWorkbook(inStream);                         
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		DataBaseHelper dbAdapter = new DataBaseHelper(this);
		Sheet sheet1 = wb.getSheetAt(0);
		if (sheet1 == null) {
			return;
		}
		dbAdapter.Open();
		Excel2SQLiteHelper.InsertExcelToSqlite(dbAdapter, sheet1);
		dbAdapter.CloseDb();
		showDialog("Импорт завершен");
		Intent watch = getIntent();
    	finish();
    	startActivity(watch);
	}
	

	// ----------- Export to xls ----------//
	public void exportSqlite2Excel() {
		dbconnect = new DataBaseHelper(this);    

		
		dbconnect.Open();                             // Connect to db
		ArrayList<String> aryList = dbconnect.GetColumn();      // Get list of rooms
		globalArycolm = aryList;
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet firstSheet = workbook.createSheet("Rooms");
		
		// Create a title of table
		HSSFRow rowA = firstSheet.createRow(0);                 // Create a row
		for(int i = 0; i < globalArycolm.size(); i++) {         // Create a cell
			HSSFCell cellA = rowA.createCell(i);
			//rowA.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
			cellA.setCellValue(new HSSFRichTextString("" + globalArycolm.get(i)));
		}
	
		// Fill cells in table
		Cursor cursor = dbconnect.ExportAllItems();           // Export data from db to rows and cells
		cursor.moveToFirst();
		int n = 1;
		while(!cursor.isAfterLast()) {
			HSSFRow rowA2 = firstSheet.createRow(n);
			for(int j = 0; j < globalArycolm.size(); j++) {
				HSSFCell cellA = rowA2.createCell(j);
				//rowA.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
				cellA.setCellValue(new HSSFRichTextString(cursor.getString(j)));
			}
			n++;
			cursor.moveToNext();
		}
		dbconnect.CloseDb();                                // Close connection to db

		
		FileOutputStream fos = null;
			try {
				String str_path = Environment.getExternalStorageDirectory().toString();          // Way for xls file
		        File file;
		                file = new File(str_path, "OUT.xls");  
		                fos = new FileOutputStream(file);
		                workbook.write(fos);                                                     // Write data in xls
		    }
		    catch (IOException e) {
		    	e.printStackTrace();
		    }
		    finally {
		        if (fos != null) {
		        	try 
		            {
		        		fos.flush();
		                fos.close();
		            }
		            catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		        showDialog("Экспорт завершен");
		    }
	 }


	// ----------- Delete list ----------//
	private void DeleteListOfRooms() {                 
	try {
		dbconnect = new DataBaseHelper(this);
		dbconnect.Open();
		dbconnect.DeleteTable();                                 // Delete table
		dbconnect.CloseDb();
		showDialog("Таблица удалена");
		} 
		catch (Exception e) {
			showDialog(e.toString());
		}
	}
	
	
}	
	
