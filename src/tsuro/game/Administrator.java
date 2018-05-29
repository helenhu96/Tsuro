package tsuro.game;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Administrator {
    private static final String[] COLORS =
        new String[] {"blue", "red", "green", "orange", "sienna", "hotpink", "darkgreen", "purple"};

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
        List<String> colorlist = new ArrayList<>();
        for (String c: COLORS) {
            colorlist.add(c);
        }
        player.initialize(color, colorlist);
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

    public Set<String> play() throws Exception {
        setupGame();

        //play till we have a winner/winners
        while (winners.isEmpty()) {
            SPlayer currPlayer = activePlayers.get(0);
            //no tile in hand
            if (currPlayer.numHandTiles() == 0 && currPlayer.doIHaveDragon()) {
                reorderPlayers(activePlayers, currPlayer);

            } else {
                Tile t = currPlayer.getIplayer().playTurn(board, currPlayer.getHandTiles(), drawPile.size());
                //handle illegal play
                while (!this.legalPlay(currPlayer, board, t)) {
                    System.out.println(currPlayer.getColor() + "cheated");
                    currPlayer.dealWithCheater();
                    ((RandPlayer) currPlayer.getIplayer()).setState(PlayerState.PLAYING);
                    t = currPlayer.getIplayer().playTurn(board, currPlayer.getHandTiles(), drawPile.size());
                }
                currPlayer.removeTile(t);
                winners = playATurn(drawPile, activePlayers, deadPlayers, board, t);
            }

        }

        Set<String> winningColors = new HashSet<>();
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
    public boolean legalPlay(SPlayer player, Board board, Tile tile) throws Exception{
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
    public boolean checkTileOnBoard(Tile tile) throws Exception{
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

    private void moveSPlayer(SPlayer splayer, List<SPlayer> playersDiedThisTurn) throws Exception{
        PlayerPosition finalPosition = board.moveAlongPath(splayer);

        this.setPlayerPosition(splayer, finalPosition);
        int y = finalPosition.getY();
        int x = finalPosition.getX();
        // making sure it is not first turn
        if (board.isBorder(finalPosition) && board.getTile(y, x) != null) {
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

        List<SPlayer> drawOrder = changeDrawOrder(activePlayers);
        //reorder order of active players
        activePlayers = reorderPlayers(activePlayers, currentPlayer);


        SPlayer currentDrawer = drawOrder.get(0);
        drawTiles(pile, currentDrawer, drawOrder);

        //check if there are any players left
        return getWinners(board, activePlayers, playersDiedThisTurn);
    }

    public List<SPlayer> changeDrawOrder(List<SPlayer> activePlayers) {
        List<SPlayer> drawOrder = new ArrayList<>();
        if (playerWithDragonTile != null) {
            // if some player has dragon tile, change the order of drawing
            int index = activePlayers.indexOf(playerWithDragonTile);
            for (int i = index; i < activePlayers.size(); i++) {
                drawOrder.add(activePlayers.get(i));
            }
            for (int i = 0; i < index; i++) {
                drawOrder.add(activePlayers.get(i));
            }
        } else {
            drawOrder.addAll(activePlayers);
        }
        return drawOrder;
    }

    public void handleDeadPlayers(List<SPlayer> playersDiedThisTurn, DrawPile pile) throws Exception{
        for (SPlayer dead: playersDiedThisTurn){
            if (dead.equals(playerWithDragonTile)) {
                dead.returnDragon();
                int dragonIndex = (this.activePlayers.indexOf(dead) + 1) % this.activePlayers.size();
                playerWithDragonTile = activePlayers.get(dragonIndex);
                playerWithDragonTile.getDragon();
            }
            activePlayers.remove(dead);
            pile.addTilesAndShuffle(dead.getHandTiles());
            dead.removeHandTiles();
            deadPlayers.add(dead);
            dead.setDead();
        }
    }

    public void drawTiles(DrawPile pile, SPlayer currentDrawer, List<SPlayer> drawOrder) throws Exception{
        // if current player is the dragon owner, return the dragon to pile first
        if (playerWithDragonTile != null) {
            pile = returnDragon(pile);
        }
        //distribute tiles to players with under-full hands
        while (!stopDrawing(pile, activePlayers)) {
            try {
                Tile newtile = pile.drawATile();
                if (newtile != null) {
                    currentDrawer.receiveTile(newtile);
                } else { // we got dragon tile
                    playerWithDragonTile = currentDrawer;
                    playerWithDragonTile.getDragon();
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
        this.playerWithDragonTile.returnDragon();
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
        if (activePlayers.size() == 0) {
            winners = toBeDead;
        } else if (board.getNumTiles()==35) {
            winners = activePlayers;
        } else if (activePlayers.size() == 1) {
            winners = activePlayers;
        }
        return winners;
    }



    //update a player's position (mostly used for testing
    public void setPlayerPosition(SPlayer player, PlayerPosition position) {
        board.updatePlayerPosition(player, position);
    }


    public SPlayer getPlayerWithDragonTile() {
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
         throw new IllegalArgumentException("No such player found");
    }

    public static List<Tile> getAllLegalTiles() {
        List<Tile> tiles = new ArrayList<>();
        try {
            File file = new File("./tiles.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;

            while ((line = br.readLine()) != null) {
                line = line.replace('(', ' ');
                line = line.replace(')', ' ');
                String[] nums = line.split("[ ]+");

                int[] my_array = new int[8];
                for (int i = 1; i < nums.length; i++) {
                    my_array[i-1] = Integer.parseInt(nums[i]);
                }
                Tile new_tile = new Tile(my_array);
                tiles.add(new_tile);
            }
            Collections.shuffle(tiles);
        }
        catch(IOException e){
            System.err.println("IOException occurred!");
        }
        return tiles;
    }


    // for testing only
    public SPlayer getSPlayer(int index) {
        return activePlayers.get(index);
    }
    public DrawPile getDrawPile() {
        return this.drawPile;
    }



}


