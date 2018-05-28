package tsuro.game;

import org.junit.Test;
import tsuro.game.DrawPile;
import tsuro.game.Tile;

import java.util.*;


import static org.junit.Assert.*;

public class DrawPileTest {



    @Test
    public void testConstructor() {
        DrawPile drawPile = new DrawPile();
        drawPile.initialize();
        assertEquals(35, drawPile.size());
    }

    @Test
    public void testDrawATile() throws Exception{
        DrawPile drawPile = new DrawPile();
        drawPile.initialize();
        System.out.println(drawPile.size());
        drawPile.drawATile();
        drawPile.drawATile();
        drawPile.drawATile();
        assertEquals(32, drawPile.size());

    }


    @Test
    public void addTilesAndShuffle() throws Exception{
        DrawPile drawPile = new DrawPile();
        drawPile.initialize();

        drawPile.drawATile();
        drawPile.drawATile();

        Tile tile = new Tile(new int[]{0,1,2,3,4,5,6,7});
        Set<Tile> set = new HashSet<>();
        set.add(tile);

        drawPile.addTilesAndShuffle(set);
        assertEquals(34, drawPile.size());

    }
}