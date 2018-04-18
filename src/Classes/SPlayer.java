package Classes;

import java.util.*;
public class SPlayer {
    private String color;
    private List<Tile> handTiles;

    public SPlayer(String color) {
        this.color = color;
        handTiles = new ArrayList<>();
    }

    public String getColor() {
        return color;
    }

    //returns hand tiles
    public List<Tile> getHandTiles() {
        return handTiles;
    }

    public void removeHandTiles(){
        this.handTiles = new ArrayList<>();
    }



    //returns true if successfully receives tile, returns false if
    //player already has 3 tiles
    public boolean receiveTile(Tile tile) {
        if (handTiles.size()>=3) {
            return false;
        }
        handTiles.add(tile);
        return true;
    }

    //removes given tile from player's hand. returns false if player has no such tile
    public boolean removeTile(Tile tile) {
        for (int i=0; i<handTiles.size(); i++) {
            if (handTiles.get(i).sameTile(tile)) {
                handTiles.remove(i);
                return true;
            }
        }
        return false;
    }

    //returns number of tiles a player has
    public int numHandTiles() {
        return handTiles.size();
    }
}
