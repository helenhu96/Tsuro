package Classes;

import java.util.*;

//Tiles have one field: a list of number pairs, each representing two connected points on the tile
public class Tile {
    private List<int[]> pairs;

    public Tile(){
        this.pairs = new ArrayList<>();
    }

    public Tile(List<int[]> pairs) {
        this.pairs = pairs;
    }

    public Tile(int[] points) {
        if (points.length!=8) System.err.println("Wrong arg size for tile constructor");
        List<int[]> array = new ArrayList<>();
        array.add(new int[]{points[0], points[1]});
        array.add(new int[]{points[2], points[3]});
        array.add(new int[]{points[4], points[5]});
        array.add(new int[]{points[6], points[7]});
        this.pairs = array;
    }

    public Tile(Tile tile) {
        List<int[]> array = new ArrayList<>();
        for (int[] a : tile.getPairs()) {
            array.add(new int[]{a[0], a[1]});
        }
        this.pairs = array;
    }

    public void rotateClockwise() {
        for (int i = 0; i < 4; i++) {
            pairs.get(i)[0] = (pairs.get(i)[0]+2)%8;
            pairs.get(i)[1] = (pairs.get(i)[1]+2)%8;
        }

    }

    public void print() {
        for (int i = 0; i < 4; i++){
            System.out.println(pairs.get(i)[0] + " , " + pairs.get(i)[1]);
        }
    }

    //returns the connected pairs
    public List<int[]> getPairs() {
        return pairs;
    }

    //checks if given tile is a rotated version of this tile
    public boolean sameTile(Tile tile) {
        boolean result = false;
        for (int i = 0; i<4; i++) {
            List<int[]> a = this.getPairs();
            List<int[]> b = tile.getPairs();
            for (int j = 0; j<4; j++) {
                if (!Arrays.equals(a.get(j), b.get(j))) {
                    break;
                }
                else {
                    if (j==3) result = true;
                }
            }
            tile.rotateClockwise();
        }
        return result;
    }

    //returns the endpoint connected to the given endpoint
    public int getConnected(int curr) {
        for (int i=0; i<pairs.size(); i++) {
            if (pairs.get(i)[0]==curr) return pairs.get(i)[1];
            if (pairs.get(i)[1]==curr) return pairs.get(i)[0];
        }
        return -1;
    }



    //Test
    public static void main(String argv[]) {
        Tile testTile = new Tile(new int[]{0,5,1,3,2,6,4,7});

        Tile original = new Tile(testTile);
        System.out.println("Original tile:");
        testTile.print();
        testTile.rotateClockwise();
        System.out.println("After 1 rotate()");
        testTile.print();
        System.out.println("Still the same tile? : " + original.sameTile(testTile));

        System.out.println("After 3 rotate()");

        testTile.rotateClockwise();
        testTile.rotateClockwise();
        testTile.rotateClockwise();
        testTile.print();
        System.out.println("Still the same tile? : " + original.sameTile(testTile));

        Tile diffTile = new Tile(new int[]{0,1,2,3,4,5,6,7});

        System.out.println("Should return false : " + original.sameTile(diffTile));

    }
}

