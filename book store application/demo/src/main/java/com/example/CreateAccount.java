package com.example;

import com.bookstore.ReaderManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
public class CreateAccount {

    private Scene scene;
    private Label usernameLabel;
    private Label emailLabel;
    private Label passwordLabel;
    private TextField usernameField;
    private TextField emailField;
    private PasswordField passwordField;
    private Button createAccountButton;
    private Button backButton;
    private Stage primaryStage;
    private MainMenu mainMenu;
   

    public CreateAccount(Stage primaryStage, MainMenu mainMenu) {
        this.primaryStage = primaryStage;
        this.mainMenu=mainMenu;
        
        
        Background commonBackground = CommonBackground.createCommonBackground();
        
       
        usernameLabel = new Label("Enter Username");
        usernameField = new TextField();
        emailLabel = new Label("Enter Email");
        emailField = new TextField();
        passwordLabel = new Label("Password");
        passwordField = new PasswordField();
        createAccountButton = new Button("Create Account");
        backButton = new Button("Home");
    
        // Style UI components
        usernameLabel.setTextFill(Color.WHITE);
        emailLabel.setTextFill(Color.WHITE);
        passwordLabel.setTextFill(Color.WHITE);
        createAccountButton.setTextFill(Color.BLACK);
        backButton.setTextFill(Color.BLACK);
    
        // Arrange components in a VBox
        VBox vbox = new VBox(10, usernameLabel, usernameField, emailLabel, emailField, passwordLabel, passwordField);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(60));
    
        // Create an HBox for the top section with the backButton aligned to the right
        HBox topBox = new HBox(backButton);
        topBox.setAlignment(Pos.TOP_RIGHT);
        topBox.setPadding(new Insets(10));

        // Configure BorderPane
        BorderPane root = new BorderPane();
        root.setBackground(commonBackground);
        root.setTop(topBox); // Set top section containing the backButton
        root.setCenter(vbox); // Center the form VBox
        root.setBottom(createAccountButton); // Bottom section for the createAccountButton

        root.setPadding(new Insets(20)); // Padding around the BorderPane
    
        // Create and configure the scene
        scene = new Scene(root, 1000, 600);
        
        adjustFontSize(scene.getWidth());
    
        // Update font size if the window size changes
        scene.widthProperty().addListener((obs, oldVal, newVal) -> adjustFontSize(newVal.doubleValue()));
    
        // Set button actions
        createAccountButton.setOnAction(e -> createAccount(usernameField.getText(), emailField.getText(), passwordField.getText()));
        backButton.setOnAction(e -> primaryStage.setScene(mainMenu.getScene()));
    }

    private void createAccount(String username, String email, String password) {
        ReaderManager manager = ReaderManager.getInstance(); // Use the singleton instance

        String result = manager.createAccount(username, email, password);

        switch (result) {
            case "SUCCESS":
                CreateAcountPopUp successDialog = new CreateAcountPopUp(primaryStage, mainMenu);
                successDialog.showSuccessDialog();
                break;
            case "EMPTY_CREDENTIALS":
                CreateAcountPopUp failDialog = new CreateAcountPopUp(primaryStage, mainMenu);
                failDialog.showFailedDialog();
                break;
            case "ACCOUNT_EXISTS":
                CreateAcountPopUp repeatedDialog = new CreateAcountPopUp(primaryStage, mainMenu);
                repeatedDialog.showRepeatedDialog();
                break;

            
        }
        
}


    private void adjustFontSize(double width) {
        double fontSize = width / 30; // Adjust the divisor to fit your needs
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        usernameField.setFont(Font.font("Arial", fontSize / 1.5));
        emailField.setFont(Font.font("Arial", fontSize / 1.5));
        passwordField.setFont(Font.font("Arial", fontSize / 1.5));
        createAccountButton.setFont(Font.font("Arial", fontSize / 2));
        backButton.setFont(Font.font("Arial", fontSize / 2));
    }

    public Scene getScene() {
        return scene;
    }
}
