package Classes;

import java.util.*;
public class SPlayer {
    private String color;
    private List<Tile> handTiles;
    private IPlayer iplayer;

    public SPlayer(String color) {
        this.color = color;
        handTiles = new ArrayList<>();
    }

    public void associatePlayer(IPlayer iplayer){
        this.iplayer = iplayer;
    }

    public IPlayer getIplayer() {
        return iplayer;
    }

    public String getColor() {
        return color;
    }

    public void cheat(){
        iplayer = new RandPlayer(color);
    }

    //returns hand tiles
    public List<Tile> getHandTiles() {
        return handTiles;
    }

    public void removeHandTiles(){
        this.handTiles = new ArrayList<>();
    }


    // player receives tile
    public void receiveTile(Tile tile) {
        handTiles.add(tile);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SPlayer that = (SPlayer) o;
        if (!color.equals(that.color)) return false;
        if (this.numHandTiles()!=that.numHandTiles()) return false;
        List<Tile> a = this.getHandTiles();
        List<Tile> b = that.getHandTiles();
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i))) {
                return false;
            }
        }
        return true;
    }
}
