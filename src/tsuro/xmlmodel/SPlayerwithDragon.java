package tsuro.xmlmodel;
import tsuro.game.SPlayer;
import tsuro.game.Tile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.*;
@XmlType(name = "splayer-dragon")
public class SPlayerwithDragon extends XmlSplayer{
    String color;
    SetofTile setofTile;
    public SPlayerwithDragon() {}
    public SPlayerwithDragon(SPlayer splayer) {
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


}
