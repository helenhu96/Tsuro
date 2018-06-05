package tsuro.game;

public class RunTournament {

    public static void main(String[] args) throws Exception{
        IPlayer iPlayer1 = new MostSymmetricPlayer("a");
        ClientPlayer c1 = new ClientPlayer(iPlayer1);
        ClientThread clientThread1 = new ClientThread(c1);
        clientThread1.start();
        Thread.sleep(1000);
        IPlayer iPlayer2 = new LeastSymmetricPlayer("b");
        ClientPlayer c2 = new ClientPlayer(iPlayer2);
        ClientThread clientThread2 = new ClientThread(c2);
        clientThread2.start();
        Thread.sleep(1000);
        IPlayer iPlayer3 = new RandPlayer("c");
        ClientPlayer c3 = new ClientPlayer(iPlayer3);
        ClientThread clientThread3 = new ClientThread(c3);
        clientThread3.start();

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
