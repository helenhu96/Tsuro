package tsuro.game;

import org.junit.Test;

public class NetworkTest {

    //TODO: FIX COLORS!!!!
    public class ServerThread extends Thread {
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

    public class ClientThread extends Thread {
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

    @Test
    public void startNetworkTest() throws Exception{
        Administrator admin = new Administrator();
        Administrator admin1 = new Administrator();
        // client side
        IPlayer iPlayer1 = new MostSymmetricPlayer("a");
        IPlayer iPlayer2 = new LeastSymmetricPlayer("b");
        ClientPlayer c1 = new ClientPlayer(iPlayer1);
        ClientPlayer c2 = new ClientPlayer(iPlayer2);
        ClientThread clientThread1 = new ClientThread(c1);
        ClientThread clientThread2 = new ClientThread(c2);

        // server side
        ServerPlayer s1 = new ServerPlayer(admin);
        ServerPlayer s2 = new ServerPlayer(admin);
        ServerThread serverThread1 = new ServerThread(s1);
        ServerThread serverThread2 = new ServerThread(s2);

        serverThread1.start();
        clientThread1.start();
        Thread.sleep(1000);
        serverThread2.start();
        clientThread2.start();

        admin.registerPlayer(s1);
        admin.registerPlayer(s2);

        System.out.println(admin.play());
    }
}
