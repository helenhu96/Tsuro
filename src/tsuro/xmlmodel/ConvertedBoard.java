package tsuro.xmlmodel;

import tsuro.game.Board;

import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement (name = "board")
@XmlType (propOrder = {"tiles", "pawns"})
public class ConvertedBoard {

    Tiles tiles = new Tiles();
    Pawns pawns;
    public ConvertedBoard() {}

    public ConvertedBoard(Board board) {
        setTiles(new Tiles(board));
        setPawns(new Pawns(board));
    }

    @XmlElement(name = "map")
    public void setTiles(Tiles tiles) {
        this.tiles = tiles;
    }

    public Tiles getTiles() {
        return this.tiles;
    }


    @XmlElement(name = "map")
    public void setPawns(Pawns pawns) {
        this.pawns = pawns;
    }

    public Pawns getPawns() {
        return this.pawns;
    }


}
