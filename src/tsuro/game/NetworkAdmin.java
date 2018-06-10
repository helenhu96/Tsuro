package tsuro.game;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ServerThread extends Thread {
    ServerPlayer serverPlayer;
    public ServerThread(ServerPlayer sp) {
        this.serverPlayer = sp;
    }

    @Override
    public void run(){
        try {
            this.serverPlayer.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ClientThread extends Thread {
    ClientPlayer clientPlayer;

    public ClientThread (ClientPlayer cp) {
        this.clientPlayer = cp;
    }

    @Override
    public void run() {
        try {
            this.clientPlayer.connect();
            this.clientPlayer.processMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class NetworkAdmin {

    public static void initNetworkPlayers(List<IPlayer> iplayers) throws Exception{
        List<ClientThread> clientThreads = new ArrayList<>();
        for (IPlayer iplayer: iplayers) {
            ClientPlayer CPlayer = new ClientPlayer(iplayer);
            ClientThread clientThread = new ClientThread(CPlayer);
            clientThreads.add(clientThread);
        }

        for (int i = 0; i < iplayers.size(); i++) {
            clientThreads.get(i).start();
            Thread.sleep(1000);
        }

    }

    public static void connectLocal(Administrator admin, List<IPlayer> iPlayers) throws Exception{
        List<ClientThread> clientThreads = new ArrayList<>();
        List<ServerThread> serverThreads = new ArrayList<>();
        List<ServerPlayer> serverPlayers = new ArrayList<>();
        for (IPlayer iplayer: iPlayers) {
            ClientPlayer CPlayer = new ClientPlayer(iplayer);
            ClientThread clientThread = new ClientThread(CPlayer);
            ServerPlayer serverPlayer = new ServerPlayer(admin);
            ServerThread serverThread = new ServerThread(serverPlayer);
            clientThreads.add(clientThread);
            serverThreads.add(serverThread);
            serverPlayers.add(serverPlayer);
        }
        for (int i = 0; i < iPlayers.size(); i++) {
            serverThreads.get(i).start();
            clientThreads.get(i).start();
            Thread.sleep(1000);
        }

        for (int i = 0; i < iPlayers.size(); i++) {
            admin.registerPlayer(serverPlayers.get(i));
        }

    }


}
