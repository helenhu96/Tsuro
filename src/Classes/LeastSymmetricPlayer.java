package Classes;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class LeastSymmetricPlayer extends MPlayer {

    public final static int UP = 0;
    public final static int RIGHT = 1;
    public final static int DOWN = 2;
    public final static int LEFT = 3;

    LeastSymmetricPlayer(String name) { super(name); }

    public String getName(){return name;}

    public void initialize(String color, List<String> colors) {
        this.color = color;
    }


    public Tile playTurn(Board board, List<Tile> tiles, int numTiles) {
        Map<Integer, List<Tile>> scores = new HashMap<>();

        for (Tile t: tiles){
            int score = symmetry(t);

            if (!scores.containsKey(score)){
                List<Tile> newList = new ArrayList<>();
                newList.add(t);
                scores.put(score, newList);
            } else {
                scores.get(score).add(t);
            }
        }


        int[] array = new int[]{0,1,2,4};

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
        return null;

    }

    public void endGame(Board board, List<String> winnerColors) {

    }

}
