package Classes;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TileTest {

//    public boolean tileEqual(Classes.Tile a, Classes.Tile b) {
//        List<int[]> apairs = a.getPairs();
//        List<int[]> bpairs = b.getPairs();
//        if (apairs.size() == bpairs.size()) {
//            if (apairs.size() == 4) {
//                for (int i=0; i<4; i++) {
//
//                }
//            }
//        }
//    }

    @org.junit.Test
    public void rotateClockwise() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});
        testTile.rotateClockwise();
        Tile expected = new Tile(new int[]{2,7,3,5,4,0,6,1});

        boolean result = false;
        List<int[]> a = expected.getPairs();
        List<int[]> b = testTile.getPairs();
        for (int j = 0; j<4; j++) {
            if (!Arrays.equals(a.get(j), b.get(j))) {
                break;
            }
            else {
                if (j==3) result = true;
            }
        }

        assertTrue(result);

    }

    @Test
    public void sameTile() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});

        Tile rotated = new Tile(new int[]{2,7,3,5,4,0,6,1});

        assertTrue(testTile.sameTile(rotated));

    }

    @Test
    public void getConnected() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});

        assertEquals(testTile.getConnected(0), 5);
        assertEquals(testTile.getConnected(7), 4);
        assertEquals(testTile.getConnected(2), 6);
        assertEquals(testTile.getConnected(9), -1);



    }


}