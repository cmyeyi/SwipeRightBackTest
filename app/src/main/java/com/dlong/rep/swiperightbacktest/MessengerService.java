package com.dlong.rep.swiperightbacktest;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

public class MessengerService extends Service {
    public static final int MSG_FROM_CLIENT = 1001;
    public static final int MSG_FROM_SERVER = 1001;
    Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FROM_CLIENT:
                    Log.d("######", "receive message :" + msg.getData().get("msg"));
                    Messenger client = msg.replyTo;


                    Message message = Message.obtain(null, MSG_FROM_SERVER);
                    Bundle data = new Bundle();
                    data.putString("reply","谢谢，我是服务端，我收到了你的消息！");
                    message.setData(data);
                    if(client != null) {

                        try {
                            client.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d("######", "client is null");
                    }

                    break;
                default:
                    super.handleMessage(msg);


            }
        }
    });


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
