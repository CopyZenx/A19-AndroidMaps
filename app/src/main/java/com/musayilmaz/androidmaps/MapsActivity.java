package com.musayilmaz.androidmaps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener{

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);


         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
         locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                /*
                mMap.clear();
                LatLng userLocation = new LatLng(location.getAltitude(),location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 2));
                */

                /*
                Geocoder geocoder = new Geocoder(getApplicationContext(),Locale.getDefault());

                try {
                    List<Address> adressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
                if (adressList != null && adressList.size() > 0) {
                    System.out.println("adress:" + adressList.get(0).toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */



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
        };




        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //permisson
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            //location
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastLocation !=null) {
                LatLng userLastLocation = new LatLng(lastLocation.getAltitude(), lastLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLastLocation).title("Last Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocation, 10));

            }

        }




        // Add a marker in Sydney and move the camera
        //LatLng anitkabir = new LatLng(39.925943, 32.836772);
        //mMap.addMarker(new MarkerOptions().position(anitkabir).title("Anıtkabir"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(anitkabir,12));
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length>0) {
            if (requestCode == 1) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            } else {

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

            }


        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        mMap.clear();

        Geocoder geocoder = new Geocoder(getApplicationContext(),Locale.getDefault());


        String address = "";
        String address1 = " No:";



        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                if (addressList.get(0).getThoroughfare() != null);
                address +=  addressList.get(0).getThoroughfare();

                if (addressList.get(0).getSubThoroughfare() != null);
                address1 +=  addressList.get(0).getSubThoroughfare();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        mMap.addMarker(new MarkerOptions().position(latLng).title(address +address1)).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));







    }

}
