package com.bookstore;
import java.util.List;

public interface BookManager {

    List<Book> searchBook(String bookName);
    List<Book> displayBook();
    
}
