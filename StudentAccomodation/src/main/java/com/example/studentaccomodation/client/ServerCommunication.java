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
            out.println(request);
            if(request.toLowerCase().equals("exit")) running = false;
            if(request.equals("send file")){
                receiveFile();
            }
            if(request.startsWith("l:")){
                String response = getResponse();
                control.sendResponse(response);
                System.out.println(response);
            }
            else{
                String response = getResponse();
                control.receiveInput();
                System.out.println(response);
            }
        }
    }

    private void receiveFile() throws IOException {
        String path = "src/main/resources/receivedFiles/file.txt";
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
