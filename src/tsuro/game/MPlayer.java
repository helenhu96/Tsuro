package tsuro.game;

import com.google.common.base.Preconditions;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

abstract class MPlayer implements IPlayer {
    protected String name;
    protected String color;
    protected PlayerState state;
    protected MPlayer(String name) {
        this.name = name;
        state = PlayerState.UNINITIALIZED;
    }
    final static String[] COLOR_VALUES =
            new String[] {"blue", "red", "green", "orange", "sienna", "hotpink", "darkgreen", "purple"};



    protected final static int UP = 0;
    protected final static int RIGHT = 1;
    protected final static int DOWN = 2;
    protected final static int LEFT = 3;


    //protected final static Set<String> COLORS_SET= new HashSet<>(Arrays.asList(COLOR_VALUES));


    public String getName() { return this.name; }

    public String getColor() {
        return this.color;
    }

    public void initialize(String color, List<String> colors) {
        try {
            checkState(PlayerState.UNINITIALIZED);
            if (!colors.contains(color)) {
                throw new IllegalArgumentException("Can't initialize with this color!");
            }
            this.color = color;
            state = PlayerState.INITIALIZED;
        } catch (IllegalStateException e){
            throw e;
        }



    }


    public PlayerPosition placePawn(Board board) {
        checkState(PlayerState.INITIALIZED);
        if (board.getPlayerColors().contains(this.color)) {
            throw new java.lang.IllegalStateException("Pawn already exists on board");
        }

        PlayerPosition result = null;
        do {
            //returns a number between 0 to 3, representing four sides of the board
            int side = ThreadLocalRandom.current().nextInt(UP, LEFT + 1);
            int blockNum = ThreadLocalRandom.current().nextInt(0, 5 + 1);
            int spotOffset = ThreadLocalRandom.current().nextInt(0, 1 + 1);
            switch (side) {
                case UP:
                    result = new PlayerPosition(0, blockNum, 0+spotOffset);
                    break;
                case RIGHT:
                    result = new PlayerPosition(blockNum, 5, 2+spotOffset);
                    break;
                case DOWN:
                    result = new PlayerPosition(5, blockNum, 4+spotOffset);
                    break;
                case LEFT:
                    result = new PlayerPosition(blockNum, 0, 6+spotOffset);
                    break;
            }
        } while(board.positionHasPlayer(result));
        state = PlayerState.PLAYING;
        return result;
    }


    public void endGame(Board board, Set<String> winnerColors) {
        state = PlayerState.UNINITIALIZED;
    }

    public void setState(PlayerState s) {
        this.state = s;
    }

    public PlayerState getState() {
        return state;
    }

    abstract public Tile playTurn(Board b, Set<Tile> hand, int tilesInDeck);



    //TODO: consider change for loop
    public Set<Tile> chooseLegalRotations(Board board, Set<Tile> hand) throws Exception{
        Set<Tile> legalTiles = new HashSet<>();
        Map<SPlayer, PlayerPosition> SPlayers = board.getPlayerToPosition();
        SPlayer mysplayer = null;
        for (SPlayer splayer: SPlayers.keySet()) {
            if (getColor().equals(splayer.getColor())) {
                mysplayer = splayer;
            }
        }
        if (mysplayer == null) {
            throw new Exception("can't find corresponding splayer!");
        }
        for (Tile tile: hand) {
            Tile copied = new Tile(tile);
            for (int i = 0; i < 4; i++) {
                if (board.tileLegal(mysplayer, copied)) {
                    legalTiles.add(new Tile(copied));
                }
                copied.rotateClockwise();
            }
        }
        // if no rotation of tile is legal, then every rotation of every tile is legal
        if (legalTiles.size() == 0) {
            for (Tile tile: hand) {
                Tile copied = new Tile(tile);
                for (int i = 0; i < 4; i++) {
                    legalTiles.add(new Tile(copied));
                    copied.rotateClockwise();
                }
            }
        }
        return legalTiles;
    }

    public void checkState(PlayerState desiredState) throws IllegalStateException {
        if (this.state != desiredState) {
            throw new IllegalStateException("Expect State " + desiredState + " actual state " + this.state);
        }
    }




}
