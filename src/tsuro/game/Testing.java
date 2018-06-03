package tsuro.game;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public  class Testing {


    public static void main(String[] args) throws Exception {
        System.out.println("ily<3");
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            // Drawpile
            Document doc = Decoder.getDocument(bf.readLine());
            Node tilesNode = doc.getElementsByTagName("list").item(0);
            List<Tile> tiles =  Decoder.decode_listofTiles(tilesNode);

            // winners
            doc = Decoder.getDocument(bf.readLine());
            Node activePlayersNode = doc.getElementsByTagName("list").item(0);
            List<SPlayer> activePlayers =  Decoder.decode_listofSPlayer(activePlayersNode);

            // losers
            doc = Decoder.getDocument(bf.readLine());
            Node outNode = doc.getElementsByTagName("list").item(0);
            List<SPlayer> outPlayers =  Decoder.decode_listofSPlayer(outNode);

            // board
            doc = Decoder.getDocument(bf.readLine());
            Node boardNode = doc.getElementsByTagName("board").item(0);
            Board board =  Decoder.decode_board(boardNode);

            //tile
            Tile tile =  Decoder.decodeTile(bf.readLine());

            Administrator admin = new Administrator();
            List<SPlayer> winners = admin.playATurn(tiles, activePlayers, outPlayers, board, tile);
            String tilesString = Encoder.encodeListofTile(tiles);
            System.out.println(tilesString);
            String winnerString = Encoder.encodeMaybeListofSPlayers(winners);
            System.out.println(winnerString);
            String outString = Encoder.encodeMaybeListofSPlayers(outPlayers);
            System.out.println(outString);
            String boardString = Encoder.encodeBoard(board);
            System.out.println(boardString);
            String tileString = Encoder.encodeTile(tile);
            System.out.println(tilesString);
        }
    }
}
