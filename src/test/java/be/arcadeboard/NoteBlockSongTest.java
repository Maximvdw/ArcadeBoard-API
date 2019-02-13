package be.arcadeboard;

import be.arcadeboard.api.music.NoteBlockSong;
import org.junit.Test;

import java.io.IOException;

/**
 * NoteBlockSongTest
 * Created by Maxim on 8/01/2018.
 */
public class NoteBlockSongTest {

    @Test
    public void testLoadFromResources() throws IOException {
        NoteBlockSong nbs = NoteBlockSong.fromStream(NoteBlockSongTest.class.getResourceAsStream("/music/theme.nbs"));
        System.out.println(nbs.getLength());
        System.out.println(nbs.getLayerCount());
        System.out.println(nbs.getLayer((short) 0).getVolume());
    }
}
