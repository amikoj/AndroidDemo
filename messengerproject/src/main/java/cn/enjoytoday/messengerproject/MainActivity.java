package cn.enjoytoday.messengerproject;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private final Messenger messenger = new Messenger(new MessengerHandler());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
