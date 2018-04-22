package Classes;

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
    public void getRotation() {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});
        List<int[]> expected = new ArrayList<>();
        expected.add(new int[]{2, 7});
        expected.add(new int[]{3, 5});
        expected.add(new int[]{4, 0});
        expected.add(new int[]{6, 1});


        List<int[]> test = testTile.getRotation(1);
        for (int i=0; i<expected.size(); i++) {
            assertTrue(Arrays.equals(expected.get(i), test.get(i)));
        }
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

}