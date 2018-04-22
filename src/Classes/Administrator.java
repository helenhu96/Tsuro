package Classes;
import java.util.*;

public class Administrator {
    private List<SPlayer> activePlayers;
    private Board board;
    private List<Tile> drawPile;
    private List<SPlayer> deadPlayers;

    public Administrator() {
        activePlayers = new ArrayList<>();
        board = new Board();
        drawPile = new ArrayList<>();
        deadPlayers = new ArrayList<>();
    }

    public void addPlayer(SPlayer player) {
        activePlayers.add(player);
    }

    public void addToDrawPile(Tile tile) {
        drawPile.add(tile);
    }

    //adds every tile to the drawPile
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
    }


    public boolean legalPlay(SPlayer player, Board board, Tile tile) {
        Tile tempTile = new Tile(tile);
        //Check if this tile is one of the player's handTiles
        boolean hasTile = false;
        for (Tile t: player.getHandTiles()){
            if (t.sameTile(tempTile)){
                hasTile = true;
            }
        }

        if (!hasTile) return false;

        //if tile is legal, return true
        if (tileLegal(player, board, tempTile)) return true;

        boolean result = true;
        //else, loop through player's hand tiles and check if there are other legal moves. If so, return false
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
        SPlayer currPlayer = activePlayers.remove(0);
        Integer[] playerPosition = board.getTokensNext(currPlayer);

        //place tile at current position
        board.placeTile(tile, playerPosition[0], playerPosition[1]);

        //move current player and check if he's eliminated
        Integer[] finalPosition = moveAlongPath(playerPosition, board);
        board.updateTokensNext(currPlayer, finalPosition);
        boolean survived = false;
        if (board.isBorder(finalPosition)) {
            toBeDead.add(currPlayer);
        }
        else {
            //player is still in the game, draw a tile
            if (drawPile.isEmpty()) {
                System.out.println("Out of tiles in draw pile");
            }
            else {
                currPlayer.receiveTile(drawPile.remove(0));
            }
            survived = true;
        }

        //move all other players if they are affected
        Iterator<SPlayer> iter = activePlayers.iterator();
        while (iter.hasNext()) {
            SPlayer player = iter.next();
            Integer[] pos = board.getTokensNext(player);
            //if this player is on the affected tile, move them
            if (pos[0].equals(playerPosition[0]) && pos[1].equals(playerPosition[1])) {
                Integer[] fPos = moveAlongPath(pos, board);
                board.updateTokensNext(player, fPos);
                //check if the player is now out of bound
                if (board.isBorder(fPos)) {
                    toBeDead.add(player);
                    iter.remove();
                }
            }
        }

        //if the player who just played a turn survived, add him to the back of the activePlayer list
        if (survived) activePlayers.add(currPlayer);

        //add eliminated player's hand tiles to draw pile and then shuffle.
        for (SPlayer dead: toBeDead){
            drawPile.addAll(dead.getHandTiles());
            Collections.shuffle(drawPile);
            dead.removeHandTiles();
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
        Integer[] position = board.getTokensNext(player);
        position[2] = tile.getConnected(position[2]);
        if (board.isBorder(position)) return false;

        //starting point is the point next to the one that the player would move to from the placed tile
        Integer[] startingPoint = board.flip(position);

        //call moveAlongPath to see if token would reach end of board
        if (board.isBorder(moveAlongPath(startingPoint, board))) return false;
        return true;
    }

    //returns the furthest adjacent position a player can move to from given starting position
    //return edge's coordinates if moved to edge
    public Integer[] moveAlongPath(Integer[] startingPoint, Board board) {
        Integer [] point = startingPoint.clone();
        Tile curr = board.getTile(point[0], point[1]);
        while (curr != null){
            int next = curr.getConnected(point[2]);
            point[2] = next;
            //if at edge, return edge coordinates
            if (board.isBorder(point))
                return point;

            point = board.flip(point);
            curr = board.getTile(point[0], point[1]);
        }
        return point;
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
        System.out.println(admin.activePlayers.get(0).getColor());*/

        Administrator admin = new Administrator();
        SPlayer player1 = new SPlayer("Green");
        SPlayer player2 = new SPlayer("Red");
        admin.addPlayer(player1);
        admin.addPlayer(player2);
        admin.board.updateTokensNext(player1, new Integer[]{0, 0, 1});
        admin.board.updateTokensNext(player2, new Integer[]{0, 0, 7});
        Tile tile1 = new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6});

        List<SPlayer> winner = admin.playATurn(tile1);



    }


}
