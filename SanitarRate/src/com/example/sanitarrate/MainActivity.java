package com.example.sanitarrate;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button viewButton = (Button) findViewById(R.id.view_button);
		viewButton.setOnClickListener(this);
		Button addButton = (Button) findViewById(R.id.add_button);
		addButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		
			case R.id.view_button: {
				Intent watch = new Intent(getBaseContext(), WatchBaseData.class);
				startActivity(watch);
				break;
			}
			case R.id.add_button: {
				Intent add = new Intent(getBaseContext(), AddRowToBaseData.class);
				startActivity(add);
				break;
			}
			default:
				break;
		}
		
	}

}
