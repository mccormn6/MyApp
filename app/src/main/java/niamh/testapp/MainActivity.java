package niamh.testapp;

import android.content.Context;
import android.location.Location;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.support.v7.app.ActionBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;



import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;

public class MainActivity extends ActionBarActivity {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private TextView mLatitudeText;
    private TextView mLongitudeText;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    public void onCreate2(View view) {
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.create_event);
        Button button2 = (Button) findViewById(R.id.search_event);
        Button button3 = (Button) findViewById(R.id.view_events);
        Button button4 = (Button) findViewById(R.id.test_geo);
        Button button5 = (Button) findViewById(R.id.test_geo1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, CreateEvent.class);
                Intent addAccountIntent = new Intent(Settings.ACTION_ADD_ACCOUNT);
                //addAccountIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                addAccountIntent.putExtra(Settings.EXTRA_AUTHORITIES, new String[]{"niamh.testapp"});
                startActivity(i);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchEvent.class);
                Intent addAccountIntent = new Intent(Settings.ACTION_ADD_ACCOUNT);
                //addAccountIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                addAccountIntent.putExtra(Settings.EXTRA_AUTHORITIES, new String[]{"niamh.testapp"});
                startActivity(i);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, View.class);
                Intent addAccountIntent = new Intent(Settings.ACTION_ADD_ACCOUNT);
                //addAccountIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                addAccountIntent.putExtra(Settings.EXTRA_AUTHORITIES, new String[]{"niamh.testapp"});
                startActivity(i);
            }

        });


        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TestGeo.class);
                Intent addAccountIntent = new Intent(Settings.ACTION_ADD_ACCOUNT);
                //addAccountIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                addAccountIntent.putExtra(Settings.EXTRA_AUTHORITIES, new String[]{"niamh.testapp"});
                startActivity(i);


            }

        });
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LocationMap.class);
                Intent addAccountIntent = new Intent(Settings.ACTION_ADD_ACCOUNT);
                //addAccountIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                addAccountIntent.putExtra(Settings.EXTRA_AUTHORITIES, new String[]{"niamh.testapp"});
                startActivity(i);


            }

        });


    }
}