package tsuro;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

import static org.junit.Assert.*;
import static tsuro.Decoder.getDocument;

public class ConvertedTileTest {

    @Test
    public void testEncodeTile() throws Exception{
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        String actual = Encoder.encodeTile(tile);
        System.out.println(actual);
//        assertEquals(actual, "<tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>2</n></connect><connect><n>3</n><n>4</n></connect><connect><n>5</n><n>6</n></connect></tile>");
    }

    @Test
    public void testDecodeTile() throws Exception{
        Decoder decoder = new Decoder();
        String input = "<tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>2</n></connect><connect><n>3</n><n>4</n></connect><connect><n>5</n><n>6</n></connect></tile>";
        Tile expected = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Node node = getDocument(input);
        Tile t = decoder.decode_tile(node);
        assertTrue(expected.sameTile(t));
    }

    @Test
    public void testListOfTile() throws Exception{
        Board board = new Board();
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile1 = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        board.placeTile(tile, 0,0);
        board.placeTile(tile1, 0,1);
        Encoder.encodeBoard(board);
    }

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
    public void testSetofTile() throws Exception{
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile1 = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        Set <Tile> set = new HashSet<>();
        SetofTile tiles = new SetofTile(set);
        tiles.addSetofTile(tile);
        tiles.addSetofTile(tile1);
        Encoder.encodeSetofTile(tiles);
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
        Encoder.encodeSPlayers(list);
    }




}
