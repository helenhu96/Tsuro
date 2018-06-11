package tsuro.xmlmodel;
import tsuro.game.Tile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement (name = "list")
public class ListofTile {
    List<ConvertedTile> ctiles;

    public ListofTile() {
    }

    public ListofTile(List<Tile> tiles) {
        setListofTile(new ArrayList<>());
        for (Tile t: tiles) {
            addListofTile(t);
        }

    }

    @XmlElement (name = "tile")
    public void setListofTile(List<ConvertedTile> list){
        this.ctiles = list;
    }

    public List<ConvertedTile> getListofTile(){
        return this.ctiles;
    }

    public void addListofTile(Tile t) {
        this.ctiles.add(new ConvertedTile(t));
    }


}
