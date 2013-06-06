package com.example.bringmehome.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.bringmehome.Configuration;

public class ButtonConfigListener implements OnClickListener {

	private Activity starter;
	
	public ButtonConfigListener(Activity act) {
		this.starter = act;
	}
	
	@Override
	public void onClick(View arg0) {
		Intent myIntent = new Intent(starter, Configuration.class);
		// myIntent.putExtra("src", value); //Optional parameters
		starter.startActivity(myIntent);
	}

}
