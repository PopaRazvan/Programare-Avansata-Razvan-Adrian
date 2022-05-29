package com.example.studentaccomodation.UI;

import com.example.studentaccomodation.client.Control;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class LoginController {

    private class Logger extends Thread{
        LoginController loginController;

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
            else{
                loginController.successfulLogin();
            }
            System.out.println("End");
        }
    }
    @FXML
    private PasswordField userPassword;
    @FXML
    private TextField userName;
    @FXML
    private Label successLoginLabel;
    @FXML
    private Label failedLoginLabel;

    private Control control = Control.getInstance();

    private String localInput;
    private String localResponse = "null";
    public void initialize(){
        successLoginLabel.setVisible(false);
        failedLoginLabel.setVisible(false);
    }

    public void login(ActionEvent e){
        Logger logger = new Logger(this);
        logger.start();
    }

    private void successfulLogin(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(700));
        fadeTransition.setNode(successLoginLabel);
        fadeTransition.setFromValue(1);
        textTimer(fadeTransition, successLoginLabel);
    }

    private void unsuccessfulLogin(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(700));
        fadeTransition.setNode(failedLoginLabel);
        fadeTransition.setFromValue(1.0);
        textTimer(fadeTransition, failedLoginLabel);
    }

    private void textTimer(FadeTransition fadeTransition, Label failedLoginLabel) {
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


}