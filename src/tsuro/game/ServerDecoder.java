package tsuro.game;

import org.w3c.dom.*;

public class ServerDecoder extends Decoder{

    private String returnType;
    private Object object;
    private Board board;

    public ServerDecoder() {
        returnType = null;
        object = null;
        board = null;
    }





    public void decode(String docString) throws Exception{
        try {
            Document doc = getDocument(docString);
            this.returnType = doc.getDocumentElement().getTagName();


            // TODO: consider how to handle the string case!!

            // handle different cases based on the different function calls
            if (returnType.equals("void")) {


            } else if (returnType.equals("tile")) {
                object = decode_tile(doc.getElementsByTagName("tile").item(0));

            } else if (returnType.equals("pawn-loc")) {


                //TODO: Think about how to handle this situation!!
                object = decodePawnLoc(doc.getElementsByTagName("pawn-loc").item(0), board);

            } else {
                throw new IllegalArgumentException("Not a legal xml file!");
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }



}