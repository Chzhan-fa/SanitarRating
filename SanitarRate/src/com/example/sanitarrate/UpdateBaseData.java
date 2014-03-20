package com.example.sanitarrate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateBaseData extends Activity implements OnClickListener {

	DataBaseHelper helper;
	int key;
	String room;
	String rate;
	EditText editRate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);

		TextView tvname = (TextView) findViewById(R.id.room2);
		editRate = (EditText) findViewById(R.id.editRate);
		Button update = (Button) findViewById(R.id.update);
		update.setOnClickListener(this);
		Button delete = (Button) findViewById(R.id.delete);
		delete.setOnClickListener(this);
		
		Bundle extras = getIntent().getExtras();
		key = extras.getInt("id");
		
		String room = extras.getString("room");
		String rate = extras.getString("rate");

		tvname.setText(room);
		editRate.setText(rate);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_update, menu);
		return true;
	}*/

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.update:
			UpdateRate();
			break;
		case R.id.delete:
			DeleteRoom();
			break;
		default:
			break;
		}
	}

	private void DeleteRoom() {
		try {
			helper = new DataBaseHelper(this);
			helper.open();
			helper.DeleteRow(key);
			helper.CloseDb();
			showDialog("Комната удалена");
			finish();
		} catch (Exception e) {
			showDialog(e.toString());
		}
	}

	private void UpdateRate() {
		try {
			helper = new DataBaseHelper(this);
			helper.open();
			rate = editRate.getText().toString();
			helper.UpdateRow(key, rate);
			helper.CloseDb();
			if (rate != null) {
				showDialog("База данных обновлена");
			}
			else {
				showDialog("null");
			}
			finish();
		} catch (Exception e) {
			showDialog(e.toString());
		}
	}

	private void showDialog(String s) {
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
	}
}
