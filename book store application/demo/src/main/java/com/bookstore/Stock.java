package com.bookstore;

import java.util.ArrayList;
import java.util.List;

public class Stock {
    private int quantity;
    private List<StockEntry> stockHistory;
    private Book book;
    
    public Stock(Book book) {
        this.book = book;
        this.quantity = book.getStock();
        this.stockHistory = new ArrayList<>();
        this.stockHistory.add(new StockEntry(book.getName(), quantity));
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        book.setStock(quantity);
        ReadBooks.updateCSV();
        this.stockHistory.add(new StockEntry(book.getName(), quantity));
    }

    public int getQuantity() {
        return quantity;
    }

    public void addStock(int amount) {
        this.quantity += amount;
        book.setStock(quantity);
        ReadBooks.updateCSV();
        this.stockHistory.add(new StockEntry(book.getName(), amount));
    }

    public void removeStock(int amount) {
        if (amount <= this.quantity) {
            this.quantity -= amount;
            book.setStock(quantity);
            ReadBooks.updateCSV();
            this.stockHistory.add(new StockEntry(book.getName(), -amount)); 
        } else {
            System.out.println("You have entered a bigger amount than available stock");
        }
    }
    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        String result = "Book: " + book.getName() + "\nStock: " + quantity + "\nHistory:\n";
        for (StockEntry entry : stockHistory) {
            result += entry + "\n";
        }
        return result;
    }
}
