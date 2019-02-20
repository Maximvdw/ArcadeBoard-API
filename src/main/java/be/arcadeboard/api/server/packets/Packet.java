package be.arcadeboard.api.server.packets;


import java.io.Serializable;

public abstract class Packet implements Serializable {
    private int id = 0;

    /**
     * Get packet ID
     *
     * @return packet id
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
