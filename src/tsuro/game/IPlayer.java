package tsuro.game;

import java.util.*;

public interface IPlayer {

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
    Tile playTurn(Board board, Set<Tile> tiles, int numTiles);

    /**
     *
     * @param board
     * @param winnerColors
     * Called to inform the player of the final board state and which players won the game
     */
    void endGame(Board board, Set<String> winnerColors);

}
