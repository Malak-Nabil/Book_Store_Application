module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base; // Ensure javafx.base is required

    opens com.example to javafx.fxml;
    opens com.bookstore to javafx.base; // Allow javafx.base to access com.bookstore

    exports com.example;
    exports com.bookstore; // Export com.bookstore if needed
}
