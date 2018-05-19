package tsuro;

import java.util.*;

public interface IPlayer {

    enum State {
        UNINITIALIZED,
        INITIALIZED,
        PLAYING,
    }

    final static String[] COLOR_VALUES =
            new String[] {"Blue", "Red", "Green", "Orange", "Sienna", "Hotpink", "Darkgreen", "Purple"};
    State state = State.UNINITIALIZED;

    /**
     * return player's name
      */
    String getName();

    // Called to indicate a game is starting.
    // The first argument is the player's color
    // and the second is all of the players'
    // colors, in the order that the game will be played.
    void initialize(String color, List<String> colors);

    /**
     * Called at the first step in a game; indicates where
     * the player wishes to place their pawn. The pawn must
     * be placed along the edge in an unoccupied space.
     */
    PlayerPosition placePawn(Board board);

    // Called to ask the player to make a move. The tiles
    // are the ones the player has, the number is the
    // count of tiles that are not yet handed out to players.
    // The result is the tile the player should place,
    // suitably rotated.
    Tile playTurn(Board board, List<Tile> tiles, int numTiles);

    // Called to inform the player of the final board
    // state and which players won the game.
    void endGame(Board board, List<String> winnerColors);

}
