package tsuro.game;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class LeastSymmetricPlayerTest {

    @Test
    public void playTurn() throws Exception{
        Board board = new Board();
        SPlayer p = new SPlayer("Green");
        MPlayer m = new LeastSymmetricPlayer("G");
        m.initialize("Green", null);
        board.updatePlayerPosition(p, new PlayerPosition(4,1,2));
        m.setState(PlayerState.PLAYING);
        board.placeTile(new Tile(new int[]{0,3,1,6,2,5,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 0);
        Set<Tile> set = new HashSet<>();
        set.add(new Tile(new int[]{0,5,1,7,2,3,4,6}));
        set.add(new Tile(new int[]{0,7,1,2,3,4,5,6}));
        set.add(new Tile(new int[]{0,2,1,6,3,7,4,5}));
        Tile actual = m.playTurn(board, set,10);
        assertEquals(new Tile(new int[]{0,2,1,6,3,7,4,5}), actual);
    }


    @Test
    public void playTurn1() throws Exception{
        Board board = new Board();
        SPlayer p = new SPlayer("Green");
        MPlayer m = new LeastSymmetricPlayer("G");
        m.initialize("Green", null);
        board.updatePlayerPosition(p, new PlayerPosition(4,1,2));
        m.setState(PlayerState.PLAYING);
        board.placeTile(new Tile(new int[]{0,3,1,6,2,5,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 0);
        Set<Tile> set = new HashSet<>();
        set.add(new Tile(new int[]{0,3,1,6,2,7,4,5}));
        set.add(new Tile(new int[]{0,4,1,3,2,7,5,6}));
        set.add(new Tile(new int[]{0,2,1,5,3,7,4,6}));
        Tile actual = m.playTurn(board, set,9);
        Tile expected = new Tile(new int[]{0,4,1,3,2,7,5,6});
        expected.rotateClockwise();
        assertEquals(expected, actual);
    }

    @Test
    public void playTurn2() throws Exception{
        Board board = new Board();
        SPlayer p = new SPlayer("Green");
        MPlayer m = new LeastSymmetricPlayer("G");
        m.initialize("Green", null);
        board.updatePlayerPosition(p, new PlayerPosition(4,1,2));
        m.setState(PlayerState.PLAYING);
        board.placeTile(new Tile(new int[]{0,3,1,6,2,5,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 0);
        Set<Tile> set = new HashSet<>();
        set.add(new Tile(new int[]{0,3,1,2,4,7,5,6}));
        set.add(new Tile(new int[]{0,5,1,4,2,7,3,6}));
        set.add(new Tile(new int[]{0,5,1,6,2,7,3,4}));
        Tile actual = m.playTurn(board, set,9);
        Tile expected = new Tile(new int[]{0,5,1,6,2,7,3,4});
        expected.rotateClockwise();
        expected.rotateClockwise();
        assertEquals(expected, actual);
    }

    @Test
    public void playTurnStartFromEdge() throws Exception{
        Board board = new Board();
        SPlayer p = new SPlayer("Green");
        MPlayer m = new LeastSymmetricPlayer("G");
        m.initialize("Green", null);
        board.updatePlayerPosition(p, new PlayerPosition(0,3,1));
        m.setState(PlayerState.PLAYING);
        board.placeTile(new Tile(new int[]{0,7,1,2,3,4,5,6}), 0, 4);
        Set<Tile> set = new HashSet<>();
        set.add(new Tile(new int[]{0,4,1,3,2,6,5,7}));
        set.add(new Tile(new int[]{0,2,1,3,4,6,5,7}));
        set.add(new Tile(new int[]{0,6,1,2,3,4,5,7}));
        Tile actual = m.playTurn(board, set,9);
        Tile expected = new Tile(new int[]{0,4,1,3,2,6,5,7});
        assertEquals(expected, actual);
    }

    @Test
    public void allMovesEliminate() throws Exception{
        Board board = new Board();

        SPlayer p = new SPlayer("Green");
        MPlayer m = new LeastSymmetricPlayer("G");
        m.initialize("Green", null);
        board.updatePlayerPosition(p, new PlayerPosition(5, 0, 0));
        m.setState(PlayerState.PLAYING);
        board.placeTile(new Tile(new int[]{0,5,1,2,3,6,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,7,1,2,3,4,5,6}), 4, 1);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 1);
        Set<Tile> set = new HashSet<>();
        set.add(new Tile(new int[]{0,2,1,3,4,6,5,7}));
        set.add(new Tile(new int[]{0,3,1,7,2,6,4,5}));
        set.add(new Tile(new int[]{0,5,1,4,2,6,3,7}));
        Tile actual = m.playTurn(board, set,9);
        Tile expected = new Tile(new int[]{0,2,1,3,4,6,5,7});
        assertEquals(expected, actual);

    }


}