package com.ryandro.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceActivity extends AppCompatActivity implements MyInterface {
    private Intent serviceIntend;
    private MyService myService;
    private boolean isServiceBound = false;
    private ServiceConnection serviceConnection;

    @Nullable
    @BindView(R.id.txt_ServiceData)
    public TextView txt_serviceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        serviceIntend = new Intent(ServiceActivity.this, MyService.class);
        Log.d(getResources().getString(R.string.Service_Demo_Tag), "In Activity Thread ID: " + Thread.currentThread().getId() + "");

    }

    @OnClick(R.id.btn_startService)
    public void startService() {
        serviceIntend.putExtra(getResources().getString(R.string.Service_Intent_Key), "Intent Value present");
        startService(serviceIntend);
    }

    @OnClick(R.id.btn_stopService)
    public void stopService() {
        stopService(serviceIntend);
    }

    @OnClick(R.id.btn_bindService)
    public void bindService() {
        if (serviceConnection == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    MyService.MyBinder myBinder = (MyService.MyBinder) iBinder;
                    myService = myBinder.getServiceBinder();
                    isServiceBound = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    isServiceBound = false;
                }
            };
        }
        bindService(serviceIntend, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.btn_unBindService)
    public void unBindService() {
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }

    @OnClick(R.id.btn_getServiceData)
    public void displaySericeData() {
        if (isServiceBound)
            txt_serviceData.setText("" + myService.getCountNumber());
        else
            txt_serviceData.setText("Service is Not Bind");
    }

    @Override
    public int onResponse() {
        return 0;
    }
}
