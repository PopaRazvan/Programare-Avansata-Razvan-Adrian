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
        if (adaptedRequest.startsWith("cp:")) {
            return RequestDecoder.CHANGE_PASSWORD_CODE;
        }
        if (adaptedRequest.startsWith("gc:")) { //give information about user
            return RequestDecoder.GIVE_CREDENTIALS_CODE;
        }
        if (adaptedRequest.startsWith("snis:") || adaptedRequest.startsWith("sns:")) { //"save new id student" , "save new student"
            return RequestDecoder.SAVE_NEW_STUDENT_CODE;
        }
        if (adaptedRequest.startsWith("anu:")) {
            return RequestDecoder.ADD_NEW_USER_CODE;
        }
        if (adaptedRequest.equals("stop")) {
            return RequestDecoder.STOP_CODE;
        }
        if (adaptedRequest.equals("gar")) {
            return RequestDecoder.GET_ALL_ROOMS_CODE;
        }
        if (adaptedRequest.equals("gas")) {
            return RequestDecoder.GET_ALL_STUDENTS_CODE;
        }
        if (adaptedRequest.equals("gad")) {
            return RequestDecoder.GET_ALL_DORMS_CODE;
        }
        if (adaptedRequest.equals("aa")) {
            return RequestDecoder.APPLY_ALGORITHM_CODE;
        }
        return RequestDecoder.UNKNOWN_REQUEST_CODE;
    }
}
