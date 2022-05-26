package App.Server;

import App.Server.States.LoginState;
import App.Server.States.ServerState;

public class RequestDecoder {
    public static final int UNKNOWN_REQUEST_CODE = 0;
    public static final int STOP_CODE = 1;
    public static final int CLIENT_EXIT_CODE = 2;
    public static final int ADD_USER_CODE = 3;
    public static final int LAUNCH_INTERFACE_CODE = 4;
    public static final int LOGIN_CODE = 5;
    public static final int LOGOUT_CODE = 6;

    private static RequestDecoder requestDecoder = new RequestDecoder();

    ServerState serverState;

    public RequestDecoder() {
        serverState = new LoginState();
    }

    public int decodeRequest(String clientRequest) {

//        String adaptedRequest = clientRequest.toLowerCase().trim();
//
//        if (adaptedRequest.equals("stop")) {
//            return STOP_CODE;
//        }
//        if (adaptedRequest.equals("exit")) {
//            return CLIENT_EXIT_CODE;
//        }
//        if (adaptedRequest.equals("login")) {
//            return LOGIN_CODE;
//        }
//        if (adaptedRequest.equals("add user")) {
//            return ADD_USER_CODE;
//        }
//        if (adaptedRequest.equals("launch")) {
//            return LAUNCH_INTERFACE_CODE;
//        }
//        return UNKNOWN_REQUEST_CODE;
        return serverState.decodeRequest(clientRequest);
    }

    public static RequestDecoder getInstance() {
        return requestDecoder;
    }
}
