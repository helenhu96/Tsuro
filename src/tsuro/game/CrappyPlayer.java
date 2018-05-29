package tsuro.game;

import com.google.common.base.Preconditions;

import java.util.Set;

class CrappyPlayer extends MPlayer {

    CrappyPlayer(String name) {super(name);}

    @Override
    public PlayerPosition placePawn(Board board) {
        checkState(PlayerState.INITIALIZED);

        //return invalid position
        state = PlayerState.PLAYING;
        return new PlayerPosition(3,4,5);
    }

    public Tile playTurn(Board board, Set<Tile> tiles, int numTiles) {
        Preconditions.checkArgument(this.state == PlayerState.PLAYING, "Expected PLAYING state, got " + this.state);
        return new Tile(new int[]{0,2,3,1,4,5,6,7});
    }

}
