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





//    public Board decode_board(Node b) throws JAXBException {
//        try {
//            JAXBContext jc = JAXBContext.newInstance(ConvertedBoard.class);
//            Unmarshaller u = jc.createUnmarshaller();
//            Object element = u.unmarshal(b);
//
//            Board result = ((ConvertedBoard)element).backtoBoard();
//            return result;
//
//
//        } catch (JAXBException e){
//            throw new JAXBException(e.getMessage());
//        }
//    }




    public static void main(String argv[]) throws Exception {



        Document doc = getDocument("./src/tsuro/board.xml");

        //NodeList tileList = doc.getElementsByTagName("board");

        /*
        Decoder dec = new Decoder();
        Board oboard = dec.decode_board(tileList.item(0));
        */

        Element el = doc.getDocumentElement();
        String hehe = el.getTagName();





    }
}
