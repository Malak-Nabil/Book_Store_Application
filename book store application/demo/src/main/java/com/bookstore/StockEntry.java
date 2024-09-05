package com.bookstore;
import java.time.LocalDateTime;

public class StockEntry {
    private final LocalDateTime date;
    private final String bookName;
    private final int amount;

    public StockEntry(String bookName, int amount) {
        this.date = LocalDateTime.now();
        this.bookName = bookName;
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getBookName() {
        return bookName;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("BookName: %s, Date: %s, Amount: %d", bookName, date, amount);
    }
}
