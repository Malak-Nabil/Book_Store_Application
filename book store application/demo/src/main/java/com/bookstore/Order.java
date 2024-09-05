package com.bookstore;

import java.util.List;
public class Order {
    private static int orderCounter = 0;
    private static double lastTotalPrice;
    private int orderId;
    private Reader reader;
    private List<OrderedItem> orderedItems;
    private double totalPrice;
    

    public Order(Reader reader, List<OrderedItem> orderedItems, String phoneNumber, String address, String paymentMethod) {
        this.orderId = ++orderCounter;
        this.reader = reader;
        this.orderedItems = orderedItems;
        this.totalPrice = calculateTotalPrice();
        lastTotalPrice = this.totalPrice;
        reader.setPhoneNumber(phoneNumber);
        reader.setAddress(address);
        reader.setPaymentMethod(paymentMethod);
        
    }

    public double calculateTotalPrice() {
        double total = 0;
        for (OrderedItem item : orderedItems) {
            total += item.getBook().getPrice() * item.getQuantity(); // Correct calculation
        }
        return total;
    }

    public int getOrderId() {
        return orderId;
    }

    public Reader getReader() {
        return reader;
    }

    public List<OrderedItem> getOrderedItems() {
        return orderedItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public static double getLastTotalPrice() {
        return lastTotalPrice;
    }

    public static int getOrderCounter() {
        return orderCounter;
    }
}
