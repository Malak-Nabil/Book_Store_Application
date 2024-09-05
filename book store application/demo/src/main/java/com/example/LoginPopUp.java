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

public class LoginPopUp {
    
    private Stage primaryStage;
    private MainMenu mainMenu;
    private AdminMenu adminMenu;
    private ReaderMenu readermenu;

    public LoginPopUp(Stage primaryStage, MainMenu mainMenu) {
        this.primaryStage = primaryStage;
        this.mainMenu = mainMenu;
    }

    public void showSuccessAdminDialog() {
        adminMenu=new AdminMenu(primaryStage, mainMenu);
        // Create a new Stage for the success message
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Success");

        // Create the success message layout
        Label label = new Label("Login successfull");
        label.setFont(Font.font(18));
        label.setTextFill(Color.BLUE);
    
        Button okButton = new Button("OK");
        okButton.setTextFill(Color.BLUE);
        okButton.setOnAction(e -> {
            dialog.close();
            primaryStage.setScene(adminMenu.getScene());
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

    public void showSuccessReaderDialog() {
        readermenu=new ReaderMenu(primaryStage, mainMenu);
        // Create a new Stage for the success message
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Success");

        // Create the success message layout
        Label label = new Label("Login successfull");
        label.setFont(Font.font(18));
        label.setTextFill(Color.BLUE);
    
        Button okButton = new Button("OK");
        okButton.setTextFill(Color.BLUE);
        okButton.setOnAction(e -> {
            dialog.close();
            primaryStage.setScene(readermenu.getScene());
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
        Label label = new Label("Incorrect password or username");
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
    public void showEmptyDialog()
    {
        // Create a new Stage for the success message
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Success");

        // Create the success message layout
        Label label = new Label("can't Login with missing information");
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
