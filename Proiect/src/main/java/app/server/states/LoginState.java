package app.server.states;

import app.server.RequestDecoder;

public class LoginState implements ServerState {
    @Override
    public int decodeRequest(String request) {
        String adaptedRequest = request.toLowerCase().trim();

        if (adaptedRequest.equals("exit")) {
            return RequestDecoder.CLIENT_EXIT_CODE;
        }
        if (adaptedRequest.equals("login")) {
            return RequestDecoder.LOGIN_CODE;
        }
        if (adaptedRequest.equals("send file")) {
            return RequestDecoder.SEND_FILE_CODE;
        }
        if (adaptedRequest.startsWith("l:")) {
            return RequestDecoder.LOGIN_CREDENTIALS_CODE;
        }
        return RequestDecoder.UNKNOWN_REQUEST_CODE;
    }
}
