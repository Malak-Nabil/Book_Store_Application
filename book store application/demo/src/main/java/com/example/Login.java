package com.example;

import com.bookstore.AdminManager;
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

public class Login {
    private Scene scene;
    private Label usernameLabel;
    private Label passwordLabel;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button backButton;
    private Stage primaryStage;
    private MainMenu mainMenu;

    public Login(Stage primaryStage, MainMenu mainMenu) {
        Background commonBackground = CommonBackground.createCommonBackground();
        this.primaryStage = primaryStage;
        this.mainMenu=mainMenu;
        usernameLabel = new Label("Enter Username or Email");
        usernameField = new TextField();
        passwordLabel = new Label("Password");
        passwordField = new PasswordField();
        loginButton = new Button("Login");
        backButton = new Button("Home");

        usernameLabel.setTextFill(Color.WHITE);
        passwordLabel.setTextFill(Color.WHITE);
        loginButton.setTextFill(Color.BLACK);
        backButton.setTextFill(Color.BLACK);

        // Create and configure the VBox layout for input fields and labels
        VBox vbox = new VBox(10, usernameLabel, usernameField, passwordLabel, passwordField,loginButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(60)); // Adjust padding as needed

        HBox topBox = new HBox(backButton);
        topBox.setAlignment(Pos.TOP_RIGHT);
        topBox.setPadding(new Insets(10));
        // Create and configure the BorderPane layout
        BorderPane root = new BorderPane();
       
        root.setTop(topBox); // Place the topBox containing the backButton at the top
        root.setCenter(vbox); // Place the VBox in the center
        // Place the loginButton at the bottom
        root.setPadding(new Insets(20)); // Padding around the BorderPane
        root.setBackground(commonBackground);
        
        // Create and configure the scene
        scene = new Scene(root, 1000, 600);
        

        adjustFontSize(scene.getWidth());

        // Update font size if the window size changes
        scene.widthProperty().addListener((obs, oldVal, newVal) -> adjustFontSize(newVal.doubleValue()));

        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));
        backButton.setOnAction(e -> primaryStage.setScene(mainMenu.getScene()));
    }

    private void handleLogin(String usernameOrEmail, String password) {

        ReaderManager manager = ReaderManager.getInstance();
        if (usernameOrEmail == null || usernameOrEmail.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
                LoginPopUp emptyDialog = new LoginPopUp(primaryStage, mainMenu);
                emptyDialog.showEmptyDialog();
            
            }
        else if (AdminManager.getAdmin().logIn(usernameOrEmail,usernameOrEmail, password)) { // Assuming admin.login(username, password) method exists
            System.out.println("admin login successful");
            LoginPopUp successDialog = new LoginPopUp(primaryStage, mainMenu);
                successDialog.showSuccessAdminDialog();
        } 
        else if(manager.logIn(usernameOrEmail, usernameOrEmail, password)){
            System.out.println("user login successful");
            LoginPopUp successDialog = new LoginPopUp(primaryStage, mainMenu);
            successDialog.showSuccessReaderDialog();
        }
        else
        {
            System.out.println("login failed");
            LoginPopUp failDialog = new LoginPopUp(primaryStage, mainMenu);
            failDialog.showFailedDialog();
        }
}

    private void adjustFontSize(double width) {
        double fontSize = width / 30; // Adjust the divisor to fit your needs
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        usernameField.setFont(Font.font("Arial", fontSize));
        
        passwordField.setFont(Font.font("Arial", fontSize));
        loginButton.setFont(Font.font("Arial", fontSize/2));
        backButton.setFont(Font.font("Arial", fontSize/2));
    }

    public Scene getScene() {
        return scene;
    }
}
