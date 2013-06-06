package com.example.bringmehome.listeners;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.example.bringmehome.objects.MyRouteObject;
import com.example.bringmehome.xmlparser.RouteContentHandler;
import com.google.android.gms.maps.model.PolylineOptions;

public class DrawingListener {
	private MyRouteObject route;
	private HttpClient httpClient;
	private RouteContentHandler handler = new RouteContentHandler();
	
	static {
		System.setProperty("org.xml.sax.driver","org.xmlpull.v1.sax2.Driver");
	}
	
	public DrawingListener(MyRouteObject route) {
		this.route = route;
	}

	public PolylineOptions getActualPolyline() {
		if (httpClient == null) {
			httpClient = new DefaultHttpClient();
		}
		HttpGet request = new HttpGet(route.getRouteURL());
		HttpResponse response;
		try {
			response = httpClient.execute(request);

			// Get the response
			InputStreamReader rd = new InputStreamReader(response.getEntity().getContent());
			
			InputSource inputSource = new InputSource(rd);
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			
			// parse the response
			xmlReader.setContentHandler(handler);
			xmlReader.parse(inputSource);
			
			PolylineOptions routeLine = new PolylineOptions();
			routeLine.addAll(handler.getPolyList());
			routeLine.color(-16711681); // CYAN

			return routeLine; 
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
