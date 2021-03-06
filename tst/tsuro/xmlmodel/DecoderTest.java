package tsuro.xmlmodel;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import tsuro.game.*;


import static org.junit.Assert.assertTrue;
public class DecoderTest {
    @Test
    public void decodeTileTest() throws Exception {

        Document doc = Decoder.getDocument("<tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>3</n></connect><connect><n>4</n><n>5</n></connect><connect><n>6</n><n>7</n></connect></tile>");

        Tile result = Decoder.decodeTile(doc.getElementsByTagName("tile").item(0));
        Tile expected = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});

        assertTrue(result.sameTile(expected));

        doc = Decoder.getDocument("<tile><connect><n>0</n><n>5</n></connect><connect><n>1</n><n>7</n></connect><connect><n>2</n><n>3</n></connect><connect><n>4</n><n>6</n></connect></tile>");
        Tile result2 = Decoder.decodeTile(doc.getElementsByTagName("tile").item(0));
        Tile expected2 = new Tile(new int[]{0, 5, 1, 7, 2, 3, 4, 6});

        assertTrue(result2.sameTile(expected2));

    }


    @Test
    public void decodePawnLocTest() throws Exception {
        Board b = new Board();
        Tile t = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        b.placeTile(t, 1, 1);

        Document doc = Decoder.getDocument("<test><pawn-loc><h></h><n>2</n><n>3</n></pawn-loc><pawn-loc><v></v><n>5</n><n>8</n></pawn-loc><pawn-loc><h></h><n>6</n><n>0</n></pawn-loc><pawn-loc><h></h><n>1</n><n>9</n></pawn-loc><pawn-loc><v></v><n>0</n><n>6</n></pawn-loc></test>");
        PlayerPosition result = Decoder.decodePawnLoc(doc.getElementsByTagName("pawn-loc").item(0), b);
        PlayerPosition expected = new PlayerPosition(2, 1, 1);

        assertTrue(result.equals(expected));


        b.placeTile(new Tile(new int[]{0, 6, 1, 2, 3, 4, 5, 7}), 4, 5);
        PlayerPosition result2 = Decoder.decodePawnLoc(doc.getElementsByTagName("pawn-loc").item(1), b);
        PlayerPosition expected2 = new PlayerPosition(4, 4, 2);

        assertTrue(result2.equals(expected2));


        b.placeTile(new Tile(new int[]{0, 2, 1, 5, 3, 7, 4, 6}), 5, 0);
        PlayerPosition result3 = Decoder.decodePawnLoc(doc.getElementsByTagName("pawn-loc").item(2), b);
        PlayerPosition expected3 = new PlayerPosition(5, 0, 5);

        assertTrue(result3.equals(expected3));


        b.placeTile(new Tile(new int[]{0, 7, 1, 2, 3, 4, 5, 6}), 0, 4);
        PlayerPosition result4 = Decoder.decodePawnLoc(doc.getElementsByTagName("pawn-loc").item(3), b);
        PlayerPosition expected4 = new PlayerPosition(1, 4, 1);

        assertTrue(result4.equals(expected4));


        b.placeTile(new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7}), 3, 0);
        PlayerPosition result5 = Decoder.decodePawnLoc(doc.getElementsByTagName("pawn-loc").item(4), b);
        PlayerPosition expected5 = new PlayerPosition(3, 0, 7);

        assertTrue(result5.equals(expected5));
    }


    @Test
    public void decodeBoardTest() throws Exception {
        Document doc = Decoder.getDocument("<board><map><ent><xy><x>2</x><y>2</y></xy><tile><connect><n>0</n><n>4</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>6</n></connect><connect><n>3</n><n>7</n></connect></tile></ent><ent><xy><x>0</x><y>0</y></xy><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>3</n></connect><connect><n>4</n><n>5</n></connect><connect><n>6</n><n>7</n></connect></tile></ent><ent><xy><x>1</x><y>1</y></xy><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>4</n></connect><connect><n>3</n><n>7</n></connect><connect><n>5</n><n>6</n></connect></tile></ent>" +
                "<ent><xy><x>2</x><y>0</y></xy><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>6</n></connect><connect><n>3</n><n>7</n></connect><connect><n>4</n><n>5</n></connect></tile></ent><ent><xy><x>0</x><y>2</y></xy><tile><connect><n>0</n><n>6</n></connect><connect><n>1</n><n>5</n></connect><connect><n>2</n><n>4</n></connect><connect><n>3</n><n>7</n></connect></tile></ent><ent><xy><x>2</x><y>1</y></xy><tile><connect><n>0</n><n>2</n></connect><connect><n>1</n><n>6</n></connect><connect><n>3</n><n>7</n></connect><connect><n>4</n><n>5</n></connect></tile></ent><ent><xy><x>3</x><y>0</y></xy><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>4</n></connect><connect><n>5</n><n>6</n></connect></tile></ent>" +
                "<ent><xy><x>1</x><y>0</y></xy><tile><connect><n>0</n><n>5</n></connect><connect><n>1</n><n>4</n></connect><connect><n>2</n><n>7</n></connect><connect><n>3</n><n>6</n></connect></tile></ent><ent><xy><x>0</x><y>1</y></xy><tile><connect><n>0</n><n>1</n></connect><connect><n>2</n><n>4</n></connect><connect><n>3</n><n>6</n></connect><connect><n>5</n><n>7</n></connect></tile></ent><ent><xy><x>1</x><y>2</y></xy><tile><connect><n>0</n><n>4</n>" +
                "</connect><connect><n>1</n><n>7</n></connect><connect><n>2</n><n>3</n></connect><connect><n>5</n><n>6</n></connect></tile></ent></map>" + "<map><ent><color>blue</color><pawn-loc><v></v><n>0</n><n>2</n></pawn-loc></ent><ent><color>orange</color><pawn-loc><h></h><n>3</n><n>3</n></pawn-loc></ent><ent><color>green</color><pawn-loc><h></h><n>3</n><n>2</n></pawn-loc></ent><ent><color>red</color><pawn-loc><h></h><n>3</n><n>1</n></pawn-loc></ent><ent><color>sienna</color><pawn-loc><v></v><n>3</n><n>3</n></pawn-loc></ent></map></board>");
        Node boardNode = doc.getElementsByTagName("board").item(0);
        Board result = Decoder.decodeBoard(boardNode);


        Tile t1 = new Tile(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        Tile t2 = new Tile(new int[]{0, 5, 1, 4, 2, 7, 3, 6});
        Tile t3 = new Tile(new int[]{0, 1, 2, 6, 3, 7, 4, 5});
        Tile t4 = new Tile(new int[]{0, 1, 2, 7, 3, 4, 5, 6});
        Tile t5 = new Tile(new int[]{0, 1, 2, 4, 3, 6, 5, 7});
        Tile t6 = new Tile(new int[]{0, 2, 1, 4, 3, 7, 5, 6});
        Tile t7 = new Tile(new int[]{0, 2, 1, 6, 3, 7, 4, 5});
        Tile t8 = new Tile(new int[]{0, 6, 1, 5, 2, 4, 3, 7});
        Tile t9 = new Tile(new int[]{0, 4, 1, 7, 2, 3, 5, 6});
        Tile t10 = new Tile(new int[]{0, 4, 1, 5, 2, 6, 3, 7});

        Board expected = new Board();
        expected.placeTile(t1, 0, 0);
        expected.placeTile(t2, 0, 1);
        expected.placeTile(t3, 0, 2);
        expected.placeTile(t4, 0, 3);
        expected.placeTile(t5, 1, 0);
        expected.placeTile(t6, 1, 1);
        expected.placeTile(t7, 1, 2);
        expected.placeTile(t8, 2, 0);
        expected.placeTile(t9, 2, 1);
        expected.placeTile(t10, 2, 2);

        assertTrue(result.getTile(0, 0).sameTile(expected.getTile(0, 0)));
        assertTrue(result.getTile(0, 1).sameTile(expected.getTile(0, 1)));
        assertTrue(result.getTile(0, 2).sameTile(expected.getTile(0, 2)));
        assertTrue(result.getTile(0, 3).sameTile(expected.getTile(0, 3)));
        assertTrue(result.getTile(1, 0).sameTile(expected.getTile(1, 0)));
        assertTrue(result.getTile(1, 1).sameTile(expected.getTile(1, 1)));
        assertTrue(result.getTile(1, 2).sameTile(expected.getTile(1, 2)));
        assertTrue(result.getTile(2, 0).sameTile(expected.getTile(2, 0)));
        assertTrue(result.getTile(2, 1).sameTile(expected.getTile(2, 1)));
        assertTrue(result.getTile(2, 2).sameTile(expected.getTile(2, 2)));

        PlayerPosition expected_blue = new PlayerPosition(1, 0, 7);
        PlayerPosition expected_red = new PlayerPosition(3, 0, 1);
        PlayerPosition expected_green = new PlayerPosition(3, 1, 0);
        PlayerPosition expected_orange = new PlayerPosition(3, 1, 1);
        PlayerPosition expected_sienna = new PlayerPosition(1, 3, 6);


        assertTrue(result.getPlayerPositionByColor("blue").equals(expected_blue));
        assertTrue(result.getPlayerPositionByColor("red").equals(expected_red));
        assertTrue(result.getPlayerPositionByColor("green").equals(expected_green));
        assertTrue(result.getPlayerPositionByColor("orange").equals(expected_orange));
        assertTrue(result.getPlayerPositionByColor("sienna").equals(expected_sienna));

    }


}
