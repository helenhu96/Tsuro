package tsuro.xmlmodel;

import tsuro.game.SPlayer;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MaybeListofSPlayer {
//    @XmlElements(value = {
//            @XmlElement(name="false",
//                    type=SPlayerwithDragon.class),
//            @XmlElement(type=ListofSPlayer.class),
//    })
//    XmlMaybe maybeSPlayers;
//
//    public MaybeListofSPlayer() {};
//
//    public MaybeListofSPlayer(List<SPlayer> winners) {
//        if (winners.size() == 0) {
//            setMaybeSPlayers(new NoWinner());
//        } else {
//            setMaybeSPlayers(new ListofSPlayer());
//        }
//    }
//
//
//    public void setMaybeSPlayers(XmlMaybe maybe) {
//        this.maybeSPlayers = maybe;
//    }
//
//    public XmlMaybe getMaybeSPlayers() {
//        return this.maybeSPlayers;
//    }

}
