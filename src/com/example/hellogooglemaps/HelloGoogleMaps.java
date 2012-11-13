package com.example.hellogooglemaps;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HelloGoogleMaps extends MapActivity implements OnClickListener {
	
	int markerCounter = 0; // used to keep track of marker placement
	Button mAddMarker;
	ArrayList<GeoPoint> geoPoints = new ArrayList<GeoPoint>(); // used to dynamically store geopoints
	private static int latitude = 35281750; // lat and long variables are only generated for testing here
	int longitude = -120658873;
	MapView mapView;                              // declaring these variables here (but not initializing them!)
	MyLocationOverlay myLocationOverlay;          // allows them to be referenced in multiple methods
	List<Overlay> mapOverlays;
	HelloItemizedOverlay itemizedoverlay;
	Drawable drawable;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        extractMapView();
        
        // create an overlay that shows our current location
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        
        drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        itemizedoverlay = new HelloItemizedOverlay(drawable, this);
        
        mapOverlays = mapView.getOverlays();
        mapOverlays.add(myLocationOverlay);
        mapView.postInvalidate();
        mAddMarker = (Button)findViewById(R.id.add_marker_button);
        mAddMarker.setOnClickListener(this);
        markerCounter = 0;
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	// when our activity resumes, we want to register for location updates
    	myLocationOverlay.enableMyLocation();
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	// when our activity pauses, we want to remove listening for location updates
    	myLocationOverlay.disableMyLocation();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {         // auto-generates menu? not very clear on this one, just include it
        getMenuInflater().inflate(R.menu.activity_hello_google_maps, menu);
        return true;
    }
    
	@Override
	protected boolean isRouteDisplayed() {                  //merely needs to be overridden and return false for our app
		return false;
	}
	
	public void onClick(View btnClicked) {
		geoPoints.add(new GeoPoint(latitude, longitude));
		
		extractMapView();
		
		itemizedoverlay.addOverlay(new OverlayItem(geoPoints.get(markerCounter), "Point " + markerCounter, null));
		mapOverlays.add(itemizedoverlay);
        
        //above was initially in onCreate
		mapView.postInvalidate();
		markerCounter++;
		longitude += 5000;
	}
	
	public void extractMapView(){                           // extracts mapview from layout in order to add overlays
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);               // enables zoom
	}
	
}
