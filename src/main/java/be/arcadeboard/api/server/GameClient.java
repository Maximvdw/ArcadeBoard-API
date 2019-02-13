package be.arcadeboard.api.server;

import javax.websocket.*;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class GameClient {
    private GameServer server = null;
    private Session userSession = null;

    /**
     * Connect to a game server
     *
     * @param uri URI to connect to
     * @throws URISyntaxException
     */
    public void connect(String uri) throws Exception {
        connect(new URI(uri));
        connect();
    }

    /**
     * Connect to a game server
     *
     * @param address server address
     * @param port    server port
     * @param path    server path
     * @param secure  secure websockets
     * @throws URISyntaxException
     */
    public void connect(String address, int port, String path, boolean secure) throws Exception {
        server = new GameServer(address, port, path, secure);
        connect();
    }

    /**
     * Connect to a game server
     *
     * @param uri URI to connect to
     */
    public void connect(URI uri) throws Exception {
        server = new GameServer(uri);
        connect();
    }

    private void connect() throws Exception {
        if (server == null) {
            throw new Exception("Server is not declared!");
        }
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, getServer().getURI());
    }

    public GameServer getServer(){
        return this.server;
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason      the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
    }
}
