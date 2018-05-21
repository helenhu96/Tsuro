package tsuro;
import com.google.common.collect.Lists;

import java.util.*;

public class Administrator {
    private static final String[] COLORS =
        new String[] {"Blue", "Red", "Green", "Orange", "Sienna", "Hotpink", "Darkgreen", "Purple"};

    private List<SPlayer> activePlayers;
    private Board board;
    private DrawPile drawPile;
    private List<SPlayer> deadPlayers;
    private SPlayer playerWithDragonTile;
    private int numPlayer;
    private List<SPlayer> winners;

    public static final int HAND_SIZE = 3;
    public Administrator() {
        this(new ArrayList<>(), new Board(), new DrawPile(), new ArrayList<>(), null);
    }

    public Administrator(List<SPlayer> activePlayers, Board board, DrawPile drawPile,
                         List<SPlayer> deadPlayers, SPlayer playerWithDragonTile) {
        this.activePlayers = activePlayers;
        this.board = board;
        this.drawPile = drawPile;
        this.deadPlayers = deadPlayers;
        this.playerWithDragonTile = playerWithDragonTile;
        numPlayer = 0;
        this.winners = new ArrayList<>();
    }

    public void registerPlayer(IPlayer player){
        if (numPlayer>7) {
            System.err.println("Game is full");
            return;
        }
        String color = COLORS[numPlayer];
        SPlayer splayer = new SPlayer(color);
        splayer.associatePlayer(player);
        player.initialize(color, Lists.newArrayList(COLORS));
        activePlayers.add(splayer);
        numPlayer++;
    }

    public void setupGame() throws Exception{
        //initialize drawPile
        drawPile.initialize();
        //initialize players
        for (int i=0; i<activePlayers.size(); i++) {
            SPlayer s = activePlayers.get(i);
            initializePlayerPosition(s);
            initializePlayerHand(s);
        }
    }

    private void initializePlayerPosition(SPlayer s) {
        PlayerPosition pos = s.getIplayer().placePawn(board);
        while (!board.isBorder(pos) || board.positionHasPlayer(pos)) {
            //player cheated
            System.out.println(s.getColor() + " cheated.");
            s.dealWithCheater();
            pos = s.getIplayer().placePawn(board);
        }
        board.updatePlayerPosition(s, pos);
    }

    private void initializePlayerHand(SPlayer s) throws Exception{
        //every player starts with 3 tiles
        for (int j=0; j<HAND_SIZE; j++) {
            try {
                s.receiveTile(drawPile.drawATile());
            } catch (Exception e) {
                throw new Exception("illegal drawing from the tile!", e);
            }
        }
    }

    public List<String> play() throws Exception {
        setupGame();

        //play till we have a winner/winners
        while (winners.isEmpty()) {
            SPlayer currPlayer = activePlayers.get(0);
            Tile t = currPlayer.getIplayer().playTurn(board, currPlayer.getHandTiles(), drawPile.size());
            //handle illegal play
            if (!this.legalPlay(currPlayer, board, t)) {
                System.out.println(currPlayer.getColor() + "cheated");
                currPlayer.dealWithCheater();
                ((RandPlayer) currPlayer.getIplayer()).setState(IPlayer.State.PLAYING);
                t = currPlayer.getIplayer().playTurn(board, currPlayer.getHandTiles(), drawPile.size());
            }
            currPlayer.removeTile(t);
            winners = playATurn(drawPile, activePlayers, deadPlayers, board, t);
        }

        List<String> winningColors = new ArrayList<>();
        for (SPlayer s : winners) {
            winningColors.add(s.getColor());
        }

        for (SPlayer s : activePlayers) {
            s.getIplayer().endGame(board, winningColors);
        }

        for (SPlayer s : deadPlayers) {
            s.getIplayer().endGame(board, winningColors);
        }

        return winningColors;
    }


