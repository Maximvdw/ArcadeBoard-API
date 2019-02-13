package be.arcadeboard.api.music;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * NoteBlockSong
 * <p>
 * Modified from NoteBlockAPI NBSDecoder
 * https://github.com/koca2000/NoteBlockAPI/blob/master/src/main/java/com/xxmicloxx/NoteBlockAPI/NBSDecoder.java
 */
public class NoteBlockSong {
    private Map<Short, Layer> layers = new HashMap<Short, Layer>();
    private File file = null;
    private String title = "";
    private String author = "";
    private String description = "";
    private boolean repeat = false;
    private float tempo = 0F;
    private short length = 0;

    /**
     * Load song from input strema
     *
     * @param stream input stream
     * @return song
     * @throws IOException
     */
    public static NoteBlockSong fromStream(InputStream stream) throws IOException {
        NoteBlockSong song = new NoteBlockSong();
        song.layers = new HashMap<Short, Layer>();
        byte biggestInstrumentIndex = -1;
        DataInputStream dis = new DataInputStream(stream);
        song.setLength(readShort(dis));
        short songHeight = readShort(dis);
        String title = readString(dis);
        String author = readString(dis);
        readString(dis);
        String description = readString(dis);
        song.setTempo(readShort(dis) / 100f);
        dis.readBoolean(); // auto-save
        dis.readByte(); // auto-save duration
        dis.readByte(); // x/4ths, time signature
        readInt(dis); // minutes spent on project
        readInt(dis); // left clicks (why?)
        readInt(dis); // right clicks (why?)
        readInt(dis); // blocks added
        readInt(dis); // blocks removed
        readString(dis); // .mid/.schematic file name
        short tick = -1;
        while (true) {
            short jumpTicks = readShort(dis); // jumps till next tick
            if (jumpTicks == 0) {
                break;
            }
            tick += jumpTicks;
            short layerIdx = -1;
            while (true) {
                short jumpLayers = readShort(dis); // jumps till next layer
                if (jumpLayers == 0) {
                    break;
                }
                layerIdx += jumpLayers;
                byte instrument = dis.readByte();
                if (instrument > biggestInstrumentIndex) {
                    biggestInstrumentIndex = instrument;
                }
                Layer layer = song.layers.get(layerIdx);
                if (layer == null) {
                    // Create a new layer
                    layer = new Layer();
                    song.layers.put(layerIdx, layer);
                }
                layer.setNote(tick, new Note(instrument, dis.readByte()));
            }
        }
        for (int i = 0; i < songHeight; i++) {
            Layer layer = song.layers.get(i);

            String name = readString(dis);
            byte volume = dis.readByte();
            if (layer != null) {
                layer.setName(name);
                layer.setVolume(volume);
            }
        }

        dis.close(); // Close stream

        // Meta information
        song.setTitle(title);
        song.setAuthor(author);
        song.setDescription(description);
        return song;
    }

    /**
     * Load song from file
     *
     * @param file File to load song from
     * @return ResourceSong
     */
    public static NoteBlockSong fromFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        return fromStream(fis);
    }

    /**
     * Get layer by index
     *
     * @param layer layer index
     * @return Layer if found
     */
    public Layer getLayer(short layer) {
        return layers.get(layer);
    }

    /**
     * Get song file
     *
     * @return File
     */
    public File getFile() {
        return file;
    }

    private void setFile(File file) {
        this.file = file;
    }

    /**
     * Is it a repeating song
     *
     * @return repeating song
     */
    public boolean isRepeat() {
        return repeat;
    }

    /**
     * Set as repeating song
     *
     * @param repeat Repeating song
     */
    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    /**
     * Get tempo
     *
     * @return Tempo
     */
    public float getTempo() {
        return tempo;
    }

    /**
     * Set tempo
     *
     * @param tempo Tempo
     */
    public void setTempo(float tempo) {
        this.tempo = tempo;
    }

    /**
     * Get song length
     *
     * @return song length
     */
    public short getLength() {
        return length;
    }

    /**
     * Set song length
     *
     * @param length Length
     */
    public void setLength(short length) {
        this.length = length;
    }

    /**
     * Get layer count
     *
     * @return layer count
     */
    public int getLayerCount() {
        return layers.size();
    }

    private static short readShort(DataInputStream dis) throws IOException {
        int byte1 = dis.readUnsignedByte();
        int byte2 = dis.readUnsignedByte();
        return (short) (byte1 + (byte2 << 8));
    }

    private static int readInt(DataInputStream dis) throws IOException {
        int byte1 = dis.readUnsignedByte();
        int byte2 = dis.readUnsignedByte();
        int byte3 = dis.readUnsignedByte();
        int byte4 = dis.readUnsignedByte();
        return (byte1 + (byte2 << 8) + (byte3 << 16) + (byte4 << 24));
    }

    private static String readString(DataInputStream dis) throws IOException {
        int length = readInt(dis);
        StringBuilder sb = new StringBuilder(length);
        for (; length > 0; --length) {
            char c = (char) dis.readByte();
            if (c == (char) 0x0D) {
                c = ' ';
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
