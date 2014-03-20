package com.example.sanitarrate;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class WatchBaseData extends Activity implements OnClickListener {
	// Описание переменных
	int TEXT_SIZE = 25;              
	DataBaseHelper dbconnect;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_watch);		
	}

	private void collectData() {
		dbconnect = new DataBaseHelper(this);
		dbconnect.open();
		ArrayList<String> list = dbconnect.GetDBContent();
		int leng = list.size();
		if(leng == 0) {
			showDialog("База пуста");
			finish();
		}
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
			//tr.addView(key);
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

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_watch, menu);
		return true;
	}*/

	@Override
	public void onClick(View arg0) {	
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
	}
	
	private void showDialog(String s) {
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
	}
}
