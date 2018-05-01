package Classes;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MostSymmetricPlayerTest {

    @Test
    public void playTurn() {
        Board board = new Board();
        SPlayer p = new SPlayer("Green");
        MPlayer m = new MostSymmetricPlayer("G");
        m.initialize("Green", null);
        board.updatePlayerPosition(p, new PlayerPosition(4,1,2));
        board.placeTile(new Tile(new int[]{0,3,1,6,2,5,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 0);
        List<Tile> list = new ArrayList<>();
        list.add(new Tile(new int[]{0,5,1,7,2,3,4,6}));
        list.add(new Tile(new int[]{0,7,1,2,3,4,5,6}));
        list.add(new Tile(new int[]{0,2,1,6,3,7,4,5}));
        Tile actual = m.playTurn(board,list,10);
        assertEquals(new Tile(new int[]{0,7,1,2,3,4,5,6}), actual);
        assertEquals(0, actual.getOrientation());
    }

    @Test
    public void playTurn1() {
        Board board = new Board();
        SPlayer p = new SPlayer("Green");
        MPlayer m = new MostSymmetricPlayer("G");
        m.initialize("Green", null);
        board.updatePlayerPosition(p, new PlayerPosition(4,1,2));
        board.placeTile(new Tile(new int[]{0,3,1,6,2,5,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 0);
        List<Tile> list = new ArrayList<>();
        list.add(new Tile(new int[]{0,3,1,6,2,7,4,5}));
        list.add(new Tile(new int[]{0,4,1,3,2,7,5,6}));
        list.add(new Tile(new int[]{0,2,1,5,3,7,4,6}));
        Tile actual = m.playTurn(board,list,9);
        Tile expected = new Tile(new int[]{0,3,1,6,2,7,4,5});
        expected.rotateClockwise();
        assertEquals(expected, actual);
    }

    @Test
    public void playTurn2() {
        Board board = new Board();
        SPlayer p = new SPlayer("Green");
        MPlayer m = new MostSymmetricPlayer("G");
        m.initialize("Green", null);
        board.updatePlayerPosition(p, new PlayerPosition(4,1,2));
        board.placeTile(new Tile(new int[]{0,3,1,6,2,5,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 0);
        List<Tile> list = new ArrayList<>();
        list.add(new Tile(new int[]{0,3,1,2,4,7,5,6}));
        list.add(new Tile(new int[]{0,5,1,4,2,7,3,6}));
        list.add(new Tile(new int[]{0,5,1,6,2,7,3,4}));
        Tile actual = m.playTurn(board,list,9);
        Tile expected = new Tile(new int[]{0,3,1,2,4,7,5,6});
        assertEquals(expected, actual);
    }
}