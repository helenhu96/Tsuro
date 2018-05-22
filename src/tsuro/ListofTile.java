package tsuro;
import com.sun.tools.javac.util.Convert;

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
            ConvertedTile ctile = new ConvertedTile(t);
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

    public List<Tile> backtoTiles(){
        List<Tile> result = new ArrayList<>();
        for (int i = 0; i < ctiles.size(); i++){
            result.add(ctiles.get(i).backtoTile());
        }
        return result;
    }
}
