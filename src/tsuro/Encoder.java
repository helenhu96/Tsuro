package tsuro;
import java.io.StringWriter;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class Encoder {

    public static void encodeTile(Tile t) throws Exception
    {
        ConvertedTile ctile = new ConvertedTile(t);
        JAXBContext jaxbContext = JAXBContext.newInstance(ConvertedTile.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(ctile, sw);
        String xmlString = sw.toString();
        System.out.println(xmlString);
    }


}
