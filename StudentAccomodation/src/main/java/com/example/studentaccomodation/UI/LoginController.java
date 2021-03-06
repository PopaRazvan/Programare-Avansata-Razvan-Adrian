package com.example.studentaccomodation.UI;

import com.example.studentaccomodation.HelloApplication;
import com.example.studentaccomodation.client.Control;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LoginController { //Class that controls the login screen

    private class Logger extends Thread{ //Separate class to manage login and authorization on a separate thread
        private LoginController loginController;

        public Logger(LoginController loginController) {
            this.loginController = loginController;
        }

        @Override
        public void run(){
            String username = userName.getText();
            String password = userPassword.getText();

            control.sendInput("l:" + username + '|' + password);
            System.out.println("Sent: " + control.getUserInput());
            String response = control.receiveResponse();
            System.out.println("Received: " + response);
            if(response.equals("ILU")){
                loginController.unsuccessfulLogin();
            }
            else if(response.equals("VLU")){
                control.setCurrentUser(username);
                control.setCurrentPassword(password);
                loginController.successfulLogin();
                control.drawUser = true;
            }
            else if(response.equals("VLA")){
                control.setCurrentUser(username);
                control.setCurrentPassword(password);
                loginController.successfulLogin();
                control.drawAdmin = true;
            }
            System.out.println("End Transmission");
        }
    }

//    FXML injections
    @FXML
    private PasswordField userPassword;
    @FXML
    private TextField userName;
    @FXML
    private Label successLoginLabel;
    @FXML
    private Label failedLoginLabel;

    @FXML
    private VBox loginContent;

    private Stage stage;
    private Parent root;
    private Scene scene;

    private Control control = Control.getInstance(); //Class to manage asynchronous communication between GUI and client logic

    private String localInput;
    private String localResponse = "null";
    public void initialize(){
        control.drawAdmin = false;
        successLoginLabel.setVisible(false);
        failedLoginLabel.setVisible(false);
    }

    public void login(ActionEvent event){ //Logging in into the application after the authorization was complete
        Logger logger = new Logger(this);
        logger.start();
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(control.drawUser){
            try {
                drawUserInterface(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(control.drawAdmin){
            try {
                drawAdminInterface(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void successfulLogin(){ //Feedback for successful login
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(700));
        fadeTransition.setNode(successLoginLabel);
        fadeTransition.setFromValue(1);
        textTimer(fadeTransition, successLoginLabel);
    }

    private void unsuccessfulLogin(){//Feedback for unsuccessful login
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(700));
        fadeTransition.setNode(failedLoginLabel);
        fadeTransition.setFromValue(1.0);
        textTimer(fadeTransition, failedLoginLabel);
    }

    private void textTimer(FadeTransition fadeTransition, Label failedLoginLabel) { //Manages fade-in and fade-out animations
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
        failedLoginLabel.setVisible(true);
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1.2));
        pauseTransition.setOnFinished(e -> fadeTransition.playFromStart());
        PauseTransition disableText = new PauseTransition(Duration.seconds(2));
        disableText.setOnFinished(e -> {
            //Disabling the text and setting fade back to 1
            failedLoginLabel.setVisible(false);
            FadeTransition ft = new FadeTransition(Duration.millis(10), failedLoginLabel);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.setCycleCount(1);
            ft.playFromStart();
        });
        pauseTransition.playFromStart();
        disableText.playFromStart();
    }

    private void drawUserInterface(ActionEvent event) throws IOException { // Brings up the ordinary user's interface
        control.drawUser = false;
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("user.fxml"));
        Scene scene = new Scene(root, 810, 700);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    private void drawAdminInterface(ActionEvent event) throws IOException { // Brings up the admin's interface
        control.drawAdmin = false;
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("admin.fxml"));
        Scene scene = new Scene(root, 810, 700);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public static void exitClient(){ //Terminates back-end client processes
        Control controller = Control.getInstance();
        controller.sendInput("exit");
    }
}