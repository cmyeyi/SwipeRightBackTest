package com.dlong.rep.swiperightbacktest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {


    private List<Book> bookList;

    @Override
    public void onCreate() {
        super.onCreate();
        bookList = new ArrayList<>();
        initData();
    }

    private void initData() {
        Book book1 = new Book(1, "一生无爱");
        Book book2 = new Book(2, "方尖碑");
        Book book3 = new Book(3, "三题");
        Book book4 = new Book(4, "爱的代价");
        Book book5 = new Book(5, "我不是你的所有");
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);
        bookList.add(book5);
    }

    private final IBookMananger.Stub stub = new IBookMananger.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (book != null) {
                book.setName("服务器改了新书的名字 InOut");
                bookList.add(book);
            } else {
                Log.e("####", "接收到了一个空对象 InOut");
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
