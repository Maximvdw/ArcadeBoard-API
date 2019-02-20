package be.arcadeboard.api.server.packets;

import com.google.gson.JsonObject;

public abstract class Packet {
    private int packetId = 0;
    private JsonObject jsonObject = null;

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public int getPacketId() {
        return packetId;
    }

    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }
}
