package tsuro;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.*;

@XmlRootElement (name = "tile")
public class ConvertedTile {

    List<Number> connect;

    public ConvertedTile() {}

    public ConvertedTile (Tile tile) {
        setConnect(new ArrayList<>());
        List<int[]> path = tile.getPath();
        for (int[] pair: path) {
            Number num = new Number();
            List<Integer> list = new ArrayList<>();
            list.add(pair[0]);
            list.add(pair[1]);
            num.setNumber(list);
            addConnect(num);
        }
    }

    @XmlElement
    public void setConnect(List<Number> list)
    {

        this.connect = list;

    }

    public void addConnect(Number n) {
        this.connect.add(n);
    }

    public List<Number> getConnect()

    {
        return this.connect;
    }


}
