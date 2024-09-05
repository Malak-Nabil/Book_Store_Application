package com.bookstore;

import java.util.ArrayList;
import java.util.List;
public class Admin extends User implements BookManager{
    public Admin (String username, String email,String password)
    {
        super(username, email, password);
        
    }
    public boolean  logIn(String username, String email, String password) {
        
        if ((this.getUsername().equals(username) || this.getEmail().equals(email)) && this.getPassword().equals(password)) {
            
            return true;  
            }
            return false;
        }

    
    public void addNewBook(Book newBook)
    {
        ReadBooks.addBook(newBook);;
        
    }
    public void deleteBook(Book book)
    {
        ReadBooks.removeBook(book);

    }

    @Override
    public List<Book> displayBook()
    {
        return ReadBooks.getBooks();
    }
    @Override
    public List<Book> searchBook(String bookName)
    {
        List<Book> availableBooks=new ArrayList<>();
        for (Book book : ReadBooks.getBooks()) {
            if (book.getStock() > 0 && book.getName().toLowerCase().contains(bookName.toLowerCase())) {
                availableBooks.add(book);
            }
        }

        if (availableBooks.isEmpty()) {
            System.err.println("Book not found");
        }

        return availableBooks;
        
    }
    
}
