package tsuro;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RandPlayerTest {

    @Test
    public void placePawn() {
        MPlayer randP = new RandPlayer("Cathy");
        Board board = new Board();
        SPlayer testP = new SPlayer("Green");
        SPlayer testP2 = new SPlayer("Red");
        board.updatePlayerPosition(testP, new PlayerPosition(0, 0, 0));
        board.updatePlayerPosition(testP2, new PlayerPosition( 5, 3, 5));
        Administrator admin = new Administrator();
        admin.registerPlayer(randP);
        PlayerPosition test = randP.placePawn(board);
        assertTrue(board.isBorder(test));
    }

    //TODO: test this
    @Test
    public void playTurn() {
        //honestly not sure how to test this...

        Board board = new Board();
        SPlayer p = new SPlayer("Green");
        MPlayer m = new RandPlayer("G");
        m.initialize("Green", null);
        board.updatePlayerPosition(p, new PlayerPosition(4,1,2));
        board.placeTile(new Tile(new int[]{0,3,1,6,2,5,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 0);
        List<Tile> list = new ArrayList<>();
        list.add(new Tile(new int[]{0,5,1,7,2,3,4,6}));
        list.add(new Tile(new int[]{0,7,1,2,3,4,5,6}));
        list.add(new Tile(new int[]{0,2,1,6,3,7,4,5}));
        }

    @Test
    public void allMovesEliminate() {
        //or how to test this....
        Board board = new Board();

        SPlayer p = new SPlayer("Green");
        MPlayer m = new RandPlayer("G");
        m.initialize("Green", null);
        board.updatePlayerPosition(p, new PlayerPosition(5, 0, 0));
        board.placeTile(new Tile(new int[]{0,5,1,2,3,6,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,7,1,2,3,4,5,6}), 4, 1);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 1);
        List<Tile> list = new ArrayList<>();
        list.add(new Tile(new int[]{0,2,1,3,4,6,5,7}));
        list.add(new Tile(new int[]{0,3,1,7,2,6,4,5}));
        list.add(new Tile(new int[]{0,5,1,4,2,6,3,7}));

    }

}