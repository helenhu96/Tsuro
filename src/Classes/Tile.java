package Classes;

import java.util.*;

//Tiles have one field: a list of number pairs, each representing two connected points on the tile
public class Tile {
    //List of possible rotations of tile
    private List<List<int[]>> rotations;
    //int in the range of [0, 1, 2, 3], each representing a different direction
    private int orientation;

    public Tile(){
        this.rotations = new ArrayList<>();
        this.orientation = 0;
    }

    public Tile(List<List<int[]>> rotations) {
        this.rotations = rotations;
        this.orientation = 0;
    }

    public Tile(int[] points) {
        if (points.length!=8) {
            throw new java.lang.IllegalArgumentException("Bad argument for Tile constructor");
        }

        this.rotations = new ArrayList<>();
        for (int i=0; i<4; i++) {
            List<int[]> list = new ArrayList<>();
            list.add(new int[]{(points[0]+2*i)%8, (points[1]+2*i)%8});
            list.add(new int[]{(points[2]+2*i)%8, (points[3]+2*i)%8});
            list.add(new int[]{(points[4]+2*i)%8, (points[5]+2*i)%8});
            list.add(new int[]{(points[6]+2*i)%8, (points[7]+2*i)%8});
            this.rotations.add(list);
        }

        this.orientation = 0;
    }

    public Tile(Tile tile) {
        this.rotations = new ArrayList<>();
        for (int i=0; i<tile.rotations.size(); i++) {
            List<int[]> list = new ArrayList<>();
            for (int[] a : tile.rotations.get(i)) {
                list.add(a.clone());
            }
            this.rotations.add(list);
        }

        this.orientation = tile.orientation;
    }

    public void rotateClockwise() {
        this.orientation = (this.orientation+1)%4;
    }

    public List<int[]> getRotation(int r) {
        if (r<0 || r>3) {
            System.out.println("Rotation invalid");
            return new ArrayList<>();
        }
        return rotations.get(r);
    }

    public void print() {
        for (int i = 0; i < 4; i++){
            System.out.println(rotations.get(orientation).get(i)[0] + " , " + rotations.get(orientation).get(i)[1]);
        }
        System.out.println("orientation is " + this.orientation);
    }

    //returns paths under current orientation
    public List<int[]> getPaths() {
        return rotations.get(orientation);
    }

    public int getOrientation() {
        return this.orientation;
    }

    //checks if given tile is the same as this tile
    public boolean sameTile(Tile tile) {
        for (int i=0; i<rotations.size(); i++) {
            List<int[]> a = this.rotations.get(i);
            List<int[]> b = tile.rotations.get(i);
            for (int j=0; j<a.size(); j++) {
                if (a.get(j)[0] != b.get(j)[0] ||
                        a.get(j)[1] != b.get(j)[1])
                    return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return orientation == tile.orientation &&
                sameTile(tile);
    }


    //returns the endpoint connected to the given endpoint
    public int getConnected(int curr) {
        List<int[]> pairs = this.rotations.get(orientation);
        for (int i=0; i<pairs.size(); i++) {
            if (pairs.get(i)[0]==curr) return pairs.get(i)[1];
            if (pairs.get(i)[1]==curr) return pairs.get(i)[0];
        }
        throw new java.lang.IllegalArgumentException("Given point does not exist on tile");
    }



/*    public static void main(String argv[]) {
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

        System.out.println("Should return false : " + original.sameTile(diffTile));*//*

    }*/
}

