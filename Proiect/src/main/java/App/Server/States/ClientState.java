package App.Server.States;

import App.Server.RequestDecoder;

public class ClientState implements ServerState {
    @Override
    public int decodeRequest(String request) {
        String adaptedRequest = request.toLowerCase().trim();

        if (adaptedRequest.equals("exit")) {
            return RequestDecoder.CLIENT_EXIT_CODE;
        }
        if (adaptedRequest.equals("launch")) {
            return RequestDecoder.LAUNCH_INTERFACE_CODE;
        }
        if (adaptedRequest.equals("logout")) {
            return RequestDecoder.LOGOUT_CODE;
        }
        return RequestDecoder.UNKNOWN_REQUEST_CODE;
    }
}
