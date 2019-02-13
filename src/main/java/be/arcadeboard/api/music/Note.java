package be.arcadeboard.api.music;

public class Note {
    private byte instrument;
    private byte key;

    public Note(byte instrument, byte key) {
        setKey(key);
        setInstrument(instrument);
    }

    public byte getInstrument() {
        return instrument;
    }

    public void setInstrument(byte instrument) {
        this.instrument = instrument;
    }

    public byte getKey() {
        return key;
    }

    public void setKey(byte key) {
        this.key = key;
    }
}
