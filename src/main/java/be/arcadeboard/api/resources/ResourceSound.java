package be.arcadeboard.api.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ResourceSound {
    private String name = "";
    private String category = "";
    private List<InputStream> soundStreams = new ArrayList<InputStream>();

    public ResourceSound(String name, File soundFile) throws FileNotFoundException {
        setName(name);
        addSoundFile(soundFile);
    }

    public ResourceSound(String name, InputStream soundStream) {
        setName(name);
        addSoundStream(soundStream);
    }

    /**
     * Get sound name
     *
     * @return sound name
     */
    public String getName() {
        return name;
    }

    /**
     * Set sound name
     *
     * @param name sound name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get sound streams
     *
     * @return sound file streams
     */
    public List<InputStream> getSoundStreams() {
        return soundStreams;
    }

    /**
     * Add sound file
     *
     * @param file sound file
     */
    public void addSoundFile(File file) throws FileNotFoundException {
        InputStream initialStream = new FileInputStream(file);
        addSoundStream(initialStream);
    }

    public void addSoundStream(InputStream stream) {
        this.soundStreams.add(stream);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
