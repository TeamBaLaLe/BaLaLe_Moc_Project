package com.example.bringmehome;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

public class ShowMap extends FragmentActivity {
	private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_showmap);
			Fragment f;
	//		map = ((MapFragment) getSupportFragmentManager().findViewById(R.id.map)).getMap();
			map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	//		Intent intent = getIntent();
	//		String value = intent.getStringExtra("key"); //if it's a string you stored.
		}catch(Exception e) {
			Log.d("MyApplication", e.getMessage());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
