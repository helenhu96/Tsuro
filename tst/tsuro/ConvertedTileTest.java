package tsuro;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.*;

import static org.junit.Assert.*;

public class ConvertedTileTest {

    //TODO: add assertEqual after implementing decoder
    @Test
    public void testConvertTile() throws Exception{
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Encoder.encodeTile(tile);
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
    public void testBoard() throws Exception{
        Board board = new Board();
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile1 = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        board.placeTile(tile, 0,0);
        board.placeTile(tile1, 0,1);


        SPlayer player1 = new SPlayer("red");
        SPlayer player2 = new SPlayer("blue");
        PlayerPosition position = new PlayerPosition(0,0,1);
        board.playerToPosition.put(player1, position);
        PlayerPosition position1 = new PlayerPosition(0,5,2);
        board.playerToPosition.put(player2, position1);
        PawnEntry entry = new PawnEntry("red", new PawnLocation(position));
        PawnEntry entry1 = new PawnEntry("blue", new PawnLocation(position1));
        Encoder.encodeBoard(board);
    }

}
