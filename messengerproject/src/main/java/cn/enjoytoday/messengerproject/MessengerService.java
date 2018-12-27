package cn.enjoytoday.messengerproject;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

/**
 * @author hfcai
 */
public class MessengerService extends Service {




    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    class ServiceHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
