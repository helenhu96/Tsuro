package tsuro.game;

import org.junit.Test;
import tsuro.game.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MPlayerTest {

    @Test
    public void placePawn() {
        Board board = new Board();
        List<String> colors = new ArrayList<>();
        colors.add("green");
        MPlayer p = new RandPlayer("R");
        p.initialize("green", colors);
        PlayerPosition pos = p.placePawn(board);
        assertTrue(board.isBorder(pos));
    }

    @Test
    public void placePawn2() {
        for (int i=0; i<100; i++) {
            Board board = new Board();
            List<String> colors = new ArrayList<>();
            colors.add("hotpink");
            colors.add("green");
            colors.add("red");
            MPlayer r = new RandPlayer("R");
            r.initialize("hotpink", colors);
            PlayerPosition pos = r.placePawn(board);
            board.updatePlayerPosition(new SPlayer("hotpink"), pos);
            MPlayer g = new RandPlayer("G");
            g.initialize("green", colors);
            PlayerPosition pos2 = g.placePawn(board);
            board.updatePlayerPosition(new SPlayer("green"), pos2);
            MPlayer b = new RandPlayer("B");
            b.initialize("red", colors);
            PlayerPosition pos3 = b.placePawn(board);
            board.updatePlayerPosition(new SPlayer("red"), pos3);
            assertNotEquals(pos, pos2);
            assertNotEquals(pos, pos3);
            assertNotEquals(pos2, pos3);
            assertTrue(board.isBorder(pos));
            assertTrue(board.isBorder(pos2));
            assertTrue(board.isBorder(pos3));
        }

    }




    @Test (expected = IllegalStateException.class)
    public void sequenceContractTest() throws Exception{
        MPlayer p = new RandPlayer("R");
        List<String> colors = new ArrayList<>();
        colors.add("blue");
        p.initialize("blue", colors);
        p.playTurn(null, null, 0);

    }

    @Test (expected = IllegalStateException.class)
    public void sequenceContractTest_1() {
        MPlayer p = new RandPlayer("a");
        List<String> colors = new ArrayList<>();
        colors.add("blue");
        p.initialize("blue", colors);
        Board board = new Board();
        p.placePawn(board);
        p.placePawn(board);

    }


}