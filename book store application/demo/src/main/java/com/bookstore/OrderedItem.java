package com.bookstore;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class OrderedItem {
    private Book book;
    private SimpleIntegerProperty quantityProperty;
    private double totalPrice;

    public OrderedItem(Book book, int quantity) {
        this.book = book;
        this.quantityProperty = new SimpleIntegerProperty(quantity);
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        this.totalPrice = book.getPrice() * getQuantity();
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantityProperty.get();
    }

    public boolean  setQuantity(int quantity) {
        if (quantity > book.getStock()) { // Check if quantity exceeds available stock
            return true; // Stock exceeded
        }
        quantityProperty.set(quantity); // Update quantity using the observable property
        calculateTotalPrice();
        return false;
    }

    public IntegerProperty quantityProperty() {
        return quantityProperty;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}