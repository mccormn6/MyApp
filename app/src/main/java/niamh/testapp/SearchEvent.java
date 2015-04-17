package niamh.testapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by niamh on 4/14/15.
 */
public class SearchEvent  extends AsyncTask<String,Void,String> {

    private TextView resultField;
    //private EditText username, password;

    private int byGetOrPost = 0;
    //EditText distance;
    MainActivity main = new MainActivity();

    //flag 0 means get and 1 means post.(By default it is get.)
    public SearchEvent(
                          TextView resultField) {


        // this.username = username;
        //this.password = password;
      //  byGetOrPost = flag;
    }

    protected void onPreExecute() {
        // username = (EditText)findViewById(R.id.editText1);
    }

    // @Override
    protected String doInBackground(String... arg0) {
                try {
                    String distance = (String) arg0[0];
                    // String password = (String) arg0[1];
                    String link = "http://testtraintogether.site88.net/search.php";
                    String data = URLEncoder.encode("distance", "UTF-8")
                            + "=" + URLEncoder.encode(distance, "UTF-8");
                    //data += "&" + URLEncoder.encode("password", "UTF-8");
                    //  + "=" + URLEncoder.encode(password, "UTF-8");
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


    @Override
    protected void onPostExecute(String result) {
        if (result != null) {

            this.resultField.setText(result);

        }

    }
}


  /*  @Override
    protected void onPostExecute(String result){
        this.statusField.setText("Login Successful");
        this.roleField.setText(result);
    }
}*/

