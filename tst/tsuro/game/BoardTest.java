package tsuro.game;

import org.junit.Test;
import tsuro.game.*;

import static org.junit.Assert.*;

public class BoardTest {

    public Board board = new Board();
    @Test
    public void isBorder() {
        PlayerPosition test = new PlayerPosition(3, 2, 5);
        assertFalse(board.isBorder(test));
    }


    @Test
    public void isBorder1() {
        PlayerPosition test = new PlayerPosition(0, 5, 1);
        assertTrue(board.isBorder(test));
    }

    @Test
    public void flip() throws Exception{
        PlayerPosition test = new PlayerPosition(0, 5, 6);
        PlayerPosition expected = new PlayerPosition(0, 4, 3);
        test = board.flip(test);
        assertTrue(expected.equals(test));
    }

    @Test (expected = IllegalArgumentException.class)
    public void flip1() throws Exception{
        PlayerPosition test = new PlayerPosition(0, 5, 1);
        board.flip(test);
    }

    @Test
    public void positionHasPlayer() {
        PlayerPosition test = new PlayerPosition(0, 5, 1);
        PlayerPosition test1 = new PlayerPosition(5, 2, 3);
        board.updatePlayerPosition(new SPlayer("green"), test);
        board.updatePlayerPosition(new SPlayer("red"), test1);

        PlayerPosition expect = new PlayerPosition(0, 5, 1);
        PlayerPosition expect1 = new PlayerPosition(5, 2, 3);

        assertTrue(board.positionHasPlayer(expect));
        assertTrue(board.positionHasPlayer(expect1));
        assertFalse(board.positionHasPlayer(new PlayerPosition(0,0,1)));

    }

    @Test
    public void test_legal_play_1() throws Exception{
        SPlayer player1 = new SPlayer("green");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(0,0, 7));
        Tile tile1 = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile2 = new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6});
        player1.receiveTile(tile1);
        player1.receiveTile(tile2);
        Administrator admin = new Administrator();
        assertFalse(board.tileLegal(player1, tile1));
        assertFalse(admin.legalPlay(player1, board, tile1));
    }


    @Test //if all tiles in hand kill the player
    public void test_legal_play_2() throws Exception{
        SPlayer player1 = new SPlayer("green");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(0,0, 7));
        Tile tile1 = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        player1.receiveTile(tile1);
        Administrator admin = new Administrator();
        assertTrue(board.tileLegal(player1, tile1));
        assertTrue(admin.legalPlay(player1, board, tile1));
    }


}