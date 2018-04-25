package Classes;
import java.util.*;

public class Administrator {
    private List<SPlayer> activePlayers;
    private Board board;
    private DrawPile drawPile;
    private List<SPlayer> deadPlayers;
    private SPlayer playerWithDragonTile;

    public Administrator() {
        activePlayers = new ArrayList<>();
        board = new Board();
        drawPile = new DrawPile();
        deadPlayers = new ArrayList<>();
        playerWithDragonTile = null;
    }

    public Administrator(List<SPlayer> activePlayers, Board board, DrawPile drawPile,
                         List<SPlayer> deadPlayers, SPlayer playerWithDragonTile) {
        this.activePlayers = activePlayers;
        this.board = board;
        this.drawPile = drawPile;
        this.deadPlayers = deadPlayers;
        this.playerWithDragonTile = playerWithDragonTile;
    }

    public void addPlayer(SPlayer player) {
        activePlayers.add(player);
    }

/*    //adds every tile to the drawPile
    public void initializeDrawPile() {
        if (!drawPile.isEmpty()) drawPile.clear();
        String text = "((0 1) (2 3) (4 5) (6 7))"+
                "((0 1) (2 4) (3 6) (5 7))"+
                "((0 6) (1 5) (2 4) (3 7))"+
                "((0 5) (1 4) (2 7) (3 6))"+
                "((0 2) (1 4) (3 7) (5 6))"+
                "((0 4) (1 7) (2 3) (5 6))"+
                "((0 1) (2 6) (3 7) (4 5))"+
                "((0 2) (1 6) (3 7) (4 5))"+
                "((0 4) (1 5) (2 6) (3 7))"+
                "((0 1) (2 7) (3 4) (5 6))"+
                "((0 2) (1 7) (3 4) (5 6))"+
                "((0 3) (1 5) (2 7) (4 6))"+
                "((0 4) (1 3) (2 7) (5 6))"+
                "((0 3) (1 7) (2 6) (4 5))"+
                "((0 1) (2 5) (3 6) (4 7))"+
                "((0 3) (1 6) (2 5) (4 7))"+
                "((0 1) (2 7) (3 5) (4 6))"+
                "((0 7) (1 6) (2 3) (4 5))"+
                "((0 7) (1 2) (3 4) (5 6))"+
                "((0 2) (1 4) (3 6) (5 7))"+
                "((0 7) (1 3) (2 5) (4 6))"+
                "((0 7) (1 5) (2 6) (3 4))"+
                "((0 4) (1 5) (2 7) (3 6))"+
                "((0 1) (2 4) (3 5) (6 7))"+
                "((0 2) (1 7) (3 5) (4 6))"+
                "((0 7) (1 5) (2 3) (4 6))"+
                "((0 4) (1 3) (2 6) (5 7))"+
                "((0 6) (1 3) (2 5) (4 7))"+
                "((0 1) (2 7) (3 6) (4 5))"+
                "((0 3) (1 2) (4 6) (5 7))"+
                "((0 3) (1 5) (2 6) (4 7))"+
                "((0 7) (1 6) (2 5) (3 4))"+
                "((0 2) (1 3) (4 6) (5 7))"+
                "((0 5) (1 6) (2 7) (3 4))"+
                "((0 5) (1 3) (2 6) (4 7))";

        text = text.replace('(',' ');
        text = text.replace(')',' ');

        String delims = "[ ]+";
        String[] everything = text.split(delims);

        int index = 1;

        for (int i = 0; i < 35; i++){
            int[] my_array = new int[8];
            for (int j = 0; j < 8; j++){
                my_array[j] = Integer.parseInt(everything[index]);
                index++;
            }
            Tile new_tile = new Tile(my_array);
            drawPile.add(new_tile);
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

    //returns the list of winners if game is over, otherwise returns null
    public List<SPlayer> playATurn(Tile tile) {
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
                    }
                    //remove player from list of active players
                    iter.remove();
                }
            }
        }

        //if the player who just played a turn survived, add him to the back of the activePlayer list
        if (survived) activePlayers.add(currPlayer);

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
        if (activePlayers.size()==1) {
            return activePlayers;
        }
        else if (activePlayers.isEmpty()) { //if there are no active players left, then players who are eliminated in the last round win
            return toBeDead;
        }

        return null;
    }


    //returns true if a tile doesn't lead player to edge of board
    public boolean tileLegal(SPlayer player, Board board, Tile tile) {
        //Check if placing this tile would make the token move to the border
        PlayerPosition position = board.getPlayerPosition(player);
        int nextSpot = tile.getConnected(position.getSpot());
        position.setSpot(nextSpot);
        if (board.isBorder(position)) return false;

        //starting point is the point next to the one that the player would move to from the placed tile
        PlayerPosition startingPosition = board.flip(position);

        //call moveAlongPath to see if token would reach end of board
        if (board.isBorder(moveAlongPath(startingPosition, board))) return false;
        return true;
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
/*        Administrator admin = new Administrator();
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
