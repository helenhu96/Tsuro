package tsuro;

import java.util.*;

public interface IPlayer {

    //TODO: remove this from IPlayer?
    enum State {
        UNINITIALIZED,
        INITIALIZED,
        PLAYING,
    }


    /**
     * return player's name
      */
    String get_name();

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
    PlayerPosition place_pawn(Board board);

    /**
     *
     * @param board
     * @param tiles
     * @param numTiles
     * @return the tile the player should place, suitably rotated
     * Called to ask the player to make a move. The tiles
     * are the ones the player has, the number is the
     * count of tiles that are not yet handed out to players.
     */
    Tile play_turn(Board board, List<Tile> tiles, int numTiles) throws Exception;

    /**
     *
     * @param board
     * @param winnerColors
     * Called to inform the player of the final board state and which players won the game
     */
    void end_game(Board board, List<String> winnerColors);


    /**
     *
     * @param color
     * @return whether the color is valid
     */
    //boolean isColorValid(String color);
}
