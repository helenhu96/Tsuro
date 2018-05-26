package tsuro;

import com.google.common.base.Preconditions;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

abstract class MPlayer implements IPlayer {
    protected String name;
    protected String color;
    protected State state;
    protected MPlayer(String name) {
        this.name = name;
        state = State.UNINITIALIZED;
    }
    final static String[] COLOR_VALUES =
            new String[] {"Blue", "Red", "Green", "Orange", "Sienna", "Hotpink", "Darkgreen", "Purple"};



    protected final static int UP = 0;
    protected final static int RIGHT = 1;
    protected final static int DOWN = 2;
    protected final static int LEFT = 3;


    protected final static Set<String> COLORS_SET= new HashSet<>(Arrays.asList(COLOR_VALUES));


    public String get_name() { return this.name; }

    public String getColor() {
        return this.color;
    }

    //TODO: fix color contract
    public void initialize(String color, List<String> colors) {
        Preconditions.checkState(state == State.UNINITIALIZED, "Expect State Uninitialized, actual state " + state);
        if (!colors.contains(color)) {
            throw new IllegalArgumentException("Can't initialize with this color!");
        }
        this.color = color;
        state = State.INITIALIZED;


    }


    public PlayerPosition placePawn(Board board) {
        Preconditions.checkState(state == State.INITIALIZED, "Expect State Initialized, actual state " + state);

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
        state = State.PLAYING;
        return result;
    }


    public void end_game(Board board, List<String> winnerColors) {
        state = State.UNINITIALIZED;
    }

    public void setState(State s) {
        this.state = s;
    }


    abstract public Tile play_turn(Board b, Set<Tile> hand, int tilesInDeck);



    //protected helper functions-----------------------------------------------------------------------------------


    //TODO: consider moving these to board class or remove continue

//    protected Tile rotateTileTillLegal(Board board, Tile tile) throws Exception{
//        Tile t = new Tile(tile);
//        for (int i = 0; i < 4; i++) {
//
//            //get position of this player
//            PlayerPosition position = board.getPlayerPositionByColor(this.color);
//
//            //call moveAlongPath to see if token would reach end of board
//            boolean result = true;
//            board.placeTile(t, position.getY(), position.getX());
//            if (board.isBorder(moveAlongPath(position, board))) result = false;
//            board.removeTile(position.getY(), position.getX());
//            if (result) {return t;}
//            else {
//                t.rotateClockwise();
//                continue; }
//        }
//        return null;
//    }
//
//
//    //TODO: DELETE THIS AND RESOLVE ISSUES
//    /**
//     *
//     * @param startingPosition
//     * @param board
//     * @return returns the furthest adjacent position a player can move to from given starting position
//     */
//    protected PlayerPosition moveAlongPath(PlayerPosition startingPosition, Board board) throws Exception{
//        PlayerPosition position = new PlayerPosition(startingPosition);
//        Tile currTile = board.getTile(position.getY(), position.getX());
//        while (currTile != null){
//            int nextSpot = currTile.getConnected(position.getSpot());
//            position.setSpot(nextSpot);
//            //if at edge, return edge coordinates
//            if (board.isBorder(position))
//                return position;
//
//            position = board.flip(position);
//            currTile = board.getTile(position.getY(), position.getX());
//        }
//        return position;
//    }




}
