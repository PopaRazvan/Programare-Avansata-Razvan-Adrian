package App.Server.States;

import App.Server.ClientThread;

public interface ServerState {
    public int decodeRequest(String request);
}
