package tsuro.xmlmodel;
import tsuro.game.SPlayer;

import java.util.*;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement (name = "list")
public class ListofSPlayer extends XmlMaybe{
    @XmlElements(value = {
            @XmlElement(name="splayer-dragon",
                    type=SPlayerwithDragon.class),
            @XmlElement(name="splayer-nodragon",
                    type=SPlayerNoDragon.class),
    })
    List<XmlSplayer> splayers;
    public ListofSPlayer() {}

    public ListofSPlayer(List<SPlayer> splayers) {
        setSplayer(new ArrayList<>());
        for (SPlayer splayer: splayers) {
            addSplayer(splayer);
        }
    }


    public List<XmlSplayer> getSplayers() {
        return this.splayers;
    }

    public void setSplayer(List<XmlSplayer> splayers) {
        this.splayers = splayers;
    }


    public void addSplayer(SPlayer splayer) {
        if (splayer.doIHaveDragon()) {
            this.splayers.add(new SPlayerwithDragon(splayer));
        } else {
            this.splayers.add(new SPlayerNoDragon(splayer));
        }
    }

    public List<SPlayer> backtoSPlayers(){
        List<SPlayer> list = new ArrayList<>();

        for (XmlSplayer player: splayers){
            if (player instanceof SPlayerwithDragon){
                list.add(((SPlayerwithDragon) player).backtoSPlayer());
            } else {
                list.add(((SPlayerNoDragon)player).backtoSPlayer());
            }
        }
        return list;
    }
}
