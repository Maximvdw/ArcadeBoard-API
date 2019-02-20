package be.arcadeboard.server;

import be.arcadeboard.api.server.GameClient;
import be.arcadeboard.api.server.GameServer;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.UnknownHostException;

public class GameServerTest {

    private class HigherLowerServer extends GameServer {
        public HigherLowerServer(int port) throws UnknownHostException {
            super(port);
        }
    }

    private class HigherLowerClient extends GameClient {

        public HigherLowerClient(URI serverUri) {
            super(serverUri);
        }

    }

    private HigherLowerServer server = null;

    @Before
    public void setup() throws UnknownHostException {
        server = new HigherLowerServer(6028);
        server.start();
    }

    @Test
    public void testConnection() throws Exception {
        HigherLowerClient client = new HigherLowerClient(new URI("ws://localhost:6028"));
        client.connect();
    }
}
