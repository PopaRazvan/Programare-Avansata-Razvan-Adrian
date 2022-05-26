package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;
import java.util.stream.Collectors;

public class ServerCommunication {

    private String serverAddress;
    private int PORT;
    private boolean running;
    private BufferedReader clientInput;
    private String request;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ServerCommunication(String serverAddress, int PORT) throws IOException {
        this.serverAddress = serverAddress;
        this.PORT = PORT;
        this.clientInput = new BufferedReader(new InputStreamReader(System.in));
        this.running = true;
        socket = new Socket(serverAddress, PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//      Implementation idea:
//        - Terminal used only for logging in to the server via username and password
//        - If logged in as admin then provide admin abilities
//        - If logged in as client then provide interface for viewing data
    }

    public void beginCommunication() throws IOException {

        while(running){
            request = clientInput.readLine();
            out.println(request);
            if(request.toLowerCase().equals("exit")) running = false;
            String response = getResponse();
            System.out.println(response);
        }
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
