package tsuro;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TileTest {

    @org.junit.Test
    public void constructor() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});
        List<List<int[]>> expected = new ArrayList<>();
        List<int[]> list1 = new ArrayList<>();
        list1.add(new int[]{2, 7});
        list1.add(new int[]{3, 5});
        list1.add(new int[]{4, 0});
        list1.add(new int[]{6, 1});
        expected.add(list1);
        List<int[]> list2 = new ArrayList<>();
        list2.add(new int[]{4, 1});
        list2.add(new int[]{5, 7});
        list2.add(new int[]{6, 2});
        list2.add(new int[]{0, 3});
        expected.add(list2);
        List<int[]> list3 = new ArrayList<>();
        list3.add(new int[]{6, 3});
        list3.add(new int[]{7, 1});
        list3.add(new int[]{0, 4});
        list3.add(new int[]{2, 5});
        expected.add(list3);
        List<int[]> list4 = new ArrayList<>();
        list4.add(new int[]{0, 5});
        list4.add(new int[]{1, 3});
        list4.add(new int[]{2, 6});
        list4.add(new int[]{4, 7});
        expected.add(list4);

    }

    @Test
    public void rotateClockwise_1() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});
        testTile.rotateClockwise();

        assertEquals(1, testTile.getOrientation());

    }

    @Test
    public void rotateClockwise_2() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});
        testTile.rotateClockwise();
        testTile.rotateClockwise();
        testTile.rotateClockwise();
        assertEquals(3, testTile.getOrientation());

    }

    @Test
    public void rotateClockwise_3() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});
        testTile.rotateClockwise();
        testTile.rotateClockwise();
        testTile.rotateClockwise();
        testTile.rotateClockwise();
        testTile.rotateClockwise();
        assertEquals(1, testTile.getOrientation());

    }



    @Test
    public void sameTile() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});

        Tile rotated = new Tile(new int[]{0,5,1,3,2,6,4,7});

        rotated.rotateClockwise();
        rotated.rotateClockwise();

        assertTrue(testTile.sameTile(rotated));

    }


    @Test
    public void getConnected() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});

        assertEquals(testTile.getConnected(0), 5);
        assertEquals(testTile.getConnected(7), 4);
        assertEquals(testTile.getConnected(2), 6);
    }

    @Test
    public void getConnected_rotated() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});
        testTile.rotateClockwise();

        assertEquals(testTile.getConnected(0), 4);
        assertEquals(testTile.getConnected(7), 2);
        assertEquals(testTile.getConnected(2), 7);
    }


    @Test(expected = IllegalArgumentException.class)
    public void getConnected_fail() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});
        testTile.getConnected(9);
    }



    @Test
    public void isSameTile() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});
        Tile newtile = new Tile(new int[]{0,4,1,6,2,7,3,5});
        assertTrue(testTile.sameTile(newtile));
    }

    @Test
    public void equals() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});
        Tile testTile_same = new Tile(new int[]{0,5,1,3,2,6,4,7});
        testTile.rotateClockwise();
        testTile.rotateClockwise();
        testTile.rotateClockwise();
        testTile.rotateClockwise();
        assertEquals(testTile, testTile_same);
    }

    @Test
    public void getOrientation() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});
        testTile.rotateClockwise();
        testTile.rotateClockwise();
        assertEquals(2, testTile.getOrientation());
    }

    @Test
    public void symmetry4() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym4 = new Tile(new int[]{0,1,2,3,4,5,6,7});
        assertEquals(4, sym4.getSymmetryScore());
    }

    @Test
    public void symmetry4_1() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym4 = new Tile(new int[]{0,5,1,4,2,7,3,6});
        assertEquals(4, sym4.getSymmetryScore());
    }

    @Test
    public void symmetry4_2() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym4 = new Tile(new int[]{0,3,1,6,2,5,4,7});
        assertEquals(4, sym4.getSymmetryScore());
    }


    @Test
    public void symmetry2() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym2 = new Tile(new int[]{0,1,2,6,3,7,4,5});
        assertEquals(2, sym2.getSymmetryScore());
    }

    @Test
    public void symmetry2_1() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym2 = new Tile(new int[]{0,3,1,5,2,6,4,7});
        assertEquals(2, sym2.getSymmetryScore());
    }

    @Test
    public void symmetry2_2() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym2 = new Tile(new int[]{0,4,1,2,3,7,5,6});
        assertEquals(2, sym2.getSymmetryScore());
    }

    @Test
    public void symmetry2_3() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym2 = new Tile(new int[]{0,2,1,3,4,6,5,7});
        assertEquals(2, sym2.getSymmetryScore());
    }

    @Test
    public void symmetry1() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym1 = new Tile(new int[]{2,3,4,5,1,6,0,7});
        assertEquals(1, sym1.getSymmetryScore());
    }

    @Test
    public void symmetry1_1() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym1 = new Tile(new int[]{0,5,1,6,2,7,3,4});
        assertEquals(1, sym1.getSymmetryScore());
    }



    @Test
    public void symmetry0() {
        MPlayer p = new LeastSymmetricPlayer("R");
        Tile sym0 = new Tile(new int[]{0,3,1,7,2,6,4,5});
        assertEquals(0, sym0.getSymmetryScore());
    }


}