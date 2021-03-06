package com.example.studentaccomodation.UI;

import com.example.studentaccomodation.HelloApplication;
import com.example.studentaccomodation.client.Control;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController { //Class that controls the admin screen
    private Control control = Control.getInstance(); //Class to manage asynchronous communication between GUI and client logic

    @FXML
    private Label dataNotFull;
    @FXML
    private Label userDataIncomplete;
    @FXML
    private Label wrongDataUser;
    @FXML
    private Label wrongDataStudent;

    @FXML
    private TextField studentId;
    @FXML
    private TextField registerNr;
    @FXML
    private TextField sName;
    @FXML
    private TextField sSurname;
    @FXML
    private TextField sGender;
    @FXML
    private TextField sYear;
    @FXML
    private TextField sGroup;
    @FXML
    private TextField avgMark;
    @FXML
    private TextField birthDate;
    @FXML
    private TextField sEmail;
    @FXML
    private TextField prefStudent;

    @FXML
    private TextField userId;
    @FXML
    private TextField username;
    @FXML
    private TextField userPassword;
    @FXML
    private RadioButton isSuperuser;


    public void initialize(){
        dataNotFull.setVisible(false);
        userDataIncomplete.setVisible(false);
        wrongDataUser.setVisible(false);
        wrongDataStudent.setVisible(false);
    }
    public void logout(ActionEvent event) throws IOException { //Logs out of the application and returns to log in screen
        control.setCurrentUser("none");
        control.setCurrentPassword("none");
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(root, 810, 700);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void saveStudent(ActionEvent event){ //Tells the server to save a student with the given data
        dataNotFull.setVisible(false);
        wrongDataStudent.setVisible(false);
        if(registerNr.getText().isEmpty() || sName.getText().isEmpty() || sSurname.getText().isEmpty() || sYear.getText().isEmpty() || sGender.getText().isEmpty()
                || sGroup.getText().isEmpty() || avgMark.getText().isEmpty() || birthDate.getText().isEmpty() || studentId.getText().isEmpty() || prefStudent.getText().isEmpty()){ //Verifying for null fields
            dataNotFull.setVisible(true);
            return;
        }
        if(!studentId.getText().isEmpty() && !isInteger(studentId.getText())){//Checks for data type
            wrongDataStudent.setVisible(true);
            return;
        }
        if(!isInteger(sYear.getText()) || !isDouble(avgMark.getText()) || !isInteger(prefStudent.getText())){//Checks for data type
            wrongDataStudent.setVisible(true);
            return;
        }
        dataNotFull.setVisible(false);
        wrongDataStudent.setVisible(false);
        String input;
        if(studentId.getText().isEmpty()){
            input = "sns:";
        }
        else {
            input = "snis:";
            input += studentId.getText() + ",";
        }
        input += registerNr.getText() + ",";
        input += sName.getText() + ",";
        input += sSurname.getText() + ",";
        input += sGender.getText() + ",";
        input += sYear.getText() + ",";
        input += sGroup.getText() + ",";
        input += avgMark.getText() + ",";
        input += birthDate.getText() + ",";
        input += sEmail.getText() + ",";
        input += prefStudent.getText();
        System.out.println(input);
        control.sendInput(input);
    }

    public void addNewUser(ActionEvent event){ //Tells the server to add a new user with the given data
        wrongDataUser.setVisible(false);
        userDataIncomplete.setVisible(false);
        if(userId.getText().isEmpty() || username.getText().isEmpty() || userPassword.getText().isEmpty()){ //Verifying for null fields
            userDataIncomplete.setVisible(true);
            return;
        }
        if(!isInteger(userId.getText())){ //Checks for data type
            wrongDataUser.setVisible(true);
            return;
        }
        wrongDataUser.setVisible(false);
        userDataIncomplete.setVisible(false);
        String input = "anu:";
        input += userId.getText() + ",";
        input += username.getText() + ",";
        input += userPassword.getText() + ",";
        if(isSuperuser.isSelected()){
            input += "t";
        }
        else {
            input += "f";
        }
        System.out.println(input);
        control.sendInput(input);
    }

    private boolean isDouble(String value){ //Checks if String is a Double
        if (value == null) {
            return false;
        }
        try {
            double number = Double.parseDouble(value);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private  boolean isInteger(String value){ //Checks if String is an Integer
        if (value == null) {
            return false;
        }
        try {
            int number = Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void stopServer(ActionEvent event){
        control.sendInput("stop");
    }

    public void getAllStudents(ActionEvent event){
        control.sendInput("gas");
    }

    public void getAllRooms(ActionEvent event){
        control.sendInput("gar");
    }

    public void getAllDorms(ActionEvent event){
        control.sendInput("gad");
    }

    public void applyAlgorithm(ActionEvent event){ //Tells the server to apply the matching algorithm
        control.sendInput("aa");
    }
}
