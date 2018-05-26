package tsuro;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public abstract class Decoder {

    // helper function to unpack the document
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

    public int decode_n(Node node) throws IllegalArgumentException{
        if (node.getNodeType() == Node.ELEMENT_NODE){
            return Integer.parseInt(node.getTextContent());
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

}
