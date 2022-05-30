package com.example.studentaccomodation.client;

import java.io.*;
import java.net.Socket;

public class ServerCommunication {

    private String serverAddress;
    private int PORT;
    private boolean running;
    private BufferedReader clientInput;
    private String request;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private Control control;

    public ServerCommunication(String serverAddress, int PORT) throws IOException {
        this.serverAddress = serverAddress;
        this.PORT = PORT;
        this.clientInput = new BufferedReader(new InputStreamReader(System.in));
        this.running = true;
        socket = new Socket(serverAddress, PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        control = Control.getInstance();
    }

    public void beginCommunication() throws IOException { //Communicates with the server
                                //Establishes a communication between client's UI and server logic

        while(running){ //Main interaction with the server
            request = control.receiveInput(); //Waiting for client input (from UI)
            System.out.println("Read: " + request);
            out.println(request); //Sends the request to the server
            System.out.println("Sent to server");
            if(request.toLowerCase().equals("exit")) running = false;
            else if(request.equals("send file")){
                receiveFile("src/main/resources/receivedFiles/file.txt");
            }
            else if(request.startsWith("l:")){ //Treats sending a login request
                String response = getResponse();
                control.sendResponse(response);
                System.out.println(response);
            }
            else if(request.startsWith("gc:")){//Treats sending a get credentials (info) of the current user
                String response = getResponse();
                control.sendResponse(response);
            }
            else if(request.startsWith("cp:")){//Treats sending a change password request
                String response = getResponse();
                System.out.println(response);
            }
            else if(request.startsWith("sn")){//Treats sending a save new student request
                String response = getResponse();
                System.out.println(response);
            }
            else if(request.startsWith("anu:")){//Treats sending an add new user request
                String response = getResponse();
                System.out.println(response);
            }
            else if(request.equals("stop")){//Treats sending a stop server request
                String response = getResponse();
                System.out.println(response);
            }
            else if(request.equals("gar")){//Treats sending a get all rooms request
                receiveFile("src/main/resources/receivedFiles/rooms.json");
            }
            else if(request.equals("gad")){//Treats sending a get all dorms request
                receiveFile("src/main/resources/receivedFiles/dorms.json");
            }
            else if(request.equals("gas")){//Treats sending a get all students request
                receiveFile("src/main/resources/receivedFiles/students.json");
            }
            else if(request.equals("aa")){//Treats sending an apply algorithm request
                String response = getResponse();
                System.out.println(response);
            }
            else{//Treats unhandled requests
                String response = getResponse();
                System.out.println(response);
            }
            System.out.println("Communication success...");
        }
    }

    private void receiveFile(String path) throws IOException { //Method for receiving a file through socket
        int bytesRead;
        int current = 0;
        int FILE_SIZE = 6022386;
        byte [] bytes  = new byte [FILE_SIZE];
        InputStream inputStream = socket.getInputStream();
        File outputFile = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bytesRead = inputStream.read(bytes,0,bytes.length);
        current = bytesRead;

        bufferedOutputStream.write(bytes, 0 , current);


        bufferedOutputStream.flush();
        fileOutputStream.close();
        bufferedOutputStream.close();
        System.out.println("File received!");
    }

    private String getResponse() throws IOException { //Method for receiving server response
        String response = new String();
        while (true){
            char charBuff = (char)in.read();
            if(charBuff == '*') break;
            response+=charBuff;
        }
        return response;
    }
}
