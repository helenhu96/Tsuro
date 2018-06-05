package tsuro.xmlmodel;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import tsuro.game.*;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ConvertedTileTest {

    @Test
    public void testEncodeTile() throws Exception {

        String input = "<tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>2</n></connect><connect><n>3</n><n>4</n></connect><connect><n>5</n><n>6</n></connect></tile>";
        Tile expected = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        String encodedTile = Encoder.encodeTile(expected);
        assertEquals(encodedTile, input);
    }

    @Test
    public void testDecodeTile() throws Exception
    {
        Decoder decoder = new Decoder();
        String input = "<tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>2</n></connect><connect><n>3</n><n>4</n></connect><connect><n>5</n><n>6</n></connect></tile>";
        Tile expected = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Document doc = decoder.getDocument(input);
        Node node = doc.getElementsByTagName("tile").item(0);
        Tile t = decoder.decode_tile(node);
        assertTrue(expected.sameTile(t));
    }

    @Test
    public void testSetofColor() throws Exception {
        Set<String> set = new HashSet<>();
        set.add("red");
        set.add("blue");
        String a = Encoder.encodeSetofColor(set);
        assertEquals(a, "<set><color>red</color><color>blue</color></set>");
    }


    @Test
    public void testListofColor() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("red");
        list.add("blue");
        String a = Encoder.encodeListofColor(list);
        assertEquals(a, "<list><color>red</color><color>blue</color></list>");
    }

    @Test
    public void testNumber() throws Exception
    {
        String a =Encoder.encodeNum(5);
        assertEquals(a,"<n>5</n>");
    }



    @Test
    public void testPawnLoc() throws Exception{
        Board b = new Board();
        PlayerPosition position = new PlayerPosition(0,0,1);
        String decoded = Encoder.encodePawnLoc(position);
        PlayerPosition p = Decoder.decodePawnLoc(Decoder.getDocument(decoded).getElementsByTagName("pawn-loc").item(0), b);
        assertTrue(p.equals(position));
    }



    @Test
    public void testListofTile1() throws Exception{
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile1 = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        List <Tile> list = new ArrayList<>();
        list.add(tile);
        list.add(tile1);
        String encoded = Encoder.encodeListofTile(list);
        List<Tile> tiles = Decoder.decode_listofTiles(Decoder.getDocument(encoded).getElementsByTagName("list").item(0));
        for (Tile t: list) {
            assertTrue(tiles.contains(t));
        }
    }


    @Test
    public void testSPlayer() throws Exception{
        SPlayer sp = new SPlayer("red");
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile1 = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        sp.receiveTile(tile);
        sp.receiveTile(tile1);
        List<SPlayer> list = new ArrayList<>();
        list.add(sp);
        String docString = Encoder.encodeSPlayers(list);
        Document doc = Decoder.getDocument(docString);
        List<SPlayer> SPlayers = Decoder.decode_listofSPlayer(doc.getElementsByTagName("list").item(0));
        assertEquals(list.size(), SPlayers.size());
        for (SPlayer player: list) {
            assertEquals(SPlayers.get(SPlayers.indexOf(player)), player);
        }
    }

    @Test
    public void testSPlayers() throws Exception{
        SPlayer sp = new SPlayer("red");
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile1 = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        SPlayer sp1 = new SPlayer("blue");
        sp.receiveTile(tile);
        sp1.receiveTile(tile1);
        sp1.getDragon();
        List<SPlayer> list = new ArrayList<>();
        list.add(sp);
        list.add(sp1);

        String docString = Encoder.encodeSPlayers(list);
        Document doc = Decoder.getDocument(docString);
        List<SPlayer> SPlayers = Decoder.decode_listofSPlayer(doc.getElementsByTagName("list").item(0));
        assertEquals(list.size(), SPlayers.size());
        for (SPlayer player: list) {
            assertEquals(SPlayers.get(SPlayers.indexOf(player)), player);
        }
    }

    @Test
    public void testMaybeSPlayers() throws Exception{
        SPlayer sp = new SPlayer("red");
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile1 = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        SPlayer sp1 = new SPlayer("blue");
        sp.receiveTile(tile);
        sp1.receiveTile(tile1);
        sp1.getDragon();
        List<SPlayer> list = new ArrayList<>();
        assertEquals(Encoder.encodeMaybeListofSPlayers(list), "<false></false>");
        list.add(sp);
        list.add(sp1);
        assertTrue(Encoder.encodeMaybeListofSPlayers(list).equals("<list><splayer-nodragon><color>red</color><set><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>2</n></connect><connect><n>3</n><n>4</n></connect><connect><n>5</n><n>6</n></connect></tile></set></splayer-nodragon><splayer-dragon><color>blue</color><set><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>3</n></connect><connect><n>4</n><n>5</n></connect><connect><n>6</n><n>7</n></connect></tile></set></splayer-dragon></list>"));
    }



}
