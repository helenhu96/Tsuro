package tsuro;

import java.util.*;

class MostSymmetricPlayer extends MPlayer {

    public final static int UP = 0;
    public final static int RIGHT = 1;
    public final static int DOWN = 2;
    public final static int LEFT = 3;

    MostSymmetricPlayer(String name) { super(name); }


    public Tile playTurn(Board board, List<Tile> tiles, int numTiles) {
        if (state != State.PLAYING) {
            throw new IllegalStateException("Can't playTurn in this state!");
        }
        Map<Integer, List<Tile>> scores = new HashMap<>();

        for (Tile t: tiles){
            int score = t.getSymmtryScore();

            if (!scores.containsKey(score)){
                List<Tile> newList = new ArrayList<>();
                newList.add(t);
                scores.put(score, newList);
            } else {
                scores.get(score).add(t);
            }
        }


        int[] array = new int[]{4,2,1,0};

        for (int i = 0; i <array.length; i++){
            List<Tile> target = scores.get(array[i]);
            if (target != null) {
                for (int j=0; j<target.size(); j++){
                    Tile result = rotateTileTillLegal(board, target.get(j));
                    if (result != null){
                        return result;
                    }
                }
            }
        }
        return tiles.get(0);
    }

}
