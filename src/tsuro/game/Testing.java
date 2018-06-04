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
//        String a = "<list><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>5</n></connect><connect><n>3</n><n>6</n></connect><connect><n>4</n><n>7</n></connect></tile><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>5</n></connect><connect><n>4</n><n>6</n></connect></tile><tile><connect><n>0</n><n>3</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>7</n></connect><connect><n>4</n><n>6</n></connect></tile><tile><connect><n>0</n><n>6</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>4</n></connect><connect><n>3</n><n>7</n></connect></tile><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>6</n></connect><connect><n>4</n><n>5</n></connect></tile><tile><connect><n>0</n><n>3</n></connect><connect><n>1</n><n>7</n></connect><connect><n>2</n><n>6</n></connect><connect><n>4</n><n>5</n></connect></tile><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>6</n></connect><connect><n>2</n><n>5</n></connect><connect><n>3</n><n>4</n></connect></tile><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>4</n></connect><connect><n>3</n><n>5</n></connect><connect><n>6</n><n>7</n></connect></tile><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>4</n></connect><connect><n>3</n><n>6</n></connect><connect><n>5</n><n>7</n></connect></tile><tile><connect><n>0</n><n>6</n></connect><connect><n>1</n><n>3</n></connect><connect><n>2</n><n>5</n></connect><connect><n>4</n><n>7</n></connect></tile><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>6</n></connect><connect><n>3</n><n>7</n></connect><connect><n>4</n><n>5</n></connect></tile><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>7</n></connect><connect><n>3</n><n>5</n></connect><connect><n>4</n><n>6</n></connect></tile><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>4</n></connect><connect><n>3</n><n>6</n></connect><connect><n>5</n><n>7</n></connect></tile><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>7</n></connect><connect><n>3</n><n>4</n></connect><connect><n>5</n><n>6</n></connect></tile><tile><connect><n>0</n><n>5</n></connect><connect><n>1</n><n>6</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>4</n></connect></tile><tile><connect><n>0</n><n>3</n></connect><connect><n>1</n><n>6</n></connect><connect><n>2</n><n>5</n></connect><connect><n>4</n><n>7</n></connect></tile><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>3</n></connect><connect><n>4</n><n>5</n></connect><connect><n>6</n><n>7</n></connect></tile><tile><connect><n>0</n><n>4</n></connect><connect><n>1</n><n>3</n></connect><connect><n>2</n><n>7</n></connect><connect><n>5</n><n>6</n></connect></tile><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>4</n></connect><connect><n>5</n><n>6</n></connect></tile><tile><connect><n>0</n><n>3</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>6</n></connect><connect><n>4</n><n>7</n></connect></tile><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>6</n></connect><connect><n>3</n><n>7</n></connect><connect><n>4</n><n>5</n></connect></tile><tile><connect><n>0</n><n>5</n></connect><connect><n>1</n><n>3</n></connect><connect><n>2</n><n>6</n></connect><connect><n>4</n><n>7</n></connect></tile><tile><connect><n>0</n><n>3</n></connect><connect><n>1</n><n>2</n></connect><connect><n>4</n><n>6</n></connect><connect><n>5</n><n>7</n></connect></tile><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>4</n></connect><connect><n>3</n><n>7</n></connect><connect><n>5</n><n>6</n></connect></tile><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>6</n></connect><connect><n>2</n><n>3</n></connect><connect><n>4</n><n>5</n></connect></tile><tile><connect><n>0</n><n>4</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>6</n></connect><connect><n>3</n><n>7</n></connect></tile></list>";
//        String b = "<list><splayer-nodragon><color>blue</color><set><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>2</n></connect><connect><n>3</n><n>4</n></connect><connect><n>5</n><n>6</n></connect></tile><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>6</n></connect><connect><n>3</n><n>4</n></connect></tile></set></splayer-nodragon><splayer-nodragon><color>red</color><set><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>3</n></connect><connect><n>4</n><n>6</n></connect><connect><n>5</n><n>7</n></connect></tile><tile><connect><n>0</n><n>5</n></connect><connect><n>1</n><n>4</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>6</n></connect></tile><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>3</n></connect><connect><n>4</n><n>6</n></connect></tile></set></splayer-nodragon><splayer-nodragon><color>green</color><set><tile><connect><n>0</n><n>4</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>6</n></connect></tile><tile><connect><n>0</n><n>4</n></connect><connect><n>1</n><n>7</n></connect><connect><n>2</n><n>3</n></connect><connect><n>5</n><n>6</n></connect></tile><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>3</n></connect><connect><n>2</n><n>5</n></connect><connect><n>4</n><n>6</n></connect></tile></set></splayer-nodragon></list>";
//        String c = "<list></list>";
//        String d = "<board><map></map><map><ent><color>red</color><pawn-loc><h></h><n>6</n><n>11</n></pawn-loc></ent><ent><color>blue</color><pawn-loc><h></h><n>0</n><n>0</n></pawn-loc></ent><ent><color>green</color><pawn-loc><v></v><n>0</n><n>0</n></pawn-loc></ent></map></board>";
//        String e = "<tile><connect><n>0</n><n>4</n></connect><connect><n>1</n><n>7</n></connect><connect><n>2</n><n>6</n></connect><connect><n>3</n><n>5</n></connect></tile>";
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            // Drawpile
            String s = bf.readLine();
            if (s == null) {
                return;
            }
            Document doc = Decoder.getDocument(s);
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
