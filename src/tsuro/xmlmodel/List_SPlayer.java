package tsuro.xmlmodel;
import tsuro.game.SPlayer;

import java.util.*;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement (name = "list")
public class List_SPlayer {
    @XmlElements(value = {
            @XmlElement(name="splayer-dragon",
                    type=SPlayer_dragon.class),
            @XmlElement(name="splayer-nodragon",
                    type=SPlayer_nodragon.class),
    })
    List<XmlSplayer> splayers;
    public List_SPlayer() {}

    public List_SPlayer(List<SPlayer> splayers) {
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
            this.splayers.add(new SPlayer_dragon(splayer));
        } else {
            this.splayers.add(new SPlayer_nodragon(splayer));
        }
    }

    public List<SPlayer> backtoSPlayers(){
        List<SPlayer> list = new ArrayList<>();

        for (XmlSplayer player: splayers){
            if (player instanceof SPlayer_dragon){
                list.add(((SPlayer_dragon) player).backtoSPlayer());
            } else {
                list.add(((SPlayer_nodragon)player).backtoSPlayer());
            }
        }
        return list;
    }
}
