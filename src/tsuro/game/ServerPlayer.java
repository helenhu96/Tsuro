package tsuro.game;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerPlayer implements IPlayer {

    Socket socket;
    PrintWriter toClient;
    BufferedReader fromClient;
    PlayerDecoder playerDecoder = new PlayerDecoder();
    PlayerState state;
    int PORT;
    public ServerPlayer() throws IOException{
        this.PORT = 3000;
        ServerSocket serverSocket = new ServerSocket(PORT);
        this.socket = serverSocket.accept();
        this.toClient = new PrintWriter(socket.getOutputStream(), true);
        this.fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.state = PlayerState.UNINITIALIZED;
    }
    /**
     * return player's name
     */
    public String getName(){
        String answer = null;
        try {
            String outDocString = Encoder.encodeGetName();
            String result = sendXml(outDocString);
            answer = playerDecoder.decodeGetName(result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return answer;
    }

    public String sendXml(String s) throws IOException{
        toClient.println(s);
        String msg = fromClient.readLine();
        return msg;
    }

    public void initialize(String color, List<String> colors) {
        checkState(PlayerState.UNINITIALIZED);
        try {
            String outDocString = Encoder.encodeInitialize(color, colors);
            String in = sendXml(outDocString);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        this.state = PlayerState.INITIALIZED;
    }
//    // Called to indicate a game is starting.
//    // The first argument is the player's color
//    // and the second is all of the players'
//    // colors, in the order that the game will be played.
//
//    /**
//     * Called at the first step in a game; indicates where
//     * the player wishes to place their pawn. The pawn must
//     * be placed along the edge in an unoccupied space.
//     */
    public PlayerPosition placePawn(Board board) {
        checkState(PlayerState.INITIALIZED);
        PlayerPosition pp = null;
        try {
            String outDocString = Encoder.encodePlacePawn(board);
            String in = sendXml(outDocString);
            pp = playerDecoder.decodePawnLoc(Decoder.getDocument(in), board);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.state = PlayerState.PLAYING;
        return pp;
    }
//
//    /**
//     *
//     * @param board
//     * @param tiles
//     * @param numTiles
//     * @return the tile the player should place, suitably rotated
//     * Called to ask the player to make a move. The tiles
//     * are the ones the player has, the number is the
//     * count of tiles that are not yet handed out to players.
//     */
    public Tile playTurn(Board board, Set<Tile> tiles, int numTiles) {
        checkState(PlayerState.PLAYING);
        Tile tile = new Tile();
        try {
            String outDocString = Encoder.encodePlayTurn(board, tiles, numTiles);
            String in = sendXml(outDocString);
            tile = playerDecoder.decodeTile(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tile;
    }
//
//    /**
//     *
//     * @param board
//     * @param winnerColors
//     * Called to inform the player of the final board state and which players won the game
//     */
    public void endGame(Board board, Set<String> winnerColors) {
        try {
            String outDocString = Encoder.encodeEndGame(board, winnerColors);
            String in = sendXml(outDocString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void checkState(PlayerState desiredState) throws IllegalStateException {
        if (this.state != desiredState) {
            throw new IllegalStateException("Expect State " + desiredState + " actual state " + this.state);
        }
    }

}
