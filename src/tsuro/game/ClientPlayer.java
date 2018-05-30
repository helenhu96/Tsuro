package tsuro.game;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Set;

public class ClientPlayer {
    IPlayer iplayer;
    Socket socket;
    private int PORT;
    private String host = "localhost";
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private PlayerDecoder decoder;
    public ClientPlayer(IPlayer p) throws IOException {
        this.iplayer = p;
        this.PORT = 3000;
        socket = new Socket(host, PORT);
        this.toServer = new PrintWriter(socket.getOutputStream(), true);
        this.fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendXml(String docString) {
        toServer.println(docString);
    }


    //  get:
    //  <get-name> </get-name>  -> give back name
    //  <initialize> color list-of-color  </initialize> -> initialize then return void
    // then call the corresponding iplayer function and send back msg
    public void decode(String docString) throws Exception{
        try {
            Document doc = decoder.getDocument(docString);
            String function = doc.getDocumentElement().getTagName();

            // handle different cases based on the different function calls
            if (function.equals("get-name")) {
                String str = doc.getFirstChild().getTextContent();
                sendXml(Encoder.encodePlayerName(this.iplayer.getName()));

            } else if (function.equals("initialize")) {
                Node color = doc.getFirstChild();
                Node list_of_color = color.getNextSibling();
                String c = color.getTextContent();
                List<String> colors = PlayerDecoder.decode_listofColors(list_of_color);
                this.iplayer.initialize(c, colors);
                sendXml(Encoder.encodeVoid());

            } else if (function.equals("place-pawn")) {
                String boardString = doc.getFirstChild().getTextContent();
                Board board = PlayerDecoder.decodeBoard(boardString);

                PlayerPosition playerPosition = this.iplayer.placePawn(board);
                sendXml(Encoder.encodePawnLoc(playerPosition));
            }  else if (function.equals("play-turn")) {
                String boardString = doc.getFirstChild().getTextContent();
                Board board = PlayerDecoder.decodeBoard(boardString);
                Node tilesNode = doc.getFirstChild().getNextSibling();
                Set<Tile> tiles = PlayerDecoder.decode_setofTiles(tilesNode);
                int n = Integer.parseInt(doc.getLastChild().getTextContent());
                Tile t = this.iplayer.playTurn(board, tiles, n);
                sendXml(Encoder.encodeTile(t));
            }  else if (function.equals("end-game")) {
                String boardString = doc.getFirstChild().getTextContent();
                Board board = PlayerDecoder.decodeBoard(boardString);
                Node colorSet = doc.getChildNodes().item(1);
                Set<String> colors = PlayerDecoder.decode_setofColors(colorSet);
                this.iplayer.endGame(board, colors);
                sendXml(Encoder.encodeVoid());
            } else {
                throw new IllegalArgumentException("Not a legal xml file!");
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }
}
