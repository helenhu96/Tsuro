package tsuro.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.*;

public class AdministratorTest {

    @Test
    public void setup_game() throws Exception{
        MPlayer player1 = new RandPlayer("green");
        Administrator admin = new Administrator();
        admin.registerPlayer(player1);
        admin.initPlayers();
        SPlayer splayer1 = admin.getSPlayer(0);
        Board board = new Board();
        board.updatePlayerPosition(splayer1, new PlayerPosition(3,1, 6));
        admin.setupGame();
        assertEquals(32, admin.getDrawPile().size());
        assertEquals(3, splayer1.numHandTiles());

    }


    @Test
    public void legalPlay_play_is_legal() throws Exception{
        SPlayer player1 = new SPlayer("green");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(3,1, 6));
        Tile tile = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        tile.rotateClockwise();
        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);
        player1.receiveTile(tile);
        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();

        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers);
        assertTrue(tile.isLegalTile());
        assertTrue(admin.legalPlay(player1, board, tile));
    }

    @Test
    public void legalPlay_move_is_illegal() throws Exception{
        SPlayer player1 = new SPlayer("green");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(0, 0, 0));
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile1 = new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6});
        player1.receiveTile(tile);
        player1.receiveTile(tile1);
        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);

        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        //drawPile.initialize();
        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers);
        assertFalse(admin.legalPlay(player1, board, tile));


    }

    @Test
    public void legalPlay_all_moves_are_eliminating() throws Exception{
        SPlayer player1 = new SPlayer("green");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(0, 0, 5));
        Tile tile = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile1 = new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6});
        player1.receiveTile(tile);
        player1.receiveTile(tile1);
        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);

        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        //drawPile.initialize();
        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers);
        assertTrue(admin.legalPlay(player1, board, tile));
        assertTrue(admin.legalPlay(player1, board, tile1));

    }




    @Test    //both players start in a corner, player green/s first move immediately eliminates player red
    public void playATurnImmediateElimination() throws Exception{
        SPlayer player1 = new SPlayer("green");
        SPlayer player2 = new SPlayer("red");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(0,0,1));
        board.updatePlayerPosition(player2, new PlayerPosition(0,0,7));
        Tile tile1 = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile2 = new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6});
        Tile tile3 = new Tile(new int[]{0, 4, 1, 5, 2, 6, 3, 7});
        List<SPlayer> activePlayers = new ArrayList<>();
        player2.receiveTile(tile2);
        player1.receiveTile(tile3);
        activePlayers.add(player1);
        activePlayers.add(player2);
        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        System.out.println(drawPile.size());
        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers);

        List<SPlayer> winners = admin.playATurnHelp(drawPile, activePlayers, deadPlayers, board, tile1);

        assertTrue(board.getPlayerPosition(player2).equals(new PlayerPosition(0,0,0)));
        assertEquals(1, winners.size());
        assertEquals(player1, winners.get(0));
        assertEquals(1, activePlayers.size());
        assertEquals(player1, activePlayers.get(0));
        assertEquals(1, player1.numHandTiles());
        assertEquals(1, deadPlayers.size());
        assertFalse(player2.isAlive());
        assertEquals(0, player2.numHandTiles());
        assertEquals(1, drawPile.size());
        assertEquals(null, admin.getPlayerWithDragonTile());
    }

    @Test
    public void playATurnCrossMultipleTiles() throws Exception{
        SPlayer player1 = new SPlayer("green");
        SPlayer player2 = new SPlayer("red");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(1,1,6));
        board.updatePlayerPosition(player2, new PlayerPosition(2,1,5));

        Tile tile1 = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile2 = new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6});
        Tile tile3 = new Tile(new int[]{0, 4, 1, 5, 2, 6, 3, 7});

        board.placeTile(tile2, 1,0);
        board.placeTile(tile3, 2,1);
        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);
        activePlayers.add(player2);
        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers);
        List<SPlayer> winners = admin.playATurnHelp(drawPile, activePlayers, deadPlayers, board, tile1);

        assertTrue(winners.isEmpty());
        assertEquals(2, activePlayers.size());
        assertEquals(player2, activePlayers.get(0));
        assertEquals(player1, activePlayers.get(1));
        assertEquals(0, deadPlayers.size());

        assertEquals(0, drawPile.size());
        assertEquals(board.getPlayerByPosition(new PlayerPosition(3, 1, 1)), "green");
        assertEquals(board.getPlayerByPosition(new PlayerPosition(1, 2, 6)), "red");
        assertEquals(board.getTile(1,1), tile1);
        assertEquals(board.getNumTiles(), 3);
        assertEquals(drawPile.size(), 0);

    }




    @Test
    public void playATurnEliminateAll() throws Exception{
        SPlayer player1 = new SPlayer("green");
        SPlayer player2 = new SPlayer("red");
        SPlayer player3 = new SPlayer("blue");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(0,0,2));
        board.updatePlayerPosition(player2, new PlayerPosition(1,1,4));
        board.updatePlayerPosition(player3, new PlayerPosition(0,0,4));

        Tile tile1 = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});
        Tile tile2 = new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6});
        Tile tile3 = new Tile(new int[]{0, 4, 1, 5, 2, 6, 3, 7});

        board.placeTile(tile1, 0,1);
        board.placeTile(tile3, 1,1);
        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);
        activePlayers.add(player2);
        activePlayers.add(player3);
        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers);
        List<SPlayer> winners = admin.playATurnHelp(drawPile, activePlayers, deadPlayers, board, tile2);

        assertEquals(winners.size(), 3);
        assertEquals(0, activePlayers.size());
        assertEquals(3, deadPlayers.size());

        assertEquals(0, drawPile.size());
        assertEquals(board.getPlayerByPosition(new PlayerPosition(0, 0, 7)), "green");
        assertEquals(board.getPlayerByPosition(new PlayerPosition(0, 0, 6)), "red");
        assertEquals(board.getPlayerByPosition(new PlayerPosition(0, 0, 1)), "blue");

    }
