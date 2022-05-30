package com.example.studentaccomodation.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client extends Thread{ //Class that defines a client side communication
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

    public void connect(){//Connects to server

        try{
            ServerCommunication serverComm = new ServerCommunication(serverAddress, PORT);
            serverComm.beginCommunication(); //Start server-client interaction
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() { //Starts server connection on a separate thread
        connect();
    }
}
