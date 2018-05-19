package tsuro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Tiles have one field: a list of number pairs, each representing two connected points on the tile
public class Tile {
    //List of possible rotations of tile
    private List<List<int[]>> rotations;
    //int in the range of [0, 1, 2, 3], each representing a different direction
    private int orientation;
    private int SymmetryScore;

    public Tile(){
        this.rotations = new ArrayList<>();
        this.orientation = 0;
    }

    public Tile(List<List<int[]>> rotations) {
        this.rotations = rotations;
        this.orientation = 0;
    }

    //TODO: do we need a list for that??
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


    public static List<Tile> getAllLegalTiles() {
        List<Tile> tiles = new ArrayList<>();
        try {
            File file = new File("./tiles.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;

            while ((line = br.readLine()) != null) {
                line = line.replace('(', ' ');
                line = line.replace(')', ' ');
                String[] nums = line.split("[ ]+");

                int[] my_array = new int[8];
                for (int i = 1; i < nums.length; i++) {
                    my_array[i-1] = Integer.parseInt(nums[i]);
                }
                Tile new_tile = new Tile(my_array);
                tiles.add(new_tile);
            }
            Collections.shuffle(tiles);
        }
        catch(IOException e){
            System.err.println("IOException occurred!");
        }
        return tiles;
    }

    //todo: consider rotation
    public boolean isLegalTile(){
        Tile copyTile = new Tile(this);
        List<Tile> tiles = getAllLegalTiles();
        for (int i=0; i<4; i++) {
            if (tiles.contains(copyTile)) {
                return true;
            }
            copyTile.rotateClockwise();
        }
        return false;
    }


    static protected Map<Integer, Integer> createMap(int[] input){
        Map<Integer,Integer> my_map = new HashMap<>();
        for (int i = 0; i < 4; i++){
            my_map.put(input[i*2], input[i*2+1]);
            my_map.put(input[i*2+1], input[i*2]);
        }
        return my_map;
    }

    static protected boolean contains(List<int[]> parent, int[] child){
        for (int[] pair: parent){
            if (((pair[0] == child[0]) && (pair[1] == child[1])) || ((pair[0] == child[1]) && (pair[1] == child[0]))){
                return true;
            }
        }
        return false;
    }

    //TODO: implement this and test this
    public int getSymmtryScore(){
        Set<Map<Integer, Integer>> maps = new HashSet<>();
        maps.add(createMap(new int[]{2,3,1,4,0,5,6,7}));
        maps.add(createMap(new int[]{0,1,2,7,3,6,4,5}));
        maps.add(createMap(new int[]{1,2,0,3,4,7,5,6}));
        maps.add(createMap(new int[]{0,7,1,6,2,5,3,4}));
        List<int[]> paths = this.getRotation(0);
        int count = 0;
        for (Map<Integer, Integer> m: maps){
            boolean stillgood = true;
            for (int[] pair: paths){
                int a = pair[0];
                int b = pair[1];
                int dicA = m.get(a);
                int dicB = m.get(b);
                if (b != dicA && !contains(paths, new int[]{dicA, dicB}) && !contains(paths, new int[]{dicB, dicA})){
                    stillgood = false;
                    break;
                }
            }
            if (stillgood){
                count++;
            }
        }
        return count;
    }



    public static void main(String argv[]) {
        Tile tile = new Tile(new int[]{0, 5, 1, 3, 2, 6,4, 7});
        System.out.println(tile.getSymmtryScore());
    }
}

