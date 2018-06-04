package tsuro.game;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import com.google.common.base.Preconditions;

//TODO: rewrite this
class RandPlayer extends MPlayer {


    RandPlayer(String name) { super(name); }

    //TODO: check exception handling
    public Tile playTurn(Board board, Set<Tile> hand, int tilesInDeck){
        checkState(PlayerState.PLAYING);
        if (hand.size() > 3) {
            throw new IllegalArgumentException("can't hand more than three cards in hand!");
        }
        Set<Tile> possibleMoves = new HashSet<>();
        PlayerPosition position = board.getPlayerPositionByColor(this.getColor());
        try {
            possibleMoves = chooseLegalRotations(board, hand, position);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

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
