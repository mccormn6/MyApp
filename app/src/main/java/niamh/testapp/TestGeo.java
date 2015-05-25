package niamh.testapp;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by niamh on 4/30/15.
 */
public class TestGeo extends FragmentActivity {

    private GoogleMap mMap;
    double longitude, latitude;

    List<Address> addresses;
    MarkerOptions miami;
    String myLocation = "38 Highfield Road, Cavan, Ireland";
    String otherLocation ="1 Highfield Road, Cavan, Ireland";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_geo);
        try {
            if (mMap == null) {
                mMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.map)).getMap();
            }


            if (mMap != null) {

                Geocoder geocoder = new Geocoder(this);


                double latitude = 0;
                double longitude = 0;

                while (addresses == null) {
                    try {
                        addresses = geocoder.getFromLocationName(otherLocation, 1);

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
