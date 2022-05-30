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
    public static final int LOGIN_CREDENTIALS_CODE = 8;
    public static final int CHANGE_PASSWORD_CODE = 9;
    public static final int GIVE_CREDENTIALS_CODE = 10;
    public static final int SAVE_NEW_STUDENT_CODE = 11;
    public static final int ADD_NEW_USER_CODE = 12;
    public static final int GET_ALL_ROOMS_CODE = 13;
    public static final int GET_ALL_STUDENTS_CODE = 14;
    public static final int GET_ALL_DORMS_CODE = 15;
    public static final int APPLY_ALGORITHM_CODE = 16;
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
