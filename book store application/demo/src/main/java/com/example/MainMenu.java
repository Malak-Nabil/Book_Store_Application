package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox; // Import for HBox
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenu extends Application {

    private Scene scene;
    

    @Override
    public void start(Stage primaryStage) {
        Background commonBackground = CommonBackground.createCommonBackground();
        
        // Create UI components
        Button button2 = new Button("Create Account");
        Button button3 = new Button("Log In");
        
        button2.setOnAction(e -> showCreateAccountScene(primaryStage));
        button3.setOnAction(e -> showLogInScene(primaryStage));

        // Create and configure the HBox layout for buttons
        HBox hbox = new HBox(10, button2, button3);
        hbox.setAlignment(Pos.TOP_RIGHT); // Center the buttons horizontally
        hbox.setSpacing(20);
        hbox.setPadding(new Insets(20)); // Add padding around the HBox

        // Create and configure the BorderPane layout
        BorderPane root = new BorderPane();
        root.setCenter(hbox); // Place the HBox (containing buttons) in the center
        root.setPadding(new Insets(10));
        root.setBackground(commonBackground);
        
        // Create and configure the scene
        scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bookstore");
        
        primaryStage.show();

        button2.setPrefWidth(scene.getWidth()/4);
        button2.setFont(Font.font("Arial", scene.getWidth()/40));
        button3.setPrefWidth(scene.getWidth()/8);
        button3.setFont(Font.font("Arial", scene.getWidth()/40));


        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newWidth = newVal.doubleValue();
            button2.setPrefWidth(newWidth / 4);
            button2.setFont(Font.font("Arial", newWidth / 40));
            button3.setPrefWidth(newWidth / 8);
            button3.setFont(Font.font("Arial", newWidth / 40));
        });
    }

    public Scene getScene() {
        return scene;
    }
    private void showCreateAccountScene(Stage primaryStage) {
        CreateAccount createAccountScene = new CreateAccount(primaryStage, this);
        primaryStage.setScene(createAccountScene.getScene());
    }

    private void showLogInScene(Stage primaryStage) {
        Login logInScene = new Login(primaryStage, this);
        primaryStage.setScene(logInScene.getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
