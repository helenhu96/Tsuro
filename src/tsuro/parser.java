package tsuro;

import org.xml.sax.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import static org.junit.Assert.*;

public class parser {

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


    public static void main(String argv[]) throws Exception {

        Document doc = getDocument("./src/tsuro/tile.xml");

        NodeList connectList = doc.getElementsByTagName("connect");

        if (connectList.getLength() != 4) {
            System.err.println("Wrong tile xml format!");
        }

        int[] input = new int[8];

        int count = 0;

        for (int i = 0; i < 4; i++){
            Node connect = connectList.item(i);
            if (connect.getNodeType() == Node.ELEMENT_NODE){
                Element con = (Element) connect;
                NodeList nList = con.getElementsByTagName("n");
                if (nList.getLength() != 2){
                    System.err.println("N list not correct!");
                }
                for (int j = 0; j < 2; j++){
                    Node n = nList.item(j);
                    if (n.getNodeType() == Node.ELEMENT_NODE){
                        input[count] = Integer.parseInt(n.getTextContent());
                        count++;
                    }
                }

        }
        }

        Tile my_tile = new Tile(input);
        assertTrue(my_tile.sameTile(new Tile(new int[]{0, 5, 1, 3, 2, 6, 4, 7})));
    }

}