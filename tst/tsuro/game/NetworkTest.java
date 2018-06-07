package tsuro.game;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NetworkTest {
    @Test
    public void startNetworkTest() throws Exception{
        NetworkAdmin networkAdmin = new NetworkAdmin();
        Administrator admin = new Administrator();
        List<IPlayer> iPlayers = new ArrayList<>();
        IPlayer iPlayer1 = new MostSymmetricPlayer("a");
        IPlayer iPlayer2 = new LeastSymmetricPlayer("b");
        IPlayer iPlayer3 = new RandPlayer("c");
        IPlayer iPlayer4 = new RandPlayer("d");
        IPlayer iPlayer5 = new RandPlayer("e");
        IPlayer iPlayer6 = new RandPlayer("f");
        IPlayer iPlayer7 = new RandPlayer("g");
        iPlayers.add(iPlayer1);
        iPlayers.add(iPlayer2);
        iPlayers.add(iPlayer3);
        iPlayers.add(iPlayer4);
        iPlayers.add(iPlayer5);
        iPlayers.add(iPlayer6);
        iPlayers.add(iPlayer7);

        networkAdmin.connectLocal(admin, iPlayers);
        networkAdmin.registerPlayers(admin, iPlayers);
        admin.initPlayers();
        System.out.println(admin.play());
    }

    @Test
    public void NetworkTestPrint() throws Exception{
        Administrator admin = new Administrator();
        // client side
        IPlayer iPlayer1 = new MostSymmetricPlayer("a");
        IPlayer iPlayer2 = new LeastSymmetricPlayer("b");
        IPlayer iPlayer3 = new RandPlayer("c");
        IPlayer iPlayer4 = new RandPlayer("d");
        IPlayer iPlayer5 = new RandPlayer("e");
        IPlayer iPlayer6 = new RandPlayer("f");
        IPlayer iPlayer7 = new RandPlayer("g");

        ClientPlayer c1 = new ClientPlayer(iPlayer1);
        ClientPlayer c2 = new ClientPlayer(iPlayer2);
        ClientPlayer c4 = new ClientPlayer(iPlayer4);
        ClientPlayer c7 = new ClientPlayer(iPlayer7);
        ClientThread clientThread1 = new ClientThread(c1);
        ClientThread clientThread2 = new ClientThread(c2);
        ClientThread clientThread4= new ClientThread(c4);
        ClientThread clientThread7= new ClientThread(c7);
        // server side
        ServerPlayer s1 = new ServerPlayer(admin);
        ServerPlayer s2 = new ServerPlayer(admin);
        ServerPlayer s4 = new ServerPlayer(admin);
        ServerPlayer s7 = new ServerPlayer(admin);
        ServerThread serverThread1 = new ServerThread(s1);
        ServerThread serverThread2 = new ServerThread(s2);
        ServerThread serverThread4 = new ServerThread(s4);
        ServerThread serverThread7 = new ServerThread(s7);

        serverThread1.start();
        clientThread1.start();
        Thread.sleep(1000);
        serverThread2.start();
        clientThread2.start();
        Thread.sleep(1000);
        serverThread4.start();
        clientThread4.start();
        Thread.sleep(1000);
        serverThread7.start();
        clientThread7.start();

        admin.registerPlayer(s1);
        admin.registerPlayer(s2);
        admin.registerPlayer(iPlayer3);
        admin.registerPlayer(s4);
        admin.registerPlayer(iPlayer5);
        admin.registerPlayer(iPlayer6);
        admin.registerPlayer(s7);
        admin.initPlayers();
        System.out.println(admin.play());

    }
}


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