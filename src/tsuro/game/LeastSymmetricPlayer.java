package tsuro.game;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class LeastSymmetricPlayer extends SymmetricPlayer {

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
