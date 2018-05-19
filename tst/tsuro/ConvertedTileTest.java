package tsuro;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

import static org.junit.Assert.*;

public class ConvertedTileTest {

    @Test
    public void testConvertTile() throws Exception{
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Encoder.encodeTile(tile);
    }

}
