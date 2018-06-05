package tsuro.xmlmodel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlRootElement
@XmlType(propOrder = {"xy", "ctile"}, name = "ent")
public class TileEntry {
    TileLocation xy;
    ConvertedTile ctile;

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

//
//    public Object[] backToPosandTile(){
//        Object[] result = new Object[2];
//        result[0] = xy.backtoYX();
//        result[1] = ctile.backtoTile();
//        return result;
//    }


}
