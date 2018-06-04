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
        String a = "<list><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>5</n></connect><connect><n>3</n><n>6</n></connect><connect><n>4</n><n>7</n></connect></tile><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>5</n></connect><connect><n>4</n><n>6</n></connect></tile><tile><connect><n>0</n><n>3</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>7</n></connect><connect><n>4</n><n>6</n></connect></tile><tile><connect><n>0</n><n>6</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>4</n></connect><connect><n>3</n><n>7</n></connect></tile><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>6</n></connect><connect><n>4</n><n>5</n></connect></tile><tile><connect><n>0</n><n>3</n></connect><connect><n>1</n><n>7</n></connect><connect><n>2</n><n>6</n></connect><connect><n>4</n><n>5</n></connect></tile><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>6</n></connect><connect><n>2</n><n>5</n></connect><connect><n>3</n><n>4</n></connect></tile><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>4</n></connect><connect><n>3</n><n>5</n></connect><connect><n>6</n><n>7</n></connect></tile><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>4</n></connect><connect><n>3</n><n>6</n></connect><connect><n>5</n><n>7</n></connect></tile><tile><connect><n>0</n><n>6</n></connect><connect><n>1</n><n>3</n></connect><connect><n>2</n><n>5</n></connect><connect><n>4</n><n>7</n></connect></tile><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>6</n></connect><connect><n>3</n><n>7</n></connect><connect><n>4</n><n>5</n></connect></tile><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>7</n></connect><connect><n>3</n><n>5</n></connect><connect><n>4</n><n>6</n></connect></tile><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>4</n></connect><connect><n>3</n><n>6</n></connect><connect><n>5</n><n>7</n></connect></tile><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>7</n></connect><connect><n>3</n><n>4</n></connect><connect><n>5</n><n>6</n></connect></tile><tile><connect><n>0</n><n>5</n></connect><connect><n>1</n><n>6</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>4</n></connect></tile><tile><connect><n>0</n><n>3</n></connect><connect><n>1</n><n>6</n></connect><connect><n>2</n><n>5</n></connect><connect><n>4</n><n>7</n></connect></tile><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>3</n></connect><connect><n>4</n><n>5</n></connect><connect><n>6</n><n>7</n></connect></tile><tile><connect><n>0</n><n>4</n></connect><connect><n>1</n><n>3</n></connect><connect><n>2</n><n>7</n></connect><connect><n>5</n><n>6</n></connect></tile><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>4</n></connect><connect><n>5</n><n>6</n></connect></tile><tile><connect><n>0</n><n>3</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>6</n></connect><connect><n>4</n><n>7</n></connect></tile><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>6</n></connect><connect><n>3</n><n>7</n></connect><connect><n>4</n><n>5</n></connect></tile><tile><connect><n>0</n><n>5</n></connect><connect><n>1</n><n>3</n></connect><connect><n>2</n><n>6</n></connect><connect><n>4</n><n>7</n></connect></tile><tile><connect><n>0</n><n>3</n></connect><connect><n>1</n><n>2</n></connect><connect><n>4</n><n>6</n></connect><connect><n>5</n><n>7</n></connect></tile><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>4</n></connect><connect><n>3</n><n>7</n></connect><connect><n>5</n><n>6</n></connect></tile><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>6</n></connect><connect><n>2</n><n>3</n></connect><connect><n>4</n><n>5</n></connect></tile><tile><connect><n>0</n><n>4</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>6</n></connect><connect><n>3</n><n>7</n></connect></tile></list>";        String b = "<list><splayer-nodragon><color>blue</color><set><tile><connect><n>0</n><n>3</n></connect><connect><n>1</n><n>6</n></connect><connect><n>2</n><n>5</n></connect><connect><n>4</n><n>7</n></connect></tile><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>6</n></connect><connect><n>3</n><n>4</n></connect></tile></set></splayer-nodragon><splayer-nodragon><color>red</color><set><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>5</n></connect><connect><n>3</n><n>6</n></connect><connect><n>4</n><n>7</n></connect></tile><tile><connect><n>0</n><n>3</n></connect><connect><n>1</n><n>2</n></connect><connect><n>4</n><n>6</n></connect><connect><n>5</n><n>7</n></connect></tile><tile><connect><n>0</n><n>4</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>6</n></connect><connect><n>3</n><n>7</n></connect></tile></set></splayer-nodragon><splayer-nodragon><color>green</color><set><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>3</n></connect><connect><n>4</n><n>6</n></connect><connect><n>5</n><n>7</n></connect></tile><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>7</n></connect><connect><n>3</n><n>5</n></connect><connect><n>4</n><n>6</n></connect></tile><tile><connect><n>0</n><n>4</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>6</n></connect></tile></set></splayer-nodragon><splayer-nodragon><color>orange</color><set><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>5</n></connect><connect><n>4</n><n>6</n></connect></tile><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>4</n></connect><connect><n>3</n><n>6</n></connect><connect><n>5</n><n>7</n></connect></tile><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>6</n></connect><connect><n>2</n><n>3</n></connect><connect><n>4</n><n>5</n></connect></tile></set></splayer-nodragon><splayer-nodragon><color>sienna</color><set><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>3</n></connect><connect><n>4</n><n>5</n></connect><connect><n>6</n><n>7</n></connect></tile><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>7</n></connect><connect><n>3</n><n>4</n></connect><connect><n>5</n><n>6</n></connect></tile><tile><connect><n>0</n><n>5</n></connect><connect><n>1</n><n>4</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>6</n></connect></tile></set></splayer-nodragon><splayer-nodragon><color>hotpink</color><set><tile><connect><n>0</n><n>3</n></connect><connect><n>1</n><n>7</n></connect><connect><n>2</n><n>6</n></connect><connect><n>4</n><n>5</n></connect></tile><tile><connect><n>0</n><n>4</n></connect><connect><n>1</n><n>3</n></connect><connect><n>2</n><n>7</n></connect><connect><n>5</n><n>6</n></connect></tile><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>3</n></connect><connect><n>2</n><n>5</n></connect><connect><n>4</n><n>6</n></connect></tile></set></splayer-nodragon><splayer-nodragon><color>darkgreen</color><set><tile><connect><n>0</n><n>3</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>6</n></connect><connect><n>4</n><n>7</n></connect></tile><tile><connect><n>0</n><n>5</n></connect><connect><n>1</n><n>3</n></connect><connect><n>2</n><n>6</n></connect><connect><n>4</n><n>7</n></connect></tile><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>3</n></connect><connect><n>4</n><n>6</n></connect></tile></set></splayer-nodragon></list>";
        String pl = "<list><splayer-nodragon><color>blue</color><set><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>2</n></connect><connect><n>3</n><n>4</n></connect><connect><n>5</n><n>6</n></connect></tile><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>6</n></connect><connect><n>3</n><n>4</n></connect></tile></set></splayer-nodragon><splayer-nodragon><color>red</color><set><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>3</n></connect><connect><n>4</n><n>6</n></connect><connect><n>5</n><n>7</n></connect></tile><tile><connect><n>0</n><n>5</n></connect><connect><n>1</n><n>4</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>6</n></connect></tile><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>3</n></connect><connect><n>4</n><n>6</n></connect></tile></set></splayer-nodragon><splayer-nodragon><color>green</color><set><tile><connect><n>0</n><n>4</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>6</n></connect></tile><tile><connect><n>0</n><n>4</n></connect><connect><n>1</n><n>7</n></connect><connect><n>2</n><n>3</n></connect><connect><n>5</n><n>6</n></connect></tile><tile><connect><n>0</n><n>7</n></connect><connect><n>1</n><n>3</n></connect><connect><n>2</n><n>5</n></connect><connect><n>4</n><n>6</n></connect></tile></set></splayer-nodragon></list>";        String d = "<board><map></map><map><ent><color>orange</color><pawn-loc><h></h><n>0</n><n>10</n></pawn-loc></ent><ent><color>red</color><pawn-loc><v></v><n>6</n><n>10</n></pawn-loc></ent><ent><color>darkgreen</color><pawn-loc><h></h><n>0</n><n>4</n></pawn-loc></ent><ent><color>sienna</color><pawn-loc><h></h><n>6</n><n>8</n></pawn-loc></ent><ent><color>blue</color><pawn-loc><v></v><n>0</n><n>1</n></pawn-loc></ent><ent><color>hotpink</color><pawn-loc><h></h><n>6</n><n>0</n></pawn-loc></ent><ent><color>green</color><pawn-loc><h></h><n>6</n><n>6</n></pawn-loc></ent></map></board>";
        String c = "<list></list>";
        String bo = "<board><map></map><map><ent><color>red</color><pawn-loc><h></h><n>6</n><n>11</n></pawn-loc></ent><ent><color>blue</color><pawn-loc><h></h><n>0</n><n>0</n></pawn-loc></ent><ent><color>green</color><pawn-loc><v></v><n>0</n><n>0</n></pawn-loc></ent></map></board>";
        String ti = "<tile><connect><n>0</n><n>4</n></connect><connect><n>1</n><n>7</n></connect><connect><n>2</n><n>6</n></connect><connect><n>3</n><n>5</n></connect></tile>";
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
//        while (true) {

            // Drawpile
            //Document doc = Decoder.getDocument(bf.readLine());
            Document doc  = Decoder.getDocument(a);
            Node tilesNode = doc.getElementsByTagName("list").item(0);
            List<Tile> tiles =  Decoder.decode_listofTiles(tilesNode);

            // winners
            //doc = Decoder.getDocument(bf.readLine());
            doc = Decoder.getDocument(pl);
            Node activePlayersNode = doc.getElementsByTagName("list").item(0);
            List<SPlayer> activePlayers =  Decoder.decode_listofSPlayer(activePlayersNode);

            // losers
            //doc = Decoder.getDocument(bf.readLine());
            doc = Decoder.getDocument(c);
            Node outNode = doc.getElementsByTagName("list").item(0);
            List<SPlayer> outPlayers =  Decoder.decode_listofSPlayer(outNode);

            // board
            //doc = Decoder.getDocument(bf.readLine());
            doc = Decoder.getDocument(bo);
            Node boardNode = doc.getElementsByTagName("board").item(0);
            Board board =  Decoder.decode_board(boardNode);


            //tile
//          doc = Decoder.getDocument(bf.readLine());
            Tile tile =  Decoder.decodeTile(ti);


            Administrator admin = new Administrator(activePlayers, board, new DrawPile(tiles, true), outPlayers, null);
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
//    }
}
