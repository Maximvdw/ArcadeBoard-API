package be.arcadeboard.api.resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class OffsetResource extends ResourceIcon {
    private static final Map<Integer, OffsetResource> OFFSETS = new HashMap<>();

    static {
        try {
            for (int i = -256; i <= 256 ; i++) {
                OFFSETS.put(i, new OffsetResource("offset." + i, i));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static OffsetResource getOffset(int offset) {
        if (OFFSETS.containsKey(offset))
            return OFFSETS.get(offset);
        return null;
    }

    private OffsetResource(String name, int offset) throws IOException {
        super(name, getOffsetImage(offset));
        setAscent((-32768) * 2 + 1);
        setHeight((-2 + offset) * 2);
        setWidth(16);
        setAllowRotation(false);
        setBorders(true);
    }

    protected static BufferedImage getOffsetImage(int offset) throws IOException {
        return ImageIO.read(Objects.requireNonNull(OffsetResource.class.getResourceAsStream(
                "/resourcepack/fonts/offset_" + (offset >= 0 ? "positive" : "negative") + ".png"
        )));
    }

    /**
     * Get all offsets
     * @return offsets
     */
    public static Collection<OffsetResource> values() {
        return OFFSETS.values();
    }
}
