package tsuro.game;

import org.junit.Test;
import tsuro.game.SPlayer;
import tsuro.game.Tile;

import java.util.*;

import static org.junit.Assert.*;

public class SPlayerTest {

    @Test
    public void receiveTile() {
        SPlayer testPlayer = new SPlayer("green");
        Tile tile1 = new Tile(new int[]{0,5,1,3,2,6,4,7});
        testPlayer.receiveTile(tile1);
        Set<Tile> expected = new HashSet<>();
        expected.add(tile1);
        assertEquals(expected, testPlayer.getHandTiles());

    }



    @Test
    public void removeHandTiles() {
        SPlayer testPlayer = new SPlayer("green");
        Tile tile1 = new Tile(new int[]{0,5,1,3,2,6,4,7});
        testPlayer.receiveTile(tile1);
        testPlayer.removeHandTiles();

        assertEquals(new HashSet<>(), testPlayer.getHandTiles());
    }


    @Test
    public void removeTile() {
        SPlayer testPlayer = new SPlayer("green");
        Tile tile1 = new Tile(new int[]{0,5,1,3,2,6,4,7});
        Tile tile2 = new Tile(new int[]{0,1,2,3,4,5,6,7});
        testPlayer.receiveTile(tile1);
        testPlayer.receiveTile(tile2);
        Set<Tile> expected = new HashSet<>();
        expected.add(tile1);
        testPlayer.removeTile(tile2);
        assertEquals(expected, testPlayer.getHandTiles());

    }

    @Test
    public void removeTile_fail() {
        SPlayer testPlayer = new SPlayer("green");
        Tile tile1 = new Tile(new int[]{0,5,1,3,2,6,4,7});
        Tile tile2 = new Tile(new int[]{0,1,2,3,4,5,6,7});
        testPlayer.receiveTile(tile1);

        assertFalse(testPlayer.removeTile(tile2));

    }

    @Test
    public void numHandTiles() {
        SPlayer testPlayer = new SPlayer("green");
        Tile tile1 = new Tile(new int[]{0,5,1,3,2,6,4,7});
        Tile tile2 = new Tile(new int[]{0,1,2,3,4,5,6,7});
        testPlayer.receiveTile(tile1);
        testPlayer.receiveTile(tile2);

        assertEquals(2, testPlayer.numHandTiles());

    }

    @Test
    public void numHandTiles_1() {
        SPlayer testPlayer = new SPlayer("green");
        assertEquals(0, testPlayer.numHandTiles());

    }


}