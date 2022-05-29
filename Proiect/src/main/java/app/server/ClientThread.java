package app.server;

import app.server.states.AdminState;
import app.server.states.ClientState;
import app.server.states.LoginState;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private final RequestDecoder requestDecoder;
    private boolean servingClient;
    private boolean adminUser;

    //TODO link with database
    //TODO implement current user / authorize admin user
    public ClientThread(Socket socket, ServerSocket serverSocket) {
        this.socket = socket;
        this.serverSocket = serverSocket;
        this.servingClient = true;
        this.requestDecoder = RequestDecoder.getInstance();
        this.adminUser = false;
    }

    public void run() {
        //Thread responsible for communicating with the client
        try {
            while (servingClient) { //Main While loop
                String request = readFromClient();
                String response = processRequest(request);
                sendResponse(response);
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
//            Implementation idea:
//            - Terminal used only for logging in to the server via username and password
//            - If logged in as admin then provide admin abilities
//            - If logged in as client then provide interface for viewing data
        switch (requestDecoder.decodeRequest(request)) {
            case RequestDecoder.STOP_CODE:
                serverSocket.close();
                return "Server stopped!";
            case RequestDecoder.UNKNOWN_REQUEST_CODE:
                return "Server received unknown request: " + request;
            case RequestDecoder.CLIENT_EXIT_CODE:
                servingClient = false;
                return "Closing connection...";
            case RequestDecoder.ADD_USER_CODE:
                return addUser();
            case RequestDecoder.LOGIN_CODE:
                return login();
            case RequestDecoder.LOGOUT_CODE:
                return logout();
            case RequestDecoder.LAUNCH_INTERFACE_CODE:
                return launchInterface();
            case RequestDecoder.SEND_FILE_CODE:
                return sendFile("src/main/resources/IOFiles/test.txt");
            case RequestDecoder.LOGIN_CREDENTIALS_CODE:
                return loginCredentials(request);
            //TODO implement other requests handling

            default:
                return "Server received the request " + request;
        }
    }

    private String loginCredentials(String request) {
        request = request.substring(2);
        String[] credentials = request.split("\\|");
        String username = credentials[0];
        String password = credentials[1];
        if (isValidUser(username, password)) {
            if (isSuperUser(username)) {
                return "VLA"; //valid login admin
            }
            return "VLU"; //valid login user
        }
        return "ILU"; //invalid login user
    }

    private boolean isSuperUser(String username) {
        if (username.equals("admin")) {
            return true;
        }
        System.out.println("|" + username + "|");
        return false;
    }

    private boolean isValidUser(String username, String password) {
        return true;
        //TODO implement validation
    }

    private String addUser() throws IOException {
        sendResponse("Provide username:");
        String name = readFromClient();
        sendResponse("Provide password:");
        String surname = readFromClient();
        return "Added";
    }

    private String readFromClient() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return in.readLine();
    }

    private void sendResponse(String response) throws IOException {
        if (response.equals("nores")) return;
        response += "*";
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.print(response);
        out.flush();
    }

    private String login() throws IOException {
        sendResponse("Provide username:");
        String username = readFromClient();
        if (username.trim().equals("admin")) {
            requestDecoder.setState(new AdminState());
        } else {
            requestDecoder.setState(new ClientState());
        }
        return "Logged in!";
    }

    private String logout() throws IOException {
        requestDecoder.setState(new LoginState());
        return "Logged out!";
    }

    private String launchInterface() {
        return "Launching app...";
    }

    private String sendFile(String path) throws IOException {
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        File myFile = new File(path);
        byte[] byteArray = new byte[(int) myFile.length()];
        fileInputStream = new FileInputStream(myFile);
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        bufferedInputStream.read(byteArray, 0, byteArray.length);
        bufferedInputStream.close();
        outputStream = socket.getOutputStream();
        outputStream.write(byteArray, 0, byteArray.length);
        outputStream.flush();
        return "nores";
    }
}
