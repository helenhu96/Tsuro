package tsuro;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import com.google.common.base.Preconditions;

//TODO: rewrite this
class RandPlayer extends MPlayer {


    RandPlayer(String name) { super(name); }


    public Tile play_turn(Board b, Set<Tile> hand, int tilesInDeck) throws IllegalArgumentException{
        Preconditions.checkState(state == State.PLAYING, "Expect State Playing, actual state " + state);
        Preconditions.checkArgument(hand.size()<= Administrator.HAND_SIZE, "Can't have more than 3 tiles in hand");
        List<Tile> possibleMoves = new ArrayList<>();

//        while (board.tileLegal())

        //loop through each tile and add legal moves to list


//        for (int i=0; i<tiles.size(); i++) {
//            Tile t = tiles.get(i);
//            for (int r=0; r<4; r++) {
//                if (board.tileLegal(admin.getSPlayerFromColor(this.getColor()), t)) possibleMoves.add(new Tile(t));
//                t.rotateClockwise();
//            }
//        }
//        if (possibleMoves.size()>0) {
//            int rand = ThreadLocalRandom.current().nextInt(0, possibleMoves.size());
//            return possibleMoves.get(rand);
//        }
//        //otherwise, no non-eliminating moves, return a random tile
//        int tileNum = ThreadLocalRandom.current().nextInt(0, tiles.size());
//        int rotNum = ThreadLocalRandom.current().nextInt(0, 3+1);

//        Tile ret = tiles.get(tileNum);
//        for (int i=0; i<rotNum; i++) {
//            ret.rotateClockwise();
//        }
//        return ret;
        return new Tile();
    }





}
