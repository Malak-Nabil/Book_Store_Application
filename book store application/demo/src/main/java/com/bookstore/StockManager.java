package com.bookstore;

import java.util.ArrayList;
import java.util.List;

public class StockManager {
    private static StockManager instance;
    private List<Stock> stockList;

    private StockManager() {
        stockList = new ArrayList<>();
    }

    public static StockManager getInstance() {
        if (instance == null) {
            instance = new StockManager();
        }
        return instance;
    }

    public Stock getStock(Book book) {
        for (Stock stock : stockList) {
            if (stock.getBook().equals(book)) {
                return stock;
            }
        }
        // If the stock doesn't exist for the book, create a new one
        Stock newStock = new Stock(book);
        stockList.add(newStock);
        return newStock;
    }
}
