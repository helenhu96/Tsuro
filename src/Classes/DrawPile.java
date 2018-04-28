package Classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;

public class DrawPile {
    private List<Tile> tiles;

    public DrawPile() {
        tiles = new ArrayList<>();
    }


    public void initialize() {
        try {
            File file = new File("./tiles.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;

            while ((line = br.readLine()) != null) {
                line = line.replace('(', ' ');
                line = line.replace(')', ' ');
                String[] nums = line.split("[ ]+");

                int[] my_array = new int[8];
                for (int i = 1; i < nums.length; i++) {
                    my_array[i-1] = Integer.parseInt(nums[i]);
                }
                Tile new_tile = new Tile(my_array);
                tiles.add(new_tile);
            }
            Collections.shuffle(tiles);
        }
        catch(IOException e){
            System.err.println("IOException occurred!");
        }
    }

    public Tile drawATile() {
        return tiles.remove(0);
    }

    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    public void addTilesAndShuffle(List<Tile> tilesToAdd) {
        tiles.addAll(tilesToAdd);
        Collections.shuffle(tiles);
    }

    public int size() {
        return tiles.size();
    }

}