import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import niamh.testapp.MainActivity;
import niamh.testapp.R;

public class SearchEvent  extends MainActivity {
    public TextView statusField, resultField;
    private Spinner sportChoose;
    private EditText distanceField, paceField, timeField, dateField, locationField;
    //private EditText username, password;
    private Context context;
    private Button btnSubmit;
    private String result;
    Spinner choose_sport;
    String sport, distance, pace, date, time, location = null;
    MainActivity main = new MainActivity();

    //flag 0 means get and 1 means post.(By default it is get.)
    /*public CreateEvent(Context context, Button btnSubmit, Spinner sportChoose, EditText distanceField,
                       EditText paceField, EditText timeField, EditText dateField, EditText locationField,
            TextView resultField) {
        // this.context = context;
        this.context = context;
        this.btnSubmit = btnSubmit;
        this.sportChoose = sportChoose;
        this.distanceField = distanceField;
        this.paceField = paceField;
        this.timeField = timeField;
        this.dateField = dateField;
        this.locationField = locationField;
        this.resultField = resultField;

    }
*/
    public SearchEvent() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        context = this;
        btnSubmit = (Button) findViewById((R.id.btnSubmit));
        // sportChoose = (Spinner) findViewById(R.id.choose_sport);
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

                resultField = (TextView) findViewById(R.id.resultField);
                distance = distanceField.getText().toString();
                pace = paceField.getText().toString();


                new searchData().execute(distance, pace);
                resultField.setText(result);


            }
        });
    }
    //@Override


    class searchData extends AsyncTask<String, String, String> {


        protected String doInBackground(String... arg0) {
            try {

                String link = "http://testtraintogether.site88.net/create.php";
                String distance = (String) arg0[0];
                String pace = (String) arg0[1];

                
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                return sb.toString();

            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        protected void onPostExecute(String result) {
            if (result != null) {

                resultField.setText(result);

            }

        }
    }

