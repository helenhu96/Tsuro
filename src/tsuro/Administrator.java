package tsuro;
import com.google.common.collect.Lists;

import java.util.*;

import static org.junit.Assert.*;

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
        SPlayer splayer = new SPlayer(COLORS[numPlayer]);
        splayer.associatePlayer(player);
        activePlayers.add(splayer);
        numPlayer++;
    }

    private void setupGame() throws Exception{
        //initialize drawPile
        drawPile.initialize();

        //initialize players
        for (int i=0; i<numPlayer; i++) {
            SPlayer s = activePlayers.get(i);
            //should not need defensive deep copy
            s.getIplayer().initialize(COLORS[i], Lists.newArrayList(COLORS));
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

    //TODO: handle exception
    public List<String> play() throws Exception {
        setupGame();

        //play till we have a winner/winners
        while (!winners.isEmpty()) {
            SPlayer currPlayer = activePlayers.get(0);
            Tile t = currPlayer.getIplayer().playTurn(board, currPlayer.getHandTiles(), drawPile.size());
            //handle illegal play
            if (!legalPlay(currPlayer, board, t)) {
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


    //TODO: fix legalplay
    /**
     * Description of the method
     *
     * @param player an {@link SPlayer} to check the validity
     * @param board
     * @param tile
     * @return Description of return value
     */
    public boolean legalPlay(SPlayer player, Board board, Tile tile) {
        //Check if given tile is one of the player's handTiles
        boolean hasTile = false;
        for (Tile t: player.getHandTiles()){
            if (t.sameTile(tile)){
                hasTile = true;
            }
        }

        if (!hasTile) {
            return false;
        }

        //if tile won't lead player to elimination, return true
        if (tileLegal(player, board, tile)) {
            return true;
        }

        //else, loop through player's hand tiles and check if there are other non-eliminating moves. If so, return false
        for (Tile currTile: player.getHandTiles()) {
            Tile tempHandTile = new Tile(currTile);
            for (int i=0; i<4; i++) {
                if (tileLegal(player, board, tempHandTile)) {
                    return false;
                }
                tempHandTile.rotateClockwise();
            }
        }

        //check if the given tile is already on the board
        if (!checkIfTileIsOnBoard(tile)) {
            return false;
        }

        return true;
    }

    //if no tile on the board is the same as tile, return true; otherwise returns false
    public boolean checkIfTileIsOnBoard(Tile tile) {
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++){
                Tile curr = board.getTile(i, j);
                if (curr != null){
                    if (tile.sameTile(curr)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void moveSplayer(SPlayer splayer, List<SPlayer> playersDiedThisTurn) {
        PlayerPosition finalPosition = moveAlongPath(splayer, board);

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
            moveSplayer(splayer, playersDiedThisTurn);
        }

        //return eliminated player's hand tiles to draw pile and then re-shuffle.
        handleDeadPlayers(playersDiedThisTurn, pile);
        List<SPlayer> winners = getwinners(board, activePlayers, playersDiedThisTurn);

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

        //reshuffle order of active players
        activePlayers = reorderPlayers(activePlayers, currentPlayer);
        SPlayer currentDrawer = drawOrder.get(0);

        drawTiles(pile, currentDrawer, drawOrder);
        //add eliminated players to list of dead players
        deadPlayers.addAll(playersDiedThisTurn);

        //check if there are any players left
        return getwinners(board, activePlayers, playersDiedThisTurn);
    }

    public void handleDeadPlayers(List<SPlayer> playersDiedThisTurn, DrawPile pile) {
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

    public void drawTiles(DrawPile pile, SPlayer currentDrawer, List<SPlayer> drawOrder) {
        //distribute tiles to players with under-full hands
        while (!stopdrawing(pile, activePlayers)) {
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
    public DrawPile returnDragon(DrawPile pile) {
        this.playerWithDragonTile = null;
        try {
            pile.addDragon();
        } catch (Exception e) {

        }
        return pile;
    }

    // checks whether we should stop drawing from deck
    // stops when the pile is empty or all players has 3 cards in hand
    public boolean stopdrawing(DrawPile pile, List<SPlayer> activePlayers) {
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
    public List<SPlayer> getwinners(Board board, List<SPlayer> activePlayers, List<SPlayer> toBeDead) {
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

    //returns true if a tile doesn't lead player to edge of board
    public boolean tileLegal(SPlayer player, Board board, Tile tile) {
        //get position of player
        PlayerPosition position = board.getPlayerPosition(player);

        //call moveAlongPath to see if token would reach end of board
        boolean result = true;
        board.placeTile(tile, position.getY(), position.getX());
        if (board.isBorder(moveAlongPath(player, board))) {
            result = false;
        }
        board.removeTile(position.getY(), position.getX());
        return result;
    }

    //returns the furthest adjacent position a player can move to from given starting position
    //return edge's coordinates if moved to edge
    public PlayerPosition moveAlongPath(SPlayer splayer,  Board board) {


        PlayerPosition position = board.getPlayerPosition(splayer);
        Tile currTile = board.getTile(position.getY(), position.getX());
        while (currTile != null){
            int nextSpot = currTile.getConnected(position.getSpot());
            position.setSpot(nextSpot);
            //if at edge, return edge coordinates
            if (board.isBorder(position))
                return position;

            position = board.flip(position);
            currTile = board.getTile(position.getY(), position.getX());
        }

        return position;
    }

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

    public static void main(String[] args) throws Exception{
        SPlayer player1 = new SPlayer("Green");
        SPlayer player2 = new SPlayer("Red");
        Board board = new Board();
        board.updatePlayerPosition(player1, new PlayerPosition(0,0,1));
        board.updatePlayerPosition(player2, new PlayerPosition(0,0,7));
        Tile tile1 = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});


        List<SPlayer> activePlayers = new ArrayList<>();
        activePlayers.add(player1);
        activePlayers.add(player2);
        List<SPlayer> deadPlayers = new ArrayList<>();
        DrawPile drawPile = new DrawPile();
        drawPile.initialize();
        player1.receiveTile(drawPile.drawATile());
        player1.receiveTile(drawPile.drawATile());
        player2.receiveTile(drawPile.drawATile());
        player2.receiveTile(drawPile.drawATile());
        Administrator admin = new Administrator(activePlayers, board, drawPile, deadPlayers, player2);
        drawPile.giveDragon();
        assertEquals(admin.getPlayerWithDragonTileDragon(), player2);
        assertFalse(drawPile.hasDrogon());


        List<SPlayer> winners = admin.playATurn(drawPile, activePlayers, deadPlayers, board, tile1);
        assertEquals(1, winners.size());
        assertEquals(player1, winners.get(0));



        assertTrue(drawPile.hasDrogon());
        assertEquals(1, activePlayers.size());
        assertEquals(player1, activePlayers.get(0));
        assertEquals(1, deadPlayers.size());
        assertEquals(player2, deadPlayers.get(0));
        assertEquals(0, player2.numHandTiles());
        assertEquals(null, admin.getPlayerWithDragonTileDragon());

    }

}


