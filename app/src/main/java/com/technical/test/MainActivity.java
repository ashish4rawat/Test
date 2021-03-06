package com.technical.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {


        LocationManager locationManager;
        String provider;

        TextView infoTextView;
        Button requestUberButton;
        Location location;
        Boolean requestActive = false;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);





        }
        public void requestDoctorPanic(View view) {

                Intent intent = new Intent(this,NearbyDoctors.class);
                startActivity(intent);

        }


        public void requestDoctorRoutine(View view) {

                if (requestActive == false) {

                        Log.i("MyApp", "Uber requesed");

                        ParseObject request = new ParseObject("Requests");

                        request.put("requesterUsername", ParseUser.getCurrentUser().getUsername());

                        ParseACL parseACL = new ParseACL();
                        parseACL.setPublicWriteAccess(true);
                        parseACL.setPublicReadAccess(true);
                        request.setACL(parseACL);

                        request.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                        if (e == null) {

                                                infoTextView.setText("Finding Uber driver...");
                                                requestUberButton.setText("Cancel Uber");
                                                requestActive = true;
                                                updateLocation();


                                        }

                                }
                        });
                } else {

                        infoTextView.setText("Uber Cancelled.");
                        requestUberButton.setText("Request Uber");
                        requestActive = false;

                        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Requests");

                        query.whereEqualTo("requesterUsername", ParseUser.getCurrentUser().getUsername());

                        query.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> objects, ParseException e) {

                                        if (e == null) {

                                                if (objects.size() > 0) {

                                                        for (ParseObject object : objects) {

                                                                object.deleteInBackground();

                                                        }


                                                }

                                        }

                                }
                        });


                }


        }

        public void updateLocation() {

                Double lat =location.getLatitude();
                Double lon = location.getLongitude();
                Log.i("LocationInfo 2", lat.toString() +" , " +lon.toString());




                if (requestActive == true) {

                        final ParseGeoPoint userLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

                        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Requests");

                        query.whereEqualTo("requesterUsername", ParseUser.getCurrentUser().getUsername());

                        query.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> objects, ParseException e) {

                                        if (e == null) {

                                                if (objects.size() > 0) {

                                                        for (ParseObject object : objects) {

                                                                object.put("requesterLocation", userLocation);
                                                                object.saveInBackground();

                                                        }


                                                }

                                        }

                                }
                        });


                }

        }


}







/*

        package com.example.hamuj.uber_hj;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class UserLocation extends FragmentActivity implements OnMapReadyCallback ,LocationListener {

    private GoogleMap mMap;
    Location location;


    LocationManager locationManager;
    String provider;

    TextView infoTextView;
    Button requestUberButton;

    Boolean requestActive = false;

    public void requestUber(View view) {

        if (requestActive == false) {

            Log.i("MyApp", "Uber requesed");

            ParseObject request = new ParseObject("Requests");

            request.put("requesterUsername", ParseUser.getCurrentUser().getUsername());

            ParseACL parseACL = new ParseACL();
            parseACL.setPublicWriteAccess(true);
            parseACL.setPublicReadAccess(true);
            request.setACL(parseACL);

            request.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null) {

                        infoTextView.setText("Finding Uber driver...");
                        requestUberButton.setText("Cancel Uber");
                        requestActive = true;
                        updateLocation();


                    }

                }
            });
        } else {

            infoTextView.setText("Uber Cancelled.");
            requestUberButton.setText("Request Uber");
            requestActive = false;

            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Requests");

            query.whereEqualTo("requesterUsername", ParseUser.getCurrentUser().getUsername());

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null) {

                        if (objects.size() > 0) {

                            for (ParseObject object : objects) {

                                object.deleteInBackground();

                            }


                        }

                    }

                }
            });


        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        infoTextView = (TextView) findViewById(R.id.infoTextView);
        requestUberButton = (Button) findViewById(R.id.requestUber);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(provider);


        if (location != null) {

            //    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10));

            //  mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Your Location"));

            // Double lat =location.getLatitude();
            // Double lon = location.getLongitude();

            Log.i("LocationInfo ", "LOCATION ACHIEVED");

        } else {
            Log.i("LocationInfo ", "LOCATION NOT ACHIEVED");
        }


    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

/*
@Override
public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        // Add a marker in Sydney and move the camera
        LatLng india = new LatLng(28.633758, 77.205517);
        mMap.addMarker(new MarkerOptions().position(india).title("Marker in India"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(india,15));
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(7));
        Double lat = india.latitude;
        Double lon = india.longitude;
        Log.i("LocationInfo ind", lat.toString() + " , " + lon.toString());
        // mMap.setOnMarkerDragListener(this);
        // mMap.setOnMapLongClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
        }
        location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
                onLocationChanged(location);
        }

}

        public void updateLocation() {

                Double lat =location.getLatitude();
                Double lon = location.getLongitude();
                Log.i("LocationInfo 2", lat.toString() +" , " +lon.toString());


                mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title("Your Location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon),15));
                //  mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                //   mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10));

                //  mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Your Location"));

                if (requestActive == true) {

                        final ParseGeoPoint userLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

                        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Requests");

                        query.whereEqualTo("requesterUsername", ParseUser.getCurrentUser().getUsername());

                        query.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> objects, ParseException e) {

                                        if (e == null) {

                                                if (objects.size() > 0) {

                                                        for (ParseObject object : objects) {

                                                                object.put("requesterLocation", userLocation);
                                                                object.saveInBackground();

                                                        }


                                                }

                                        }

                                }
                        });


                }

        }

        @Override
        protected void onResume() {
                super.onResume();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                }
                locationManager.requestLocationUpdates(provider, 400, 1, this);
        }

        @Override
        protected void onPause() {
                super.onPause();
                locationManager.removeUpdates(this);
        }








        @Override
        public void onLocationChanged(Location location) {


                mMap.clear();

                updateLocation();

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
}




 */
