package tsuro.game;


import java.util.List;
import java.util.Set;

class Tournament {
    public static void main(String argv[]) throws Exception {
        for (int i=0; i<1000; i++) {
            Administrator admin = new Administrator();
            MPlayer p1 = new RandPlayer("1");
            MPlayer p2 = new MostSymmetricPlayer("2");
            MPlayer p3 = new LeastSymmetricPlayer("3");
            MPlayer p4 = new RandPlayer("4");
            MPlayer p5 = new RandPlayer("5");
            MPlayer p6 = new RandPlayer("6");
            MPlayer p7 = new MostSymmetricPlayer("7");
            MPlayer p8 = new LeastSymmetricPlayer("8");

            admin.registerPlayer(p1);
            admin.registerPlayer(p2);
            admin.registerPlayer(p3);
            admin.registerPlayer(p4);
            admin.registerPlayer(p5);
            admin.registerPlayer(p6);
            admin.registerPlayer(p7);
            admin.registerPlayer(p8);
            admin.initPlayers();
            Set<String> winners = admin.play();
            System.out.println("winners are   " + winners);
        }
    }
}
