package com.dlong.rep.swiperightbacktest;

import android.app.Service;
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

import java.util.List;

import static com.dlong.rep.swiperightbacktest.MessengerService.MSG_FROM_CLIENT;

public class MainActivity extends BaseActivity {
    private Context mContext = this;
    private Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_getBookList).setOnClickListener(clickListener);
        findViewById(R.id.btn_addBook_inOut).setOnClickListener(clickListener);
        bindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService();
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;  //取消这个页面的划动返回
    }

    public void GoToMenu(View view) {
        mIntent = new Intent();
        mIntent.setClass(mContext, MenuActivity.class);
        mContext.startActivity(mIntent);
    }

    public void GoToAdd(View view) {
        mIntent = new Intent();
        mIntent.setClass(mContext, AddActivity.class);
        mContext.startActivity(mIntent);
    }


    List<Book> bookList;
    private IBookMananger bookMananger;
    private boolean connected;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookMananger = IBookMananger.Stub.asInterface(service);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connected = false;
        }
    };

    private void bindService() {
        Intent intent = new Intent(this, AIDLService.class);
        intent.setPackage("com.dlong.rep.swiperightbacktest");
        intent.setAction("com.dlong.rep.swiperightbacktest.action");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        if (connected) {
            unbindService(serviceConnection);
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_getBookList:
                    if (connected) {
                        try {
                            bookList = bookMananger.getBookList();
                            for (Book b : bookList) {
                                Log.e("#####", "书名：" + b.getName());
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case R.id.btn_addBook_inOut:
                    if (connected) {
                        Book book = new Book(10011, "这是一本新书 InOut");
                        try {
                            bookMananger.addBook(book);
                            Log.e("#####", "向服务器以InOut方式添加了一本新书");
                            Log.e("#####", "新书名：" + book.getName());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };
}
