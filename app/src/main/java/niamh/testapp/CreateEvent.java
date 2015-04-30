package niamh.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;
import android.widget.Toast;

/**
 * Created by niamh on 4/1/15.
 */
public class CreateEvent  extends MainActivity {
    public TextView statusField, resultField;
    private Spinner sportChoose;
    private EditText distanceField, paceField, timeField, dateField, locationField;
    //private EditText username, password;
    private Context context;
    private Button btnSubmit;
    private String result;
    Spinner choose_sport;
    String sport, distance, pace, date, time, location = null;

    public CreateEvent() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        context = this;
        btnSubmit = (Button) findViewById((R.id.btnSubmit));
      sportChoose = (Spinner) findViewById(R.id.choose_sport);
        distanceField = (EditText) findViewById(R.id.distanceField);
        paceField = (EditText) findViewById(R.id.paceField);
        timeField = (EditText) findViewById(R.id.timeField);
        dateField = (EditText) findViewById(R.id.dateField);
        locationField = (EditText) findViewById(R.id.locationField);
        resultField = (TextView) findViewById(R.id.resultField);
        choose_sport = (Spinner) findViewById(R.id.choose_sport);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            // @Override
            public void onClick(View view) {
                sport = choose_sport.getSelectedItem().toString();
                //result = "Creating an event";
                resultField = (TextView) findViewById(R.id.resultField);
                distance = distanceField.getText().toString();
                pace = paceField.getText().toString();
                time = timeField.getText().toString();
                date = dateField.getText().toString();
                location = locationField.getText().toString();


                new InsertData().execute(sport, distance, pace, time, date, location);
                resultField.setText(result);


            }
        });
    }
    //@Override


    class InsertData extends AsyncTask<String, String, String> {


        protected String doInBackground(String... arg0) {
            try {
                String sport = (String) arg0[0];
                String distance = (String) arg0[1];
                String pace = (String) arg0[2];
                String time = (String) arg0[3];
                String date = (String) arg0[4];
                String location = (String) arg0[5];
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://testtraintogether.site88.net/create.php");


                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("sport", sport));
                nameValuePairs.add(new BasicNameValuePair("distance", distance));
                nameValuePairs.add(new BasicNameValuePair("pace", pace));
                nameValuePairs.add(new BasicNameValuePair("time", time));
                nameValuePairs.add(new BasicNameValuePair("date", date));
               nameValuePairs.add(new BasicNameValuePair("location", location));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                InputStream is = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                String line = null;
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                return sb.toString();

            } catch (Exception e) {
                String result = "Error" + e.getMessage();
                return result;
            }
        }

    protected void onPostExecute(String result) {
            if (result != null) {

                resultField.setText(result);

            }

        }
    }

}