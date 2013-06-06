package com.example.bringmehome.listeners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.example.bringmehome.objects.MyRouteObject;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MyLocationListener implements LocationListener {
	private MyRouteObject route;
	private GoogleMap map;
	private DrawingListener drawingListener;
	
	public MyLocationListener(GoogleMap map, MyRouteObject route) {
		this.route = route;
		this.map = map;
		drawingListener = new DrawingListener(route);
	}

	@Override
	public void onLocationChanged(Location location) {
		// Set the new Loc as srcLocation in route-Object
		route.setSrcPoint(location.getLatitude(), location.getLongitude());
		
		// Reload the Route for the map
		// Redraw the map
		redrawMap(drawingListener.getActualPolyline());
		
    }

	private void redrawMap(PolylineOptions o) {
		map.clear();
		
		// set Marker to home Point
		MarkerOptions mOpt = new MarkerOptions();
		mOpt.position(route.getDst());
		mOpt.title("Your home");
		map.addMarker(mOpt);
		
		map.addPolyline(o);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(route.getSrc(), 18));
	}
	@Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

	@Override
    public void onProviderEnabled(String provider) {}

	@Override
    public void onProviderDisabled(String provider) {}

}
