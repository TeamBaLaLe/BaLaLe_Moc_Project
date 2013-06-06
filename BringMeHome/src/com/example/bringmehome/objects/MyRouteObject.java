package com.example.bringmehome.objects;

import com.google.android.gms.maps.model.LatLng;

public class MyRouteObject {
	private LatLng dst;
	private LatLng src;
	private String traveltype;
	
	public MyRouteObject(double dstLat, double dstLon, String traveltype) {
		this.dst= new LatLng(dstLat, dstLon);
		this.traveltype = traveltype;
	}

	public void setSrcPoint(double latitude, double longitude) {
		this.src = new LatLng(latitude, longitude);
	}
	
	public LatLng getDst() {
		return dst;
	}
	
	public LatLng getSrc() {
		return src;
	}
	
	public String getRouteURL() {
		StringBuilder sb = new StringBuilder();
		sb.append("http://maps.googleapis.com/maps/api/directions/xml");
		sb.append("?origin=");
		sb.append(String.valueOf(this.src.latitude));
		sb.append(",");
		sb.append(String.valueOf(this.src.longitude));
		sb.append("&destination=");
		sb.append(String.valueOf(this.dst.latitude));
		sb.append(",");
		sb.append(String.valueOf(this.dst.longitude));
		sb.append("&sensor=true");
        
		if (traveltype != null && traveltype.length() > 0) {
			sb.append("&mode=").append(traveltype);
			if ("transit".equals(traveltype)) {
				sb.append("&departure_time=").append(System.currentTimeMillis()/1000);
			}
		}
		
		return sb.toString();
	}
	
}
