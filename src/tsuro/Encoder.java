package tsuro;
import java.io.StringWriter;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class Encoder {

    public static String encodeTile(Tile t) throws Exception
    {
        ConvertedTile ctile = new ConvertedTile(t);
        JAXBContext jaxbContext = JAXBContext.newInstance(ConvertedTile.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(ctile, sw);
        String xmlString = sw.toString();
//        System.out.println(xmlString);
        return xmlString;
    }

    public static String encodeBoard(Board board) throws Exception
    {
        ConvertedBoard cboard = new ConvertedBoard(board);

        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(ConvertedBoard.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        jaxbMarshaller.marshal(cboard, sw);
        String xmlString = sw.toString();
//        System.out.println(xmlString);
        return xmlString;
    }

    public static void encodePawnLoc(PlayerPosition position) throws Exception
    {
        PawnLocation pawn = new PawnLocation(position);
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(PawnLocation.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(pawn, sw);

        String xmlString = sw.toString();
        System.out.println(xmlString);
    }


    public static void encodePawns(Pawns pawns) throws Exception
    {
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(Pawns.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.marshal(pawns, sw);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(pawns, sw);
        String xmlString = sw.toString();
        System.out.println(xmlString);
    }

    public static void encodeListofTile(ListofTile tiles) throws Exception
    {
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(ListofTile.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(tiles, sw);
        String xmlString = sw.toString();
        System.out.println(xmlString);
    }

    public static void encodeSetofTile(SetofTile tiles) throws Exception
    {
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(SetofTile.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(tiles, sw);
        String xmlString = sw.toString();
        System.out.println(xmlString);
    }

    public static void encodeSPlayers(List<SPlayer> splayers) throws Exception
    {
        List_SPlayer lsplayer = new List_SPlayer(splayers);
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(List_SPlayer.class, XmlSplayer.class, SPlayer_dragon.class, SPlayer_nodragon.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(lsplayer, sw);
        String xmlString = sw.toString();
        System.out.println(xmlString);
    }
}
