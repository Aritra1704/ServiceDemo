package com.ryandro.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyServiceForUiUpdate extends Service {
    MyInterface myInterface;

    public MyServiceForUiUpdate(ServiceActivity serviceActivity) {
        myInterface = serviceActivity;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(getResources().getString(R.string.Service_Demo_Tag), "OnStartCommand Called, Thread ID: " + Thread.currentThread().getId() + "");

        doSometask();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean stopService(Intent name) {
        Log.d(getResources().getString(R.string.Service_Demo_Tag), "StopService Called, Thread ID: " + Thread.currentThread().getId() + "");
        return super.stopService(name);

    }

    private void doSometask() {
        int count = 1;
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(getResources().getString(R.string.Service_Demo_Tag), "Task Thread ID: " + Thread.currentThread().getId() + "");
            Log.d("Number Count ", "" + count++);
//            countNumber = count;
        }
        myInterface.onResponse(count);

    }
}