//
    @Test
    public void playATurnRotated() throws Exception{
        SPlayer player1 = new SPlayer("green");
        SPlayer player2 = new SPlayer("red");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(3,1, 6));
        board.updatePlayerPosition(player2, new PlayerPosition(5,1, 6));
        Tile tile = new Tile(new int[]{0,5,1,7,2,3,4,6});
        tile.rotateClockwise();
        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);
        activePlayers.add(player2);
        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        drawPile.initialize();

        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers);
        List<SPlayer> winners = admin.playATurnHelp(drawPile, activePlayers, deadPlayers, board, tile);
        assertEquals(29, drawPile.size());
        assertEquals(0, winners.size());


        assertEquals(3, player1.numHandTiles());
        assertEquals(2, activePlayers.size());
        assertEquals(0, deadPlayers.size());
        assertNull(admin.getPlayerWithDragonTile());

    }

    @Test  //player who has the dragon tile dies, dragon tile gets passed on to the next rightful successor
    public void playATurnDragonDies() throws Exception {
        SPlayer player1 = new SPlayer("green");
        SPlayer player2 = new SPlayer("red");
        SPlayer player3 = new SPlayer("blue");

        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(5, 0, 0));
        board.updatePlayerPosition(player2, new PlayerPosition(3, 1, 0));
        board.updatePlayerPosition(player3, new PlayerPosition(3, 1, 3));

        board.updatePlayerPosition(player1, new PlayerPosition(5, 0, 0));
        board.placeTile(new Tile(new int[]{0, 5, 1, 2, 3, 6, 4, 7}), 4, 0);
        board.placeTile(new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6}), 4, 1);
        board.placeTile(new Tile(new int[]{0, 6, 1, 2, 3, 4, 5, 7}), 5, 1);

        Tile tile3 = new Tile(new int[]{0, 5, 1, 4, 2, 6, 3, 7});
        Tile tile2 = new Tile(new int[]{0, 3, 1, 7, 2, 6, 4, 5});
        Tile tile1 = new Tile(new int[]{0, 2, 1, 3, 4, 6, 5, 7});
        Tile tile4 = new Tile(new int[]{0, 5, 2, 3, 4, 6, 1, 7});
        Tile tile5 = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        Tile tile6 = new Tile(new int[]{0, 1, 2, 3, 4, 6, 5, 7});
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

        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers);
        drawPile.giveDragon();
        assertFalse(drawPile.hasDrogon());
        player1.getDragon();
        assertEquals(admin.getPlayerWithDragonTile(), player1);
        List<SPlayer> winners = admin.playATurn(drawPile.tiles, activePlayers, deadPlayers, board, tile3);

        assertEquals(0, drawPile.size());
        assertEquals(winners.size(), 0);
        assertEquals(2, activePlayers.size());
        assertEquals(player2, activePlayers.get(0));
        assertEquals(player3, activePlayers.get(1));
        assertEquals(1, deadPlayers.size());
        assertEquals(player1, deadPlayers.get(0));
        assertEquals(0, player1.numHandTiles());
        assertEquals(3, player2.numHandTiles());
        assertEquals(2, player3.numHandTiles());
        assertEquals(admin.getPlayerWithDragonTile(), player3);
    }



    @Test  //current player eliminates player with dragon tile
    public void playATurn_Player_Eliminate_Dragon() throws Exception {
        SPlayer player1 = new SPlayer("green");
        SPlayer player2 = new SPlayer("red");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(0,0,1));
        board.updatePlayerPosition(player2, new PlayerPosition(0,0,7));
        Tile tile1 = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});

        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);
        activePlayers.add(player2);
        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        drawPile.initialize();
        player1.receiveTile(drawPile.drawATile());
        player1.receiveTile(drawPile.drawATile());
        player2.receiveTile(drawPile.drawATile());
        player2.receiveTile(drawPile.drawATile());
        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers);
//        drawPile.giveDragon();
//        assertEquals(admin.getPlayerWithDragonTile(), player2);
//        assertFalse(drawPile.hasDrogon());

        List<SPlayer> winners = admin.playATurnHelp(drawPile, activePlayers, deadPlayers, board, tile1);
        assertEquals(1, winners.size());
        assertEquals(player1, winners.get(0));

//        assertEquals(admin.getPlayerWithDragonTile(), player1);
        assertEquals(1, activePlayers.size());
        assertEquals(player1, activePlayers.get(0));
        assertEquals(1, deadPlayers.size());
        assertEquals(player2, deadPlayers.get(0));
        assertEquals(0, player2.numHandTiles());
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
    public void testRotatedLegalTile() throws Exception{
        Tile tile = new Tile(new int[]{0,7,1,2,5,3,4,6});
        assertTrue(tile.isLegalTile());
    }

    @Test
    public void testLegalTile() throws Exception{
        Tile tile = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        tile.rotateClockwise();
        assertTrue(tile.isLegalTile());
    }



}
