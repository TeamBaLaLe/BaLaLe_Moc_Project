package com.example.bringmehome.listeners;

import com.example.bringmehome.Main;
import com.example.bringmehome.ShowMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;

public class ButtonBringMeHomeListener implements OnClickListener {

	private Activity starter;
	
	public ButtonBringMeHomeListener(Activity act) {
		this.starter = act;
	}
	
	@Override
	public void onClick(View arg0) {
		Intent myIntent = new Intent(starter, ShowMap.class);
		SharedPreferences sharedPrefs = starter.getSharedPreferences("bmh_share", Context.MODE_PRIVATE);
		double latitude = Double.longBitsToDouble(sharedPrefs.getLong("bmh_dstLatitude", 0));
		double longitude = Double.longBitsToDouble(sharedPrefs.getLong("bmh_dstLongitude", 0));
		
		String traveltype = null;
		if (starter instanceof Main) {
			traveltype = sharedPrefs.getString("bmh_travelType", ((Main) starter).getTravelType().getSelectedItem().toString());
		}
		
		// set parameters
		myIntent.putExtra("bmh_dstLatitude", latitude); 
		myIntent.putExtra("bmh_dstLongitude", longitude);
		myIntent.putExtra("bmh_travelType", traveltype);
		starter.startActivity(myIntent);
	}

}
