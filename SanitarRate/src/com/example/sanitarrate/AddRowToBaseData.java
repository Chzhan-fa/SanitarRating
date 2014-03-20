package com.example.sanitarrate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddRowToBaseData extends Activity implements OnClickListener {

	EditText editRoom;
	EditText editRate;
	Button back;
	Button add;

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

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.addrow, menu);
		return true;
	}*/

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.badd:
			addToDB();

			break;

		case R.id.bback:
			finish();
			break;
		default:
			break;

		}

	}

	private void addToDB() {
		boolean worked = false;
		try {
			String room = editRoom.getText().toString();
			String rate = editRate.getText().toString();
			Intent watch = new Intent(getBaseContext(), WatchBaseData.class);
			int rates = Integer.parseInt(rate);
			if (rates > 0 && rates < 6 && room != "") {
				worked = true;
				DataBaseHelper entry = new DataBaseHelper(this);
				entry.open();
				entry.AddRow(room, rates);
				entry.CloseDb();
				startActivity(watch);
			} else
				showDialog("���������� ������� ����� �� 1 �� 5");
		} catch (Exception e) {
			worked = false;
		} finally {
			if (worked) {
				/*
				 * Dialog d = new Dialog(this); d.setTitle("Added to Database");
				 * TextView tv = new TextView(this);
				 * tv.setText("Entry successfully added to DataBase");
				 * d.setContentView(tv); d.show();
				 */
				showDialog("�������� ������� ���������");

			}
		}

	}

	private void showDialog(String s) {
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
	}

}
