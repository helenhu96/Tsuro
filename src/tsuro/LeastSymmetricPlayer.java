package tsuro;

import java.util.*;

import com.google.common.base.Preconditions;


class LeastSymmetricPlayer extends MPlayer {

    public final static int UP = 0;
    public final static int RIGHT = 1;
    public final static int DOWN = 2;
    public final static int LEFT = 3;

    LeastSymmetricPlayer(String name) { super(name); }


    public Tile playTurn(Board board, List<Tile> hand, int numTiles) {
        Preconditions.checkArgument(this.state == State.PLAYING, "Expected PLAYING state, got " + this.state);
        //if (state != PLAYING) throw new IllegalStateException("Can't playTurn in this state!");
        Map<Integer, List<Tile>> scores = new HashMap<>();

        for (Tile t: hand){
            int score = t.getSymmetryScore();

            if (!scores.containsKey(score)){
                List<Tile> newList = new ArrayList<>();
                newList.add(t);
                scores.put(score, newList);
            } else {
                scores.get(score).add(t);
            }
        }

        int[] array = new int[]{0,1,2,4};
        //loop through all possible levels of symmetricity
        for (int i = 0; i < array.length; i++){
            List<Tile> target = scores.get(array[i]);
            if (target!=null) {
                for (int j=0; j<target.size(); j++){
                    Tile result = rotateTileTillLegal(board, target.get(j));
                    if (result != null){
                        return result;
                    }
                }
            }
        }

        return hand.get(0);

    }


}
