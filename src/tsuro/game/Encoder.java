package tsuro.game;
import tsuro.xmlmodel.*;

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
        return xmlString;
    }

    public static String encodeBoard(Board board) throws Exception
    {
        ConvertedBoard cboard = new ConvertedBoard(board);

        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(ConvertedBoard.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        jaxbMarshaller.marshal(cboard, sw);
        String xmlString = sw.toString();
        xmlString = xmlString.replace("<map/>", "<map></map>");
        return xmlString;
    }

    public static String encodePawnLoc(PlayerPosition position) throws Exception
    {
        PawnLocation pawn = new PawnLocation(position);
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(PawnLocation.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        jaxbMarshaller.marshal(pawn, sw);
        String xmlString = sw.toString();
        xmlString = xmlString.replace("<v/>", "<v></v>");
        xmlString = xmlString.replace("<h/>", "<h></h>");
        return xmlString;
    }


    public static String encodePawns(Pawns pawns) throws Exception
    {
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(Pawns.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        jaxbMarshaller.marshal(pawns, sw);
//        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(pawns, sw);
        String xmlString = sw.toString();
//        System.out.println(xmlString);
        return xmlString;
    }

    public static String encodeListofTile(List<Tile> alltiles) throws Exception
    {
        ListofTile tiles = new ListofTile(alltiles);
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(ListofTile.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(tiles, sw);
        String xmlString = sw.toString();
        xmlString = xmlString.replace("<list/>", "<list></list>");
        return xmlString;
    }

    public static String encodeSetofTile(Set<Tile> t) throws Exception
    {
        SetofTile tiles = new SetofTile(t);
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(SetofTile.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(tiles, sw);
        String xmlString = sw.toString();
        return xmlString;
    }

    public static String encodeColor(String color) {
        return "<color>" + color + "</color>";
    }

    public static String encodeSPlayers(List<SPlayer> splayers) throws Exception
    {
        ListofSPlayer lsplayer = new ListofSPlayer(splayers);
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(ListofSPlayer.class, XmlSplayer.class, SPlayerwithDragon.class, SPlayerNoDragon.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        jaxbMarshaller.marshal(lsplayer, sw);
        String xmlString = sw.toString();
        xmlString = xmlString.replace("<list/>", "<list></list>");
        return xmlString;
    }

//    public static void encodeMayberSPlayers(List<SPlayer> splayers) throws Exception
//    {
//        MaybeListofSPlayer lsplayer = new MaybeListofSPlayer(splayers);
//        StringWriter sw = new StringWriter();
//        JAXBContext jaxbContext = JAXBContext.newInstance(MaybeListofSPlayer.class);
//        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        jaxbMarshaller.marshal(lsplayer, sw);
//        String xmlString = sw.toString();
//        System.out.println(xmlString);
//    }

    public static String encodeVoid() {
        return "<void></void>";
    }

    public static String encodeMaybeListofSPlayers(List<SPlayer> splayers) throws Exception{
        if (splayers.size() == 0) {
            return "<false></false>";
        }
        return encodeSPlayers(splayers);
    }


    public static String encodeSetofColor(Set<String> colors) throws Exception
    {
        SetofColor colorset = new SetofColor(colors);
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(SetofColor.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        jaxbMarshaller.marshal(colorset, sw);
        String xmlString = sw.toString();
        return xmlString;
    }

    public static String encodeListofColor(List<String> colors) throws Exception
    {
        ListofColor colorlist = new ListofColor(colors);
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(ListofColor.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        jaxbMarshaller.marshal(colorlist, sw);
        String xmlString = sw.toString();
        return xmlString;
    }

    public static String encodeNum(int n)
    {
        return "<n>" + new Integer(n).toString() + "</n>";
    }

    public static String encodeEndGame(Board board, Set<String> winnerColors) throws Exception{
        return "<end-game>" + encodeBoard(board) + encodeSetofColor(winnerColors) + "</end-game>";
    }

    public static String encodePlayTurn(Board board, Set<Tile> tiles, int numTiles) throws Exception {
        return "<play-turn>" + encodeBoard(board) + encodeSetofTile(tiles) + encodeNum(numTiles) + "</play-turn>";
    }

    public static String encodePlacePawn(Board board) throws Exception{
        return "<place-pawn>" + encodeBoard(board) + "</place-pawn>";
    }

    public static String encodeInitialize(String color, List<String> colors) throws Exception
    {
        return "<initialize>" + encodeColor(color) + encodeListofColor(colors) + "</initialize>";
    }

    public static String encodePlayerName(String name) {
        return "<player-name>" + name + "</player-name>";
    }

    public static String encodeGetName() {
        return "<get-name> </get-name>";
    }
}
