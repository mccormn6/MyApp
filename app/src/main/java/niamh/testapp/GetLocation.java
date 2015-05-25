package niamh.testapp;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class GetLocation extends FragmentActivity implements ConnectionCallbacks, OnConnectionFailedListener {
    protected GoogleApiClient mGoogleApiClient;

    private GoogleMap mMap;
    double longitude, latitude;

    List<Address> addresses;
    MarkerOptions miami;
    String myLocation = "1 Highfield Road, Cavan, Ireland";
    protected Location mLastLocation;
    protected static final String TAG = "basic-location-sample";
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;

    /*   @Override
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.test_geo1);
           mLatitudeText = (TextView) findViewById((R.id.lat));
           mLongitudeText = (TextView) findViewById((R.id.lon));
           buildGoogleApiClient();
       }
   */
    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener((OnConnectionFailedListener) this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
       /* if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }*/
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_geo);
        buildGoogleApiClient();
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        try {
            if (mMap == null) {
                mMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.map)).getMap();
            }


            if (mMap != null) {

                Geocoder geocoder = new Geocoder(this);


                double latitude = mLastLocation.getLatitude();
                double longitude = mLastLocation.getLongitude();

                while (addresses == null) {
                    try {
                        addresses = geocoder.getFromLocationName(myLocation, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            Address address = addresses.get(0);

            if (addresses.size() > 0) {
                latitude = address.getLatitude();
                longitude = address.getLongitude();
            }
                LatLng City = new LatLng(latitude, longitude);

                miami = new MarkerOptions().position(City).title("Dublin");

                mMap.addMarker(miami);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(City, 15));
            }
              catch (NullPointerException e) { e.printStackTrace();}
        }
    }


