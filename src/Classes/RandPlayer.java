package Classes;


import java.util.*;

import java.util.concurrent.ThreadLocalRandom;




class RandPlayer extends MPlayer {


    RandPlayer(String name) { super(name); }

    public String getName() {
        return name;
    }

    public void initialize(String color, List<String> colors) {
        this.color = color;
    }


    public Tile playTurn(Board board, List<Tile> tiles, int numTiles) {
        List<Tile> possibleMoves = new ArrayList<Tile>();
        //loop through each tile and add legal moves to list
        for (int i=0; i<tiles.size(); i++) {
            Tile t = tiles.get(i);
            for (int r=0; r<4; r++) {
                if (tileLegal(board, t)) possibleMoves.add(new Tile(t));
                t.rotateClockwise();
            }
        }
        if (possibleMoves.size()>0) {
            int rand = ThreadLocalRandom.current().nextInt(0, possibleMoves.size());
            return possibleMoves.get(rand);
        }
        //otherwise, no non-eliminating moves, return a random tile
        int tileNum = ThreadLocalRandom.current().nextInt(0, tiles.size());
        int rotNum = ThreadLocalRandom.current().nextInt(0, 3+1);

        Tile ret = tiles.get(tileNum);
        for (int i=0; i<rotNum; i++) ret.rotateClockwise();

        return ret;
    }

    public void endGame(Board board, List<String> winnerColors) {

    }

//private helper functions-----------------------------------------------------------------------------------
    
    //returns true if a tile doesn't lead player to edge of board
    private boolean tileLegal(Board board, Tile tile) {
        //Check if placing this tile would make the token move to the border
        PlayerPosition position = board.getPlayerPositionByColor(this.color);
        int nextSpot = tile.getConnected(position.getSpot());
        position.setSpot(nextSpot);
        if (board.isBorder(position)) return false;

        //starting point is the point next to the one that the player would move to from the placed tile
        PlayerPosition startingPosition = board.flip(position);

        //call moveAlongPath to see if token would reach end of board
        boolean result = true;
        board.placeTile(tile, position.getY(), position.getX());
        if (board.isBorder(moveAlongPath(startingPosition, board))) result = false;
        board.removeTile(position.getY(), position.getX());
        return result;
    }

}
