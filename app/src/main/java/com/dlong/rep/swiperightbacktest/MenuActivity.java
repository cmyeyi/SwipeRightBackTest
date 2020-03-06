package com.dlong.rep.swiperightbacktest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import static com.dlong.rep.swiperightbacktest.MessengerService.MSG_FROM_CLIENT;

public class MenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent = new Intent(this,MessengerService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
    public void Back(View view){
        finish();
    }



    private Messenger mService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("######", "onServiceConnected");
            mService = new Messenger(service);
            Message msg = Message.obtain(null, MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg","客户端发来问候");
            msg.setData(data);

            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
