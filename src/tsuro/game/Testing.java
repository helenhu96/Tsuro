package tsuro.game;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.*;
import java.util.List;

public  class Testing {

    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            // Drawpile
            String s = bf.readLine();
            if (s == null) {
                return;
            }

            Document doc = Decoder.getDocument(s);
            Node tilesNode = doc.getElementsByTagName("list").item(0);
            List<Tile> tiles =  Decoder.decodeListofTiles(tilesNode);

            // winners
            doc = Decoder.getDocument(bf.readLine());
            Node activePlayersNode = doc.getElementsByTagName("list").item(0);
            List<SPlayer> activePlayers =  Decoder.decodeListofsplayer(activePlayersNode);

            // losers
            doc = Decoder.getDocument(bf.readLine());
            Node outNode = doc.getElementsByTagName("list").item(0);
            List<SPlayer> outPlayers =  Decoder.decodeListofsplayer(outNode);

            // board
            doc = Decoder.getDocument(bf.readLine());
            Node boardNode = doc.getElementsByTagName("board").item(0);
            Board board =  Decoder.decode_board(boardNode);


            //tile
            doc = Decoder.getDocument(bf.readLine());
            Node tileNode = doc.getElementsByTagName("tile").item(0);
            Tile tile =  Decoder.decode_tile(tileNode);


            Administrator admin = new Administrator(activePlayers, board, new DrawPile(tiles, true), outPlayers);
            List<SPlayer> winners = admin.playATurn(tiles, activePlayers, outPlayers, board, tile);
            String tilesString = Encoder.encodeListofTile(tiles);
            System.out.println(tilesString);
            String activeString = Encoder.encodeSPlayers(activePlayers);
            System.out.println(activeString);
            String outString = Encoder.encodeSPlayers(outPlayers);
            System.out.println(outString);
            String boardString = Encoder.encodeBoard(board);
            System.out.println(boardString);
            String maybeString = Encoder.encodeMaybeListofSPlayers(winners);
            System.out.println(maybeString);
        }
    }
}
