package tsuro.xmlmodel;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import tsuro.game.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConvertedBoardTest {
    @Test
    public void testBoard() throws Exception{
        Board board = new Board();
        Encoder.encodeBoard(board);
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile1 = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        board.placeTile(tile, 0,0);
        board.placeTile(tile1, 0,1);

        SPlayer player1 = new SPlayer("red");
        SPlayer player2 = new SPlayer("blue");
        PlayerPosition position = new PlayerPosition(0,0,1);
        board.colorToPosition.put(player1.getColor(), position);
        PlayerPosition position1 = new PlayerPosition(0,5,2);
        board.colorToPosition.put(player2.getColor(), position1);
//        String s = Encoder.encodeBoard(board);
//        System.out.println(s);
    }

    @Test
    public void testBoard1() throws Exception {
        String input = "<board><map><ent><xy><x>0</x><y>0</y></xy><tile><connect><n>0</n><n>1</n></connect>" +
                "<connect><n>2</n><n>4</n></connect><connect><n>3</n><n>6</n></connect>" +
                "<connect><n>5</n><n>7</n></connect></tile></ent></map><map><ent><color>red</color>" +
                "<pawn-loc><v></v><n>1</n><n>1</n></pawn-loc></ent></map></board>";
        Document doc = Decoder.getDocument(input);
        Node boardNode = doc.getElementsByTagName("board").item(0);
        Board b = Decoder.decode_board(boardNode);
        Tile tile = new Tile(new int[]{0, 1, 2, 4, 3, 6, 5, 7});
        assertEquals(tile, b.getTile(0, 0));
        assertEquals(b.getPlayerByPosition(new PlayerPosition(0,1,6)), "red");
    }
}
