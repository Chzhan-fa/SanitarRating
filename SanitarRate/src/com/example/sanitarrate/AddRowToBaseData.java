package com.example.sanitarrate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddRowToBaseData extends Activity implements OnClickListener {
	
	EditText editRoom;
	EditText editRate;
	Button back;
	Button add;
	Button excel;
	TextView tvInfo;

	final String myTag = "DocsUpload";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addrow);
		add = (Button) findViewById(R.id.badd);
		add.setOnClickListener(this);
		back = (Button) findViewById(R.id.bback);
		back.setOnClickListener(this);
		editRoom = (EditText) findViewById(R.id.editRoom);
		editRate = (EditText) findViewById(R.id.editRate);
	}

	// Click Handler
	@Override
	public void onClick(View arg0) {
		//Intent watch = new Intent(getBaseContext(), WatchBaseData.class);
		switch (arg0.getId()) {
		case R.id.badd:
			AddToDB();
			break;
		case R.id.bback:
			finish();
			break;
		default:
			break;
		}

	}

	// Add to db func
	private void AddToDB() {
		boolean worked = false;
		try {
			String room = editRoom.getText().toString();
			String rate = editRate.getText().toString();
			Intent watch = new Intent(getBaseContext(), WatchBaseData.class);
			int rates = Integer.parseInt(rate);
			if (rates > 0 && rates < 6 && room != "") {
				worked = true;
				DataBaseHelper entry = new DataBaseHelper(this);
				entry.Open();
				entry.AddRow(room, rates);
				entry.CloseDb();
				startActivity(watch);
				finish();
			} else
				showDialog("Ïîæàëóéñòà ââåäèòå öèôğó îò 1 äî 5");
		} catch (Exception e) {
			worked = false;
		} finally {
			if (worked) {
				showDialog("Äåéñòâèå óñïåøíî âûïîëíåíî");
			}
		}
	}

	// Show dialog func
	private void showDialog(String s) {
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
	}
}
