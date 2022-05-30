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
//      Implementation idea:
//        - Terminal used only for logging in to the server via username and password
//        - If logged in as admin then provide admin abilities
//        - If logged in as com.example.studentaccomodation.client then provide interface for viewing data
    }

    public void beginCommunication() throws IOException {

        while(running){
            //request = clientInput.readLine();
            request = control.receiveInput();
            System.out.println("Read: " + request);
            out.println(request);
            System.out.println("Sent to server");
            if(request.toLowerCase().equals("exit")) running = false;
            else if(request.equals("send file")){
                receiveFile("src/main/resources/receivedFiles/file.txt");
            }
            else if(request.startsWith("l:")){
                String response = getResponse();
                control.sendResponse(response);
                System.out.println(response);
            }
            else if(request.startsWith("gc:")){
                String response = getResponse();
                control.sendResponse(response);
            }
            else if(request.startsWith("cp:")){
                String response = getResponse();
                System.out.println(response);
            }
            else if(request.startsWith("sn")){
                String response = getResponse();
                System.out.println(response);
            }
            else if(request.startsWith("anu:")){
                String response = getResponse();
                System.out.println(response);
            }
            else if(request.equals("stop")){
                String response = getResponse();
                System.out.println(response);
            }
            else if(request.equals("gar")){
                receiveFile("src/main/resources/receivedFiles/rooms.json");

            }
            else if(request.equals("gad")){
                receiveFile("src/main/resources/receivedFiles/dorms.json");

            }
            else if(request.equals("gas")){
                receiveFile("src/main/resources/receivedFiles/students.json");
                //System.out.println(response);
            }
            else if(request.equals("aa")){
                String response = getResponse();
                System.out.println(response);
            }
            else{
                String response = getResponse();
                System.out.println(response);
            }
            System.out.println("End loop");
        }
    }

    private void receiveFile(String path) throws IOException {
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
        //System.out.println(getResponse());
        System.out.println("Done!");
    }

    private String getResponse() throws IOException {
        String response = new String();
        while (true){
            char charBuff = (char)in.read();
            if(charBuff == '*') break;
            response+=charBuff;
        }
        return response;
    }
}
