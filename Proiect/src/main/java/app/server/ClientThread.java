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
            case RequestDecoder.CHANGE_PASSWORD_CODE:
                return changePassword(request);
            case RequestDecoder.GIVE_CREDENTIALS_CODE:
                return giveCredentials(request);
            //TODO implement other requests handling

            default:
                return "Server received the request " + request;
        }
    }

    private String giveCredentials(String request) {//like: "gc:username"
        request = request.substring(3);
        System.out.println("Giving credentials");
        String response;
        //TODO get info from db (name, location, register number)
        response = "Balan Andrei, C4 camera 6, 310045RSL449102";
        return response;
    }

    private String changePassword(String request) { // like: "cp:username,new_password"
        request = request.substring(3);
        String[] credentials = request.split(",");
        String username = credentials[0];
        String pass = credentials[1];

        //TODO change password

        return "Done";
    }

    private String loginCredentials(String request) {
        request = request.substring(2);
        String[] credentials = request.split("\\|");
        String username;
        String password;
        if (credentials.length == 0) {
            username = null;
            password = null;
        } else if (credentials.length == 1) {
            username = credentials[0];
            password = null;
        } else {
            username = credentials[0];
            password = credentials[1];
        }

        if (username == null || username.isEmpty()) {
            return "ILU";
        }
        if (isValidUser(username, password)) {
            if (isSuperUser(username)) {
                System.out.println("Admin State:");
                return "VLA"; //valid login admin
            }
            System.out.println("Client State:");
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
