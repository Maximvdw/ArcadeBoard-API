package be.arcadeboard.api.server;


import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.DefaultSSLWebSocketServerFactory;
import org.java_websocket.server.WebSocketServer;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.CertificateException;

public class GameServer extends WebSocketServer {
    public GameServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    public GameServer(InetSocketAddress address) {
        super(address);
    }

    /**
     * Configure SSL on the server
     *
     * @param storeType     Store type (JKS)
     * @param keystore      Key store file (file.jks)
     * @param storePassword Store password
     * @param keyPassword   Key password
     * @throws KeyStoreException
     * @throws FileNotFoundException
     */
    public void configureSSL(String storeType, File keystore, String storePassword, String keyPassword) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        // load up the key store
        KeyStore ks = KeyStore.getInstance(storeType);
        ks.load(new FileInputStream(keystore), storePassword.toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, keyPassword.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);

        SSLContext sslContext = null;
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        setWebSocketFactory(new DefaultSSLWebSocketServerFactory(sslContext));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {

    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
e.printStackTrace();
    }

    @Override
    public void onStart() {
        // Default connection timeout
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }
}
