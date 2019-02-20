package be.arcadeboard.api.server.packets;

import be.arcadeboard.api.game.GameInformation;

public class PacketOutHandshake extends PacketOut {
    private int protocolVersion = 1;
    private GameInformation gameInformation = null;

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public GameInformation getGameInformation() {
        return gameInformation;
    }

    public void setGameInformation(GameInformation gameInformation) {
        this.gameInformation = gameInformation;
    }
}
