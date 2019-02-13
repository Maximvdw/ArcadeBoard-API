package be.arcadeboard.api.server;

import be.arcadeboard.api.server.protocol.Message;
import be.arcadeboard.api.server.protocol.MessageDecoder;
import be.arcadeboard.api.server.protocol.MessageEncoder;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "{path}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class)
public class GameServer {
    private URI uri = null;
    private String path = "";
    private Session session;
    private static Set<GameClient> gameClients = new CopyOnWriteArraySet<GameClient>();
    private static HashMap<String, String> users = new HashMap<String, String>();

    public GameServer(int port, String path) {
        setPath(path);
    }

    protected GameServer(URI uri) {
        setURI(uri);
    }

    protected GameServer(String address, int port, String path, boolean secure) throws URISyntaxException {
        setURI(new URI((secure ? "wss" : "ws") + "://" + address + ":" + port + path));
    }

    public static Set<GameClient> getGameClients() {
        return gameClients;
    }

    public static void setGameClients(Set<GameClient> gameClients) {
        GameServer.gameClients = gameClients;
    }

    @OnOpen
    public void onConnectionOpen(final Session session, @PathParam("path") final String path) {
        session.getUserProperties().put("path", path); // Set path
    }

    @OnMessage
    public void onMessage(Session session, Message message)
            throws IOException {

        //message.setFrom(users.get(session.getId()));
    }

    /**
     * Get server address
     *
     * @return server address
     */
    public String getAddress() {
        return getURI().getHost();
    }


    /**
     * Get server port
     *
     * @return server port
     */
    public int getPort() {
        return getURI().getPort();
    }

    /**
     * Get server URI
     *
     * @return server URI
     */
    public URI getURI() {
        return uri;
    }

    /**
     * Set server URI
     *
     * @param uri server URI
     */
    public void setURI(URI uri) {
        this.uri = uri;
    }

    /**
     * Get server path
     *
     * @return server path
     */
    public String getPath() {
        return path;
    }

    /**
     * Set server path
     *
     * @param path server path
     */
    public void setPath(String path) {
        this.path = path;
    }
}
