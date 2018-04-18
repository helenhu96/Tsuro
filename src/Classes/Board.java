package Classes;

import java.util.*;

public class Board {
    private Tile[][] tiles;
    private Map<SPlayer, Integer[]> tokensNext;

    public Board() {
        this.tiles = new Tile[6][6];
        this.tokensNext = new HashMap<>();
    }

    public Tile getTile(int y, int x) {
        if (tiles[y][x]==null) return null;
        return new Tile(this.tiles[y][x]);
    }

    public void placeTile(Tile tile, int y, int x) {
        tiles[y][x] = new Tile(tile);
    }

    //updates token's next position
    public void updateTokensNext(SPlayer player, Integer[] newPos) {
        tokensNext.put(player, newPos.clone());
        return;
    }

    //returns copy of token's next position
    public Integer[] getTokensNext(SPlayer player) {
        return tokensNext.get(player).clone();
    }

    //flip the position across to the next adjacent block
    public Integer[] flip(Integer[] position) {
        if (isBorder(position)) return null;
        //move up
        if (position[2] == 0)
            return new Integer[]{position[0]-1, position[1], 5};
        if (position[2] == 1)
            return new Integer[]{position[0]-1, position[1], 4};
        //move right
        if (position[2] == 2)
            return new Integer[]{position[0], position[1]+1, 7};
        if (position[2] == 3)
            return new Integer[]{position[0], position[1]+1, 6};
        //move down
        if (position[2] == 4)
            return new Integer[]{position[0]+1, position[1], 1};
        if (position[2] == 5)
            return new Integer[]{position[0]+1, position[1], 0};
        //move left
        if (position[2] == 6)
            return new Integer[]{position[0], position[1]-1, 3};
        if (position[2] == 7)
            return new Integer[]{position[0], position[1]-1, 2};

        return null;
    }


    //returns true if a point is at a border
    public boolean isBorder(Integer[] position) {
        //is on top border
        if (position[0] == 0 ) {
            if (position[2] == 0 || position[2] == 1) return true;
        }

        //is on right border
        if (position[1] == 5 ) {
            if (position[2] == 2 || position[2] == 3) return true;
        }

        //is on bottom border
        if (position[0] == 5 ) {
            if (position[2] == 4 || position[2] == 5) return true;
        }

        //is on left border
        if (position[1] == 0 ) {
            if (position[2] == 6 || position[2] == 7) return true;
        }

        return false;
    }

}
