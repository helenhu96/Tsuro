package tsuro.game;

import java.util.ArrayList;
import java.util.List;

public class RunTournament {

    public static void main(String[] args) throws Exception{
        List <IPlayer> iPlayers = new ArrayList<>();
        IPlayer iPlayer1 = new MostSymmetricPlayer("a");
        IPlayer iPlayer2 = new LeastSymmetricPlayer("b");
        IPlayer iPlayer3 = new RandPlayer("c");
        iPlayers.add(iPlayer1);
        iPlayers.add(iPlayer2);
        iPlayers.add(iPlayer3);

        NetworkAdmin network = new NetworkAdmin();
        network.initNetworkPlayers(iPlayers);
    }
}
