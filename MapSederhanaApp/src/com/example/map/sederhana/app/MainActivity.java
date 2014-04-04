package com.example.map.sederhana.app;

//import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
//import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	final int RQS_GooglePlayServices = 1;
	private GoogleMap myMap;
	Polyline polyline;
	LocationManager locationManager;
	Location myLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager myFragmentManager = getSupportFragmentManager();
		SupportMapFragment mySupportMapFragment = (SupportMapFragment) myFragmentManager
				.findFragmentById(R.id.map);
		myMap = mySupportMapFragment.getMap();
		
		if (myMap != null)
			MapsInitializer.initialize(getApplicationContext());
		
		// set itb location
		final LatLng itb = new LatLng(-6.893210, 107.610447);
		
		final Location lokasiC = new Location("lokasi_c");
		lokasiC.setLatitude(itb.latitude);
		lokasiC.setLongitude(itb.longitude);
		
		MarkerOptions markerItb = new MarkerOptions();
		markerItb.position(itb);
		markerItb.title("Gerbang ITB");
		markerItb.snippet("Jalan Ganeca 7 Bandung");
		markerItb.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		
		/*
		// set map
		if (myMap != null) {
			myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			//myMap.addMarker(markerJogja);
			//myMap.addMarker(markerUgm);
			myMap.addMarker(markerItb);
			//myMap.addMarker(markerMyLoc);
			myMap.getUiSettings().setCompassEnabled(true);
			myMap.getUiSettings().setZoomControlsEnabled(true);
			myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(itb, 15));
		}
		*/
		
		// get my location
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)getSystemService(context);
		
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				
				Toast.makeText(getApplicationContext(), "longitude : " + location.getLongitude() + ", " + " latitude: " + location.getLatitude(), Toast.LENGTH_LONG)
				.show();
				
				LatLng myLoc = new LatLng(location.getLatitude(), location.getLongitude());
				
				MarkerOptions markerMyLoc = new MarkerOptions();
				markerMyLoc.position(myLoc);
				markerMyLoc.title("You are here!");
				markerMyLoc.snippet("Your mock location");
				markerMyLoc.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
				
				myMap.addMarker(markerMyLoc);
				myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 15));
				
				Double distance = (double) location.distanceTo(lokasiC);
				Double bearing = (double) location.bearingTo(lokasiC);
				String jarak = String.valueOf(distance);
				String arah = String.valueOf(bearing);

				Toast.makeText(getApplicationContext(), "jarak : " + jarak + " meter, " + " bearing : " + arah, Toast.LENGTH_LONG)
						.show();

				setDirection(myLoc, itb);
			}
			public void onStatusChanged(String provider, int status, Bundle extras) {}
			public void onProviderEnabled(String provider) {}
			public void onProviderDisabled(String provider) {}
		};
		
		String provider = LocationManager.GPS_PROVIDER;
		locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
		// Location myLocation = locationManager.getLastKnownLocation(provider);
		
		// menentukan titik lokasi
		//LatLng myloc = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
		//LatLng itb = new LatLng(-6.893210, 107.610447);
		//LatLng jogja = new LatLng(-7.775184, 110.392733);
		//LatLng ugm = new LatLng(-7.769581, 110.377789);

		// menambahkan marker diatas lokasi
		/*
		MarkerOptions markerMyLoc = new MarkerOptions();
		markerMyLoc.position(myloc);
		markerMyLoc.title("You are here!");
		markerMyLoc.snippet(" ");
		markerMyLoc.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
		*/

		// menambahkan marker diatas itb
		/*
		MarkerOptions markerItb = new MarkerOptions();
		markerItb.position(itb);
		markerItb.title("Gerbang ITB");
		markerItb.snippet("Jalan Ganeca 7 Bandung");
		markerItb.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		*/

		// menambahkan marker diatas kota jogja
		/*
		MarkerOptions markerJogja = new MarkerOptions();
		markerJogja.position(jogja);
		markerJogja.title("Yogyakarta");
		markerJogja.snippet("Jln Pringgodani Yogyakarta");
		markerJogja.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		*/

		// menambahkan marker diatas UGM
		/*
		MarkerOptions markerUgm = new MarkerOptions();
		markerUgm.position(ugm);
		markerUgm.title("UGM");
		markerUgm.snippet("Jln Kaliurang Yogyakarta");
		markerUgm.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
		*/
		
		// setting map
		if (myMap != null) {
			myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			//myMap.addMarker(markerJogja);
			//myMap.addMarker(markerUgm);
			myMap.addMarker(markerItb);
			//myMap.addMarker(markerMyLoc);
			myMap.getUiSettings().setCompassEnabled(true);
			myMap.getUiSettings().setZoomControlsEnabled(true);
			myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(itb, 15));
		}
		
		/*
		Location lokasiA = new Location("lokasi_a");
		lokasiA.setLatitude(jogja.latitude);
		lokasiA.setLongitude(jogja.longitude);

		Location lokasiB = new Location("lokasi_b");
		lokasiB.setLatitude(ugm.latitude);
		lokasiB.setLongitude(ugm.longitude);
		*/
		
		/*
		Location lokasiC = new Location("lokasi_c");
		lokasiC.setLatitude(itb.latitude);
		lokasiC.setLongitude(itb.longitude);
		*/
		
		/*
		Location lokasiD = new Location("lokasi_d");
		lokasiD.setLatitude(myloc.latitude);
		lokasiD.setLongitude(myloc.longitude);
		*/
		
		/*
		Double distance = (double) lokasiB.distanceTo(lokasiC);
		Double bearing = (double) lokasiB.bearingTo(lokasiC);
		String jarak = String.valueOf(distance);
		String arah = String.valueOf(bearing);
		Log.i("jarak", jarak);
		Toast.makeText(this, "jarak : " + jarak + " meter, " + " bearing : " + arah, Toast.LENGTH_LONG)
				.show();

		setDirection(ugm, itb);
		*/
	}

	private void setDirection(LatLng dari, LatLng ke) {
		// TODO Auto-generated method stub

		if (polyline != null) {
			polyline.remove();
		}

		PolylineOptions rectOptions = new PolylineOptions();
		rectOptions.add(dari);
		rectOptions.add(ke);
		rectOptions.width(5);
		rectOptions.color(Color.RED);

		polyline = myMap.addPolyline(rectOptions);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
