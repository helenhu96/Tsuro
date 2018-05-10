package Classes;
import java.util.*;
import java.io.*;

public class Administrator {
    private List<SPlayer> activePlayers;
    private Board board;
    private DrawPile drawPile;
    private List<SPlayer> deadPlayers;
    private SPlayer playerWithDragonTile;
    private int numPlayer;

    private static final String[] COLORS =
            new String[] {"Blue", "Red", "Green", "Orange", "Sienna", "Hotpink", "Darkgreen", "Purple"};

    public Administrator() {
        activePlayers = new ArrayList<>();
        board = new Board();
        drawPile = new DrawPile();
        deadPlayers = new ArrayList<>();
        playerWithDragonTile = null;
        numPlayer = 0;
    }

    public Administrator(List<SPlayer> activePlayers, Board board, DrawPile drawPile,
                         List<SPlayer> deadPlayers, SPlayer playerWithDragonTile) {
        this.activePlayers = activePlayers;
        this.board = board;
        this.drawPile = drawPile;
        this.deadPlayers = deadPlayers;
        this.playerWithDragonTile = playerWithDragonTile;
        numPlayer = 0;
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

    public List<String> play(){


        //initialize drawPile
        drawPile.initialize();

        //initialize players
        for (int i=0; i<numPlayer; i++) {
            SPlayer s = activePlayers.get(i);
            s.getIplayer().initialize(COLORS[i], Arrays.asList(Arrays.copyOfRange(COLORS, 0, numPlayer)));
            PlayerPosition pos = s.getIplayer().placePawn(board);
            if (!board.isBorder(pos) || board.positionHasPlayer(pos)) {
                //player cheated
                System.out.println(s.getColor() + " cheated.");
                s.cheat();
                pos = s.getIplayer().placePawn(board);
            }
            board.updatePlayerPosition(s, pos);
            //every player starts with 3 tiles
            for (int j=0; j<3; j++) {
                s.receiveTile(drawPile.drawATile());
            }
        }

        //play till we have a winner/winners
        List<SPlayer> winners = null;

        while (!activePlayers.isEmpty()) {
            SPlayer currPlayer = activePlayers.get(0);
            Tile t = currPlayer.getIplayer().playTurn(board, currPlayer.getHandTiles(), drawPile.size());
            if (!legalPlay(currPlayer, board, t)) {
                //player cheated
                System.out.println(currPlayer.getColor() + "cheated");
                currPlayer.cheat();
                ((RandPlayer) currPlayer.getIplayer()).setState("PLAYING");
                currPlayer.getIplayer().playTurn(board, currPlayer.getHandTiles(), drawPile.size());
            }
            currPlayer.removeTile(t);
            winners = playATurn(t);
            if (winners != null) break;
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


/*    //adds every tile to the drawPile
    public void initializeDrawPile() {
        if (!drawPile.isEmpty()) drawPile.clear();

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
                drawPile.add(new_tile);
            }

        }
        catch(IOException e){
            System.err.println("IOException occurred!");
        }
    }*/


    public boolean legalPlay(SPlayer player, Board board, Tile tile) {
        //Check if given tile is one of the player's handTiles
        boolean hasTile = false;
        for (Tile t: player.getHandTiles()){
            if (t.sameTile(tile)){
                hasTile = true;
            }
        }
        if (!hasTile) return false;


        //if tile won't lead player to elimination, return true
        if (tileLegal(player, board, tile)) return true;

        //else, loop through player's hand tiles and check if there are other non-eliminating moves. If so, return false
        for (Tile currTile: player.getHandTiles()) {
            Tile tempHandTile = new Tile(currTile);
            for (int i=0; i<4; i++) {
                if (tileLegal(player, board, tempHandTile))
                    return false;
                tempHandTile.rotateClockwise();
            }
        }
        return true;
    }

    //if no tile on the board is the same as tile, return true; otherwise returns false
    public boolean checkBoard(Tile tile){
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++){
                Tile curr = board.getTile(i, j);
                if (curr != null){
                    if (tile.sameTile(curr)) return false;
                }
            }
        }
        return true;
    }

    //returns the list of winners if game is over, otherwise returns null
    public List<SPlayer> playATurn(Tile tile) {

        //check if the given tile is already on the board
        if (!checkBoard(tile)) throw new java.lang.IllegalStateException("Tile already on the board!");

        //list of players that died in this turn
        List<SPlayer> toBeDead = new ArrayList<>();

        //get current player
        SPlayer currPlayer = activePlayers.get(0);
        PlayerPosition playerPosition = board.getPlayerPosition(currPlayer);

        //place tile at current position
        board.placeTile(tile, playerPosition.getY(), playerPosition.getX());

        //move current player and check if they're eliminated
        PlayerPosition finalPosition = moveAlongPath(playerPosition, board);
        board.updatePlayerPosition(currPlayer, finalPosition);


        boolean survived = false;

        //curr player is eliminated
        if (board.isBorder(finalPosition)) {
            //add currPlayer to buffer list
            toBeDead.add(currPlayer);
            //if currPlayer has the dragon tile, find the next player who needs the dragon tile
            if (playerWithDragonTile == currPlayer) {
                playerWithDragonTile = findDragonSuccessor(currPlayer, activePlayers);
            }
        }
        else {
            //player is still in the game, draw a tile
            if (drawPile.isEmpty()) {
                //if no one holds the dragon tile, currPlayer should get it
                if (playerWithDragonTile == null) {
                    playerWithDragonTile = currPlayer;
                }
            }
            else {
                currPlayer.receiveTile(drawPile.drawATile());
            }
            survived = true;
        }

        //remove currPlayer from activePlayers list
        activePlayers.remove(0);

        //if the player who just played a turn survived, add him to the back of the activePlayer list
        if (survived) {
            activePlayers.add(currPlayer);
        }

        //move all other players if they are affected
        Iterator<SPlayer> iter = activePlayers.iterator();
        while (iter.hasNext()) {
            SPlayer player = iter.next();
            PlayerPosition position = board.getPlayerPosition(player);
            //if this player is on the affected tile, move them
            if (position.getY()== playerPosition.getY() && position.getX()==playerPosition.getX()) {
                PlayerPosition fPosition = moveAlongPath(position, board);
                board.updatePlayerPosition(player, fPosition);
                //check if the player is now out of bound
                if (board.isBorder(fPosition)) {
                    toBeDead.add(player);
                    //if this player holds the dragon tile, pass it on to the next rightful player
                    if (playerWithDragonTile == player) {
                        playerWithDragonTile = findDragonSuccessor(player, activePlayers);
//                        //a rather hacky way to fix the issue of curr player eliminating player with dragon tile
//                        //and draw pile is empty
//                        if (playerWithDragonTile == null && survived && currPlayer.numHandTiles()<3) {
//                            playerWithDragonTile = currPlayer;
//                        }
                    }
                    //remove player from list of active players
                    iter.remove();
                }
            }
        }



        //add eliminated player's hand tiles to draw pile and then re-shuffle.
        for (SPlayer dead: toBeDead){
            drawPile.addTilesAndShuffle(dead.getHandTiles());
            dead.removeHandTiles();
        }

        //distribute tiles to players with under-full hands
        while (playerWithDragonTile != null && !drawPile.isEmpty()) {
            playerWithDragonTile.receiveTile(drawPile.drawATile());
            playerWithDragonTile = findDragonSuccessor(playerWithDragonTile, activePlayers);
        }

        //add eliminated players to list of dead players
        deadPlayers.addAll(toBeDead);

        //check if there are any players left
        if (activePlayers.size()==1)
            return activePlayers;
        else if (activePlayers.isEmpty())  //if there are no active players left, then players who are eliminated in the last round win
            return toBeDead;
        else if (board.getNumTiles()==35) //if every tile has been placed, game is over
            return activePlayers;

        return null;
    }


    //returns true if a tile doesn't lead player to edge of board
    public boolean tileLegal(SPlayer player, Board board, Tile tile) {
        //get position of player
        PlayerPosition position = board.getPlayerPosition(player);

        //call moveAlongPath to see if token would reach end of board
        boolean result = true;
        board.placeTile(tile, position.getY(), position.getX());
        if (board.isBorder(moveAlongPath(position, board))) result = false;
        board.removeTile(position.getY(), position.getX());
        return result;
    }

    //returns the furthest adjacent position a player can move to from given starting position
    //return edge's coordinates if moved to edge
    public PlayerPosition moveAlongPath(PlayerPosition startingPosition, Board board) {
        PlayerPosition position = new PlayerPosition(startingPosition);
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


    public SPlayer getDragon() {
        return playerWithDragonTile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrator that = (Administrator) o;
        boolean result = true;
        if (!board.equals(that.board)) return false;
        if (drawPile.size()!=that.drawPile.size()) return false;
        if (playerWithDragonTile!=that.playerWithDragonTile) return false;
        if (activePlayers.size() != that.activePlayers.size()) return false;
        for (int i=0; i<activePlayers.size();i++) {
            if (!activePlayers.get(i).equals(that.activePlayers.get(i)))
                return false;
        }

        if (deadPlayers.size() != that.deadPlayers.size()) return false;
        for (int i=0; i<activePlayers.size();i++) {
            if (!activePlayers.get(i).equals(that.activePlayers.get(i)))
                return false;
        }

        return true;
    }






    public static void main(String[] args) {
//        Tile test1 = new Tile(new int[]{0,3,1,7,2,6,4,5});
//        int ans1 = symmetry(test1);
//        System.out.println("Shoud return 0");
//        System.out.println(ans1);
//
//        Tile test2 = new Tile(new int[]{2,3,4,5,1,6,0,7});
//        int ans2 = symmetry(test2);
//        System.out.println("Shoud return 1");
//        System.out.println(ans2);
//
//        Tile test3 = new Tile(new int[]{0,1,2,6,3,7,4,5});
//        int ans3 = symmetry(test3);
//        System.out.println("Shoud return 2");
//        System.out.println(ans3);
//
//        Tile test4 = new Tile(new int[]{1,2,3,4,5,6,7,0});
//        int ans4 = symmetry(test4);
//        System.out.println("Shoud return 4");
//        System.out.println(ans4);








        /*
        Administrator admin = new Administrator();
        admin.initializeDrawPile();
        admin.addPlayer(new SPlayer("Green"));
        Tile tile1 = new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6});
        Tile tile2 = new Tile(new int[]{0, 6, 1, 3, 2, 4, 5, 7});
        Tile tile3 = new Tile(new int[]{0, 5, 1, 2, 3, 7, 4, 6});
        admin.activePlayers.get(0).receiveTile(tile1);
        admin.activePlayers.get(0).receiveTile(tile2);
        admin.activePlayers.get(0).receiveTile(tile3);
        admin.board.placeTile(new Tile(new int[]{0, 3, 1, 6, 2, 7, 4, 5}), 1, 0);
        admin.board.placeTile(new Tile(new int[]{0, 1, 2, 6, 3, 7, 4, 5}), 1, 2);
        admin.board.placeTile(new Tile(new int[]{0, 2, 1 ,6, 3, 7, 4, 5}), 1, 3);
        admin.board.placeTile(new Tile(new int[]{0, 5, 1, 4, 2, 3, 6, 7}), 0, 3);
        admin.board.updateTokensNext(admin.activePlayers.get(0), new Integer[]{1, 1, 7});

        System.out.println("legalPlay: leads player to edge. should return false:");
        System.out.println(admin.legalPlay(admin.activePlayers.get(0), admin.board, tile1));
        System.out.println("legalPlay: should return true:");
        System.out.println(admin.legalPlay(admin.activePlayers.get(0), admin.board, tile2));
        System.out.println("legalPlay: player doesn't have this tile. should return false:");
        System.out.println(admin.legalPlay(admin.activePlayers.get(0), admin.board, new Tile(new int[]{0, 1, 2, 6, 3, 7, 4, 5})));
        System.out.println("legalPlay: should return true:");
        System.out.println(admin.legalPlay(admin.activePlayers.get(0), admin.board, tile3));

        admin.addPlayer(new SPlayer("Red"));
        admin.board.updateTokensNext(admin.activePlayers.get(1), new Integer[]{1, 1, 6});
        List<SPlayer> winner = admin.playATurn(tile1);
        System.out.println("Winner should be red:");
        System.out.println(winner.get(0).getColor());


        admin.activePlayers.get(0).receiveTile(tile1);
        admin.activePlayers.get(0).receiveTile(tile2);
        admin.activePlayers.get(0).receiveTile(tile3);
        admin.board.updateTokensNext(admin.activePlayers.get(0), new Integer[]{1, 1, 7});

        admin.addPlayer(new SPlayer("Yellow"));
        admin.board.updateTokensNext(admin.activePlayers.get(1), new Integer[]{1, 1, 6});

        admin.addPlayer(new SPlayer("Blue"));
        admin.board.updateTokensNext(admin.activePlayers.get(2), new Integer[]{2, 2, 6});

        admin.activePlayers.get(0).removeTile(tile2);
        List<SPlayer> winner1 = admin.playATurn(tile2);

        System.out.println("There should be no winners:");
        System.out.println(winner1);
        System.out.println("First active player should be yellow:");
        System.out.println(admin.activePlayers.get(0).getColor());



        Administrator admin = new Administrator();
        SPlayer player1 = new SPlayer("Green");
        SPlayer player2 = new SPlayer("Red");
        admin.addPlayer(player1);
        admin.addPlayer(player2);
        admin.board.updateTokensNext(player1, new Integer[]{0, 0, 1});
        admin.board.updateTokensNext(player2, new Integer[]{0, 0, 7});
        Tile tile1 = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});

        List<SPlayer> winner = admin.playATurn(tile1);*/






    }






}
