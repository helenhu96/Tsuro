package tsuro.game;

import java.util.*;


public class Tile{
    //List of possible rotations of tile
    private List<int[]> path;
    //int in the range of [0, 1, 2, 3], each representing a different direction
    private int SymmetryScore;



    public Tile(){
        this.path = new ArrayList<>();
        this.SymmetryScore = -1;
    }

    public Tile(int[] points) {
        if (points.length!=8) {
            throw new IllegalArgumentException("Bad argument for Tile constructor");
        }

        this.path = new ArrayList<>();
        path.add(new int[]{points[0], points[1]});
        path.add(new int[]{points[2], points[3]});
        path.add(new int[]{points[4], points[5]});
        path.add(new int[]{points[6], points[7]});
        this.SymmetryScore = getSymmetryScore();

    }


    //copy tile
    public Tile(Tile tile) {
        this.path = new ArrayList<>();
        for (int[] a : tile.path) {
            this.path.add(a.clone());
        }
        this.SymmetryScore = tile.SymmetryScore;
    }

    public void rotateClockwise() {
        for (int[] pair: path) {
            int a = (pair[0] + 2) % 8;
            int b = (pair[1] + 2) % 8;
            pair[0] = Math.min(a, b);
            pair[1] = Math.max(a, b);
        }
        path.sort((int[] a, int[] b) -> Integer.compare(a[0], b[0]));
    }

    public List<int[]> getPath() {
        return this.path;
    }


    //checks if given tile is the same as this tile
    public boolean sameTile(Tile tile) {
        Tile cloned = new Tile(tile);
        for (int i = 0; i < 4; i++) {
            if (arePathsIdentical(cloned)) {
                return true;
            }
            cloned.rotateClockwise();
        }
        return false;
    }

    private boolean arePathsIdentical(Tile tile) {
        List<int[]> a = this.path;
        List<int[]> b = tile.path;
        for (int j=0; j< 4 ; j++) {
            if (a.get(j)[0] != b.get(j)[0] || a.get(j)[1] != b.get(j)[1])
                return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        Tile tile = (Tile) o;
        return arePathsIdentical(tile);
    }


    //returns the endpoint connected to the given endpoint
    public int getConnected(int curr) {
        for (int i=0; i<path.size(); i++) {
            if (path.get(i)[0]==curr) {
                return path.get(i)[1];
            }
            if (path.get(i)[1]==curr) {
                return path.get(i)[0];
            }
        }
        //TODO: is throwing excepetion the right way to handle???
        throw new IllegalArgumentException("Given point does not exist on tile");
    }




    public boolean isLegalTile(){
        Tile copyTile = new Tile(this);
        List<Tile> tiles = Administrator.getAllLegalTiles();
        for (int i=0; i<4; i++) {
            if (tiles.contains(copyTile)) {
                return true;
            }
            copyTile.rotateClockwise();
        }
        return false;
    }


    // all things below are to calculate symmetry scores
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

    public int getSymmetryScore(){
        Set<Map<Integer, Integer>> maps = new HashSet<>();
        maps.add(createMap(new int[]{2,3,1,4,0,5,6,7}));
        maps.add(createMap(new int[]{0,1,2,7,3,6,4,5}));
        maps.add(createMap(new int[]{1,2,0,3,4,7,5,6}));
        maps.add(createMap(new int[]{0,7,1,6,2,5,3,4}));
        List<int[]> paths = this.getPath();
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

    public int getScore() {
        return this.SymmetryScore;
    }

//
//    @XmlElement
//    public void setConnect(List<n> list)
//    {
//
//        this.connect = list;
//
//    }
//
//    public void addConnect(n node) {
//        this.connect.add(node);
//    }
//
//    public List<n> getConnect()
//
//    {
//        return this.connect;
//    }
//
//    @XmlType
//    public static class n {
//        private List<Integer> n;
//
//        public n() {
//            this.n = new ArrayList<>();
//        }
//
//        @XmlElement
//        public List<Integer> getN() {
//            return this.n;
//        }
//
//        public void setN(List<Integer> n) {
//            this.n = n;
//        }
//
//    }



}

