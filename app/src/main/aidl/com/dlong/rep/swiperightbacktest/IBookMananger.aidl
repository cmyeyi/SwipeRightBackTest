// IBookMananger.aidl
package com.dlong.rep.swiperightbacktest;
import com.dlong.rep.swiperightbacktest.Book;

interface IBookMananger {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBookList();
    void addBook(inout Book book);
}
