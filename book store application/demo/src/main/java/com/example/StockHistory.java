package com.example;

import java.util.List;

import com.bookstore.Book;
import com.bookstore.ReadBooks;
import com.bookstore.Stock;
import com.bookstore.StockManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StockHistory {
    private Scene scene;
    private ComboBox<String> bookComboBox;  
    private TextArea stockHistoryArea;    
    private List<Book> books;
    private Button backButton;
    private Button stockHistory;
    private Button mainMenu;

    public StockHistory(Stage primaryStage, MainMenu mainMenu, AdminMenu adminMenu) {
        Background commonBackground = CommonBackground.createCommonBackground();

        backButton = new Button("Home");
        backButton.setTextFill(Color.BLACK);

        stockHistory = new Button("Stock History");
        stockHistory.setTextFill(Color.BLACK);

        this.mainMenu = new Button("Main Menu");
        this.mainMenu.setTextFill(Color.BLACK);

        HBox topBox = new HBox(20, this.mainMenu, stockHistory, backButton);
        topBox.setAlignment(Pos.TOP_RIGHT);
        topBox.setPadding(new Insets(10));

        backButton.setOnAction(e -> primaryStage.setScene(mainMenu.getScene()));
        stockHistory.setOnAction(e -> primaryStage.setScene(getScene()));
        this.mainMenu.setOnAction(e -> primaryStage.setScene(adminMenu.getScene()));

        // Create components
        bookComboBox = new ComboBox<>();
        stockHistoryArea = new TextArea();
        stockHistoryArea.setEditable(false); // Make stock history text area read-only

        // Populate the ComboBox with book names
        books = ReadBooks.getBooks();
        for (Book book : books) {
            bookComboBox.getItems().add(book.getName());
        }

        // Set a prompt text for ComboBox
        bookComboBox.setPromptText("Select a book");

        // Handle selection of a book
        bookComboBox.setOnAction(e -> {
            String selectedBookName = bookComboBox.getSelectionModel().getSelectedItem();
            if (selectedBookName != null) {
                // Find the selected book from the list of books
                Book selectedBook = findBookByName(selectedBookName, books);
                if (selectedBook != null) {
                    // Display the stock history for the selected book using StockManager
                    displayStockHistory(selectedBook);
                }
            }
        });

        // Layout
        VBox leftVBox = new VBox(new Label("Books"), bookComboBox);
        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10));
        leftVBox.setAlignment(Pos.TOP_LEFT);

        VBox rightVBox = new VBox(stockHistoryArea);
        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10));
        rightVBox.setAlignment(Pos.TOP_LEFT);

        BorderPane root = new BorderPane();
        root.setBackground(commonBackground);
        root.setTop(topBox);
        root.setLeft(leftVBox);
        root.setCenter(rightVBox);

        

        scene = new Scene(root, 1000, 650);
        scene.widthProperty().addListener((obs, oldVal, newVal) -> adjustFontSize(newVal.doubleValue()));
        
        // Add a listener to update the ComboBox whenever the scene is shown
        primaryStage.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == scene) {
                updateBookList();
            }
        });
    }
    

    public Scene getScene() {
        return scene;
    }

    // Find a book by name from the list of books
    private Book findBookByName(String bookName, List<Book> books) {
        for (Book book : books) {
            if (book.getName().equals(bookName)) {
                return book;
            }
        }
        return null;
    }

    // Display the stock history of a selected book
    private void displayStockHistory(Book selectedBook) {
        // Get the StockManager instance
        StockManager stockManager = StockManager.getInstance();

        // Retrieve the stock for the selected book using StockManager
        Stock stock = stockManager.getStock(selectedBook);

        // Display the stock history
        if (stock != null) {
            stockHistoryArea.setText(stock.toString());
        } else {
            stockHistoryArea.setText("No stock history available for this book.");
        }
    }

    private void adjustFontSize(double width) {
        double fontSize = width / 30; // Adjust the divisor to fit your needs
        backButton.setFont(Font.font("Arial", fontSize / 2));
        stockHistory.setFont(Font.font("Arial", fontSize / 2));
        mainMenu.setFont(Font.font("Arial", fontSize / 2));
    }
    public void updateBookList() {
        bookComboBox.getItems().clear();
        books = ReadBooks.getBooks();
        for (Book book : books) {
            bookComboBox.getItems().add(book.getName());
        }
    }
}
