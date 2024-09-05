package com.example;

import java.util.ArrayList;
import java.util.List;

import com.bookstore.Book;
import com.bookstore.Order;
import com.bookstore.OrderedItem;
import com.bookstore.ReaderManager;

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

public class ReaderMenu {
    private TableView<Book> tableView;
    private Scene scene;
    private Button backButton;
    private TextField searchField;
    private Button checkoutButton;
    private RegistrationForm register;
    private OrderedItem orderedItem;
    private List<OrderedItem> orderedItems;
    
    public ReaderMenu(Stage primaryStage, MainMenu mainMenu) {
        orderedItems = new ArrayList<>();
        tableView = new TableView<>();

        backButton = new Button("Home");
        backButton.setTextFill(Color.BLACK);
        checkoutButton = new Button("Check Out");
        checkoutButton.setTextFill(Color.BLACK);

        searchField = new TextField(); // Initialize the search field
        searchField.setPromptText("Search...");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> performSearch()); // Add listener to search field
        

        HBox topBox = new HBox(backButton);
        topBox.setAlignment(Pos.TOP_RIGHT);
        topBox.setPadding(new Insets(10));

        backButton.setOnAction(e -> primaryStage.setScene(mainMenu.getScene()));
        
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

        TableColumn<Book, Void> buttonColumn = new TableColumn<>("Action");
        buttonColumn.setPrefWidth(200);
        buttonColumn.setCellFactory(column -> new TableCell<Book, Void>() {
            private final Button orderButton = new Button("Order");

            {
                orderButton.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    int availableStock = book.getStock();
                    Integer quantity = QuantityDialog.showQuantityDialog((Stage) getTableView().getScene().getWindow(),availableStock);

            if (quantity != null) {

                orderedItem = new OrderedItem(book, quantity);
                orderedItems.add(orderedItem);
                
            } else {
                // Handle the cancellation (if needed)
                System.out.println("Order canceled or invalid quantity.");
            }
                });
                setPadding(new Insets(5));
                
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : orderButton);
            }
        });

        checkoutButton.setOnAction(e -> {
            if (!orderedItems.isEmpty()) {
                // Create an Order with the ordered items
                Order order = new Order(
                    ReaderManager.getCurrentReader(),
                    orderedItems,
                    ReaderManager.getCurrentReader().getPhoneNumber(),
                    ReaderManager.getCurrentReader().getAddress(),
                    ReaderManager.getCurrentReader().getPaymentMethod()
                );

                // Create and set the scene for RegistrationForm
                RegistrationForm registrationForm = new RegistrationForm(primaryStage, mainMenu, order);
                primaryStage.setScene(registrationForm.getScene());
            } else {
                // Handle case where no items are ordered
                System.out.println("No items to checkout.");
            }
        });



        

        tableView.getColumns().addAll(nameColumn, authorColumn, priceColumn, categoryColumn,buttonColumn);

        ObservableList<Book> books = FXCollections.observableArrayList(ReaderManager.getCurrentReader().displayBook());
        tableView.setItems(books);
        
        StackPane tableContainer = new StackPane(tableView);
        tableContainer.setPadding(new Insets(20)); // Set padding here

        VBox vbox = new VBox(0, searchField, tableContainer,checkoutButton); 
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
        backButton.setFont(Font.font("Arial", fontSize / 3));
        checkoutButton.setFont(Font.font("Arial", fontSize /1.5));
        
    }
    private void performSearch() {
        String searchText = searchField.getText();
        
        ObservableList<Book> filteredBooks = FXCollections.observableArrayList(ReaderManager.getCurrentReader().searchBook(searchText));
        tableView.setItems(filteredBooks);
    }
    
    
}
