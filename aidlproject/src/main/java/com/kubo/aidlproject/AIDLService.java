package com.kubo.aidlproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 *aidl绑定service
 *
 * @author hfcai
 */
public class AIDLService extends Service {

    private static final String TAG = "AIDLService";

    ITestAidlStub stub = new ITestAidlStub();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"-----------创建AIDL服务-------------");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"----------------onStartCommand-----------------");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Log.e(TAG,"----------------------onDestroy---------------");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"----------------------onBind---------------");
        return stub;
    }
}
