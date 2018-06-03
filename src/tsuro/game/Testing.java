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
    Socket socket;
    int PORT;
    static BufferedReader bf;
    static PrintWriter pw;
    public Testing() throws IOException{
        ServerSocket serverSocket = new ServerSocket(PORT);
        this.socket = serverSocket.accept();
        this.pw = new PrintWriter(socket.getOutputStream(), true);
        this.bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        serverSocket.close();
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            Document doc = Decoder.getDocument(bf.readLine());
            Node tilesNode = doc.getElementsByTagName("list").item(0);
            List<Tile> tiles =  Decoder.decode_listofTiles(tilesNode);

            doc = Decoder.getDocument(bf.readLine());
            Node activePlayersNode = doc.getElementsByTagName("list").item(0);
            List<SPlayer> activePlayers =  Decoder.decode_listofSPlayer(activePlayersNode);

            doc = Decoder.getDocument(bf.readLine());
            Node losersNode = doc.getElementsByTagName("list").item(0);
            List<SPlayer> lostPlayers =  Decoder.decode_listofSPlayer(losersNode);

            doc = Decoder.getDocument(bf.readLine());
            Node boardNode = doc.getElementsByTagName("board").item(0);
            Board board =  Decoder.decode_board(boardNode);

            Tile tile =  Decoder.decodeTile(bf.readLine());

            Administrator admin = new Administrator();
            admin.playATurn(tiles, activePlayers, lostPlayers, board, tile);
        }
    }
}
