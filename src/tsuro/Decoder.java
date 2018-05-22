package tsuro;

import org.xml.sax.*;
import org.w3c.dom.*;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.*;
import java.io.File;
import java.io.StringReader;
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





    public static Document getDocument(String docString) throws Exception{
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setValidating(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            return builder.parse(new InputSource(new StringReader(docString)));

        }
        catch (Exception ex){
            throw new Exception(ex.getMessage());
        }

    }


    public void decode_general(String docString) throws Exception{
        try {
            Document doc = getDocument(docString);
            String function = doc.getDocumentElement().getTagName();


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


            } else if (function.equals("tile")) {

            } else if (function.equals("end-game")) {

            } else {
                //do nothing
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }



    }













    public Tile decode_tile(Node t) throws JAXBException {
        try {

            JAXBContext jc = JAXBContext.newInstance(ConvertedTile.class);
            Unmarshaller u = jc.createUnmarshaller();
            Object element = u.unmarshal(t);
            Tile result = ((ConvertedTile) element).backtoTile();
            return result;
        } catch (JAXBException e) {
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

    public List<String> decode_listofColors(Node lc) throws JAXBException {
        try {

            JAXBContext jc = JAXBContext.newInstance(ListofColor.class);
            Unmarshaller u = jc.createUnmarshaller();
            ListofColor element = (ListofColor) u.unmarshal(lc);

            return element.backtoColors();

        } catch (JAXBException e){
            throw new JAXBException(e.getMessage());
        }
    }


    public List<Tile> decode_listofTiles(Node lt) throws JAXBException {
        try {

            JAXBContext jc = JAXBContext.newInstance(ListofTile.class);
            Unmarshaller u = jc.createUnmarshaller();
            ListofTile element = (ListofTile) u.unmarshal(lt);

            return element.backtoTiles();

        } catch (JAXBException e){
            throw new JAXBException(e.getMessage());
        }
    }

    public Set<Tile> decode_setofTiles(Node st) throws JAXBException {
        try {

            JAXBContext jc = JAXBContext.newInstance(SetofTile.class);
            Unmarshaller u = jc.createUnmarshaller();
            SetofTile element = (SetofTile) u.unmarshal(st);

            return element.backtoTiles();

        } catch (JAXBException e){
            throw new JAXBException(e.getMessage());
        }
    }

    public Set<String> decode_setofColors(Node sc) throws JAXBException {
        try {

            JAXBContext jc = JAXBContext.newInstance(SetofColor.class);
            Unmarshaller u = jc.createUnmarshaller();
            SetofColor element = (SetofColor) u.unmarshal(sc);

            return element.backtoColors();

        } catch (JAXBException e){
            throw new JAXBException(e.getMessage());
        }
    }

    public List<SPlayer> decode_listofSPlayers(Node lp) throws JAXBException {
        try {

            JAXBContext jc = JAXBContext.newInstance(List_SPlayer.class);
            Unmarshaller u = jc.createUnmarshaller();
            List_SPlayer element = (List_SPlayer) u.unmarshal(lp);

            return element.backtoSPlayers();


        } catch (JAXBException e){
            throw new JAXBException(e.getMessage());
        }
    }
    


}
