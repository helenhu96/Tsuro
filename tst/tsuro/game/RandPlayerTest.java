package tsuro.game;

import org.junit.Test;
import tsuro.game.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class RandPlayerTest {

    @Test
    public void placePawn() {
        MPlayer randP = new RandPlayer("Cathy");
        Board board = new Board();
        SPlayer testP = new SPlayer("green");
        SPlayer testP2 = new SPlayer("red");
        board.updatePlayerPosition(testP, new PlayerPosition(0, 0, 0));
        board.updatePlayerPosition(testP2, new PlayerPosition( 5, 3, 5));
        Administrator admin = new Administrator();
        admin.registerPlayer(randP);
        PlayerPosition test = randP.placePawn(board);
        assertTrue(board.isBorder(test));
    }

    //TODO: test this
    @Test
    public void playTurn() throws Exception{
        //honestly not sure how to test this...
        Board board = new Board();
        SPlayer p = new SPlayer("green");
        MPlayer player = new RandPlayer("G");
        List<String> colors = new ArrayList<>();
        colors.add("green");
        player.initialize("green", colors);
        Set<Tile> set = new HashSet<>();
        List<Tile> help = new ArrayList<>();
        Tile tile1 = new Tile(new int[]{0,5,1,7,2,3,4,6});
        help.add(tile1);
        Tile tile2 = new Tile(new int[]{0,7,1,2,3,4,5,6});
        help.add(tile2);
        Tile tile3 = new Tile(new int[]{0,2,1,6,3,7,4,5});
        help.add(tile3);
        for (int i=0; i<3; i++) {
            Tile tile = help.get(i);
            Tile t = new Tile(tile);
            for (int j=0; j<4; j++) {
                set.add(new Tile(t));
                t.rotateClockwise();
            }
        }
        PlayerPosition position = player.placePawn(board);
        board.updatePlayerPosition(p, position);
        Tile newtile = player.playTurn(board, new HashSet<>(help), 32);
        assertTrue(set.contains(newtile));


        }

    @Test
    public void allMovesEliminate() throws Exception{
        //or how to test this....
        Board board = new Board();
        SPlayer p = new SPlayer("green");
        MPlayer m = new RandPlayer("G");
        List<String> colors = new ArrayList<>();
        colors.add("green");
        m.initialize("green", colors);
        // just so that we updated state
        m.placePawn(board);
        board.updatePlayerPosition(p, new PlayerPosition(5, 0, 0));
        board.placeTile(new Tile(new int[]{0,5,1,2,3,6,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,7,1,2,3,4,5,6}), 4, 1);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 1);
        Set<Tile> set = new HashSet<>();
        Tile tile1 = new Tile(new int[]{0,2,1,3,4,6,5,7});
        Tile tile2 = new Tile(new int[]{0,3,1,7,2,6,4,5});
        Tile tile3 = new Tile(new int[]{0,5,1,4,2,6,3,7});
        set.add(tile1);
        set.add(tile2);
        set.add(tile3);
        Tile t = m.playTurn(board, set, 29);
        assertTrue(t.sameTile(tile1) || t.sameTile(tile2) || t.sameTile(tile3));
    }

}