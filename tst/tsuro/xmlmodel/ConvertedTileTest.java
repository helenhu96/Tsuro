package tsuro.xmlmodel;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import tsuro.game.*;
import tsuro.xmlmodel.*;

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
        PlayerDecoder decoder = new PlayerDecoder();
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

//    @Test
//    public void testListOfTile() throws Exception{
//        Board board = new Board();
//        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
//        Tile tile1 = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
//        board.placeTile(tile, 0,0);
//        board.placeTile(tile1, 0,1);
//        Encoder.encodeBoard(board);
//    }
//
    @Test
    public void testPawnLoc() throws Exception{
        PlayerPosition position = new PlayerPosition(0,0,1);
        Encoder.encodePawnLoc(position);

    }



    @Test
    public void testPawns() throws Exception{
        Board board = new Board();
        SPlayer player1 = new SPlayer("red");
        SPlayer player2 = new SPlayer("blue");
        PlayerPosition position = new PlayerPosition(0,0,1);
        board.playerToPosition.put(player1, position);
        PlayerPosition position1 = new PlayerPosition(0,5,2);
        board.playerToPosition.put(player2, position1);
        PawnEntry entry = new PawnEntry("red", new PawnLocation(position));
        PawnEntry entry1 = new PawnEntry("blue", new PawnLocation(position1));
        Pawns pawns = new Pawns(board);
        Encoder.encodePawns(pawns);
    }


    @Test
    public void testListofTile1() throws Exception{
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile1 = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        List <Tile> list = new ArrayList<>();
        ListofTile tiles = new ListofTile(list);
        tiles.addListofTile(tile);
        tiles.addListofTile(tile1);
        Encoder.encodeListofTile(tiles);
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
        System.out.println(Encoder.encodeSPlayers(list));
    }

    @Test
    public void testMayberSPlayers() throws Exception{
        SPlayer sp = new SPlayer("red");
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile1 = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        SPlayer sp1 = new SPlayer("blue");
        sp.receiveTile(tile);
        sp1.receiveTile(tile1);
        sp1.getDragon();
        List<SPlayer> list = new ArrayList<>();
        assertEquals(Encoder.encodeMaybeListofSPlayers(list), "<false> </false>");
        list.add(sp);
        list.add(sp1);
        assertTrue(Encoder.encodeMaybeListofSPlayers(list).equals("<list><splayer-nodragon><color>red</color><set><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>2</n></connect><connect><n>3</n><n>4</n></connect><connect><n>5</n><n>6</n></connect></tile></set></splayer-nodragon><splayer-dragon><color>blue</color><set><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>3</n></connect><connect><n>4</n><n>5</n></connect><connect><n>6</n><n>7</n></connect></tile></set></splayer-dragon></list>"));
    }



}
