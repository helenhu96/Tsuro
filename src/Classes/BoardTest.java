package Classes;

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
        assertEquals(expected, test);
    }

    @Test
    public void flip1() {
        PlayerPosition test = new PlayerPosition(0, 5, 1);
        assertNull(board.flip(test));
    }



}