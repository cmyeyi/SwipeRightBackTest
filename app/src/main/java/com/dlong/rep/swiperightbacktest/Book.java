package com.dlong.rep.swiperightbacktest;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private int bookId;
    private String name;

    public Book(int bookId, String name) {
        this.bookId = bookId;
        this.name = name;
    }


    protected Book(Parcel in) {
        bookId = in.readInt();
        name = in.readString();
    }

    public void readFromParcel(Parcel dest) {
        bookId = dest.readInt();
        name = dest.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bookId);
        dest.writeString(this.name);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };




    @Override
    public String toString() {
        return "book bookId：" + bookId + ", name："+ name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
