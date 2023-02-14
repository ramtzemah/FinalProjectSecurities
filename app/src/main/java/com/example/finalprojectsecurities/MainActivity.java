package com.example.finalprojectsecurities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton start;
    private ExtendedFloatingActionButton stop;
    private ExtendedFloatingActionButton graph;
    private ListOfPointsActivitry listOfPointsActivitry;
    private Calendar calendar;

    private BroadcastReceiver myRadio = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            double prg = intent.getDoubleExtra(SensorService.BROADCAST_NEW_SENSOR_EXTRA_KEY, 0);
            calendar = GregorianCalendar.getInstance();
            int hours = calendar.get(Calendar.HOUR_OF_DAY);     // gets the current month
            int minute = calendar.get(Calendar.MINUTE);
            int thePoint = ((hours*60)+ minute);
            listOfPointsActivitry.getListOfPoints().put(thePoint,prg);
            String json = new Gson().toJson(listOfPointsActivitry);
            MSPV.getMe().putString("points", json);
            Log.d("pttt","11111111111    "+prg);
            Log.d("pttt","33333333333    "+thePoint);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        MSPV.initHelper(this);
        String js = MSPV.getMe().getString("points", "");
        if(js!=""){
            listOfPointsActivitry = new Gson().fromJson(js, ListOfPointsActivitry.class);
        }

        start.setOnClickListener(v -> startService());
        stop.setOnClickListener(v -> stopService());
        graph.setOnClickListener(v -> graphClick());
        MyReminder.startReminder(this);
    }

    private void graphClick() {
        Intent intent = new Intent(MainActivity.this, GraphActivity.class);
        intent.putExtra("list", listOfPointsActivitry.getListOfPoints());

        startActivity(intent);
    }

    private void findViews() {
        listOfPointsActivitry = new ListOfPointsActivitry();
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        graph = findViewById(R.id.graph);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        IntentFilter intentFilter = new IntentFilter(SensorService.BROADCAST_NEW_SENSOR);
        registerReceiver(myRadio, new IntentFilter(SensorService.BROADCAST_NEW_SENSOR));

//        LocalBroadcastManager.getInstance(this).registerReceiver(myRadio, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myRadio);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            if (getIntent().getAction().equals(SensorService.MAIN_ACTION)) {
                // came from notification
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startService() {
        Intent intent = new Intent(this, SensorService.class);
        intent.setAction(SensorService.START_FOREGROUND_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
            // or
            //ContextCompat.startForegroundService(this, startIntent);
        } else {
            startService(intent);
        }
    }

    private void stopService() {
        Intent intent = new Intent(this, SensorService.class);
        intent.setAction(SensorService.STOP_FOREGROUND_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
            // or
            //ContextCompat.startForegroundService(this, startIntent);
        } else {
            startService(intent);
        }
    }
}