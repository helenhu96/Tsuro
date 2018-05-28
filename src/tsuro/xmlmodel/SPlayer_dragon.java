package tsuro.xmlmodel;
import tsuro.game.SPlayer;
import tsuro.game.Tile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.*;
@XmlType(name = "splayer-dragon")
public class SPlayer_dragon extends XmlSplayer{
    String color;
    SetofTile setofTile;
    public SPlayer_dragon() {}
    public SPlayer_dragon(SPlayer splayer) {
        this.color = splayer.getColor();
        this.setofTile = new SetofTile(new HashSet<>());
        for (Tile t: splayer.getHandTiles()) {
            addSetofTile(t);
        }
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @XmlElement(name = "set")
    public void setSetofTile(SetofTile setofTile) {
        this.setofTile = setofTile;
    }

    public SetofTile getSetofTile() {
        return setofTile;
    }

    public void addSetofTile(Tile t) {
        this.setofTile.addSetofTile(t);
    }

    public SPlayer backtoSPlayer(){
        SPlayer player = new SPlayer(color);
        Set<Tile> tiles = setofTile.backtoTiles();

        for(Tile t: tiles){
            player.receiveTile(t);
        }
        player.setDragon(true);

        return player;
    }
}
