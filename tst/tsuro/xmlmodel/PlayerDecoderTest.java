package tsuro.xmlmodel;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import tsuro.game.*;
import tsuro.xmlmodel.PawnEntry;
import tsuro.xmlmodel.PawnLocation;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.rmi.server.ExportException;

import static org.junit.Assert.assertTrue;
import static tsuro.game.Decoder.getDocument;

public class PlayerDecoderTest {

    public static Document getDocument(String docString) throws Exception{
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setValidating(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            return builder.parse(new InputSource(docString));

        }
        catch (Exception ex){
            throw new Exception(ex.getMessage());
        }

    }

    @Test
    // Test the decode_tile function of PlayerDecoder
    public void decodeTileTest() throws Exception {
        try {
            Decoder dec = new PlayerDecoder();
            Document doc = getDocument("./tst/tsuro/xmlmodel/tiletest1.xml");

            Tile result = dec.decode_tile(doc.getElementsByTagName("tile").item(0));
            Tile expected = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});

            assertTrue(result.sameTile(expected));

            doc = getDocument("./tst/tsuro/xmlmodel/tiletest2.xml");
            Tile result2 = dec.decode_tile(doc.getElementsByTagName("tile").item(0));
            Tile expected2 = new Tile(new int[]{0, 5, 1, 7, 2, 3, 4, 6});

            assertTrue(result2.sameTile(expected2));


        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    @Test
    public void decodePawnLocTest() throws Exception {
        try {

            Decoder dec = new PlayerDecoder();

            Board b = new Board();
            Tile t = new Tile(new int[]{0,1,2,3,4,5,6,7});
            b.placeTile(t, 1, 1);

            Document doc = getDocument("./tst/tsuro/xmlmodel/pawnloctest1.xml");

            PlayerPosition result = dec.decode_pawnLoc(doc.getElementsByTagName("pawn-loc").item(0), b);
            PlayerPosition expected = new PlayerPosition(1,1,4);

            assertTrue(result.equals(expected));


            b.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 4, 5);
            PlayerPosition result2 = dec.decode_pawnLoc(doc.getElementsByTagName("pawn-loc").item(1), b);
            PlayerPosition expected2 = new PlayerPosition(4,5,7);

            assertTrue(result2.equals(expected2));


            b.placeTile(new Tile(new int[]{0,2,1,5,3,7,4,6}), 5,0);
            PlayerPosition result3 = dec.decode_pawnLoc(doc.getElementsByTagName("pawn-loc").item(2), b);
            PlayerPosition expected3 = new PlayerPosition(5,0,5);

            assertTrue(result3.equals(expected3));


            b.placeTile(new Tile(new int[]{0,7,1,2,3,4,5,6}), 0, 4);
            PlayerPosition result4 = dec.decode_pawnLoc(doc.getElementsByTagName("pawn-loc").item(3), b);
            PlayerPosition expected4 = new PlayerPosition(0, 4, 4);

            assertTrue(result4.equals(expected4));


            b.placeTile(new Tile(new int[]{0,1,2,3,4,5,6,7}), 3, 0);
            PlayerPosition result5 = dec.decode_pawnLoc(doc.getElementsByTagName("pawn-loc").item(4), b);
            PlayerPosition expected5 = new PlayerPosition(3, 0, 7);

            assertTrue(result5.equals(expected5));



        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    @Test
    public void decodeBoardTest() throws Exception{
        try {

            PlayerDecoder dec = new PlayerDecoder();

            Document doc = getDocument("./tst/tsuro/xmlmodel/boardtest.xml");
            Board result = dec.decode_board(doc.getElementsByTagName("board").item(0));

            Tile t1 = new Tile(new int[]{0,1,2,3,4,5,6,7});
            Tile t2 = new Tile(new int[]{0,5,1,4,2,7,3,6});
            Tile t3 = new Tile(new int[]{0,1,2,6,3,7,4,5});
            Tile t4 = new Tile(new int[]{0,1,2,7,3,4,5,6});
            Tile t5 = new Tile(new int[]{0,1,2,4,3,6,5,7});
            Tile t6 = new Tile(new int[]{0,2,1,4,3,7,5,6});
            Tile t7 = new Tile(new int[]{0,2,1,6,3,7,4,5});
            Tile t8 = new Tile(new int[]{0,6,1,5,2,4,3,7});
            Tile t9 = new Tile(new int[]{0,4,1,7,2,3,5,6});
            Tile t10 = new Tile(new int[]{0,4,1,5,2,6,3,7});

            Board expected = new Board();
            expected.placeTile(t1, 0, 0);
            expected.placeTile(t2, 0, 1);
            expected.placeTile(t3, 0, 2);
            expected.placeTile(t4, 0, 3);
            expected.placeTile(t5, 1, 0);
            expected.placeTile(t6, 1, 1);
            expected.placeTile(t7, 1, 2);
            expected.placeTile(t8, 2, 0);
            expected.placeTile(t9, 2, 1);
            expected.placeTile(t10, 2, 2);

            assertTrue(result.getTile(0, 0).sameTile(expected.getTile(0,0)));
            assertTrue(result.getTile(0, 1).sameTile(expected.getTile(0,1)));
            assertTrue(result.getTile(0, 2).sameTile(expected.getTile(0,2)));
            assertTrue(result.getTile(0, 3).sameTile(expected.getTile(0,3)));
            assertTrue(result.getTile(1, 0).sameTile(expected.getTile(1,0)));
            assertTrue(result.getTile(1, 1).sameTile(expected.getTile(1,1)));
            assertTrue(result.getTile(1, 2).sameTile(expected.getTile(1,2)));
            assertTrue(result.getTile(2, 0).sameTile(expected.getTile(2,0)));
            assertTrue(result.getTile(2, 1).sameTile(expected.getTile(2,1)));
            assertTrue(result.getTile(2, 2).sameTile(expected.getTile(2,2)));

            PlayerPosition expected_blue = new PlayerPosition(1,0,7);
            PlayerPosition expected_red = new PlayerPosition(2,0,4);
            PlayerPosition expected_green = new PlayerPosition(2,1,5);
            PlayerPosition expected_orange = new PlayerPosition(2,1,4);
            PlayerPosition expected_sienna = new PlayerPosition(1,2,3);


            assertTrue(result.getPlayerPositionByColor("blue").equals(expected_blue));
            assertTrue(result.getPlayerPositionByColor("red").equals(expected_red));
            assertTrue(result.getPlayerPositionByColor("green").equals(expected_green));
            assertTrue(result.getPlayerPositionByColor("orange").equals(expected_orange));
            assertTrue(result.getPlayerPositionByColor("sienna").equals(expected_sienna));


        } catch (Exception e){
            throw new Exception(e.getMessage());
        }


    }
}
