package tsuro;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class Board {
    private Tile[][] tiles;
    private Map<SPlayer, PlayerPosition> playerToPosition;
    private int numTiles;
    private static int boardsize = 6;

    public Board() {
        this.tiles = new Tile[boardsize][boardsize];
        this.playerToPosition = new HashMap<>();
        this.numTiles = 0;
    }

    public Tile getTile(int y, int x) {
        if (tiles[y][x]==null) return null;
        return new Tile(this.tiles[y][x]);
    }

    public void placeTile(Tile tile, int y, int x) {
        if(tiles[y][x]!=null) {
            throw new java.lang.IllegalArgumentException("Position already has tile");
        }
        tiles[y][x] = new Tile(tile);
        numTiles++;
    }


    public Tile removeTile(int y, int x) {
        if(tiles[y][x]==null) throw new java.lang.IllegalArgumentException("Position is empty");
        Tile result = tiles[y][x];
        tiles[y][x] = null;
        numTiles--;
        return result;
    }

    public int getNumTiles(){
        return numTiles;
    }

    //updates token's next position
    public void updatePlayerPosition(SPlayer player, PlayerPosition newPos) {
        playerToPosition.put(player, new PlayerPosition(newPos));
        return;
    }

    //returns the set of colors of the players in this game
    public Set<String> getPlayerColors(){
        Set<String> colors = new HashSet<>();
        for (SPlayer p: playerToPosition.keySet()){
            colors.add(p.getColor());
        }
        return colors;
    }

    //returns copy of player's position
    public PlayerPosition getPlayerPosition(SPlayer player) {
        return new PlayerPosition(playerToPosition.get(player));
    }

    //returns copy of token's position
    public PlayerPosition getPlayerPositionByColor(String color) {
        for (Map.Entry<SPlayer, PlayerPosition> entry : playerToPosition.entrySet()) {
            if (entry.getKey().getColor().equals(color))
                return new PlayerPosition(entry.getValue());
        }
        throw new java.lang.IllegalArgumentException("No such player");
    }


    //flip the position across to the next adjacent block
    public PlayerPosition flip(PlayerPosition position) {
        if (isBorder(position)) return null;
        int spot = position.getSpot();
        int y = position.getY();
        int x = position.getX();

        //move up
        if (spot == 0)
            return new PlayerPosition(y - 1, x, 5);
        if (spot == 1)
            return new PlayerPosition(y-1, x, 4);
        //move right
        if (spot == 2)
            return new PlayerPosition(y, x + 1, 7);
        if (spot == 3)
            return new PlayerPosition(y , x + 1, 6);
        //move down
        if (spot == 4)
            return new PlayerPosition(y + 1, x, 1);
        if (spot == 5)
            return new PlayerPosition(y + 1, x, 0);
        //move left
        if (spot == 6)
            return new PlayerPosition(y, x - 1, 3);
        if (spot == 7)
            return new PlayerPosition(y, x - 1, 2);

        return null;
    }


    //returns true if a point is at a border
    public boolean isBorder(PlayerPosition position) {
        //is on top border
        int y = position.getY();
        int x = position.getX();
        int spot = position.getSpot();
        if (y == 0) {
            if (spot == 0 || spot == 1) {
                return true;
            }
        }
        //is on right border
        if (x == 5 ) {
            if (spot == 2 || spot == 3) {
                return true;
            }
        }

        //is on bottom border
        if (y == 5 ) {
            if (spot == 4 || spot == 5) {
                return true;
            }
        }

        //is on left border
        if (x == 0 ) {
            if (spot == 6 || spot == 7) {
                return true;
            }
        }

        return false;
    }

    //checks if an existing position has a player already
    public boolean positionHasPlayer(PlayerPosition position) {
        for (PlayerPosition p: playerToPosition.values()) {
            if (p.equals(position)) return true;
        }
        return false;
    }

    public SPlayer getPlayerByPosition(PlayerPosition position) {
        for (SPlayer splayer: playerToPosition.keySet()) {
            if (position.equals(playerToPosition.get(splayer))) {
                return splayer;
            }
        }
        return null;
    }

    //TODO:fill this in
//    public boolean legalplay(Tile tile) {
//
//    }


}
