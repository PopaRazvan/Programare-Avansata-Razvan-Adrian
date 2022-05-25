package App.Server;

import App.Manager.Manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    private int PORT;

    public Server() {
        PORT = 8100;
    }

    public Server(int PORT) {
        this.PORT = PORT;
    }

    public void runServer() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                new ClientThread(socket, serverSocket).start();
            }
        } catch (IOException e) {
            if (e.getClass() == SocketException.class) {
                System.out.println(e.getMessage());
            } else System.err.println("Error: " + e);
        } finally {
            serverSocket.close();
        }
    }
}
