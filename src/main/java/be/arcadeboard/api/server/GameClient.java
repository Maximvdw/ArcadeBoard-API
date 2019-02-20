package be.arcadeboard.api.server;

import be.arcadeboard.api.server.packets.Packet;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class GameClient extends WebSocketClient {
    private Map<Integer,Packet> packetMap = new HashMap<>();

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

    public Map<Integer, Packet> getPacketMap() {
        return packetMap;
    }

    public void setPacketMap(Map<Integer, Packet> packetMap) {
        this.packetMap = packetMap;
    }
}
