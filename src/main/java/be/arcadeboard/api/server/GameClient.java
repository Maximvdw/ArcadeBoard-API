package be.arcadeboard.api.server;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class GameClient extends WebSocketClient {

    public GameClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Client: open");
    }

    @Override
    public void onMessage(String s) {

    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Client: close");
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
