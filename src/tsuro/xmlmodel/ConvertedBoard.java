package tsuro.xmlmodel;

import tsuro.game.Board;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement (name = "board")
@XmlType (propOrder = {"tiles", "pawns"})
public class ConvertedBoard {

    Tiles tiles;
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






//    public Board backtoBoard(){
//
//        Board ob = new Board();
//
//        Map<Tile, int[]> tileLocations = backtoTiles(tiles.entries);
//        for (Tile t: tileLocations.keySet()){
//            ob.placeTile(t, tileLocations.get(t)[0], tileLocations.get(t)[1]);
//        }
//
//
//        //unhandled pawn positions
//
//
//
//        return ob;
//    }
//
//
//    public static Map<Tile, int[]> backtoTiles(List<TileEntry> entries){
//
//        Map<Tile, int[]> tile_map = new HashMap<>();
//
//        for (int i = 0; i < entries.size(); i++) {
//            TileEntry curr = entries.get(i);
//
//            Tile t = curr.getCtile().backtoTile();
//            int[] loc = curr.getXy().backtoXY();
//
//            tile_map.put(t, loc);
//        }
//
//        return tile_map;
//    }

}
