package Classes;

import org.junit.Test;

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
        PlayerPosition test = randP.placePawn(board);
        System.out.println(test.getY() + " " + test.getX()+" " + test.getSpot());
        assertTrue(board.isBorder(test));
        test = randP.placePawn(board);
        System.out.println(test.getY() + " " + test.getX()+" " + test.getSpot());
        assertTrue(board.isBorder(test));
        test = randP.placePawn(board);
        System.out.println(test.getY() + " " + test.getX()+" " + test.getSpot());
        assertTrue(board.isBorder(test));
        test = randP.placePawn(board);
        System.out.println(test.getY() + " " + test.getX()+" " + test.getSpot());
        assertTrue(board.isBorder(test));
        test = randP.placePawn(board);
        System.out.println(test.getY() + " " + test.getX()+" " + test.getSpot());
        assertTrue(board.isBorder(test));


    }
}