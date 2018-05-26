package tsuro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawPile {
    private List<Tile> tiles;
    private boolean hasDragon;

    public DrawPile() {
        this.tiles = new ArrayList<>();
        this.hasDragon = true;
    }

    // initialize the draw pile as all the legal tiles
    public void initialize() {
        this.tiles = Administrator.getAllLegalTiles();
    }


    public void giveDragon(){
        this.hasDragon = false;
    }

    public Tile drawATile() throws Exception{
        if (this.size() > 0) {
            return tiles.remove(0);
        } else if (this.hasDragon) {
            giveDragon();
            return null;
        } else {
            throw new Exception("trying to draw a tile while the pile is empty!");
        }
    }

    public boolean isEmpty() {
        return !(this.size() > 0 || this.hasDragon);
    }



    public void addTilesAndShuffle(List<Tile> tilesToAdd) {
        tiles.addAll(tilesToAdd);
        Collections.shuffle(tiles);
    }

    public void addDragon() throws Exception{
        if (this.hasDragon) {
            throw new Exception("return dragon while dragon in pile!");
        }
        this.hasDragon = true;
    }

    public boolean hasDrogon() {
        return this.hasDragon;
    }

    public int size() {
        return tiles.size();
    }

}