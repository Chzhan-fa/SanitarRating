package com.example.sanitarrate;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class WatchBaseData extends Activity implements OnClickListener {
	int TEXT_SIZE=25;
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
		
		if(leng==0) {
			showDialog("Database is empty");
			finish();
		}
		else showDialog("������� �� ������ ��� ��������������/��������");
		
		dbconnect.CloseDb();

		int it = 0;

		TableLayout hotness_table = (TableLayout) findViewById(R.id.rate_table);
		hotness_table.removeAllViews();
		while (it < leng) {
			
			TableRow tr = new TableRow(this);
			//tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 80));
			int id=Integer.parseInt(list.get(it));
			tr.setId(id);
			tr.setOnClickListener(this);
			
			TextView key = new TextView(this);
			key.setText(list.get(it++));
			key.setTextSize(TEXT_SIZE);
			//tr.addView(key);
			
			TextView name = new TextView(this);
			name.setText(list.get(it++));
			name.setTextSize(TEXT_SIZE);
			tr.addView(name);

			TextView hotness = new TextView(this);
			hotness.setText(list.get(it++));
			hotness.setTextSize(TEXT_SIZE);
			hotness.setGravity(Gravity.RIGHT);
			tr.addView(hotness);
			
			//add a small View for lines between rows
			View v = new View(this);
			v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
			v.setBackgroundColor(Color.rgb(0, 0, 0));
			//add the table row and the line to the Table
			hotness_table.addView(tr);
			hotness_table.addView(v);
			
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
		TextView name = (TextView) tr.getChildAt(0);
		String sname=name.getText().toString();
		
		TextView hotness = (TextView) tr.getChildAt(1);
		String shotness=hotness.getText().toString();
		
		Intent i = new Intent(this, UpdateBaseData.class);
		i.putExtra("id", id);
		i.putExtra("name", sname);
		i.putExtra("hotness", shotness);
		startActivity(i);		
	}
	
	private void showDialog(String s) {
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
	}

}
