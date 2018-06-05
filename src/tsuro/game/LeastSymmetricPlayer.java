package tsuro.game;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class LeastSymmetricPlayer extends SymmetricPlayer {

    public final static int UP = 0;
    public final static int RIGHT = 1;
    public final static int DOWN = 2;
    public final static int LEFT = 3;

    LeastSymmetricPlayer(String name) { super(name); }

    public Tile chooseSymmetricTile(Map<Integer, Set<Tile>> scores) throws Exception{
        int[] array = new int[]{0,1,2,4};
        //loop through all possible levels of symmetricity
        for (int i =0; i < array.length; i++){
            Set <Tile> target = scores.get(array[i]);
            if (target!=null) {
                int randomIndex = ThreadLocalRandom.current().nextInt(target.size());
                return new ArrayList<Tile>(target).get(randomIndex);
            }
        }
        throw new Exception("no tile legal in hand!");
    }

}
