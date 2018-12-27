package cn.enjoytoday.messengerproject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * @author hfcai
 */
public class MainActivity extends AppCompatActivity {

    private static final int SEND_MESSAGE_TO_SEVICE = 1000;

    private Messenger serviceMessenger = null;
    private final Messenger messenger = new Messenger(new MessengerHandler());

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceMessenger = new Messenger(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceMessenger =null;

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }



    public void onClick(View view){

        switch (view.getId()){

            case R.id.bind:
                bindService(new Intent(this, MessengerService.class),
                        connection, Context.BIND_AUTO_CREATE);
                break;


            case R.id.send:
                //发送数据
                if (serviceMessenger!=null){
                    try {
                        Message message = Message.obtain(null, SEND_MESSAGE_TO_SEVICE);
                        message.replyTo = messenger;
                        serviceMessenger.send(message);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }


                break;

            default:
                break;
        }
    }





    class MessengerHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){



                default:
                    break;
            }
        }
    }

}
