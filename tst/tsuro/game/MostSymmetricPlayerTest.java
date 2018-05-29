package tsuro.game;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class MostSymmetricPlayerTest {

    @Test
    public void playTurn() throws Exception{
        Board board = new Board();
        SPlayer p = new SPlayer("green");
        MPlayer m = new MostSymmetricPlayer("G");
        List<String> colors = new ArrayList<>();
        colors.add("green");
        m.initialize("green", colors);
        m.setState(PlayerState.PLAYING);
        board.updatePlayerPosition(p, new PlayerPosition(4,1,2));
        board.placeTile(new Tile(new int[]{0,3,1,6,2,5,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 0);
        Set<Tile> set = new HashSet<>();
        set.add(new Tile(new int[]{0,5,1,7,2,3,4,6}));
        set.add(new Tile(new int[]{0,7,1,2,3,4,5,6}));
        set.add(new Tile(new int[]{0,2,1,6,3,7,4,5}));
        Tile actual = m.playTurn(board,set,10);
        assertEquals(new Tile(new int[]{0,7,1,2,3,4,5,6}), actual);
    }

    @Test
    public void playTurn1() throws Exception{
        Board board = new Board();
        SPlayer p = new SPlayer("green");
        MPlayer m = new MostSymmetricPlayer("G");
        List<String> colors = new ArrayList<>();
        colors.add("green");
        m.initialize("green", colors);
        m.setState(PlayerState.PLAYING);
        board.updatePlayerPosition(p, new PlayerPosition(4,1,2));
        board.placeTile(new Tile(new int[]{0,3,1,6,2,5,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 0);
        Set<Tile> set = new HashSet<>();
        set.add(new Tile(new int[]{0,3,1,6,2,7,4,5}));
        set.add(new Tile(new int[]{0,4,1,3,2,7,5,6}));
        set.add(new Tile(new int[]{0,2,1,5,3,7,4,6}));
        Tile actual = m.playTurn(board,set,9);
        Tile expected = new Tile(new int[]{0,3,1,6,2,7,4,5});
        expected.rotateClockwise();
        assertTrue(expected.sameTile(actual));
    }

    @Test
    public void playTurn2() throws Exception{
        Board board = new Board();
        SPlayer p = new SPlayer("green");
        MPlayer m = new MostSymmetricPlayer("G");
        List<String> colors = new ArrayList<>();
        colors.add("green");
        m.initialize("green", colors);
        m.setState(PlayerState.PLAYING);
        board.updatePlayerPosition(p, new PlayerPosition(4,1,2));
        board.placeTile(new Tile(new int[]{0,3,1,6,2,5,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 0);
        Set<Tile> set = new HashSet<>();
        set.add(new Tile(new int[]{0,3,1,2,4,7,5,6}));
        set.add(new Tile(new int[]{0,5,1,4,2,7,3,6}));
        set.add(new Tile(new int[]{0,5,1,6,2,7,3,4}));
        Tile actual = m.playTurn(board,set,9);
        Tile expected = new Tile(new int[]{0,5,1,4,2,7,3,6});
        assertTrue(expected.sameTile(actual));
    }

    @Test
    public void playTurnStartFromEdge() throws Exception{
        Board board = new Board();
        SPlayer p = new SPlayer("green");
        MPlayer m = new MostSymmetricPlayer("G");
        List<String> colors = new ArrayList<>();
        colors.add("green");
        m.initialize("green", colors);
        m.setState(PlayerState.PLAYING);
        board.updatePlayerPosition(p, new PlayerPosition(0,3,1));
        board.placeTile(new Tile(new int[]{0,7,1,2,3,4,5,6}), 0, 4);
        Set<Tile> set = new HashSet<>();
        set.add(new Tile(new int[]{0,4,1,3,2,6,5,7}));
        set.add(new Tile(new int[]{0,2,1,3,4,6,5,7}));
        set.add(new Tile(new int[]{0,6,1,2,3,4,5,7}));
        Tile actual = m.playTurn(board,set,9);
        Tile expected = new Tile(new int[]{0,2,1,3,4,6,5,7});
        assertTrue(expected.sameTile(actual));
    }

    @Test
    public void allMovesEliminate() throws Exception{
        Board board = new Board();

        SPlayer p = new SPlayer("green");
        MPlayer m = new MostSymmetricPlayer("G");
        List<String> colors = new ArrayList<>();
        colors.add("green");
        m.initialize("green", colors);
        m.setState(PlayerState.PLAYING);
        board.updatePlayerPosition(p, new PlayerPosition(5, 0, 0));
        board.placeTile(new Tile(new int[]{0,5,1,2,3,6,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,7,1,2,3,4,5,6}), 4, 1);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 1);
        Set<Tile> set = new HashSet<>();
        Tile t1 = new Tile(new int[]{0,2,1,3,4,6,5,7});
        set.add(t1);
        Tile t2 = new Tile(new int[]{0,3,1,7,2,6,4,5});
        set.add(t2);
        Tile t3 = new Tile(new int[]{0,5,1,4,2,6,3,7});
        set.add(t3);
        Tile actual = m.playTurn(board,set,9);

        assertTrue(actual.sameTile(t3) || actual.sameTile(t1));

    }
}