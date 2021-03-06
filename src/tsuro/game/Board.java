package tsuro.game;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
public class Board {


    private Tile[][] tiles;
    private int numTiles;
    private static int boardsize = 6;

    public Map<String, PlayerPosition> colorToPosition;

    private List<Tile> tilesOnBoard;


    public Board() {
        this.tiles = new Tile[boardsize][boardsize];
        this.colorToPosition = new HashMap<>();
        this.numTiles = 0;
        this.tilesOnBoard = new ArrayList<>();
    }

    public Tile getTile(int y, int x){
        if (tiles[y][x]==null){
            return null;
        }
        return new Tile(this.tiles[y][x]);
    }

    public void placeTile(Tile tile, int y, int x) {
        if(tiles[y][x]!=null) {
            throw new IllegalArgumentException("Position already has tile");
        }
        tiles[y][x] = new Tile(tile);
        numTiles++;
        tilesOnBoard.add(tile);
    }



    public Map<String, PlayerPosition> getColorToPosition() {
        return this.colorToPosition;
    }



    public Tile removeTile(int y, int x) {
        if(tiles[y][x]==null) {
            throw new IllegalArgumentException("Position is empty");
        }
        Tile result = tiles[y][x];
        tiles[y][x] = null;
        numTiles--;
        return result;
    }

    public int getNumTiles(){
        return numTiles;
    }

    public void updatePlayerPosition(SPlayer player, PlayerPosition newPos) {
        colorToPosition.put(player.getColor(), new PlayerPosition(newPos));
    }

    //returns the set of colors of the players in this game
    public Set<String> getPlayerColors(){
        Set<String> colors = new HashSet<>();
        colors.addAll(this.colorToPosition.keySet());
        return colors;
    }

    //returns copy of player's position
    public PlayerPosition getPlayerPosition(SPlayer player) {
        PlayerPosition pp = null;
        try {
            for (String color : this.colorToPosition.keySet()) {
                if (color.equals(player.getColor())) {
                    pp = new PlayerPosition(colorToPosition.get(color));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pp;
    }

    //returns copy of token's position
    public PlayerPosition getPlayerPositionByColor(String color) {
        for (String c: this.colorToPosition.keySet()) {
            if (c.equals(color))
                return new PlayerPosition(colorToPosition.get(c));
        }
        throw new IllegalArgumentException("No such player");
    }


    //flip the position across to the next adjacent block
    public PlayerPosition flip(PlayerPosition position) throws Exception {
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
        throw new IllegalArgumentException("Player position not valid");
    }


    //returns true if a point is at a border
    public boolean isBorder(PlayerPosition position){
        int spot = position.getSpot();

        if (spot == 0 || spot == 1)
            return position.getY() == 0;

        if (spot == 2 || spot == 3)
            return position.getX() == 5;

        if (spot == 4 || spot == 5)
            return position.getY() == 5;

        if (spot == 6 || spot == 7) {
             return position.getX() == 0;
        }
        return false;
    }

    //checks if an existing position has a player already
    public boolean positionHasPlayer(PlayerPosition position) {
        for (PlayerPosition p: colorToPosition.values()) {
            if (p.equals(position)) {
                return true;
            }
        }
        return false;
    }

    public String getPlayerByPosition(PlayerPosition position) throws Exception{
        for (String c: colorToPosition.keySet()) {
            if (position.equals(colorToPosition.get(c))) {
                return c;
            }
        }
        throw new Exception("no player here!");
    }

    /**
     *
     * @param position
     * @param tile
     * @return whether the tile is a legal move, rotation specific
     */
    public boolean tileLegal(PlayerPosition position, Tile tile, Set<Tile> hand) throws Exception{
        if (!tileKillsPlayer(position, tile)) {
            return true;
        }
        // if the tile kills the player
        for (Tile currTile: hand) {
            Tile tempHandTile = new Tile(currTile);
            for (int i=0; i<4; i++) {
                if (!tileKillsPlayer(position, tempHandTile)) {
//                    System.err.println("some rotation does not kill!");
                    return false;
                }
                tempHandTile.rotateClockwise();
            }
        }
        return true;
    }


    /**
     *
     * @param p
     * @param tile
     * @return whether the tile kills the player
     */
    public boolean tileKillsPlayer(PlayerPosition p, Tile tile) throws Exception{
        boolean result = false;
        PlayerPosition position = new PlayerPosition(p);
        this.placeTile(tile, position.getY(), position.getX());
        if (this.isBorder(moveAlongPath(position))) {
            result = true;
        }
        this.removeTile(position.getY(), position.getX());
        return result;
    }

    public PlayerPosition moveAlongPath(PlayerPosition position) throws Exception{
        Tile currTile = this.getTile(position.getY(), position.getX());
        while (currTile != null){
            int nextSpot = currTile.getConnected(position.getSpot());
            position.setSpot(nextSpot);
            //if at edge, return edge coordinates
            if (this.isBorder(position)) {
                return position;
            }
            position = this.flip(position);
            currTile = this.getTile(position.getY(), position.getX());
        }
        return position;
    }


    public boolean isTileOnBoard(PlayerPosition pp){
        int y = pp.getY();
        int x = pp.getX();

        if (y<0 || y>5 || x<0 || x>5){
            return false;
        } else {
            if (tiles[y][x] != null){
                return true;
            } else {
                return false;
            }
        }
    }

}
