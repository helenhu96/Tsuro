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



}