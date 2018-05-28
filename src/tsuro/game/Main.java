package tsuro.game;


import java.util.List;

class Main {
    public static void main(String argv[]) throws Exception {
        int rWins = 0;
        int mWins = 0;
        int lWins = 0;
        for (int i=0; i<200; i++) {
        Administrator admin = new Administrator();
        MPlayer p1 = new RandPlayer("1");
        MPlayer p2 = new MostSymmetricPlayer("2");
        MPlayer p3 = new LeastSymmetricPlayer("3");

        admin.registerPlayer(p1);
        admin.registerPlayer(p2);
        admin.registerPlayer(p3);

        List<String> winners = admin.play();
        System.out.println(winners);

        for (String s : winners) {
            if (s.equals("Blue")) rWins++;
            if (s.equals("Red")) mWins++;
            if (s.equals("Green")) lWins++;
            }
        }
        System.out.println("Random wins: " + rWins);
        System.out.println("Most symmetric wins: " + mWins);
        System.out.println("Least symmetric wins: " + lWins);
    }
}
