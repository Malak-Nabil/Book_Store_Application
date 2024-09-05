package com.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateAcountPopUp {
    
    private Stage primaryStage;
    private MainMenu mainMenu;

    public CreateAcountPopUp(Stage primaryStage, MainMenu mainMenu) {
        this.primaryStage = primaryStage;
        this.mainMenu = mainMenu;
    }

    public void showSuccessDialog() {
        // Create a new Stage for the success message
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Success");

        // Create the success message layout
        Label label = new Label("Account created successfully");
        label.setFont(Font.font(18));
        label.setTextFill(Color.BLUE);
    
        Button okButton = new Button("OK");
        okButton.setTextFill(Color.BLUE);
        okButton.setOnAction(e -> {
            dialog.close();
            primaryStage.setScene(mainMenu.getScene());
        });
        
        BorderPane message = new BorderPane();
        message.setPrefSize(300, 150); // Adjusted to reasonable dimensions
        message.setCenter(label);
        message.setBottom(okButton);
        BorderPane.setAlignment(okButton, Pos.CENTER);

        // Create and set the scene for the dialog
        Scene dialogScene = new Scene(message);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public void showFailedDialog()
    {
        // Create a new Stage for the success message
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Success");

        // Create the success message layout
        Label label = new Label("Couldn't Create Acount");
        label.setFont(Font.font(18));
        label.setTextFill(Color.BLUE);
    
        Button okButton = new Button("OK");
        okButton.setTextFill(Color.BLUE);
        okButton.setOnAction(e -> {
            dialog.close();
        });
        
        BorderPane message = new BorderPane();
        message.setPrefSize(300, 150); // Adjusted to reasonable dimensions
        message.setCenter(label);
        message.setBottom(okButton);
        BorderPane.setAlignment(okButton, Pos.CENTER);

        // Create and set the scene for the dialog
        Scene dialogScene = new Scene(message);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    public void showRepeatedDialog()
    {
        // Create a new Stage for the success message
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Success");

        // Create the success message layout
        Label label = new Label("Acount already Exists");
        label.setFont(Font.font(18));
        label.setTextFill(Color.BLUE);
    
        Button okButton = new Button("OK");
        okButton.setTextFill(Color.BLUE);
        okButton.setOnAction(e -> {
            dialog.close();
        });
        
        BorderPane message = new BorderPane();
        message.setPrefSize(300, 150); // Adjusted to reasonable dimensions
        message.setCenter(label);
        message.setBottom(okButton);
        BorderPane.setAlignment(okButton, Pos.CENTER);

        // Create and set the scene for the dialog
        Scene dialogScene = new Scene(message);
        dialog.setScene(dialogScene);
        dialog.show();
    }


}
