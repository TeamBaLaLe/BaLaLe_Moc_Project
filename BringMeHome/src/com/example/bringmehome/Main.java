package com.example.bringmehome;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;

import com.example.bringmehome.listeners.ButtonBringMeHomeListener;
import com.example.bringmehome.listeners.ButtonConfigListener;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class Main extends Activity {

	private Button bringMeHome;
	private Button configuration;
	private int mapsAvailable;
	private Spinner travelType;
	
	public Spinner getTravelType() {
		return travelType;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// CHECK IF MAPS is available
		mapsAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		Log.d("MyActivity", "GooglePlayServicesUtil.isGooglePlayServicesAvailable returns: " + Integer.valueOf(mapsAvailable).toString());
		
		configuration = (Button) this.findViewById(R.id.setupButton);
		configuration.setOnClickListener(new ButtonConfigListener(this));
		
		travelType = (Spinner) this.findViewById(R.id.travelType);
		
		bringMeHome = (Button) this.findViewById(R.id.bringmehomeButton);
		bringMeHome.setOnClickListener(new ButtonBringMeHomeListener(this));
		
		enableButton();
		
	}
	
	private void enableButton() {
		// Read saved dst-Position
		SharedPreferences sharedPrefs = this.getSharedPreferences("bmh_share", Context.MODE_PRIVATE);
		
		double latitude = Double.longBitsToDouble(sharedPrefs.getLong("bmh_dstLatitude", 0));
		double longitude = Double.longBitsToDouble(sharedPrefs.getLong("bmh_dstLongitude", 0));
		if (latitude != 0 && longitude != 0) {
			bringMeHome.setEnabled(true);
		} else {
			bringMeHome.setEnabled(false);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		enableButton();
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

}
