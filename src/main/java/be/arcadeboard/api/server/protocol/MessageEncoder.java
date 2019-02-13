package be.arcadeboard.api.server.protocol;

import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * MessageEncoder
 * Created by Maxim on 26/01/2019.
 */
public class MessageEncoder implements Encoder.Text<Message> {
    private static Gson gson = new Gson();

    public String encode(Message message) throws EncodeException {
        return gson.toJson(message);
    }

    public void init(EndpointConfig endpointConfig) {

    }

    public void destroy() {

    }
}
