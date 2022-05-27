package app.server;

import app.server.states.LoginState;
import app.server.states.ServerState;

public class RequestDecoder {
    public static final int UNKNOWN_REQUEST_CODE = 0;
    public static final int STOP_CODE = 1;
    public static final int CLIENT_EXIT_CODE = 2;
    public static final int ADD_USER_CODE = 3;
    public static final int LAUNCH_INTERFACE_CODE = 4;
    public static final int LOGIN_CODE = 5;
    public static final int LOGOUT_CODE = 6;
    public static final int SEND_FILE_CODE = 7;

    private static RequestDecoder requestDecoder = new RequestDecoder();

    private ServerState serverState;

    public RequestDecoder() {
        serverState = new LoginState();
    }

    public int decodeRequest(String clientRequest) {
        return serverState.decodeRequest(clientRequest);
    }

    public static RequestDecoder getInstance() {
        return requestDecoder;
    }

    public void setState(ServerState newState) {
        serverState = newState;
    }
}
