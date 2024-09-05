package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class QuantityDialog {

    public static Integer showQuantityDialog(Stage primaryStage, int availableStock) {
        final Integer[] quantity = new Integer[1]; // Use Integer array to handle null values

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Enter Quantity");

        Label label = new Label("Enter the quantity:");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            try {
                int inputQuantity = Integer.parseInt(quantityField.getText());
                if (inputQuantity <= 0) {
                    showError("Quantity must be greater than zero.");
                } else if (inputQuantity > availableStock) {
                    showError("Quantity exceeds available stock.");
                } else {
                    quantity[0] = inputQuantity;
                    
                    dialog.close();
                }
            } catch (NumberFormatException ex) {
                showError("Invalid quantity. Please enter a number.");
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            quantity[0] = null; // Indicate cancellation
            dialog.close();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, quantityField, new HBox(10, okButton, cancelButton));

        Scene scene = new Scene(layout, 300, 150);
        dialog.setScene(scene);
        dialog.showAndWait();

        return quantity[0]; // Return null if canceled
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
