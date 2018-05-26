package tsuro;

import org.junit.Test;
import org.w3c.dom.Node;

import static org.junit.Assert.assertTrue;
import static tsuro.Decoder.getDocument;

public class ConvertedBoardTest {
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
        String s = Encoder.encodeBoard(board);
        System.out.println(s);
    }

    @Test
    public void testBoard1() throws Exception {
        Decoder decoder = new Decoder();
        String input = "<board><map><ent><xy><x>0</x><y>0</y></xy><tile><connect><n>0</n><n>1</n></connect>" +
                "<connect><n>2</n><n>4</n></connect><connect><n>3</n><n>6</n></connect>" +
                "<connect><n>5</n><n>7</n></connect></tile></ent></map><map><ent><color>red</color>" +
                "<pawn-loc><v></v><n>1</n><n>1</n></pawn-loc></ent></map></board>";
        Node node = getDocument(input);
        Board b = decoder.decode_board(node);

        String answer = Encoder.encodeBoard(b);
        assertTrue(answer.equals(input));
    }
}
