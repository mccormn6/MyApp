package niamh.testapp;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

public class SearchEvent  extends MainActivity {
    public TextView  timeField, dateField, locationField, sportField1, searchevent,
    distanceField1, paceField1;
    private Spinner sportChoose;
    Object returnValue[] = new Object[6];

    private EditText distanceField, paceField;
    //private EditText username, password;
    private Context context;
    private Button btnSubmit;
    private String result;
    Spinner choose_sport;
    String sport, distance, pace, date, time, location = null;
    public static List<String> Event = new ArrayList<String>();
    MainActivity main = new MainActivity();

    public SearchEvent() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchevent = (TextView)findViewById((R.id.searchevent));
        context = this;
        btnSubmit = (Button) findViewById((R.id.search));
        // sportChoose = (Spinner) findViewById(R.id.choose_sport);
        distanceField = (EditText) findViewById(R.id.distanceField);
        //paceField = (EditText) findViewById(R.id.paceField);
        paceField = (EditText) findViewById(R.id.paceField);



        btnSubmit.setOnClickListener(new View.OnClickListener() {

            // @Override
            public void onClick(View view) {


                distance = distanceField.getText().toString();
                pace = paceField.getText().toString();


                new searchData().execute(distance, pace);
               // resultField.setText(result);


            }
        });
    }
    private void onBackgroundTaskDataObtained(String result) {

        try

        {
        setContentView(R.layout.view_event);

        sportField1 = (TextView) findViewById(R.id.sport);
        distanceField1 = (TextView) findViewById(R.id.distance);
        paceField1 = (TextView) findViewById(R.id.paceField);
        timeField = (TextView) findViewById(R.id.time);
        dateField = (TextView) findViewById(R.id.date);
        locationField = (TextView) findViewById(R.id.location);
        Button attend = (Button) findViewById(R.id.attend);


            JSONArray jArray = new JSONArray(result);
            JSONObject json_data = jArray.getJSONObject(0);
            returnValue[0] = (json_data.getString("eventid"));
            returnValue[1] = (json_data.getString("sport"));
            returnValue[2] = (json_data.getString("distance"));
            returnValue[3] = (json_data.getString("pace"));
            returnValue[4] = (json_data.getString("time"));
            returnValue[5] = (json_data.getString("date"));
            returnValue[6] = (json_data.getString("location"));

            final String eventid = json_data.getString("eventid");
            String sport = json_data.getString("sport");
            String location = json_data.getString("location");
            String pace = json_data.getString("pace");
            String distance = json_data.getString("distance");
            String time = json_data.getString("time");
            String date = json_data.getString("date");

            sportField1.setText("The sport is " + sport);
            distanceField1.setText("The distance is " + distance);
            paceField1.setText("The pace is " + pace);
            timeField.setText("The time is " + time);
            dateField.setText("The date is " + date);
            locationField.setText("The location is " + location);

            attend.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    new attendEvent().execute(eventid);
                }


                //@Override
            });
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data" + e.toString());

        }
    }


        class searchData extends AsyncTask<String, Void, String> {


            protected String doInBackground(String... arg0) {

                try {

                    //  String link = "http://testtraintogether.site88.net/create.php";
                    String distance = (String) arg0[0];
                    String pace = (String) arg0[1];

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://testtraintogether.site88.net/search.php");


                    // Add your data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
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

                SearchEvent.this.onBackgroundTaskDataObtained(result);



            }
        }
    class attendEvent extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... arg0) {

            try {

                //  String link = "http://testtraintogether.site88.net/create.php";
                String distance = (String) arg0[0];
                String pace = (String) arg0[1];

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://testtraintogether.site88.net/search.php");


                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
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

            SearchEvent.this.onBackgroundTaskDataObtained(result);


        }
    }
    }







