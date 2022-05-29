package com.example.studentaccomodation.UI;

import com.example.studentaccomodation.HelloApplication;
import com.example.studentaccomodation.client.Control;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    class Fetcher extends Thread {
        private UserController userController;

        public Fetcher(UserController userController) {
            this.userController = userController;
        }

        @Override
        public void run() {
            control.sendInput("gc:" + control.getCurrentUser());
            System.out.println("Sent: " + control.getUserInput());
            String response = control.receiveResponse();
            System.out.println("Received: " + control.getServerResponse());
            userController.getCredentials().clear();
            String[] values = response.split(",");
            userController.getCredentials().add(values[0]);
            userController.getCredentials().add(values[1]);
            userController.getCredentials().add(values[2]);
        }
    }

    private Control control = Control.getInstance();
    @FXML
    private Label name;
    @FXML
    private Label studentLocation;
    @FXML
    private Label registerNumber;

    @FXML
    private TextField newPassword;

    private volatile List<String> credentials = new ArrayList<>();

    public void initialize() {
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        Fetcher fetcher = new Fetcher(this);
        fetcher.start();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        name.setText(credentials.get(0));
        studentLocation.setText(credentials.get(1));
        registerNumber.setText(credentials.get(2));
    }

    public void logout(ActionEvent event) throws IOException {
        control.setCurrentUser("none");
        control.setCurrentPassword("none");
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(root, 810, 700);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void changePassword(ActionEvent event) {
        String password = newPassword.getText();
        control.sendInput("cp:" + control.getCurrentUser() + "," + password);
        newPassword.setText("");
    }

    public List<String> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<String> credentials) {
        this.credentials = credentials;
    }
}
