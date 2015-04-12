package niamh.testapp;

import android.content.Context;
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
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
public class Create_Event extends MainActivity {


        private Spinner choose_sport;
        private Button btnSubmit;
        private TextView createevent;
        //private Context context;
        private int byGetOrPost = 0;
        EditText pace, distance, location, time, date;
    //flag 0 means get and 1 means post.(By default it is get.)
    public Create_Event(Spinner choose_sport, Button btnSubmit,
                          TextView createevent, EditText pace, EditText distance, EditText location, EditText time, EditText date) {

        }


       /* @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.create_event);
            addListenerOnButton();
            addListenerOnSpinnerItemSelection();}
*/

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        createevent = (TextView) findViewById(R.id.createevent);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        location = (EditText)findViewById(R.id.location);
        time = (EditText)findViewById(R.id.time);
        distance = (EditText)findViewById(R.id.distance);
        date = (EditText)findViewById(R.id.date);
        pace   = (EditText)findViewById(R.id.pace);
        choose_sport = (Spinner)findViewById(R.id.choose_sport);
        btnSubmit.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        Log.v("EditText", pace.getText().toString());
                        Log.v("EditText", location.getText().toString());
                        Log.v("EditText", distance.getText().toString());
                        Log.v("EditText", time.getText().toString());
                        Log.v("EditText", date.getText().toString());
                        Log.v("Spinner", choose_sport.getSelectedItem().toString());
                    }
                });
    }


    public void addListenerOnSpinnerItemSelection() {
        choose_sport = (Spinner) findViewById(R.id.choose_sport);
        choose_sport.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public void addListenerOnButton() {

        choose_sport = (Spinner) findViewById(R.id.choose_sport);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            //@Override
            public void onClick(View v) {

                        Toast.makeText(Create_Event.this,
                                "OnClickListener : " +
                                        //"\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
                                        "\nSpinner 2 : "+ String.valueOf(choose_sport.getSelectedItem()),
                                Toast.LENGTH_SHORT).show();
            }

        });
    }
        //@Override
        protected String doInBackground(String... arg0) {
            if(byGetOrPost == 0){ //means by Get Method
                try{
                    String sport = String.valueOf(choose_sport.getSelectedItem());
                    //String time = time;
                    String link = "http://testtraintogether.site88.net/create.php";
                    URL url = new URL(link);
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(link));
                    HttpResponse response = client.execute(request);
                    BufferedReader in = new BufferedReader
                            (new InputStreamReader(response.getEntity().getContent()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";
                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    in.close();
                    return sb.toString();
                }catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }
            }
            else{
                try{
                    String username = (String)arg0[0];
                    String password = (String)arg0[1];
                    String link="http://testtraintogether.site88.net/create.php";
                    String data  = URLEncoder.encode("username", "UTF-8")
                            + "=" + URLEncoder.encode(username, "UTF-8");
                    data += "&" + URLEncoder.encode("password", "UTF-8")
                            + "=" + URLEncoder.encode(password, "UTF-8");
                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter
                            (conn.getOutputStream());
                    wr.write( data );
                    wr.flush();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }
            }
        }




/*

        private TextView statusField,roleField;
    private Context context;
    private int byGetOrPost = 0;
    //flag 0 means get and 1 means post.(By default it is get.)
    public Create_Event(Context context,TextView statusField,
                          TextView roleField,int flag) {
        this.context = context;
        this.statusField = statusField;
        this.roleField = roleField;
        byGetOrPost = flag;

    }


    protected void onPreExecute(){

    }
    @Override
    protected String doInBackground(String... arg0) {
        if(byGetOrPost == 0){ //means by Get Method
            try{
                String username = (String)arg0[0];
                String password = (String)arg0[1];
                String link = "http://testtraintogether.site88.net/login.php";
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader
                        (new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line="";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            }catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }
        else{
            try{
                String username = (String)arg0[0];
                String password = (String)arg0[1];
                String link="http://testtraintogether.site88.net/login.php";
                String data  = URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(password, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write( data );
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            }catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }
    }
    */

}

