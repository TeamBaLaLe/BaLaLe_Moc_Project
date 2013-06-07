package com.example.bringmehome;

import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.bringmehome.listeners.MyLocationListener;
import com.example.bringmehome.objects.MyRouteObject;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.PolylineOptions;

public class ShowMap extends FragmentActivity {
	private GoogleMap map = null;
	private LocationManager locationManager = null;
	private LocationListener listener = null;
	public static final int UPDATE_INTERVAL = 20000;
	private MyRouteObject route;
//	private LatLng home = new LatLng(48.239595,16.377801);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_showmap);
			
			if (map == null) {
				map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			}
			
			locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
			
			locationManager.addGpsStatusListener(new GpsStatus.Listener() {

				@Override
				public void onGpsStatusChanged(int event) {
					requestLocationUpdates();
				}
				
			});
			
			double dstLat = 0;
			double dstLong = 0;
			String travelType = "driving";
			
			if (getIntent() != null) {
				dstLat = getIntent().getDoubleExtra("bmh_dstLatitude", 0);
				dstLong = getIntent().getDoubleExtra("bmh_dstLongitude", 0);
				travelType = getIntent().getStringExtra("bmh_travelType");
			}
			
			if ("walking".equals(travelType)
					|| "bicycling".equals(travelType)) {
				map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			}
			// init route Object width destination Point
			route = new MyRouteObject(dstLat, dstLong, travelType);
				
			
			// Define a listener that responds to location updates
			listener = new MyLocationListener(map, route);
			
			// Register the listener with the Location Manager to receive location updates
			requestLocationUpdates();
		}catch(Exception e) {
			Log.d("MyApplication", e.getMessage());
		}
	}
	
	private void requestLocationUpdates() {
		// update it with time interval
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, UPDATE_INTERVAL, 0, listener);
			Location initLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (initLoc != null)  {
				listener.onLocationChanged(initLoc);	
			}
		}
		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, UPDATE_INTERVAL, 0, listener);
			Location initLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (initLoc != null)  {
				listener.onLocationChanged(initLoc);	
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		// Stop updating, the user cannot see them
		locationManager.removeUpdates(listener);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (locationManager != null
				&& listener != null) {
			requestLocationUpdates();
		}
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	public void updatePolyline(PolylineOptions routeLine) {
		this.map.addPolyline(routeLine);
	}
}
