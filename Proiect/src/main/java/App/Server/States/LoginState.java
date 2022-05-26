package App.Server.States;

import App.Server.RequestDecoder;

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
        return RequestDecoder.UNKNOWN_REQUEST_CODE;
    }
}
