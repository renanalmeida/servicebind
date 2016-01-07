package com.servicebind;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {


    private MyService myService;
    private Intent startServiceIntent;
    private boolean serviceBound = false;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(startServiceIntent == null){
            startServiceIntent = new Intent(this, MyService.class);
            bindService(startServiceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
            startService(startServiceIntent);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.w("MainActivity", "OnServiceConnected !!!");
            MyService.MyBinder myBinder = (MyService.MyBinder)service;
            myService = myBinder.getService();
            myService.startProgress(progressBar);
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    @Override
    protected void onDestroy() {
        myService=null;
        super.onDestroy();
    }
}
