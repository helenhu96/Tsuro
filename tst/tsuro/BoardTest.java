package tsuro;

import org.junit.Test;

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
    public void flip() {
        PlayerPosition test = new PlayerPosition(0, 5, 6);
        PlayerPosition expected = new PlayerPosition(0, 4, 3);
        test = board.flip(test);
        assertTrue(expected.equals(test));
    }

    @Test
    public void flip1() {
        PlayerPosition test = new PlayerPosition(0, 5, 1);
        assertNull(board.flip(test));
    }

    @Test
    public void positionHasPlayer() {
        PlayerPosition test = new PlayerPosition(0, 5, 1);
        PlayerPosition test1 = new PlayerPosition(5, 2, 3);
        board.updatePlayerPosition(new SPlayer("Green"), test);
        board.updatePlayerPosition(new SPlayer("Red"), test1);

        PlayerPosition expect = new PlayerPosition(0, 5, 1);
        PlayerPosition expect1 = new PlayerPosition(5, 2, 3);

        assertTrue(board.positionHasPlayer(expect));
        assertTrue(board.positionHasPlayer(expect1));
        assertFalse(board.positionHasPlayer(new PlayerPosition(0,0,1)));



    }




}