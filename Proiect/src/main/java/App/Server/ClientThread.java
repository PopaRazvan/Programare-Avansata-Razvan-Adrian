package App.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread extends Thread{

    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private final RequestDecoder requestDecoder;
    private boolean servingClient;
    //TODO link with database
    //TODO implement current user / authorize admin user
    public ClientThread(Socket socket, ServerSocket serverSocket) {
        this.socket = socket;
        this.serverSocket = serverSocket;
        this.servingClient = true;
        this.requestDecoder = new RequestDecoder();
    }

    public void run() {
        //Thread responsible for communicating with the client
        try {
            while (servingClient) { //Main While loop
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String request = in.readLine();
                String response = processRequest(request);
                response += "*";
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.print(response);
                out.flush();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String processRequest(String request) throws IOException {
        switch (requestDecoder.decodeRequest(request)){
            case RequestDecoder.STOP_CODE:
                serverSocket.close();
                return "Server stopped!";
            case RequestDecoder.UNKNOWN_REQUEST_CODE:
                return "Server received unknown request: " + request;
            case RequestDecoder.CLIENT_EXIT_CODE:
                servingClient = false;
                return "Closing connection...";
        }
        //TODO implement other requests handling
        return "Server received the request " + request;
    }
}
