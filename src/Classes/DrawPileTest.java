package Classes;

import org.junit.Test;
import java.util.*;


import static org.junit.Assert.*;

public class DrawPileTest {



    @Test
    public void constructor() {
        DrawPile drawPile = new DrawPile();

        assertEquals(35, drawPile.size());
    }

    @Test
    public void drawATile() {
        DrawPile drawPile = new DrawPile();

        drawPile.drawATile();
        drawPile.drawATile();
        drawPile.drawATile();
        assertEquals(32, drawPile.size());

    }


    @Test
    public void addTilesAndShuffle() {
        DrawPile drawPile = new DrawPile();
        drawPile.drawATile();
        drawPile.drawATile();

        Tile tile = new Tile(new int[]{0,1,2,3,4,5,6,7});
        List<Tile> list = new ArrayList<>();
        list.add(tile);

        drawPile.addTilesAndShuffle(list);
        assertEquals(34, drawPile.size());

    }
}