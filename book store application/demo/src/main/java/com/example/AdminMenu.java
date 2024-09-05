package com.example;

import com.bookstore.AdminManager;
import com.bookstore.Book;
import com.bookstore.ReadBooks;
import com.bookstore.Stock;
import com.bookstore.StockManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class AdminMenu {
    private TableView<Book> tableView;
    private Scene scene;
    private Button backButton;
    private TextField searchField;
    private Button addButton;
    private Button stockHistory;
    private Button MainMenu;
    private Stock stock;
    private StockHistory history;
    public AdminMenu(Stage primaryStage, MainMenu mainMenu) {
        history=new StockHistory(primaryStage, mainMenu,this);
        
        tableView = new TableView<>();

        backButton = new Button("Home");
        backButton.setTextFill(Color.BLACK);

        stockHistory=new Button("Stock History");
        stockHistory.setTextFill(Color.BLACK);

        
        MainMenu = new Button("Main Menu");
        MainMenu.setTextFill(Color.BLACK);

        backButton.setOnAction(e -> primaryStage.setScene(mainMenu.getScene()));
        stockHistory.setOnAction(e -> primaryStage.setScene(history.getScene()));
        MainMenu.setOnAction(e -> primaryStage.setScene(getScene()));


        searchField = new TextField(); // Initialize the search field
        searchField.setPromptText("Search...");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> performSearch()); // Add listener to search field
        addButton = new Button("Add Book");
        addButton.setOnAction(e -> {
            addEmptyRow();
            history.updateBookList();  // Notify StockHistory to update its list
        });

        HBox topBox = new HBox(20,MainMenu,stockHistory,backButton);
        topBox.setAlignment(Pos.TOP_RIGHT);
        topBox.setPadding(new Insets(10));
        tableView.setEditable(true);

        Background commonBackground = CommonBackground.createCommonBackground();
        TableColumn<Book, String> nameColumn = new TableColumn<>("Book Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(140); 
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            Book book = event.getRowValue();
            book.setName(event.getNewValue());
        });

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setPrefWidth(140); 
        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorColumn.setOnEditCommit(event -> {
            Book book = event.getRowValue();
            book.setAuthor(event.getNewValue());
        });

        TableColumn<Book, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setPrefWidth(140); 
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priceColumn.setOnEditCommit(event -> {
            Book book = event.getRowValue();
            book.setPrice(event.getNewValue());
        });

        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setPrefWidth(140); 
        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryColumn.setOnEditCommit(event -> {
            Book book = event.getRowValue();
            book.setCategory(event.getNewValue());
        });

        TableColumn<Book, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockColumn.setPrefWidth(140); 
        stockColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        stockColumn.setOnEditCommit(event -> {
            Book book = event.getRowValue();
            int newQuantity = event.getNewValue();
            StockManager stockManager = StockManager.getInstance();
            stock = stockManager.getStock(book);
            stock.setQuantity(newQuantity);

        });

        TableColumn<Book, Void> buttonColumn = new TableColumn<>("Action");
        buttonColumn.setPrefWidth(150);
        buttonColumn.setCellFactory(column -> new TableCell<Book, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    ReadBooks.removeBook(book);
                    // Refresh the table view after removing the book
                    getTableView().getItems().remove(book);
                });
                setPadding(new Insets(5));
                
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(10, deleteButton, addButton));
                }
            }
        });

        tableView.getColumns().addAll(nameColumn, authorColumn, priceColumn, categoryColumn, stockColumn, buttonColumn);
        ObservableList<Book> books = FXCollections.observableArrayList(AdminManager.getAdmin().displayBook());
        tableView.setItems(books);
        
        StackPane tableContainer = new StackPane(tableView);
        tableContainer.setPadding(new Insets(20)); // Set padding here

        VBox vbox = new VBox(0, searchField, tableContainer); // Use VBox to stack searchField and tableContainer
        vbox.setPadding(new Insets(40, 20, 10, 20)); // Set padding for VBox
        BorderPane root = new BorderPane();
        root.setBackground(commonBackground);
        root.setTop(topBox); 
        root.setCenter(vbox);

        scene = new Scene(root, 1000, 650);
        scene.widthProperty().addListener((obs, oldVal, newVal) -> adjustFontSize(newVal.doubleValue()));
    }

    public Scene getScene() {
        return scene;
    }
    private void adjustFontSize(double width) {
        double fontSize = width / 30; // Adjust the divisor to fit your needs
        backButton.setFont(Font.font("Arial", fontSize /2));
        stockHistory.setFont(Font.font("Arial", fontSize/2 ));
        MainMenu.setFont(Font.font("Arial", fontSize/2 ));

        
    }
    private void performSearch() {
        String searchText = searchField.getText();
        
        ObservableList<Book> filteredBooks = FXCollections.observableArrayList(AdminManager.getAdmin().searchBook(searchText));
        tableView.setItems(filteredBooks);
    }
    private void addEmptyRow() {
        Book newBook = new Book("", "", 0.0, "", 0);
        AdminManager.getAdmin().addNewBook(newBook);
        tableView.getItems().add(newBook);
        tableView.getSelectionModel().select(newBook);
        
    }
    
    
}
