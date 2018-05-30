package tsuro.game;

import java.net.Socket;

public class ClientPlayer {
    IPlayer iplayer;
    Socket connectSocket;
    private int PORT;

    public ClientPlayer(IPlayer p) {
        this.iplayer = p;
    }
//
//    public void init() {
//        connectSocket = new Socket(PORT,);
//    }
}
