package com.example.bringmehome;

import com.example.bringmehome.listeners.ButtonBringMeHomeListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.br;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;

public class Main extends Activity {

	private Button bringMeHome;
	private int mapsAvailable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// CHECK IF MAPS is available
		mapsAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		Log.d("MyActivity", "GooglePlayServicesUtil.isGooglePlayServicesAvailable returns: " + Integer.valueOf(mapsAvailable).toString());
		
		bringMeHome = (Button) this.findViewById(R.id.bringmehomeButton);
		bringMeHome.setOnClickListener(new ButtonBringMeHomeListener(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
