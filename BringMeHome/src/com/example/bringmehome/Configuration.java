package com.example.bringmehome;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Configuration extends Activity {
	private Button btnSave;
	private EditText textStreet;
	private EditText txtHouseNumber;
	private EditText txtPostalCode;
	private EditText txtCity;
	private EditText txtCountry;
	
	private SharedPreferences localPrefs;
	private SharedPreferences sharedPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuration);
		
		btnSave = (Button) this.findViewById(R.id.saveconfig);
		
		textStreet = (EditText) this.findViewById(R.id.txtStreet);
		txtHouseNumber = (EditText) this.findViewById(R.id.txtHouseNumber);
		txtPostalCode = (EditText) this.findViewById(R.id.txtPostalCode);
		txtCity = (EditText) this.findViewById(R.id.txtCity);
		txtCountry = (EditText) this.findViewById(R.id.txtCountry);
		
		// read stored data
		localPrefs = this.getPreferences(Context.MODE_PRIVATE);
		textStreet.setText(localPrefs.getString("bmh_txtStreet", ""));
		txtHouseNumber.setText(localPrefs.getString("bmh_txtHouseNumber", ""));
		txtPostalCode.setText(localPrefs.getString("txtPostalCode", ""));
		txtCity.setText(localPrefs.getString("bmh_txtCity", ""));
		txtCountry.setText(localPrefs.getString("bmh_txtCountry", ""));
		
		sharedPrefs = this.getSharedPreferences("bmh_share", Context.MODE_PRIVATE);
		
		// init share
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				boolean coordsFound = false;
				
				// save the textfields and return
				SharedPreferences.Editor editor = localPrefs.edit();
				editor.putString("bmh_txtStreet", textStreet.getText().toString());
				editor.putString("bmh_txtHouseNumber", txtHouseNumber.getText().toString());
				editor.putString("txtPostalCode", txtPostalCode.getText().toString());
				editor.putString("bmh_txtCity", txtCity.getText().toString());
				editor.putString("bmh_txtCountry", txtCountry.getText().toString());
				editor.commit();
				
				Geocoder gc = new Geocoder(Configuration.this, Locale.ENGLISH);
				double foundLongitude = 0;
				double foundLatitude = 0;
				
				try {
					List<Address> adresses = gc.getFromLocationName(getAddressString(), 1);
					
					if (adresses != null
							&& !adresses.isEmpty()) {
						Address adr = adresses.get(0);
						
						if (adr.getLatitude() != 0
								&& adr.getLongitude() != 0) {
							coordsFound = true;
							foundLatitude = adr.getLatitude();
							foundLongitude =adr.getLongitude();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// Die Koordinaten im Shared Speicher speichern
				SharedPreferences.Editor sharedEditor = sharedPrefs.edit();
				sharedEditor.putLong("bmh_dstLatitude", Double.doubleToLongBits(foundLatitude));
				sharedEditor.putLong("bmh_dstLongitude", Double.doubleToLongBits(foundLongitude));
				sharedEditor.commit();
				
				if (coordsFound) {
					// Activity schlieﬂen
					Configuration.this.onBackPressed();
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							Configuration.this);
					// Add the buttons
					builder.setTitle(R.string.noAddressFoundTitle);
					builder.setMessage(R.string.noAddressFoundMessage);
					
					builder.setPositiveButton(R.string.noAddressFoundTryAgain,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// User clicked Try Again
									dialog.cancel();
								}
							});
					builder.setNegativeButton(R.string.noAddressFoundGoBack,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// User cancelled the dialog
									Configuration.this.onBackPressed();
								}
							});

					// Create the AlertDialog
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				
			}
		});
	}
	
	private String getAddressString() {
		StringBuilder sb = new StringBuilder();
		sb.append(txtPostalCode.getText().toString()).append(",");
		sb.append(textStreet.getText().toString()).append(",");
		sb.append(txtHouseNumber.getText().toString()).append(",");
		sb.append(txtCity.getText().toString()).append(",");
		sb.append(txtCountry.getText().toString()).append(",");
		return sb.toString();
	}
}
