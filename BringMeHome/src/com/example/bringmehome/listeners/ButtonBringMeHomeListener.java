package com.example.bringmehome.listeners;

import com.example.bringmehome.ShowMap;

import android.app.Activity;
import android.content.Intent;
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
		//myIntent.putExtra("key", value); //Optional parameters
		starter.startActivity(myIntent);
	}

}
