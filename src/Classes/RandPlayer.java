package Classes;


import java.util.Collections;
import java.util.List;
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
        //randomize the tile order
        Collections.shuffle(tiles);
        //loop through each tile to find legal move
        for (int i=0; i<tiles.size(); i++) {
            Tile t = tiles.get(i);
            int rotNum = ThreadLocalRandom.current().nextInt(0, 3+1);
            //rotate tile a random number of times between 0 and 3
            for (int r=0; r<rotNum; r++) t.rotateClockwise();
            //if legal, return this tile
            if (tileLegal(board, t)) return t;
            //else, check the other 3 possible rotations
            for (int r=0; r<3; r++) {
                t.rotateClockwise();
                if (tileLegal(board, t)) return t;
            }
        }
        //no legal move, return first tile
        return tiles.get(0);
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
