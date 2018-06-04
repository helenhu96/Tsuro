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

    //TODO: check exception handling here!!!
    public Tile playTurn(Board board, Set<Tile> hand, int tilesInDeck) {
        this.board = board;
        this.hand = hand;
        checkState(PlayerState.PLAYING);
        Map<Integer, Set<Tile>> scores = new HashMap<>();
        try {
            legalTiles = chooseLegalRotations(board, hand);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Tile t: legalTiles){
            int score = t.getScore();
            if (!scores.containsKey(score)){
                scores.put(score, new HashSet<>());
            }
            scores.get(score).add(t);
        }
        Tile tile = null;
        try {
            tile = chooseSymmetricTile(scores);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tile;
    }

    protected abstract Tile chooseSymmetricTile(Map<Integer, Set<Tile>> scores) throws Exception;
}
