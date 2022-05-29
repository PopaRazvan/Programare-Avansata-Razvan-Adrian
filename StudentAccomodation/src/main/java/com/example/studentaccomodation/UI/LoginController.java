package com.example.studentaccomodation.UI;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class LoginController {
    @FXML
    private PasswordField userPassword;
    @FXML
    private TextField userName;
    @FXML
    private Label successLoginLabel;
    @FXML
    private Label failedLoginLabel;
    public void initialize(){
        successLoginLabel.setVisible(false);
        failedLoginLabel.setVisible(false);
    }

    public void login(ActionEvent e){
        String username = userName.getText();
        String password = userPassword.getText();
        if(username.isEmpty()){
            unsuccessfulLogin();
            System.out.println("Null");
            return;
        }
        if(username.trim().equals("admin")){
            successfulLogin();
        }
        else{
            unsuccessfulLogin();
            System.out.println("|" + username + "|");
        }

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