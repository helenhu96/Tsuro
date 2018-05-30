package tsuro.game;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class MostSymmetricPlayer extends SymmetricPlayer {

    public final static int UP = 0;
    public final static int RIGHT = 1;
    public final static int DOWN = 2;
    public final static int LEFT = 3;

    MostSymmetricPlayer(String name) { super(name); }


    public Tile chooseSymmetricTile(Map<Integer, List<Tile>> scores) throws Exception{
        int[] array = new int[]{0,1,2,4};
        //loop through all possible levels of symmetricity
        for (int i = array.length - 1; i >= 0; i--){
            List<Tile> target = scores.get(array[i]);
            if (target!=null) {
                int randomIndex = ThreadLocalRandom.current().nextInt(target.size());
                return target.get(randomIndex);
            }
        }
        throw new Exception("no tile legal in hand!");
    }

}
