package com.example.studentaccomodation.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    //TODO export Client to another project
    private String serverAddress;
    private int PORT;

    private boolean running = true;
    private BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in));
    private String request;

    public Client(String serverAddress, int PORT) {
        this.serverAddress = serverAddress;
        this.PORT = PORT;
    }

    public Client() {
        this.serverAddress = "127.0.0.1";
        this.PORT = 8100;
    }

    public void connect(){

        try{
            ServerCommunication serverComm = new ServerCommunication(serverAddress, PORT);
            serverComm.beginCommunication();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
