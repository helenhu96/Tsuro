package tsuro;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"xy", "ctile"}, name = "ent")
public class TileEntry {
    ConvertedTile ctile;
    TileLocation xy;

    public TileEntry() {}
    public TileEntry(TileLocation loc, ConvertedTile ctile) {
        this.xy = loc;
        this.ctile = ctile;
    }

    public void setXy(TileLocation loc) {
        this.xy = loc;
    }

    public TileLocation getXy() {
        return this.xy;
    }

    @XmlElement(name = "tile")
    public void setCtile(ConvertedTile ctile) {
        this.ctile = ctile;
    }

    public ConvertedTile getCtile() {
        return this.ctile;
    }




}
