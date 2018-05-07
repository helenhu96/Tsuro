package Classes;


import java.util.List;

class Main {
    public static void main(String argv[]) {
        Administrator admin = new Administrator();
        RandPlayer p1 = new RandPlayer("B");
        MostSymmetricPlayer p2 = new MostSymmetricPlayer("R");
        LeastSymmetricPlayer p3 = new LeastSymmetricPlayer("G");
        RandPlayer p4 = new RandPlayer("O");

        admin.registerPlayer(p1);
        admin.registerPlayer(p2);
        admin.registerPlayer(p3);
        admin.registerPlayer(p4);


        List<String> winners = admin.play();

        for (String s : winners) {
            System.out.println(s);
        }
    }
}
