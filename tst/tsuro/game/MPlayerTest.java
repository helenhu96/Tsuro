package tsuro.game;

import org.junit.Test;
import tsuro.game.*;

import static org.junit.Assert.*;

public class MPlayerTest {

    @Test
    public void placePawn() {
        Board board = new Board();
        MPlayer p = new RandPlayer("R");
        p.initialize("Green", null);
        PlayerPosition pos = p.placePawn(board);
        assertTrue(board.isBorder(pos));
    }

    @Test
    public void placePawn2() {
        for (int i=0; i<10000; i++) {
            Board board = new Board();
            MPlayer r = new RandPlayer("R");
            r.initialize("Hotpink", null);
            PlayerPosition pos = r.placePawn(board);
            board.updatePlayerPosition(new SPlayer("Hotpink"), pos);
            MPlayer g = new RandPlayer("G");
            g.initialize("Green", null);
            PlayerPosition pos2 = g.placePawn(board);
            board.updatePlayerPosition(new SPlayer("Green"), pos2);
            MPlayer b = new RandPlayer("B");
            b.initialize("Red", null);
            PlayerPosition pos3 = b.placePawn(board);
            board.updatePlayerPosition(new SPlayer("Red"), pos3);
            assertNotEquals(pos, pos2);
            assertNotEquals(pos, pos3);
            assertNotEquals(pos2, pos3);
            assertTrue(board.isBorder(pos));
            assertTrue(board.isBorder(pos2));
            assertTrue(board.isBorder(pos3));
        }

    }




//    @Test
//    public void rotateTileTillLegal() throws Exception{
//        Board board = new Board();
//        SPlayer p = new SPlayer("Green");
//        MPlayer m = new LeastSymmetricPlayer("Green");
//        m.initialize("Green", null);
//        board.updatePlayerPosition(p, new PlayerPosition(4,1,2));
//        board.placeTile(new Tile(new int[]{0,3,1,6,2,5,4,7}), 4, 0);
//        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 0);
//        Tile actual = m.rotateTileTillLegal(board, new Tile(new int[]{0,5,1,3,2,7,4,6}));
//    }
//
//    @Test
//    public void rotateTileTillLegalFail() throws Exception{
//        Board board = new Board();
//        SPlayer p = new SPlayer("Green");
//        MPlayer m = new LeastSymmetricPlayer("G");
//        m.initialize("Green", null);
//        board.updatePlayerPosition(p, new PlayerPosition(4,1,2));
//        board.placeTile(new Tile(new int[]{0,3,1,6,2,5,4,7}), 4, 0);
//        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 0);
//        Tile actual = m.rotateTileTillLegal(board, new Tile(new int[]{0,5,1,4,2,7,3,6}));
//        assertNull(actual);
//    }

//    @Test
//    public void sequenceContractTest() {
//        MPlayer p = new RandPlayer("R");
//        p.initialize("Blue", null);
//        try {
//            p.playTurn(null, null, 0);
//            //fail the test if no expection thrown
//            assertFalse(true);
//
//        }
//        catch (java.lang.IllegalStateException e){
//            assertTrue(true);
//        }
//    }

    @Test
    public void sequenceContractTest_1() {
        MPlayer p = new RandPlayer("R");
        p.initialize("Blue", null);
        Board board = new Board();
        p.placePawn(board);

        try {
            p.placePawn(board);
            //fail the test if no expection thrown
            assertFalse(true);

        }
        catch (java.lang.IllegalStateException e){
            assertTrue(true);
        }
    }


}