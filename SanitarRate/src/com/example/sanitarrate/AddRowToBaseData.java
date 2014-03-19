package com.example.sanitarrate;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddRowToBaseData extends Activity implements OnClickListener {

	EditText editName;
	EditText editHotness;
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

		editName = (EditText) findViewById(R.id.editRoom);
		editHotness = (EditText) findViewById(R.id.editRate);
		

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
			String name = editName.getText().toString();
			String hotness = editHotness.getText().toString();
			int hot = Integer.parseInt(hotness);
			if (hot > 0 && hot < 11 && name != "") {
				
				
				worked = true;
				DataBaseHelper entry = new DataBaseHelper(this);

				entry.open();
				entry.AddRow(name, hot);
				entry.CloseDb();
			} else
				showDialog("Пожалуйста введите цифру от 1 до 10");
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
				showDialog("Действие успешно выполнено");

			}
		}

	}

	private void showDialog(String s) {
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();

	}

}
