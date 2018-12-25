package com.kubo.aidlproject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * @author hfcai
 */
public class MainActivity extends AppCompatActivity {

    ITestInterface iTestInterface;

    /**
     * 连接服务
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iTestInterface = ITestAidlStub.asInterface(service);
            Log.e("MainActivity","onServiceConnected");

        }

        /**
         * service异常终止
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            iTestInterface = null;
            Log.e("MainActivity","onServiceDisconnected");

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view){
        TestData testData = new TestData(0,"hfcai","注意提示！",true);
        switch (view.getId()){
            case R.id.bind:
                //绑定服务
               Intent intent = new Intent(this,AIDLService.class);
               bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
                break;

            case R.id.send_in:
                //发送测试数据

                if (iTestInterface!=null){

                    try {
                        iTestInterface.sendTest(testData);
                        Log.e("in","testData:"+testData.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Log.e("in error",e.getMessage());
                    }

                }else {
                    Toast.makeText(this,"连接服务失败！",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.send_out:
                //发送测试数据

                if (iTestInterface!=null){

                    try {
                        iTestInterface.sendTest2(testData,0);
                        Log.e("out","testData:"+testData.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Log.e("out error",e.getMessage());
                    }

                }else {
                    Toast.makeText(this,"连接服务失败！",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.send_inout:
                //发送测试数据

                if (iTestInterface!=null){

                    try {
                        iTestInterface.sendTest3(testData,"inout");
                        Log.e("inout","testData:"+testData.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Log.e("inout error",e.getMessage());
                    }

                }else {
                    Toast.makeText(this,"连接服务失败！",Toast.LENGTH_SHORT).show();
                }

                break;

            default:
                break;
        }
    }
}
