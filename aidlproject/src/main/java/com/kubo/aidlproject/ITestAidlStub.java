package com.kubo.aidlproject;

import android.os.RemoteException;
import android.util.Log;

/**
 *
 * @author hfcai
 */
public class ITestAidlStub extends ITestInterface.Stub {
    private static final String TAG = "ITestAidlStub";


    @Override
    public void sendTest(TestData testData) throws RemoteException {
        Log.e(TAG,"sendTest,testData is in:"+testData.toString());
        testData.setName("test_service");

    }

    @Override
    public void sendTest2(TestData testData, int a) throws RemoteException {
        Log.e(TAG,"sendTest,testData is out:"+testData.toString());
        testData.setName("test_service");

    }

    @Override
    public void sendTest3(TestData testData, String a) throws RemoteException {
        Log.e(TAG,"sendTest,testData is inout:"+testData.toString());
        testData.setName("test_service");

    }
}
