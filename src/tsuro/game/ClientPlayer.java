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

    public ClientPlayer(IPlayer p) throws IOException {
        this.iplayer = p;
        this.PORT = 12345;
    }

    public void connect() throws Exception {
        socket = new Socket(host, PORT);
        this.toServer = new PrintWriter(socket.getOutputStream(), true);
        this.fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    public void sendXml(String docString) {
        System.out.println("send message to server: " + docString);
        toServer.println(docString);
    }

    public void processMessage() throws Exception {
        while (true) {
            String message = fromServer.readLine();
            System.out.println("get message from server: " + message);
            decode(message);
        }
    }


    //  get:
    //  <get-name> </get-name>  -> give back name
    //  <initialize> color list-of-color  </initialize> -> initialize then return void
    // then call the corresponding iplayer function and send back msg
    public void decode(String docString) throws Exception{
        Document doc = Decoder.getDocument(docString);
        String function = doc.getDocumentElement().getTagName();

        // handle different cases based on the different function calls
        if (function.equals("get-name")) {
            String str = doc.getFirstChild().getTextContent();
            sendXml(Encoder.encodePlayerName(this.iplayer.getName()));

        }

        else if (function.equals("initialize"))
        {
            Node color = doc.getElementsByTagName("color").item(0);
            Node list_of_color = doc.getElementsByTagName("list").item(0);
            String c = color.getTextContent();
            List<String> colors = Decoder.decodeListofColors(list_of_color);
            this.iplayer.initialize(c, colors);
            sendXml(Encoder.encodeVoid());

        }

        else if (function.equals("place-pawn"))
        {
            Node boardNode = doc.getElementsByTagName("board").item(0);
            Board board = Decoder.decodeBoard(boardNode);
            PlayerPosition playerPosition = this.iplayer.placePawn(board);
            sendXml(Encoder.encodePawnLoc(playerPosition));
        }

        else if (function.equals("play-turn")) {
            Node boardNode = doc.getElementsByTagName("board").item(0);
            Node tilesNode = doc.getElementsByTagName("set").item(0);
            int n = Integer.parseInt(doc.getElementsByTagName("play-turn").item(0).getLastChild().getTextContent());
            Board board = Decoder.decodeBoard(boardNode);
            Set<Tile> tiles = Decoder.decodeSetofTiles(tilesNode);
            Tile t = this.iplayer.playTurn(board, tiles, n);
            sendXml(Encoder.encodeTile(t));
        }

        else if (function.equals("end-game")) {
            Node boardNode = doc.getElementsByTagName("board").item(0);
            Node colorSet = doc.getElementsByTagName("set").item(0);
            Board board = Decoder.decodeBoard(boardNode);
            Set<String> colors = Decoder.decodeSetofColors(colorSet);
            this.iplayer.endGame(board, colors);
            sendXml(Encoder.encodeVoid());
        } else {
            throw new IllegalArgumentException("Not a legal xml file!");
        }


    }
}
