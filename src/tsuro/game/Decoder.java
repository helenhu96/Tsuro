package tsuro.game;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import tsuro.xmlmodel.PawnLocation;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.*;

public class Decoder {

    public static Document getDocument(String docString) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
//            factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(docString)));

    }

    public static int decode_n(Node node) throws IllegalArgumentException{
        if (node.getNodeType() == Node.ELEMENT_NODE){
            return Integer.parseInt(node.getTextContent());
        }
        throw new IllegalArgumentException("Input is not a valid node object!");
    }


    public static int[] decode_connect(Node node) throws IllegalArgumentException {
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

//    public static Tile decode_tile(Node node) throws IllegalArgumentException {
//        if (node.getNodeType() == Node.ELEMENT_NODE) {
//            NodeList connectList = node.getChildNodes();
//            if (connectList.getLength() != 4) {
//                throw new IllegalArgumentException("Input is not a valid tile node object!");
//            }
//            int[] input = new int[8];
//            int count = 0;
//            for (int i = 0; i < 4; i++){
//                int[] curr = decode_connect(connectList.item(i));
//                input[count] = curr[0];
//                count++;
//                input[count] = curr[1];
//                count++;
//            }
//            return new Tile(input);
//        }
//        throw new IllegalArgumentException("Input is not a valid node object!");
//    }


    public static PlayerPosition decodePawnLoc(Node node, Board curr_board) throws IllegalArgumentException{
        if (node.getNodeType() == Node.ELEMENT_NODE){
            NodeList children = node.getChildNodes();
            Node hv = children.item(0);
            int a = Integer.parseInt(children.item(1).getTextContent());
            int b = Integer.parseInt(children.item(2).getTextContent());

            PawnLocation pawnloc = new PawnLocation(null, null, a, b);
            if (hv.getNodeName().equals("h")){
                pawnloc.setH("h");
            } else {
                pawnloc.setV("v");
            }

            return pawnloc.backtoPlayerPosition(curr_board);
        }
        throw new IllegalArgumentException("Input is not a valid pawnLoc node object!");
    }

    public static Board decode_tiles(Node node) throws IllegalArgumentException {
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


    public static int[] decode_xy(Node node) throws IllegalArgumentException {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            int[] xy = new int[2];
            xy[1] = Integer.parseInt(node.getFirstChild().getTextContent());
            xy[0] = Integer.parseInt(node.getFirstChild().getNextSibling().getTextContent());
            return xy;
        }
        throw new IllegalArgumentException("Input is not a valid xy node object!");
    }


    public static Tile decode_tile(Node node) throws IllegalArgumentException {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            NodeList connectList = node.getChildNodes();
            if (connectList.getLength() != 4) {
                throw new IllegalArgumentException("Input is not a valid tile node object!");
            }
            int[] input = new int[8];
            int count = 0;
            for (int i = 0; i < 4; i++){
                int[] curr = decodeConnect(connectList.item(i));
                input[count] = curr[0];
                count++;
                input[count] = curr[1];
                count++;
            }
            return new Tile(input);
        }
        throw new IllegalArgumentException("Input is not a valid node object!");
    }

    public static int[] decodeConnect(Node node) throws IllegalArgumentException {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            NodeList nList = node.getChildNodes();
            if (nList.getLength() != 2) {
                throw new IllegalArgumentException("Input is not a valid connect node object!");
            }
            int[] result = new int[2];
            for (int i = 0; i < 2; i++) {
                result[i] = decodeN(nList.item(i));
            }
            return result;
        }
        throw new IllegalArgumentException("Input is not a valid node object!");
    }

    public static int decodeN(Node node) throws IllegalArgumentException{
        if (node.getNodeType() == Node.ELEMENT_NODE){
            return Integer.parseInt(node.getTextContent());
        }
        throw new IllegalArgumentException("Input is not a valid node object!");
    }

    // the argument is a node with <map> tag
    public static Map<String, PlayerPosition> decode_pawns(Node node, Board board) throws IllegalArgumentException {
        if (node.getNodeType() == Node.ELEMENT_NODE){
            Map<String, PlayerPosition> return_map = new HashMap<>();

            NodeList children = node.getChildNodes();

            for (int i = 0; i < children.getLength(); i++){
                Node curr = children.item(i);
                Node color_node = curr.getFirstChild();
                Node pawnLoc = curr.getLastChild();

                String color = color_node.getTextContent();
                PlayerPosition pp = decodePawnLoc(pawnLoc, board);
                return_map.put(color, pp);
            }

            return return_map;
        }
        throw new IllegalArgumentException("Input is not a valid pawns node object!");
    }


    public static Board decode_board(Node node) throws IllegalArgumentException{

        // the two nodes with <map> tag
        NodeList children = node.getChildNodes();

        Board new_board = decode_tiles(children.item(0));

        Map<String, PlayerPosition> map = decode_pawns(children.item(1), new_board);

        new_board.colorToPosition = map;

        return new_board;

    }

    public static List<String> decodeListofColors(Node node) throws IllegalArgumentException{
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

    public static Set<String> decodeSetofColors(Node node) throws IllegalArgumentException{
        if (node.getNodeType() == Node.ELEMENT_NODE){
            Set<String> set = new HashSet<>();
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++){
                set.add(children.item(i).getTextContent());
            }

            return set;
        }
        throw new IllegalArgumentException("Input is not a valid listofcolors node object!");
    }

    public static List<Tile> decodeListofTiles(Node node) throws IllegalArgumentException{
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

    public static Set<Tile> decodeSetofTiles(Node node) throws IllegalArgumentException{
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

    public static SPlayer decodeSPlayer(Node node) throws IllegalArgumentException{
        if (node.getNodeType() == Node.ELEMENT_NODE){
            SPlayer sp = new SPlayer(node.getFirstChild().getTextContent());
            if (node.getNodeName().equals("splayer-dragon")){
                sp.getDragon();
            }

            Set<Tile> tiles = decodeSetofTiles(node.getFirstChild().getNextSibling());
            for (Tile t: tiles){
                sp.receiveTile(t);
            }
            return sp;

        }
        throw new IllegalArgumentException("Input is not a valid splayer node object!");
    }

    public static List<SPlayer> decodeListofsplayer(Node node) throws IllegalArgumentException{
        if (node.getNodeType() == Node.ELEMENT_NODE){
            List<SPlayer> list = new ArrayList<>();

            NodeList children = node.getChildNodes();

            for (int i = 0; i < children.getLength(); i++){
                list.add(decodeSPlayer(children.item(i)));
            }

            return list;

        }
        throw new IllegalArgumentException("Input is not a valid set of splayer node object!");
    }

    //<player-name> str </player-name> to string

    public static String decodeGetName(String docString) throws Exception{
        Document doc = getDocument(docString);
        Node type = doc.getElementsByTagName("player-name").item(0);
        String answer = type.getTextContent();
        return answer;
    }


    public static Tile decodeTile(String docString) throws Exception {
        Document doc = getDocument(docString);
        Node node = doc.getElementsByTagName("tile").item(0);
        Tile t = decode_tile(node);
        return t;
    }




}