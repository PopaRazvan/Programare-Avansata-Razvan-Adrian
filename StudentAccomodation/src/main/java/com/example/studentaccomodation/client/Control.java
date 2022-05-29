package com.example.studentaccomodation.client;

public class Control {

    volatile private String userInput;
    volatile private String serverResponse;

    volatile private String currentUser;
    volatile private String currentPassword;
    volatile private boolean transferInput;
    volatile private boolean transferResponse;
    volatile public boolean drawAdmin;
    volatile public boolean drawUser;

    private static Control control = new Control();

    private Control() {
        userInput = "no_input";
        serverResponse = "no_response";
        transferInput = true;
        transferResponse = true;
    }

    static public Control getInstance(){
        return control;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(String serverResponse) {
        this.serverResponse = serverResponse;
    }

    public synchronized String receiveInput(){
        while(transferInput){
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread Interrupted");
            }
        }
        transferInput = true;
        notifyAll();
        return userInput;
    }

    public synchronized void sendInput(String input){
        while(!transferInput){
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread Interrupted");
            }
        }
        transferInput = false;
        setUserInput(input);
        notifyAll();
    }

    public synchronized String receiveResponse(){
        while(transferResponse){
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread Interrupted");
            }
        }
        transferResponse = true;
        notifyAll();
        return serverResponse;
    }

    public synchronized void sendResponse(String response){
        while(!transferResponse){
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread Interrupted");
            }
        }
        transferResponse = false;
        setServerResponse(response);
        notifyAll();
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
