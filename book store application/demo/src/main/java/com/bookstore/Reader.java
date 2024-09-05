package com.bookstore;
import java.util.ArrayList;
import java.util.List;

public class Reader extends User implements BookManager{
    private String phoneNumber;
    private String address;
    private String paymentMethod;
   

    public Reader(String username, String email, String password)
    {
        super(username, email, password);
        this.phoneNumber = "";
        this.address = "";
        this.paymentMethod = "";
        
    }
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber=phoneNumber;
    }
    public void setAddress(String address)
    {
        this.address=address;
    }
    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod=paymentMethod;
    }
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    public String getAddress()
    {
        return address;
    } 
    public String getPaymentMethod()
    {
        return paymentMethod;
    }
    
    @Override
    public List<Book> displayBook()
    {
        
        List<Book> availableBooks=new ArrayList<>();
        for (Book book : ReadBooks.getBooks()) {
            if(book.getStock()>0)
            {
            availableBooks.add(book);
            }
        }
        return availableBooks;
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

    @Override
    public String toString()
    {
        return ("name: " + getUsername() +"\nEmail: " + getEmail() + 
        "\nPhone number: " + getPhoneNumber() + "\nAddress: " + getAddress() + "\nPayment method: " + getPaymentMethod() +"\npassword: "+"\n"+getPassword()+"\n");
    }
}

