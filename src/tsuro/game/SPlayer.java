package tsuro.game;

import java.util.*;
import javax.xml.bind.annotation.XmlElement;

public class SPlayer {
    private String color;
    @XmlElement
    private Set<Tile> handTiles;
    private IPlayer iplayer;
    private boolean alive;
    private boolean amIDragon;
    public SPlayer(String color) {
        this.color = color;
        this.handTiles = new HashSet<>();
        this.alive = true;
        this.amIDragon= false;
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

    public void dealWithCheater(List<String> colors){
        String name = iplayer.getName() + "replaced";
        this.iplayer = new RandPlayer(name);
        this.iplayer.initialize(color, colors);
    }

    //returns hand tiles
    public Set<Tile> getHandTiles() {
        return handTiles;
    }

    public void removeHandTiles(){
        this.handTiles = new HashSet<>();
    }


    // player gets tile
    public void receiveTile(Tile tile) {
        for(Tile curr: handTiles){
            if (curr.sameTile(tile)) {
                throw new java.lang.IllegalStateException("Tile already exists in hand!");
            }
        }
        if (handTiles.size() > 2) {
            throw new java.lang.IllegalStateException("Already have 3 tiles!");
        }

        handTiles.add(tile);
    }

    //removes given tile from player's hand. returns false if player has no such tile
    public boolean removeTile(Tile tile) {
        for (Tile t : handTiles) {
            if (tile.sameTile(t)) {
                handTiles.remove(t);
                return true;
            }
        }
        return false;
    }

    public void getDragon(){
        this.amIDragon = true;
    }

    public void setDragon(boolean bool) { this.amIDragon = bool;}

    public void returnDragon() {
        this.amIDragon = false;
    }

    public boolean doIHaveDragon() {
        return this.amIDragon;
    }

    //returns number of tiles a player has
    public int numHandTiles() {
        return handTiles.size();
    }

    public boolean hasTile(Tile tile) {
        for (Tile t: this.handTiles) {
            if (tile.sameTile(t)) {
                return true;
            }
        }
        return false;
    }

    public boolean isHandFull() {
        return this.handTiles.size() == 3;
    }

    public void setDead() {
        this.alive = false;
    }

    public boolean isAlive() {
        return this.alive;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof SPlayer)) {
            return false;
        }
        SPlayer p = (SPlayer) o;
        if  (!this.color.equals(p.getColor())) {
            return false;
        }
//        if (this.numHandTiles() != p.numHandTiles()) {
//            return false;
//        }
//
//        if (this.doIHaveDragon() != p.doIHaveDragon()) {
//            return false;
//        }
        return true;
    }

    @Override
    public int hashCode() {
        int sum = 0;
        for (char c: this.color.toCharArray()) {
            sum += c - 'a';
        }
        return sum;
    }

}
