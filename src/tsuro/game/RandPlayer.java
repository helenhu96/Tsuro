package tsuro.game;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import com.google.common.base.Preconditions;

//TODO: rewrite this
class RandPlayer extends MPlayer {


    RandPlayer(String name) { super(name); }


    public Tile playTurn(Board board, Set<Tile> hand, int tilesInDeck) throws Exception{
        Preconditions.checkState(state == PlayerState.PLAYING, "Expect State Playing, actual state " + state);
        Preconditions.checkArgument(hand.size()<= Administrator.HAND_SIZE, "Can't have more than 3 tiles in hand");
        Set<Tile> possibleMoves = chooseLegalRotations(board, hand);


        if (possibleMoves.size()>0) {
            int rand = ThreadLocalRandom.current().nextInt(0, possibleMoves.size());
            return new ArrayList<Tile>(possibleMoves).get(rand);
        }
        //otherwise, no non-eliminating moves, return a random tile
        int tileNum = ThreadLocalRandom.current().nextInt(0, hand.size());
        int rotNum = ThreadLocalRandom.current().nextInt(0, 4);

        Tile ret = new ArrayList<Tile>(hand).get(tileNum);
        for (int i=0; i<rotNum; i++) {
            ret.rotateClockwise();
        }
        return ret;
    }





}
