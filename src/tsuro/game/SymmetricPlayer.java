package tsuro.game;

import com.google.common.base.Preconditions;

import java.util.*;

public abstract class SymmetricPlayer extends MPlayer{

    protected SymmetricPlayer(String name) {
        super(name);
    }

    public Set<Tile> legalTiles;
    public Board board;
    public Set<Tile> hand;

    //TODO: choose legal here
    public Tile playTurn(Board board, Set<Tile> hand, int tilesInDeck) throws Exception {
        this.board = board;
        this.hand = hand;
        Preconditions.checkArgument(this.state == PlayerState.PLAYING, "Expected PLAYING state, got " + this.state);
        Map<Integer, List<Tile>> scores = new HashMap<>();
        legalTiles = chooseLegalRotations(board, hand);
        for (Tile t: legalTiles){
            int score = t.getScore();
            if (!scores.containsKey(score)){
                scores.put(score, new ArrayList<>());
            }
            scores.get(score).add(t);
        }

        return chooseSymmetricTile(scores);
    }

    protected abstract Tile chooseSymmetricTile(Map<Integer, List<Tile>> scores) throws Exception;
}
