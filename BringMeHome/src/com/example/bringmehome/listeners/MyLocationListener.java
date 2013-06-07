package com.example.bringmehome.listeners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.example.bringmehome.ShowMap;
import com.example.bringmehome.objects.MyRouteObject;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MyLocationListener implements LocationListener {
	private MyRouteObject route;
	private GoogleMap map;
	private DrawingListener drawingListener;
	private Location lastLoc;
	
	public MyLocationListener(GoogleMap map, MyRouteObject route) {
		this.route = route;
		this.map = map;
		drawingListener = new DrawingListener(route);
	}

	@Override
	public void onLocationChanged(Location location) {
		// GPS sends every second, so handle the update with this logic
		if (isBetterLocation(location, lastLoc)) {
			lastLoc = location;
			// Set the new Loc as srcLocation in route-Object
			route.setSrcPoint(location.getLatitude(), location.getLongitude());
			
			// Reload the Route for the map
			// Redraw the map
			redrawMap(drawingListener.getActualPolyline());
		}
	}

	private void redrawMap(PolylineOptions o) {
		if (o != null) {
			map.clear();
		
			// set Marker to home Point
			MarkerOptions mOpt = new MarkerOptions();
			mOpt.position(route.getDst());
			mOpt.title("Your home");
			map.addMarker(mOpt);
			
			map.addPolyline(o);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(route.getSrc(), 18));
		}
	}
	@Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

	@Override
    public void onProviderEnabled(String provider) {}

	@Override
    public void onProviderDisabled(String provider) {}

	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (currentBestLocation == null) {
	        // A new location is always better than no location
	        return true;
	    }

	    // Check whether the new location fix is newer or older
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > ShowMap.UPDATE_INTERVAL;
	    boolean isSignificantlyOlder = timeDelta < -ShowMap.UPDATE_INTERVAL;
	    boolean isNewer = timeDelta > 0;

	    // If it's been more than two minutes since the current location, use the new location
	    // because the user has likely moved
	    if (isSignificantlyNewer) {
	        return true;
	    // If the new location is more than two minutes older, it must be worse
	    } else if (isSignificantlyOlder) {
	        return false;
	    }

	    // Check whether the new location fix is more or less accurate
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

	    // Check if the old and new location are from the same provider
	    boolean isFromSameProvider = isSameProvider(location.getProvider(),
	            currentBestLocation.getProvider());

	    // Determine location quality using a combination of timeliness and accuracy
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
	        return true;
	    }
	    return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
}
