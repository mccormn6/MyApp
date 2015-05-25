package niamh.testapp;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import niamh.testapp.MainActivity;
import niamh.testapp.R;

public class LocationMap  extends MainActivity {
    public TextView timeField, dateField, locationField, sportField1, searchevent,
            distanceField1, paceField1;
    private Spinner sportChoose;
    Object returnValue[] = new Object[7];

    private EditText distanceField, paceField;
    //private EditText username, password;
    private Context context;
    private Button btnSubmit, attend, search;
    private String result;
    Spinner choose_sport;
    String sport, distance, pace, date, time, location;
    public static List<String> Event = new ArrayList<String>();
    MainActivity main = new MainActivity();
    String eventid;
    private GoogleMap mMap;
    double longitude, latitude;
    //String location;

    List<Address> addresses;
    MarkerOptions city;
    String myLocation = "38 Highfield Road, Cavan, Ireland";

    public LocationMap() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchevent = (TextView) findViewById((R.id.searchevent));
        context = this;
        btnSubmit = (Button) findViewById((R.id.search));
        // sportChoose = (Spinner) findViewById(R.id.choose_sport);
        distanceField = (EditText) findViewById(R.id.distanceField);
        //paceField = (EditText) findViewById(R.id.paceField);
        paceField = (EditText) findViewById(R.id.paceField);
        sportChoose = (Spinner) findViewById(R.id.choose_sport);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            // @Override
            public void onClick(View view) {
                sport = sportChoose.getSelectedItem().toString();

                distance = distanceField.getText().toString();
                pace = paceField.getText().toString();


                new searchData().execute(sport, distance, pace);
                // resultField.setText(result);


            }
        });
    }

    private void onBackgroundTaskDataObtained(String result) {

        try

        {
            setContentView(R.layout.test_geo);

         /*   sportField1 = (TextView) findViewById(R.id.sport);
            distanceField1 = (TextView) findViewById(R.id.distance);
            paceField1 = (TextView) findViewById(R.id.paceField);
            timeField = (TextView) findViewById(R.id.time);
            dateField = (TextView) findViewById(R.id.date);
            locationField = (TextView) findViewById(R.id.location);*/

            JSONObject json_data = new JSONObject(result);
            //JSONObject json_data = parentObject.getJSONObject("event_details");


            //   JSONArray jArray = new JSONArray(result);
            //    JSONObject json_data = jArray.getJSONObject(0);
            returnValue[0] = (json_data.getString("event_id"));
            returnValue[1] = (json_data.getString("sport"));
            returnValue[2] = (json_data.getString("distance"));
            returnValue[3] = (json_data.getString("pace"));
            returnValue[4] = (json_data.getString("time"));
            returnValue[5] = (json_data.getString("date"));
            returnValue[6] = (json_data.getString("location"));

            final String eventid = json_data.getString("event_id");
            String sport = json_data.getString("sport");
            String location = json_data.getString("location");
            String pace = json_data.getString("pace");
            String distance = json_data.getString("distance");
            String time = json_data.getString("time");
            String date = json_data.getString("date");

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
                            addresses = geocoder.getFromLocationName(location, 1);

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

                city = new MarkerOptions().position(City).title(time + "" + date);

                mMap.addMarker(city);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(City, 15));
            }
            catch (NullPointerException e) { e.printStackTrace();}

        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data" + e.toString());

        }
    }


    class searchData extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... arg0) {

            try {

                String sport = (String) arg0[0];
                String distance = (String) arg0[1];
                String pace = (String) arg0[2];

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://testtraintogether.site88.net/search.php");


                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("sport", sport));
                nameValuePairs.add(new BasicNameValuePair("distance", distance));
                nameValuePairs.add(new BasicNameValuePair("pace", pace));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                InputStream is = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));


                StringBuilder sb = new StringBuilder();

                String line = null;
                // Read Server Response
                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                    break;
                }
                return sb.toString();

            } catch (Exception e) {
                String result = "error " + e.getMessage();
                return result;
            }
        }


        protected void onPostExecute(String result) {

            LocationMap.this.onBackgroundTaskDataObtained(result);


        }
    }
}






