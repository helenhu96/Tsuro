package Classes;

import java.util.*;

public class Board {
    private Tile[][] tiles;
    private Map<SPlayer, PlayerPosition> playerToPosition;
    private int numTiles;

    public Board() {
        this.tiles = new Tile[6][6];
        this.playerToPosition = new HashMap<>();
        this.numTiles = 0;
    }

    public Tile getTile(int y, int x) {
        if (tiles[y][x]==null) return null;
        return new Tile(this.tiles[y][x]);
    }

    public void placeTile(Tile tile, int y, int x) {
        if(tiles[y][x]!=null) throw new java.lang.IllegalArgumentException("Position already has tile");
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
        //move up
        if (position.getSpot() == 0)
            return new PlayerPosition(position.getY()-1, position.getX(), 5);
        if (position.getSpot() == 1)
            return new PlayerPosition(position.getY()-1, position.getX(), 4);
        //move right
        if (position.getSpot() == 2)
            return new PlayerPosition(position.getY(), position.getX()+1, 7);
        if (position.getSpot() == 3)
            return new PlayerPosition(position.getY(), position.getX()+1, 6);
        //move down
        if (position.getSpot() == 4)
            return new PlayerPosition(position.getY()+1, position.getX(), 1);
        if (position.getSpot() == 5)
            return new PlayerPosition(position.getY()+1, position.getX(), 0);
        //move left
        if (position.getSpot() == 6)
            return new PlayerPosition(position.getY(), position.getX()-1, 3);
        if (position.getSpot() == 7)
            return new PlayerPosition(position.getY(), position.getX()-1, 2);

        return null;
    }


    //returns true if a point is at a border
    public boolean isBorder(PlayerPosition position) {
        //is on top border
        if (position.getY() == 0 ) {
            if (position.getSpot() == 0 || position.getSpot() == 1) return true;
        }

        //is on right border
        if (position.getX() == 5 ) {
            if (position.getSpot() == 2 || position.getSpot() == 3) return true;
        }

        //is on bottom border
        if (position.getY() == 5 ) {
            if (position.getSpot() == 4 || position.getSpot() == 5) return true;
        }

        //is on left border
        if (position.getX() == 0 ) {
            if (position.getSpot() == 6 || position.getSpot() == 7) return true;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        if (playerToPosition.size() != board.playerToPosition.size())
            return false;

        if (numTiles != board.numTiles) return false;

        for (int i=0; i<6; i++) {
            for (int j=0; j<6; j++) {
                if (tiles[i][j]== null && board.tiles[i][j]==null)
                    continue;
                else if (tiles[i][j]== null || board.tiles[i][j]==null)
                    return false;
                else if (!tiles[i][j].equals(board.tiles[i][j]))
                    return false;
            }
        }

        for (Map.Entry<SPlayer, PlayerPosition> entry : playerToPosition.entrySet()) {
            SPlayer key = entry.getKey();
            PlayerPosition value = entry.getValue();
            if (board.playerToPosition.get(key) == null)
                return false;
            if (!board.playerToPosition.get(key).equals(value))
                return false;
        }
        return true;

    }


}
