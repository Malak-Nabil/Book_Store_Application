package com.example;

import java.util.ArrayList;
import java.util.List;

import com.bookstore.Order;
import com.bookstore.OrderedItem;
import com.bookstore.ReadUsers;
import com.bookstore.Stock;
import com.bookstore.StockManager;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class RegistrationForm {
    private Scene scene;
    private TableView<OrderedItem> orderTable;
    private TextField nameField;
    private TextField emailField;
    private TextField addressField;
    private TextField paymentMethodField;
    private TextField phoneNumberField;
    private Label totalPriceLabel;
    private Label orderIDLabel;
    private Button confirmButton;
    private MainMenu mainMenu;
    private OrderedItem item;
    private int newQuantity;
    private Button goback;
    private ReaderMenu readermenu;
    private Order order; 

    public RegistrationForm(Stage primaryStage, MainMenu mainMenu, Order order) {
        this.mainMenu = mainMenu;
        this.readermenu=new ReaderMenu(primaryStage, mainMenu);
        this.order = order;
       
        // Initialize components
        orderTable = new TableView<>();
        orderTable.setEditable(true);
        nameField = new TextField(order.getReader().getUsername());
        emailField = new TextField(order.getReader().getEmail());
        addressField = new TextField(order.getReader().getAddress());
        paymentMethodField = new TextField(order.getReader().getPaymentMethod());
        phoneNumberField = new TextField(order.getReader().getPhoneNumber());
        totalPriceLabel = new Label();
        orderIDLabel = new Label();
        confirmButton = new Button("Confirm Order");
        goback=new Button("Go Back");
        

        // Set up the table columns
        TableColumn<OrderedItem, String> nameColumn = new TableColumn<>("Book Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook().getName()));
        nameColumn.setPrefWidth(150); 

        TableColumn<OrderedItem, Integer> quantityColumn = new TableColumn<>("Quantity Ordered");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityColumn.setPrefWidth(150);

        quantityColumn.setOnEditCommit(event -> {
            item = event.getRowValue();
            int newQuantity = event.getNewValue();

            boolean exceededStock = item.setQuantity(newQuantity);
            if (exceededStock) {
                QuantityDialog.showError("Quantity exceeds available stock.");
            } else {
                item.setQuantity(newQuantity);
                updateTotalPrice();
            }
});


    TableColumn<OrderedItem, Double> priceColumn = new TableColumn<>("Price");
    priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getBook().getPrice()).asObject());
    priceColumn.setPrefWidth(150); 

    TableColumn<OrderedItem, Double> totalPriceColumn = new TableColumn<>("Total Price");
    totalPriceColumn.setCellValueFactory(cellData -> {
    item = cellData.getValue();
    SimpleDoubleProperty totalPriceProperty = new SimpleDoubleProperty(item.getTotalPrice());
    item.quantityProperty().addListener((obs, oldValue, newValue) -> {
        item.setQuantity(newValue.intValue());
        totalPriceProperty.set(item.getTotalPrice());
    });
    return totalPriceProperty.asObject();
});
    totalPriceColumn.setPrefWidth(150);


        // Add columns to the table
        orderTable.getColumns().addAll(nameColumn, quantityColumn, priceColumn, totalPriceColumn);//, actionColumn);

        // Set the table data
        ObservableList<OrderedItem> tableData = FXCollections.observableArrayList(order.getOrderedItems());
        orderTable.setItems(tableData);

        // Calculate total price
        updateTotalPrice();

        int orderID = order.getOrderId(); // Ensure this method exists in Order
        orderIDLabel.setText("Order ID: " + orderID);

        // Set up layout
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(10));

        inputGrid.add(new Label("Name:"), 0, 0);
        inputGrid.add(nameField, 1, 0);
        inputGrid.add(new Label("Email:"), 0, 1);
        inputGrid.add(emailField, 1, 1);
        inputGrid.add(new Label("Address:"), 0, 2);
        inputGrid.add(addressField, 1, 2);
        inputGrid.add(new Label("Payment Method:"), 0, 3);
        inputGrid.add(paymentMethodField, 1, 3);
        inputGrid.add(new Label("Phone Number:"), 0, 4);
        inputGrid.add(phoneNumberField, 1, 4);

        VBox vbox = new VBox(10, orderTable, new HBox(10, totalPriceLabel, orderIDLabel), inputGrid, confirmButton);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        HBox hbox=new HBox(goback);
        hbox.setPadding(new Insets(10));
        hbox.setAlignment(Pos.BOTTOM_LEFT);

        goback.setOnAction(e -> primaryStage.setScene(readermenu.getScene()));

        BorderPane root = new BorderPane();
        root.setCenter(vbox);
        root.setBottom(hbox);
        scene = new Scene(root, 600, 600);

        // Add confirm button action
        confirmButton.setOnAction(e -> confirmOrder(primaryStage, order));
    }

    public Scene getScene() {
        return scene;
    }

    private void confirmOrder(Stage primaryStage, Order order) {
        
            String address = addressField.getText();
            String paymentMethod = paymentMethodField.getText();
            String phoneNumber = phoneNumberField.getText();
        
            if (address.isEmpty() || paymentMethod.isEmpty() || phoneNumber.isEmpty()) {
                QuantityDialog.showError("Can't complete order with missing information");
                return; // Stop further processing if information is missing
            }
        
            
            order.getReader().setUsername(nameField.getText());
            order.getReader().setEmail(emailField.getText());
            order.getReader().setAddress(address);
            order.getReader().setPaymentMethod(paymentMethod);
            order.getReader().setPhoneNumber(phoneNumber);
        

        List<OrderedItem> orderedItems = new ArrayList<>(order.getOrderedItems());
        StockManager stockManager = StockManager.getInstance();
        for (OrderedItem orderedItem : order.getOrderedItems()) {
            Stock stock = stockManager.getStock(orderedItem.getBook());
            int orderedQuantity = orderedItem.getQuantity();
    
            if (orderedQuantity <= stock.getQuantity()) {
                stock.removeStock(orderedQuantity); // Remove the ordered quantity
            } else {
                QuantityDialog.showError("Quantity exceeds available stock for " + orderedItem.getBook().getName());
                return;
            }
        }
        ReadUsers.addReader(order.getReader(),orderedItems);

        // Optionally, you might want to save the order to a database or perform further actions here

        // Return to main menu or any other logic
        primaryStage.setScene(mainMenu.getScene());
    }

    private void updateTotalPrice() {
        double totalPrice = 0.0;
        for (OrderedItem item : orderTable.getItems()) {
            totalPrice += item.getTotalPrice();
        }
        totalPriceLabel.setText("Total Price: EGP" + String.format("%.2f", totalPrice));
    }
    
}
