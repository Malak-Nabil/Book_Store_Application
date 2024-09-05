package com.bookstore;

public class Book {
    private String name;
    private String author;
    private double price;
    private String category;
    private int stock; 
    
    
    public Book(String name, String author, double price, String category, int stock) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.category = category;
        this.stock = stock;
        
    }

    public void setName(String name) {
        this.name = name;
        ReadBooks.updateCSV();
        
    }

    public void setAuthor(String author) {
        this.author = author;
        ReadBooks.updateCSV();
        
    }

    public void setPrice(double price) {
        this.price = price;
        ReadBooks.updateCSV();
        
    }

    public void setCategory(String category) {
        this.category = category;
        ReadBooks.updateCSV();
        
    }

    public void setStock(int stock) {
        this.stock = stock;
        
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }
    @Override
    public String toString() {
        return String.format("BookName: %s\n Author: %s \n Price: %.2f EGP \n Category: %s\n Stock: %d\n",
                name, author, price, category, stock);
    }
}
