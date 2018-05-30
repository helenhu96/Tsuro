package tsuro.xmlmodel;
import org.junit.Test;
import tsuro.game.Board;
import tsuro.game.PlayerPosition;
import tsuro.game.Tile;
import tsuro.xmlmodel.PawnLocation;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

public class PawnTest {

    @Test
    public void testPlayerPtoPawnP(){
        PlayerPosition pp = new PlayerPosition(1,2,4);
        PawnLocation pl = new PawnLocation(pp);
        PawnLocation expected = new PawnLocation("", null, 2,5);
        assertEquals(pl.h, expected.h);
        assertNull(pl.v);
        assertEquals(2, pl.a);
        assertEquals(5, pl.b);

    }

    @Test
    public void testPawnPtoPlayerP(){
        Tile t = new Tile();
        Board b = new Board();

        b.placeTile(t, 5,3);
        PawnLocation pl = new PawnLocation("", null, 5, 7);
        PlayerPosition pp = pl.backtoPlayerPosition(b);

    }
}
