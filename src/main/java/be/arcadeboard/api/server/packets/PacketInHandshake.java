package be.arcadeboard.api.server.packets;

import java.util.UUID;

public class PacketInHandshake extends PacketIn {
    private int protocolVersion = 1;
    private UUID playerUUID = null;

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }
}
