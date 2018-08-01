package com.ryandro.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by WIN 1O on 20-03-2018.
 */

public class MyService extends Service {
    private String strIntentValue = "Intent Value is Null";
    private boolean isTaskRun = true;
    private IBinder mBinder = new MyBinder();
    private int countNumber;
    MyInterface myInterface;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ServiceDemo ", "OnBind Method");
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d("ServiceDemo ", "On ReBind");
        super.onRebind(intent);

    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.d("ServiceDemo", "On UnBindService ");
        super.unbindService(conn);
    }

    @Override
    public void onDestroy() {
        Log.d(getResources().getString(R.string.Service_Demo_Tag), "In OnDestroy, Thread ID: " + Thread.currentThread().getId() + "");
        stopDisplayNumber();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(getResources().getString(R.string.Service_Demo_Tag), "In OnStartCommond, Thread ID: " + Thread.currentThread().getId() + "");
//        displayNumber(intent);
        doSometask();
        return START_STICKY;
    }

    private void doSometask() {
        int count = 1;
        for (int i=0 ;1<10;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(getResources().getString(R.string.Service_Demo_Tag), "Task Thread ID: " + Thread.currentThread().getId() + "");
            Log.d("Intent Value  ", "" + strIntentValue);
            Log.d("Number Count ", "" + count++);
//            countNumber = count;
        }
    }

    private void displayNumber(Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            strIntentValue = intent.getStringExtra(getResources().getString(R.string.Service_Intent_Key));
        } else {
             strIntentValue = "Value Getting Null";
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 1;
                while (isTaskRun) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(getResources().getString(R.string.Service_Demo_Tag), "Task Thread ID: " + Thread.currentThread().getId() + "");
                    Log.d("Intent Value  ", "" + strIntentValue);
                    Log.d("Number Count ", "" + count++);
                    countNumber = count;
                }
            }
        }).start();
    }

    private void stopDisplayNumber() {
        isTaskRun = false;
    }

    public class MyBinder extends Binder {
        public MyService getServiceBinder() {
            return MyService.this;
        }
    }

    public int getCountNumber() {
        return countNumber;
    }
}
