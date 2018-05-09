package Classes;

import java.util.List;

class CrappyPlayer extends MPlayer {

    CrappyPlayer(String name) {super(name);}

    @Override
    public PlayerPosition placePawn(Board board) {
        if (state != INITIALIZED) throw new java.lang.IllegalStateException("Can't call placePawn in this state");

        //return invalid position
        state = PLAYING;
        return new PlayerPosition(3,4,5);
    }

    public Tile playTurn(Board board, List<Tile> tiles, int numTiles) {
        return new Tile(new int[]{0,2,3,1,4,5,6,7});
    }


}
