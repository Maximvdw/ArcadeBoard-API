package be.arcadeboard;

import be.arcadeboard.api.game.graphics.CharacterCanvas;
import be.arcadeboard.api.resources.ColorResource;
import be.arcadeboard.api.resources.OffsetResource;
import be.arcadeboard.api.resources.ResourceFont;
import be.arcadeboard.api.resources.ResourcePack;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class CharacterCanvasTest {
    ResourcePack pack;

    @Before
    public void init() throws IOException {
        pack = new ResourcePack("default", 1);
        // Add default color icons
        for (ColorResource icon : ColorResource.values()) {
            icon.setHex(pack.addIcon(icon).getHex());
        }
        // Add default simple font
        pack.addFont(ResourceFont.getDefaultFont());

        // Add default offsets
        for (OffsetResource offset : OffsetResource.values()) {
            offset.setHex(pack.addIcon(offset).getHex());
        }
    }

    @Test
    public void testTitlePixels() {
        CharacterCanvas canvas = new CharacterCanvas(30, 15);
        canvas.setTitle("&f&lArcadeBoard Games");
        CharacterCanvas.CharacterPixel[] titlePixels = canvas.getTitlePixels();
        CharacterCanvas.CharacterPixel[][] canvasPixels = canvas.getPixels();
        assertNotNull(titlePixels);
    }

    @Test
    public void testWriteString() {
        CharacterCanvas canvas = new CharacterCanvas(30, 15);
        canvas.writeString(0, 0, "Test");
    }
}
