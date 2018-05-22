package tsuro;

import org.xml.sax.*;
import org.w3c.dom.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.*;
import java.io.File;
import java.util.*;

public class Decoder {

    private String function;
    private List<Object> arguments;


    public Decoder(){
        function = null;
        arguments = new ArrayList<>();

    }

    public String getFunction(){
        return new String(function);
    }

    public List<Object> getArguments(){
        return arguments;
    }





    public static Document getDocument(String docString) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setValidating(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            return builder.parse(new InputSource(docString));

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return null;
    }


    public void decode_general(String docString){
        Document doc = getDocument(docString);
        String function = doc.getDocumentElement().getTagName();


        // handle different cases based on the different function calls
        if (function.equals("player-name")){

        }

        else if (function.equals("initialize")){

        }


        else if (function.equals("place-pawn")){

        }

        else if (function.equals("pawn-loc")){

        }

        else if (function.equals("play-turn")){

        }

        else if (function.equals("tile")){

        }

        else if (function.equals("end-game")){

        }

        else {
            //do nothing
        }



    }













    public Tile decode_tile(String docString) throws JAXBException {
        try {
            Document doc = getDocument(docString);

            NodeList tile = doc.getElementsByTagName("tile");

            if (tile.getLength() > 1){
                throw new IllegalArgumentException("The input file is not a single tile!");
            }

            Node t = tile.item(0);

            JAXBContext jc = JAXBContext.newInstance(ConvertedTile.class);
            Unmarshaller u = jc.createUnmarshaller();
            Object element = u.unmarshal(t);
            Tile result = ((ConvertedTile) element).backtoTile();
            return result;
        } catch (JAXBException e){
            throw new JAXBException(e.getMessage());
        }
    }





    public Board decode_board(Node b) throws JAXBException {
        try {

            // tiles and pawns node
            NodeList children = b.getChildNodes();

            // tiles
            Node tiles = children.item(0);

            // pawns
            Node pawns = children.item(1);

            // list of tileentries
            NodeList allTileEntries = tiles.getChildNodes();

            // list of pawnentries
            NodeList allPawnEntries = pawns.getChildNodes();

            Board result = new Board();

            // process the tileenties
            decode_tileentries(result, allTileEntries);

            //
            Map<String, PlayerPosition> map = new HashMap<>();

            decode_pawnentries(allPawnEntries, map, result);

            return result;


        } catch (JAXBException e){
            throw new JAXBException(e.getMessage());
        }
    }

    // input is an array of pawnentries, get back the matrix of tiles
    public void decode_tileentries(Board board, NodeList entries) throws JAXBException {
        try {
            Tile[][] tiles = new Tile[6][6];

            for(int i = 0; i < entries.getLength(); i++){
                Node curr = entries.item(i);
                JAXBContext jc = JAXBContext.newInstance(TileEntry.class);
                Unmarshaller u = jc.createUnmarshaller();
                TileEntry element = (TileEntry) u.unmarshal(curr);

                Object[] result = element.backToPosandTile();
                int[] pos = (int[]) result[0];
                Tile tile = (Tile) result[1];

                board.placeTile(tile, pos[0], pos[1]);
            }

        } catch (JAXBException e){
            throw new JAXBException(e.getMessage());
        }
    }

    public void decode_pawnentries(NodeList entries, Map<String, PlayerPosition> map, Board b) throws JAXBException {
        try {

            Map<String, PlayerPosition> pawn_map = new HashMap<>();

            for (int i = 0; i < entries.getLength(); i++){
                Node curr = entries.item(i);
                JAXBContext jc = JAXBContext.newInstance(PawnEntry.class);
                Unmarshaller u = jc.createUnmarshaller();
                PawnEntry element = (PawnEntry) u.unmarshal(curr);

                element.writePawnMap(map, b);

            }

            Map<SPlayer, PlayerPosition> actualMap = new HashMap<>();

            for (String str: pawn_map.keySet()){
                actualMap.put(new SPlayer(str), pawn_map.get(str));
            }

            b.playerToPosition = actualMap;

        } catch (JAXBException e){
            throw new JAXBException(e.getMessage());
        }
    }




    public static void main(String argv[]) throws Exception {


        Document doc = getDocument("./src/tsuro/board.xml");


        //board
        NodeList boardList = doc.getChildNodes();


        Decoder dec = new Decoder();
        Board oboard = dec.decode_board(boardList.item(0));



        /*
        Element el = doc.getDocumentElement();
        String hehe = el.getTagName();
        */


    }
}
