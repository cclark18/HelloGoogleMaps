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
import android.widget.EditText;
import android.widget.TextView;

public class HelloGoogleMaps extends MapActivity implements OnClickListener {
	
	int markerCounter = 0; 						// used to keep track of marker placement
	Button mAddMarker;
	MapView mapView;                            // declaring these variables here (but not initializing them!)
	MyLocationOverlay myLocationOverlay;   		// allows them to be referenced in multiple methods
	List<Overlay> mapOverlays;
	HelloItemizedOverlay itemizedoverlay;
	Drawable drawable;
	TextView tvCurrentLocation;
	EditText etAddGeoPoint;
	String testString;
	
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
        tvCurrentLocation = (TextView)findViewById(R.id.tvCurrentLocation);
        tvCurrentLocation.setOnClickListener(this);
        etAddGeoPoint = (EditText)findViewById(R.id.etAddGeoPoint);
        testString = "35302696,-120658876";
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
	
	public void onClick(View objClicked) {
		if(objClicked == mAddMarker){
			extractMapView();
			String etString = etAddGeoPoint.getText().toString();
			GeoPoint typedGeoPoint = stringToGeoPoint(etString);
			itemizedoverlay.addOverlay(new OverlayItem(typedGeoPoint, "Point " + (markerCounter+1), null));
			mapOverlays.add(itemizedoverlay);
			mapView.postInvalidate();
			markerCounter++;
		} else if(objClicked == tvCurrentLocation){
			if(myLocationOverlay.getMyLocation()!=null){
				GeoPoint myLocationGeoPoint = myLocationOverlay.getMyLocation();
				String myLocationString = myLocationGeoPoint.toString();
				CharSequence myLocationCharSequence = (CharSequence)myLocationString; 	//unnecessary cast, but it works
				tvCurrentLocation.setText(myLocationString);							//setText takes String or CharSequence
			}
		}
	}
	
	public void extractMapView(){                           // extracts mapview from layout in order to add overlays
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);               // enables zoom
	}
	
	public static GeoPoint stringToGeoPoint(String geoString){
		String testString = geoString.replace("-", "").replace(",", "");
		int latitudeInt;
		int longitudeInt;
		String latitudeString;
		String longitudeString;
		GeoPoint geoPoint;
		if((testString.length()==14 || testString.length()==15 || testString.length()==16 || testString.length()==17)
		&& (true)){											//add logic to check for only number characters
			int commaIndex = geoString.indexOf(",");
			latitudeString = geoString.substring(0, (commaIndex));
			longitudeString = geoString.substring((commaIndex+1));
			latitudeInt = java.lang.Integer.parseInt(latitudeString);
			longitudeInt = java.lang.Integer.parseInt(longitudeString);
			geoPoint = new GeoPoint(latitudeInt, longitudeInt);
			return geoPoint;
		} else{
			return null;
		}
	}
}
//test pull
