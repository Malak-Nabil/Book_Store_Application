package com.bookstore;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReadUsers {

    private static final String FILE_PATH = "C:\\Users\\victory tech\\Desktop\\book store application\\users_list.csv";

    // Constructor
    public ReadUsers() {
        // No implementation needed for now
    }

    public static void addReader(Reader reader, List<OrderedItem> orderedItems) {
        writeUserToFile(reader, orderedItems);
    }

    private static void writeUserToFile(Reader reader, List<OrderedItem> orderedItems) {
        // Append to the file without overwriting
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            // Write the CSV formatted line for the new reader
            writer.println(CSVFormat(reader, orderedItems));
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static String CSVFormat(Reader reader, List<OrderedItem> orderedItems) {
        // Create the CSV formatted string for a single reader
        String orderedBooks = orderedItems.stream()
            .map(item -> String.format("%s:%d", item.getBook().getName(), item.getQuantity()))
            .collect(Collectors.joining("; "));

        int orderID = Order.getOrderCounter(); // Replace with actual method to get order ID
        double price = orderedItems.stream().mapToDouble(OrderedItem::getTotalPrice).sum(); // Calculate price based on ordered items

        return String.format("%s,%s,%s,%s,%s,%s,%d,%f,%s",
            reader.getUsername(),
            reader.getEmail(),
            reader.getAddress(),
            reader.getPhoneNumber(),
            reader.getPaymentMethod(),
            orderedBooks,
            orderID,
            price,
            LocalDateTime.now().toString());
    }
}
