package Classes;

import org.junit.Test;

import static org.junit.Assert.*;

public class AdministratorTest {

    @Test
    public void playATurn() {
        Administrator admin = new Administrator();
        SPlayer player1 = new SPlayer("Green");
        SPlayer player2 = new SPlayer("Red");
        admin.addPlayer(player1);
        admin.addPlayer(player2);


    }
}