package tsuro.xmlmodel;
import tsuro.game.Tile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;


@XmlRootElement (name = "set")
public class SetofTile {
    Set <ConvertedTile> ctiles;

    public SetofTile() { }

    public SetofTile(Set<Tile> tiles) {
        setSetofTile(new HashSet<>());
        for (Tile t: tiles) {
            addSetofTile(t);
        }
    }

    @XmlElement (name = "tile")
    public void setSetofTile(Set<ConvertedTile> list){
        this.ctiles = list;
    }

    public Set<ConvertedTile> getSetofTile(){
        return this.ctiles;
    }

    public void addSetofTile(Tile t) {
        this.ctiles.add(new ConvertedTile(t));
    }

}
