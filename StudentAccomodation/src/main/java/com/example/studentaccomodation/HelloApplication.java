package com.example.studentaccomodation;

import com.example.studentaccomodation.UI.LoginController;
import com.example.studentaccomodation.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException { //Sets up the main stage of the application
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root, 810, 700);
        stage.setTitle("Student Accommodation");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            System.out.println("Closing application...");
            LoginController.exitClient();
        });

    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start(); //Runs the client-server communication
        launch();
    }
}