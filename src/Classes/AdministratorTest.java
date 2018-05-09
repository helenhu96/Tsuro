package Classes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.*;

public class AdministratorTest {



    @Test
    public void legalPlay_play_is_legal() {
        SPlayer player1 = new SPlayer("Green");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(3,1, 6));
        Tile tile = new Tile(new int[]{0,5,1,7,2,3,4,6});
        tile.rotateClockwise();
        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);
        player1.receiveTile(tile);

        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        drawPile.initialize();
        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers, null);
        assertTrue(admin.legalPlay(player1, board, tile));
    }

    @Test
    public void legalPlay_move_is_illegal() {
        SPlayer player1 = new SPlayer("Green");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(0, 0, 6));
        Tile tile = new Tile(new int[]{0,5,1,4,2,3,7,6});
        player1.receiveTile(tile);

        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);

        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        drawPile.initialize();
        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers, null);
        assertFalse(admin.legalPlay(player1, board, tile));


    }

    @Test
    public void legalPlay_all_moves_are_eliminating() {
        SPlayer player1 = new SPlayer("Green");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(5, 0, 0));
        board.placeTile(new Tile(new int[]{0,5,1,2,3,6,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,7,1,2,3,4,5,6}), 4, 1);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 1);



        Tile tile3 = new Tile(new int[]{0,5,1,4,2,6,3,7});
        Tile tile2 = new Tile(new int[]{0,3,1,7,2,6,4,5});
        Tile tile1 = new Tile(new int[]{0,2,1,3,4,6,5,7});
        tile3.rotateClockwise();
        tile2.rotateClockwise();
        tile2.rotateClockwise();
        tile2.rotateClockwise();
        player1.receiveTile(tile1);
        player1.receiveTile(tile2);
        player1.receiveTile(tile3);
        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);
        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        drawPile.initialize();
        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers, null);
        assertTrue(admin.legalPlay(player1, board, tile3));


    }




    @Test    //both players start in a corner, player Green/s first move immediately eliminates player Red
    public void playATurnImmediateElimination() {
        SPlayer player1 = new SPlayer("Green");
        SPlayer player2 = new SPlayer("Red");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(0,0,1));
        board.updatePlayerPosition(player2, new PlayerPosition(0,0,7));
        Tile tile1 = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile2 = new Tile(new int[]{4,1,2,3,5,7,0,6});
        Tile tile3 = new Tile(new int[]{1,4,2,3,5,7,0,6});
        Tile tile4 = new Tile(new int[]{1,4,3,2,5,7,0,6});
        Tile tile5 = new Tile(new int[]{1,4,2,3,5,7,6,0});
        Tile tile6 = new Tile(new int[]{1,4,2,5,3,7,6,0});
        player1.receiveTile(tile2);
        player1.receiveTile(tile3);
        player2.receiveTile(tile4);
        player2.receiveTile(tile5);
        player2.receiveTile(tile6);

        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);
        activePlayers.add(player2);
        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers, null);

        List<SPlayer> winners = admin.playATurn(tile1);
        assertEquals(1, winners.size());
        assertEquals(player1, winners.get(0));

        assertEquals(1, activePlayers.size());
        assertEquals(player1, activePlayers.get(0));
        assertEquals(3, player1.numHandTiles());
        assertEquals(1, deadPlayers.size());
        assertEquals(player2, deadPlayers.get(0));
        assertEquals(0, player2.numHandTiles());

        assertEquals(2, drawPile.size());

        assertEquals(null, admin.getDragon());

        Board expectedBoard = new Board();
        expectedBoard.updatePlayerPosition(player1, new PlayerPosition(0, 1, 7));
        expectedBoard.updatePlayerPosition(player2, new PlayerPosition(0, 0, 0));
        expectedBoard.placeTile(tile1, 0,0);
        assertEquals(expectedBoard, board);
    }

    @Test
    public void playATurnCrossMultipleTiles() {
        SPlayer player1 = new SPlayer("Green");
        SPlayer player2 = new SPlayer("Red");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(2,1,2));
        board.updatePlayerPosition(player2, new PlayerPosition(2,1,0));

        board.placeTile(new Tile(new int[]{0, 2, 1, 6, 3, 4, 5, 7}), 1,1);
        board.placeTile(new Tile(new int[]{0, 1, 2, 6, 3, 7, 4, 5}), 1,2);
        board.placeTile(new Tile(new int[]{0, 2, 1, 7, 3, 6, 4, 5}), 1,3);
        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);
        activePlayers.add(player2);
        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers, null);
        List<SPlayer> winners = admin.playATurn(new Tile(new int[]{0,3,1,2,4,7,5,6}));

        assertNull(winners);

        assertEquals(2, activePlayers.size());
        assertEquals(player2, activePlayers.get(0));
        assertEquals(player1, activePlayers.get(1));
        assertEquals(0, deadPlayers.size());

        assertEquals(0, drawPile.size());

        assertEquals(player1, admin.getDragon());

        Board expectedBoard = new Board();
        expectedBoard.updatePlayerPosition(player1, new PlayerPosition(0, 3, 4));
        expectedBoard.updatePlayerPosition(player2, new PlayerPosition(2, 2, 6));
        expectedBoard.placeTile(new Tile(new int[]{0, 2, 1, 6, 3, 4, 5, 7}), 1,1);
        expectedBoard.placeTile(new Tile(new int[]{0, 1, 2, 6, 3, 7, 4, 5}), 1,2);
        expectedBoard.placeTile(new Tile(new int[]{0, 2, 1, 7, 3, 6, 4, 5}), 1,3);
        expectedBoard.placeTile(new Tile(new int[]{0,3,1,2,4,7,5,6}), 2,1);
        assertEquals(expectedBoard, board);

    }

    @Test
    public void playATurnMultiplePlayers() {
        SPlayer player1 = new SPlayer("Green");
        SPlayer player2 = new SPlayer("Red");
        SPlayer player3 = new SPlayer("Blue");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(3,3, 3));
        board.updatePlayerPosition(player2, new PlayerPosition(3,3,5));
        board.updatePlayerPosition(player3, new PlayerPosition(3,3,7));

        board.placeTile(new Tile(new int[]{0, 4, 1, 3, 2, 6, 5, 7}), 3,4);
        board.placeTile(new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6}), 4,3);
        board.placeTile(new Tile(new int[]{0, 7, 1, 2, 3, 6, 4, 5}), 3,2);

        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);
        activePlayers.add(player2);
        activePlayers.add(player3);

        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        drawPile.initialize();

        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers, null);
        List<SPlayer> winners = admin.playATurn(new Tile(new int[]{0,3,1,6,2,7,4,5}));

        assertNull(winners);

        assertEquals(3, activePlayers.size());
        assertEquals(player2, activePlayers.get(0));
        assertEquals(player3, activePlayers.get(1));
        assertEquals(player1, activePlayers.get(2));
        assertEquals(0, deadPlayers.size());
        assertEquals(1, player1.numHandTiles());

        assertEquals(34, drawPile.size());

        assertEquals(null, admin.getDragon());

        Board expectedBoard = new Board();
        expectedBoard.updatePlayerPosition(player1, new PlayerPosition(2, 3, 5));
        expectedBoard.updatePlayerPosition(player2, new PlayerPosition(4, 4, 7));
        expectedBoard.updatePlayerPosition(player3, new PlayerPosition(4, 4, 0));
        expectedBoard.placeTile(new Tile(new int[]{0, 4, 1, 3, 2, 6, 5, 7}), 3,4);
        expectedBoard.placeTile(new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6}), 4,3);
        expectedBoard.placeTile(new Tile(new int[]{0, 7, 1, 2, 3, 6, 4, 5}), 3,2);
        expectedBoard.placeTile(new Tile(new int[]{0,3,1,6,2,7,4,5}), 3,3);
        assertEquals(expectedBoard, board);

    }

    @Test
    public void playATurnEliminateAll() {
        SPlayer player1 = new SPlayer("Green");
        SPlayer player2 = new SPlayer("Red");
        SPlayer player3 = new SPlayer("Blue");

        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(3,1, 7));
        board.updatePlayerPosition(player2, new PlayerPosition(3,1,0));
        board.updatePlayerPosition(player3, new PlayerPosition(3,1,3));

        board.placeTile(new Tile(new int[]{0,5,1,6,2,7,3,4}), 5,0);
        board.placeTile(new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6}), 5,1);
        board.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 6, 3, 7}), 4,1);
        board.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6}), 0,1);
        board.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6}), 1,1);
        board.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6}), 2,1);
        board.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6}), 3,2);
        board.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6}), 3,3);
        board.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6}), 3,4);
        board.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6}), 3,5);


        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);
        activePlayers.add(player2);
        activePlayers.add(player3);

        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        drawPile.initialize();

        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers, null);
        List<SPlayer> winners = admin.playATurn(new Tile(new int[]{0,3,1,6,2,4,5,7}));

        assertEquals(3, winners.size());
        assertEquals(player1, winners.get(0));
        assertEquals(player2, winners.get(1));
        assertEquals(player3, winners.get(2));

        assertEquals(0, activePlayers.size());
        assertEquals(3, deadPlayers.size());
        assertEquals(35, drawPile.size());

        assertEquals(null, admin.getDragon());



        Board expectedBoard = new Board();
        expectedBoard.updatePlayerPosition(player1, new PlayerPosition(5, 0, 7));
        expectedBoard.updatePlayerPosition(player2, new PlayerPosition(3, 5, 3));
        expectedBoard.updatePlayerPosition(player3, new PlayerPosition(0, 1, 0));
        expectedBoard.placeTile(new Tile(new int[]{0,5,1,6,2,7,3,4}), 5,0);
        expectedBoard.placeTile(new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6}), 5,1);
        expectedBoard.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 6, 3, 7}), 4,1);
        expectedBoard.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6}), 0,1);
        expectedBoard.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6}), 1,1);
        expectedBoard.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6}), 2,1);
        expectedBoard.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6}), 3,2);
        expectedBoard.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6}), 3,3);
        expectedBoard.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6}), 3,4);
        expectedBoard.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6}), 3,5);
        expectedBoard.placeTile(new Tile(new int[]{0,3,1,6,2,4,5,7}), 3,1);

        assertEquals(expectedBoard, board);

    }

    @Test
    public void playATurnRotated() {
        SPlayer player1 = new SPlayer("Green");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(3,1, 6));
        Tile tile = new Tile(new int[]{0,5,1,7,2,3,4,6});
        tile.rotateClockwise();
        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);

        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        drawPile.initialize();

        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers, null);
        List<SPlayer> winners = admin.playATurn(tile);

        assertEquals(1, winners.size());
        assertEquals(player1, winners.get(0));
        assertEquals(1, player1.numHandTiles());

        assertEquals(1, activePlayers.size());
        assertEquals(0, deadPlayers.size());
        assertEquals(34, drawPile.size());
        assertNull(admin.getDragon());

        Board expectedBoard = new Board();
        expectedBoard.updatePlayerPosition(player1, new PlayerPosition(2, 1, 5));
        expectedBoard.placeTile(tile, 3, 1);
        assertEquals(expectedBoard, board);

    }

    @Test  //player who has the dragon tile dies, dragon tile gets passed on to the next rightful successor
    public void playATurnDragonDies() {
        SPlayer player1 = new SPlayer("Green");
        SPlayer player2 = new SPlayer("Red");
        SPlayer player3 = new SPlayer("Blue");

        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(5,0,0));
        board.updatePlayerPosition(player2, new PlayerPosition(3,1,0));
        board.updatePlayerPosition(player3, new PlayerPosition(3,1,3));

        board.updatePlayerPosition(player1, new PlayerPosition(5, 0, 0));
        board.placeTile(new Tile(new int[]{0,5,1,2,3,6,4,7}), 4, 0);
        board.placeTile(new Tile(new int[]{0,7,1,2,3,4,5,6}), 4, 1);
        board.placeTile(new Tile(new int[]{0,6,1,2,3,4,5,7}), 5, 1);

        Tile tile3 = new Tile(new int[]{0,5,1,4,2,6,3,7});
        Tile tile2 = new Tile(new int[]{0,3,1,7,2,6,4,5});
        Tile tile1 = new Tile(new int[]{0,2,1,3,4,6,5,7});
        Tile tile4 = new Tile(new int[]{0,5,2,3,4,6,1,7});
        Tile tile5 = new Tile(new int[]{0,1,2,3,4,5,6,7});
        Tile tile6 = new Tile(new int[]{0,1,2,3,4,6,5,7});
        player1.receiveTile(tile3);
        player1.receiveTile(tile2);
        player2.receiveTile(tile1);
        player2.receiveTile(tile4);
        player3.receiveTile(tile5);
        player3.receiveTile(tile6);


        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);
        activePlayers.add(player2);
        activePlayers.add(player3);

        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();

        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers, player1);
        List<SPlayer> winners = admin.playATurn(tile3);

        assertNull(winners);
        assertEquals(2, activePlayers.size());
        assertEquals(player2, activePlayers.get(0));
        assertEquals(player3, activePlayers.get(1));
        assertEquals(1, deadPlayers.size());
        assertEquals(player1, deadPlayers.get(0));

        assertEquals(0, player1.numHandTiles());
        assertEquals(3, player2.numHandTiles());
        assertEquals(3, player3.numHandTiles());

        assertEquals(0, drawPile.size());

        assertNull(admin.getDragon());
    }

    @Test  //current player eliminates player with dragon tile
    public void playATurn_Player_Eliminate_Dragon() {
        SPlayer player1 = new SPlayer("Green");
        SPlayer player2 = new SPlayer("Red");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(0,0,1));
        board.updatePlayerPosition(player2, new PlayerPosition(0,0,7));
        Tile tile1 = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile2 = new Tile(new int[]{4,1,2,3,5,7,0,6});
        Tile tile3 = new Tile(new int[]{1,4,2,3,5,7,0,6});
        Tile tile4 = new Tile(new int[]{1,4,3,2,5,7,0,6});
        Tile tile5 = new Tile(new int[]{1,4,2,3,5,7,6,0});
        Tile tile6 = new Tile(new int[]{1,4,2,5,3,7,6,0});
        player1.receiveTile(tile2);
        player1.receiveTile(tile3);
        player2.receiveTile(tile5);
        player2.receiveTile(tile6);

        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);
        activePlayers.add(player2);
        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers, player2);

        List<SPlayer> winners = admin.playATurn(tile1);
        assertEquals(1, winners.size());
        assertEquals(player1, winners.get(0));

        assertEquals(1, activePlayers.size());
        assertEquals(player1, activePlayers.get(0));
        assertEquals(3, player1.numHandTiles());
        assertEquals(1, deadPlayers.size());
        assertEquals(player2, deadPlayers.get(0));
        assertEquals(0, player2.numHandTiles());

        assertEquals(1, drawPile.size());

        assertEquals(null, admin.getDragon());

        Board expectedBoard = new Board();
        expectedBoard.updatePlayerPosition(player1, new PlayerPosition(0, 1, 7));
        expectedBoard.updatePlayerPosition(player2, new PlayerPosition(0, 0, 0));
        expectedBoard.placeTile(tile1, 0,0);
        assertEquals(expectedBoard, board);
    }

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }

    @Test
    public void playCheat() {
        Administrator admin = new Administrator();
        MPlayer p1 = new CrappyPlayer("B");
        MPlayer p2 = new MostSymmetricPlayer("R");
        MPlayer p3 = new LeastSymmetricPlayer("G");

        admin.registerPlayer(p1);
        admin.registerPlayer(p2);
        admin.registerPlayer(p3);

        admin.play();
        assertEquals("Blue cheated.\n", outContent.toString());
    }

    @Test
    public void playersAllCheat() {
        Administrator admin = new Administrator();
        MPlayer p1 = new CrappyPlayer("B");
        MPlayer p2 = new CrappyPlayer("R");
        MPlayer p3 = new CrappyPlayer("G");

        admin.registerPlayer(p1);
        admin.registerPlayer(p2);
        admin.registerPlayer(p3);

        admin.play();
        assertEquals("Blue cheated.\nRed cheated.\nGreen cheated.\n", outContent.toString());
    }


}
