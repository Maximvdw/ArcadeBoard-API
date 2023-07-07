package be.arcadeboard;

import be.arcadeboard.api.resources.OffsetResource;
import org.junit.Test;

import static org.junit.Assert.*;

public class OffsetResourceTest {

    @Test
    public void testLoadOffset() {
        assertNotNull(OffsetResource.getOffset(-1).getImage());
        assertEquals(OffsetResource.getOffset(-1).getHeight(), -6);

        assertNotNull(OffsetResource.getOffset(1).getImage());
        assertEquals(OffsetResource.getOffset(1).getHeight(), -2);
    }
}