    /**
     * Description of the method
     *
     * @param player an {@link SPlayer} to check the validity
     * @param board
     * @param tile
     * @return Description of return value
     */
    public boolean legalPlay(SPlayer player, Board board, Tile tile) {
        // check if the player is alive
        if (!player.isAlive()) {
            return false;
        }

        // check if given tile is one of the player's handTiles
        if (!player.hasTile(tile)){
            return false;
        }

        //check if the given tile is already on the board
        if (checkTileOnBoard(tile)) {
            return false;
        }

        //if tile won't lead player to elimination, return true
        if (!board.tileLegal(player, tile)) {
            return false;
        }
        return true;
    }

    //if no tile on the board is the same as tile, return true; otherwise returns false
    public boolean checkTileOnBoard(Tile tile) {
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++){
                Tile curr = board.getTile(i, j);
                if (curr != null){
                    if (tile.sameTile(curr)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void moveSPlayer(SPlayer splayer, List<SPlayer> playersDiedThisTurn) {
        PlayerPosition finalPosition = board.moveAlongPath(splayer);

        this.setPlayerPosition(splayer, finalPosition);

        if (board.isBorder(finalPosition)) {
            playersDiedThisTurn.add(splayer);
        }
    }


    //returns the list of winners if game is over, otherwise returns null
    public List<SPlayer> playATurn(DrawPile pile, List<SPlayer> activePlayers, List<SPlayer> deadPlayers, Board board, Tile tile) throws Exception{

        //list of players that died in this turn
        List<SPlayer> playersDiedThisTurn = new ArrayList<>();
        //get current player
        SPlayer currentPlayer = activePlayers.get(0);
        PlayerPosition playerPosition = board.getPlayerPosition(currentPlayer);
        //place tile at current position
        board.placeTile(tile, playerPosition.getY(), playerPosition.getX());

        //move splayers and check if they're eliminated
        for (SPlayer splayer: activePlayers) {
            moveSPlayer(splayer, playersDiedThisTurn);
        }

        //return eliminated player's hand tiles to draw pile and then re-shuffle.
        handleDeadPlayers(playersDiedThisTurn, pile);
        List<SPlayer> winners = getWinners(board, activePlayers, playersDiedThisTurn);

        if (winners.size() != 0) {
            return winners;
        }
        List<SPlayer> drawOrder = activePlayers;
        if (playerWithDragonTile != null) {
            // if some player has dragon tile, change the order of drawing
            int index = drawOrder.indexOf(playerWithDragonTile);
            drawOrder = activePlayers.subList(index, activePlayers.size()+1);
            drawOrder.addAll(activePlayers.subList(0, index));
        }

        //reorder order of active players
        activePlayers = reorderPlayers(activePlayers, currentPlayer);

        SPlayer currentDrawer = drawOrder.get(0);
        drawTiles(pile, currentDrawer, drawOrder);
        //add eliminated players to list of dead players
        deadPlayers.addAll(playersDiedThisTurn);
        //check if there are any players left
        return getWinners(board, activePlayers, playersDiedThisTurn);
    }

    public void handleDeadPlayers(List<SPlayer> playersDiedThisTurn, DrawPile pile) throws Exception{
        for (SPlayer dead: playersDiedThisTurn){
            if (dead.equals(playerWithDragonTile)) {
                this.returnDragon(pile);
            }
            activePlayers.remove(dead);
            pile.addTilesAndShuffle(dead.getHandTiles());
            dead.removeHandTiles();
            deadPlayers.add(dead);
            dead.setDead();
        }
    }

    public void drawTiles(DrawPile pile, SPlayer currentDrawer, List<SPlayer> drawOrder) throws Exception{
        //distribute tiles to players with under-full hands
        while (!stopDrawing(pile, activePlayers)) {
            // if current player is the dragon owner, return the dragon to pile first
            if (playerWithDragonTile == currentDrawer) {
                pile = returnDragon(pile);
            }
            try {
                Tile newtile = pile.drawATile();
                if (newtile != null) {
                    currentDrawer.receiveTile(newtile);
                } else {
                    playerWithDragonTile = currentDrawer;
                }
            }
            catch (Exception e) {
                System.err.println("trying to draw a tile while the pile is empty!");
            }
            currentDrawer = getNextDrawer(drawOrder, currentDrawer);
        }
    }


    public List<SPlayer> reorderPlayers(List<SPlayer> activePlayers, SPlayer currentPlayer) {
        // if currentPlayer is alive, move it to the end and return;
        // if dead return original list;
        if (currentPlayer.isAlive()) {
            activePlayers.remove(currentPlayer);
            activePlayers.add(currentPlayer);
        }
        return activePlayers;
    }

    // determines which player draws from deck next
    public SPlayer getNextDrawer(List<SPlayer> drawOrder, SPlayer currPlayer) {
        int index = drawOrder.indexOf(currPlayer);
        return drawOrder.get((index+1) % drawOrder.size());
    }

    //TODO: handle exceptions
    // returns the dragon to the pile
    public DrawPile returnDragon(DrawPile pile) throws Exception{
        this.playerWithDragonTile = null;
        try {
            pile.addDragon();
        } catch (Exception e) {
            throw e;
        }
        return pile;
    }

    // checks whether we should stop drawing from deck
    // stops when the pile is empty or all players has 3 cards in hand
    public boolean stopDrawing(DrawPile pile, List<SPlayer> activePlayers) {
        if (pile.isEmpty()) {
            return true;
        }
        for (SPlayer splayer: activePlayers) {
            if (!splayer.isHandFull()) {
                return false;
            }
        }
        return true;
    }

    //return winners if game over, empty list if game is still on
    public List<SPlayer> getWinners(Board board, List<SPlayer> activePlayers, List<SPlayer> toBeDead) {
        List<SPlayer> winners = new ArrayList<>();
        if (board.getNumTiles()==35) {
            winners = activePlayers;
        } else if (activePlayers.size() == 1) {
            winners = activePlayers;
        } else if (activePlayers.size() == 0) {
            winners = toBeDead;
        }
        return winners;
    }


    //TODO: find out if we need this???
    //given the current player who has the dragon tile, and the list of active players, return the next
    //player who needs the dragon tile. If no other player needs the dragon tile, return null.
    public SPlayer findDragonSuccessor(SPlayer currentDragon, List<SPlayer> listOfPlayers) {
        //get index of current dragon tile holder
        int currIndex = listOfPlayers.indexOf(currentDragon);

        //first, look for the dragon successor in the list after the current holder
        for (int i =  currIndex + 1;i<listOfPlayers.size(); i++) {
            if (listOfPlayers.get(i).numHandTiles()<3)
                return listOfPlayers.get(i);
        }
        //then, look for the dragon successor in the list before the current holder
        for (int i = 0; i<currIndex; i++) {
            if (listOfPlayers.get(i).numHandTiles()<3)
                return listOfPlayers.get(i);
        }
        //otherwise, everyone has a full hand, no one needs the dragon
        return null;
    }

    //update a player's position (mostly used for testing
    public void setPlayerPosition(SPlayer player, PlayerPosition position) {
        board.updatePlayerPosition(player, position);
    }


    public SPlayer getPlayerWithDragonTileDragon() {
        return this.playerWithDragonTile;
    }

    public SPlayer getSPlayerFromColor(String color) throws IllegalArgumentException{
        try {
            for (SPlayer splayer: activePlayers) {
                if (splayer.getColor().equals(color)) {
                    return splayer;
                }
            }
        }
         catch (Exception e) {
            throw e;
         }
         return null;
    }

    // for testing only
    public SPlayer getSPlayer(int index) {
        return activePlayers.get(index);
    }
    public DrawPile getDrawPile() {
        return this.drawPile;
    }



}


