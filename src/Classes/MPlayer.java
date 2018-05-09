package Classes;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

abstract class MPlayer implements IPlayer {
    protected String name;
    protected String color;
    protected int state;
    protected MPlayer(String name) {
        this.name = name;
        state = UNINITIALIZED;
    }

    public String getName() { return this.name; }


    protected final static int UP = 0;
    protected final static int RIGHT = 1;
    protected final static int DOWN = 2;
    protected final static int LEFT = 3;

    protected final static int UNINITIALIZED = 4;
    protected final static int INITIALIZED = 5;
    protected final static int PLAYING = 6;
    protected final static int GAVEOVER = 7;
    protected final static String[] COLOR_VALUES =
            new String[] {"Blue", "Red", "Green", "Orange", "Sienna", "Hotpink", "Darkgreen", "Purple"};
    protected final static Set<String> COLORS_SET= new HashSet<>(Arrays.asList(COLOR_VALUES));


    public void initialize(String color, List<String> colors) {
        if (state != UNINITIALIZED && state != GAVEOVER)
            throw new java.lang.IllegalStateException("Can't call initialize in this state!");

        if (!COLORS_SET.contains(color))
            throw new java.lang.IllegalArgumentException("Can't initialize with this color!");

        this.color = color;
        state = INITIALIZED;
    }


    public PlayerPosition placePawn(Board board) {
        if (state != INITIALIZED) throw new java.lang.IllegalStateException("Can't call placePawn in this state");

        if (board.getPlayerColors().contains(this.color)) {
            throw new java.lang.IllegalStateException("Pawn already exists on board");
        }

        PlayerPosition result = null;
        do {
            //returns a number between 0 to 3, representing four sides of the board
            int side = ThreadLocalRandom.current().nextInt(UP, LEFT + 1);
            int blockNum = ThreadLocalRandom.current().nextInt(0, 5 + 1);
            int spotOffset = ThreadLocalRandom.current().nextInt(0, 1 + 1);
            switch (side) {
                case UP:
                    result = new PlayerPosition(0, blockNum, 0+spotOffset);
                    break;
                case RIGHT:
                    result = new PlayerPosition(blockNum, 5, 2+spotOffset);
                    break;
                case DOWN:
                    result = new PlayerPosition(5, blockNum, 4+spotOffset);
                    break;
                case LEFT:
                    result = new PlayerPosition(blockNum, 0, 6+spotOffset);
                    break;
            }
        } while(board.positionHasPlayer(result));
        state = PLAYING;
        return result;
    }

    public void endGame(Board board, List<String> winnerColors) {
        state = GAVEOVER;
    }

    public void setState(String state) {
        if (state.equals("UNINITIALIZED")) this.state = UNINITIALIZED;
        else if (state.equals("INITIALIZED")) this.state = INITIALIZED;
        else if (state.equals("PLAYING")) this.state = PLAYING;
        else if (state.equals("GAMVEOVER")) this.state = GAVEOVER;
    }




    //protected helper functions-----------------------------------------------------------------------------------


    // Every map is a possible symmetry axis. The key is an integer indicating a position on a tile, the value is the symmetric position
    // follow that symmetry axis. The input is an array of the pairing of symmetric positions.
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

    // Determines how symmetric a tile is by looking at how many symmetric axes the given tile has.
    // Given a tile, returns an integer of either 0, 1, 2 or 4. Bigger number indicates higher symmetry.
    static public int symmetry(Tile t){
        Set<Map<Integer, Integer>> maps = new HashSet<>();

        maps.add(createMap(new int[]{2,3,1,4,0,5,6,7}));
        maps.add(createMap(new int[]{0,1,2,7,3,6,4,5}));
        maps.add(createMap(new int[]{1,2,0,3,4,7,5,6}));
        maps.add(createMap(new int[]{0,7,1,6,2,5,3,4}));

        List<int[]> paths = t.getRotation(0);
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
            continue;
        }

        return count;
    }

    //returns true if a tile doesn't lead player to edge of board
    protected Tile rotateTileTillLegal(Board board, Tile tile) {
        Tile t = new Tile(tile);
        for (int i = 0; i < 4; i++) {

            //Check if placing this tile would make the token move to the border
            PlayerPosition position = board.getPlayerPositionByColor(this.color);
            int nextSpot = t.getConnected(position.getSpot());
            position.setSpot(nextSpot);
            if (board.isBorder(position)){
                t.rotateClockwise();
                continue;
            }

            //starting point is the point next to the one that the player would move to from the placed tile
            PlayerPosition startingPosition = board.flip(position);

            //call moveAlongPath to see if token would reach end of board
            boolean result = true;
            board.placeTile(t, position.getY(), position.getX());
            if (board.isBorder(moveAlongPath(startingPosition, board))) result = false;
            board.removeTile(position.getY(), position.getX());
            if (result) {return t;}
            else {
                t.rotateClockwise();
                continue; }
        }
        return null;
    }

    //returns the furthest adjacent position a player can move to from given starting position
    //return edge's coordinates if moved to edge
    protected PlayerPosition moveAlongPath(PlayerPosition startingPosition, Board board) {
        PlayerPosition position = new PlayerPosition(startingPosition);
        Tile currTile = board.getTile(position.getY(), position.getX());
        while (currTile != null){
            int nextSpot = currTile.getConnected(position.getSpot());
            position.setSpot(nextSpot);
            //if at edge, return edge coordinates
            if (board.isBorder(position))
                return position;

            position = board.flip(position);
            currTile = board.getTile(position.getY(), position.getX());
        }
        return position;
    }


}
