package be.arcadeboard.api.music;

import java.util.HashMap;
import java.util.Map;

public class Layer {
    private String name = "";
    private Map<Short, Note> notes = new HashMap<Short, Note>();
    private float volume = 100;

    /**
     * Get the note at tick
     *
     * @param tick Tick to get note
     * @return Note if available
     */
    public Note getNote(short tick) {
        return notes.get(tick);
    }

    /**
     * Get layer volume
     *
     * @return layer volume
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Set layer volume
     *
     * @param volume
     */
    protected void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * Get layer name
     *
     * @return layer name
     */
    public String getName() {
        return name;
    }

    /**
     * Set layer name
     *
     * @param name layer name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set note at tick
     *
     * @param tick tick
     * @param note Note to set
     */
    public void setNote(short tick, Note note) {
        notes.put(tick, note);
    }
}
