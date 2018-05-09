package Classes;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MPlayerTest {

    @Test
    public void placePawn() {
        Board board = new Board();
        MPlayer p = new RandPlayer("R");
        PlayerPosition pos = p.placePawn(board);
        assertTrue(board.isBorder(pos));
    }

    @Test
    public void placePawn2() {
        Board board = new Board();
        MPlayer r = new RandPlayer("R");
        PlayerPosition pos = r.placePawn(board);
        MPlayer g = new RandPlayer("G");
        PlayerPosition pos2 = g.placePawn(board);
        MPlayer b = new RandPlayer("B");
        PlayerPosition pos3 = b.placePawn(board);
        assertNotEquals(pos, pos2);
        assertNotEquals(pos, pos3);
        assertNotEquals(pos2, pos3);
        assertTrue(board.isBorder(pos));
        assertTrue(board.isBorder(pos2));
        assertTrue(board.isBorder(pos3));
    }

    @Test
    public void symmetry4() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym4 = new Tile(new int[]{0,1,2,3,4,5,6,7});
        assertEquals(4, p.symmetry(sym4));
    }

    @Test
    public void symmetry4_1() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym4 = new Tile(new int[]{0,5,1,4,2,7,3,6});
        assertEquals(4, p.symmetry(sym4));
    }

    @Test
    public void symmetry4_2() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym4 = new Tile(new int[]{0,3,1,6,2,5,4,7});
        assertEquals(4, p.symmetry(sym4));
    }


    @Test
    public void symmetry2() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym2 = new Tile(new int[]{0,1,2,6,3,7,4,5});
        assertEquals(2, p.symmetry(sym2));
    }

    @Test
    public void symmetry2_1() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym2 = new Tile(new int[]{0,3,1,5,2,6,4,7});
        assertEquals(2, p.symmetry(sym2));
    }

    @Test
    public void symmetry2_2() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym2 = new Tile(new int[]{0,4,1,2,3,7,5,6});
        assertEquals(2, p.symmetry(sym2));
    }

    @Test
    public void symmetry2_3() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym2 = new Tile(new int[]{0,2,1,3,4,6,5,7});
        assertEquals(2, p.symmetry(sym2));
    }

    @Test
    public void symmetry1() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym1 = new Tile(new int[]{2,3,4,5,1,6,0,7});
        assertEquals(1, p.symmetry(sym1));
    }

    @Test
    public void symmetry1_1() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym1 = new Tile(new int[]{0,5,1,6,2,7,3,4});
        assertEquals(1, p.symmetry(sym1));
    }



    @Test
    public void symmetry0() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym0 = new Tile(new int[]{0,3,1,7,2,6,4,5});
        assertEquals(0, p.symmetry(sym0));
    }

    @Test
    public void rotateTileTillLegal() {
        Board board = new Board();
        SPlayer p = new SPlayer("Green");
        MPlayer m = new LeastSymmetricPlayer("G");
        m.initialize("Green", null);
        board.updatePlayerPosition(p, new PlayerPosition(4,1,2));
        board.placeTile(new Tile(new int[]{0,3,1,6,2,5,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 0);
        Tile actual = m.rotateTileTillLegal(board, new Tile(new int[]{0,5,1,3,2,7,4,6}));
        assertEquals(2, actual.getOrientation());
    }

    @Test
    public void rotateTileTillLegalFail() {
        Board board = new Board();
        SPlayer p = new SPlayer("Green");
        MPlayer m = new LeastSymmetricPlayer("G");
        m.initialize("Green", null);
        board.updatePlayerPosition(p, new PlayerPosition(4,1,2));
        board.placeTile(new Tile(new int[]{0,3,1,6,2,5,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 0);
        Tile actual = m.rotateTileTillLegal(board, new Tile(new int[]{0,5,1,4,2,7,3,6}));
        assertNull(actual);
    }

    @Test
    public void sequenceContractTest() {
        MPlayer p = new RandPlayer("R");
        p.initialize("Blue", null);
        try {
            p.playTurn(null, null, 0);
            //fail the test if no expection thrown
            assertFalse(true);

        }
        catch (java.lang.IllegalStateException e){
            assertTrue(true);
        }
    }

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