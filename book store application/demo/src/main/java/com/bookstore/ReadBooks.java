package com.bookstore;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ReadBooks {
    private static List<Book> books;
    private static final String file = "C:\\Users\\victory tech\\Desktop\\book store application\\books_list.csv";
    
    /*A static block in Java is a block of code that is executed when the class is first
    * loaded into memory by the Java Virtual Machine (JVM).
    * It is used to initialize static variables and perform any static initialization required for the class.
    * this initialization is done once at the start.
    */
    static {
        books = new ArrayList<>();
        loadBooks(file);  
    }
    public ReadBooks() {}

    private static void loadBooks(String file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line; // hold each line fel file
            reader.readLine(); // skip the header line
            while ((line = reader.readLine())!= null){ 
                String[] values = line.split(","); 
                if (values.length == 5) {
                    try{
                    Book book = new Book(values[0].replace("\"",""), values[1].replace("\"",""), Double.parseDouble(values[2].replace("\"","")), values[3].replace("\"",""), 
                    Integer.parseInt(values[4].replace("\"","")));
                    books.add(book);
                    }
                    catch(NumberFormatException e)
                    {
                        System.err.println("cant parse int/double in this line: "+line);
                        System.err.println("error message "+e.getMessage());
                    }
                    
                } 
                else {
                    System.err.println(line); 
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();//It prints the stack trace of the exception to help with debugging 
        }
    }
    public static void updateCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("BookName,Author,Price,Category,Stock");
            
            for (Book book : books) {
                writer.println(CSVFormat(book));
            }
        } catch (IOException e) {
            System.err.println("Error updating CSV file: " + e.getMessage());
        }
    }
    public static List<Book> getBooks() {
        
        return books;
    }
    public static void addBook(Book book) {
        books.add(book);
        updateCSV();
    }

    public static void removeBook(Book book) {
        books.remove(book);
        updateCSV();
    }

    
    private static String CSVFormat(Book book) {
        return String.format("\"%s\",\"%s\",%.2f,\"%s\",%d",
            book.getName(),
            book.getAuthor(),
            book.getPrice(),
            book.getCategory(),
            book.getStock()
        );
    }
}


