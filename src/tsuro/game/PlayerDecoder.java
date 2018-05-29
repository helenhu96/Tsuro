package tsuro.game;

import org.w3c.dom.*;
import tsuro.xmlmodel.PawnLocation;

import java.util.*;

public class PlayerDecoder extends Decoder{

    private String function;
    private List<Object> arguments;

    public PlayerDecoder(){
        function = null;
        arguments = new ArrayList<>();

    }

    public void decode(String docString) throws Exception{
        try {
            Document doc = getDocument(docString);
            this.function = doc.getDocumentElement().getTagName();


            // handle different cases based on the different function calls
            if (function.equals("player-name")) {
                String str = doc.getFirstChild().getTextContent();
                arguments.add(str);

            } else if (function.equals("initialize")) {
                Node color = doc.getFirstChild();
                Node list_of_color = doc.getFirstChild().getNextSibling();

                arguments.add(color.getTextContent());
                arguments.add(decode_listofColors(list_of_color));

            } else if (function.equals("place-pawn")) {
                Node board = doc.getFirstChild();

                arguments.add(decode_board(board));

            }  else if (function.equals("play-turn")) {
                Node board = doc.getFirstChild();
                Node setofTiles = doc.getFirstChild().getNextSibling();
                String n = doc.getLastChild().getTextContent();

                arguments.add(decode_board(board));
                arguments.add(decode_setofTiles(setofTiles));
                arguments.add(n);


            }  else if (function.equals("end-game")) {

            } else {
                throw new IllegalArgumentException("Not a legal xml file!");
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }


    // Argument is a node with <map> tag
    public Board decode_tiles(Node node) throws IllegalArgumentException {
        if (node.getNodeType() == Node.ELEMENT_NODE) {

            Board board = new Board();

            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++){
                Node curr = children.item(i);

                int[] pos = decode_xy(curr.getFirstChild());
                Tile tile = decode_tile(curr.getLastChild());

                board.placeTile(new Tile(tile), pos[0], pos[1]);
            }

            return board;
        }

        throw new IllegalArgumentException("Input is not a valid xy node object!");
    }


    public int[] decode_xy(Node node) throws IllegalArgumentException {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            int[] xy = new int[2];
            xy[0] = Integer.parseInt(node.getFirstChild().getTextContent());
            xy[1] = Integer.parseInt(node.getFirstChild().getNextSibling().getTextContent());
            return xy;
        }
        throw new IllegalArgumentException("Input is not a valid xy node object!");
    }


    public Tile decode_tile(Node node) throws IllegalArgumentException {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            NodeList connectList = node.getChildNodes();
            if (connectList.getLength() != 4) {
                throw new IllegalArgumentException("Input is not a valid tile node object!");
            }
            int[] input = new int[8];
            int count = 0;
            for (int i = 0; i < 4; i++){
                int[] curr = decode_connect(connectList.item(i));
                input[count] = curr[0];
                count++;
                input[count] = curr[1];
                count++;
            }
            return new Tile(input);
        }
        throw new IllegalArgumentException("Input is not a valid node object!");
    }

    public int[] decode_connect(Node node) throws IllegalArgumentException {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            NodeList nList = node.getChildNodes();
            if (nList.getLength() != 2) {
                throw new IllegalArgumentException("Input is not a valid connect node object!");
            }
            int[] result = new int[2];
            for (int i = 0; i < 2; i++) {
                result[i] = decode_n(nList.item(i));
            }
            return result;
        }
        throw new IllegalArgumentException("Input is not a valid node object!");
    }

    public int decode_n(Node node) throws IllegalArgumentException{
        if (node.getNodeType() == Node.ELEMENT_NODE){
            return Integer.parseInt(node.getTextContent());
        }
        throw new IllegalArgumentException("Input is not a valid node object!");
    }

    public PlayerPosition decode_pawnLoc(Node node, Board curr_board) throws IllegalArgumentException{
        if (node.getNodeType() == Node.ELEMENT_NODE){
            NodeList children = node.getChildNodes();
            Node hv = children.item(0);
            int a = Integer.parseInt(children.item(1).getTextContent());
            int b = Integer.parseInt(children.item(2).getTextContent());

            PawnLocation pawnloc = new PawnLocation(null, null, a, b);
            if (hv.getNodeName().equals("h")){
                pawnloc.h = "h";
            } else {
                pawnloc.v = "v";
            }

            return pawnloc.backtoPlayerPosition(curr_board);
        }
        throw new IllegalArgumentException("Input is not a valid pawnLoc node object!");
    }

    // the argument is a node with <map> tag
    public Map<SPlayer, PlayerPosition> decode_pawns(Node node, Board board) throws IllegalArgumentException {
        if (node.getNodeType() == Node.ELEMENT_NODE){
            Map<SPlayer, PlayerPosition> return_map = new HashMap<>();

            NodeList children = node.getChildNodes();

            for (int i = 0; i < children.getLength(); i++){
                Node curr = children.item(i);
                Node color_node = curr.getFirstChild();
                Node pawnLoc = curr.getLastChild();

                String color = color_node.getTextContent();
                PlayerPosition pp = decode_pawnLoc(pawnLoc, board);
                return_map.put(new SPlayer(color), pp);
            }

            return return_map;
        }
        throw new IllegalArgumentException("Input is not a valid pawns node object!");
    }


    public Board decode_board(Node node) throws IllegalArgumentException{
        if (node.getNodeType() == Node.ELEMENT_NODE){

            // the two nodes with <map> tag
            NodeList children = node.getChildNodes();

            Board new_board = decode_tiles(children.item(0));

            Map<SPlayer, PlayerPosition> map = decode_pawns(children.item(1), new_board);

            new_board.playerToPosition = map;

            return new_board;

        }
        throw new IllegalArgumentException("Input is not a valid board node object!");
    }

    public List<String> decode_listofColors (Node node) throws IllegalArgumentException{
        if (node.getNodeType() == Node.ELEMENT_NODE){
            List<String> list = new ArrayList<>();
            NodeList children = node.getChildNodes();

            for (int i = 0; i < children.getLength(); i++){
                list.add(children.item(i).getTextContent());
            }

            return list;
        }
        throw new IllegalArgumentException("Input is not a valid listofcolors node object!");
    }

    public List<Tile> decode_listofTiles (Node node) throws IllegalArgumentException{
        if (node.getNodeType() == Node.ELEMENT_NODE){
            List<Tile> list = new ArrayList<>();
            NodeList children = node.getChildNodes();

            for (int i = 0; i < children.getLength(); i++){
                list.add(decode_tile(children.item(i)));
            }

            return list;
        }
        throw new IllegalArgumentException("Input is not a valid listoftiles node object!");
    }

    public Set<Tile> decode_setofTiles (Node node) throws IllegalArgumentException{
        if (node.getNodeType() == Node.ELEMENT_NODE){
            Set<Tile> set = new HashSet<>();
            NodeList children = node.getChildNodes();

            for (int i = 0; i < children.getLength(); i++){
                set.add(decode_tile(children.item(i)));
            }

            return set;
        }
        throw new IllegalArgumentException("Input is not a valid setoftiles node object!");
    }




    public static void main(String argv[]) throws Exception {

        Document doc = getDocument("./src/tsuro/tile.xml");

        NodeList tile = doc.getElementsByTagName("tile");

        PlayerDecoder par = new PlayerDecoder();
        Tile t = par.decode_tile(tile.item(0));


        /*
        Tile my_tile = new Tile(input);
        assertTrue(my_tile.sameTile(new Tile(new int[]{0, 5, 1, 3, 2, 6, 4, 7})));
        */
    }

}