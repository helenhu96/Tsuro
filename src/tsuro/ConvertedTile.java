package tsuro;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.*;

@XmlRootElement (name = "tile")
public class ConvertedTile {

    List<Node> connect;

    public ConvertedTile() {}

    public ConvertedTile (Tile tile) {
        setConnect(new ArrayList<>());
        List<int[]> path = tile.getPath();
        for (int[] pair: path) {
            Node node = new Node();
            List<Integer> list = new ArrayList<>();
            list.add(pair[0]);
            list.add(pair[1]);
            node.setNode(list);
            addConnect(node);
        }
    }

    @XmlElement
    public void setConnect(List<Node> list)
    {

        this.connect = list;

    }

    public void addConnect(Node node) {
        this.connect.add(node);
    }

    public List<Node> getConnect()

    {
        return this.connect;
    }

    @XmlType
    public static class Node {
        private List<Integer> nodes;

        public Node() {
            this.nodes = new ArrayList<>();
        }

        @XmlElement(name = "n")
        public List<Integer> getNode() {
            return this.nodes;
        }

        public void setNode(List<Integer> nodes) {
            this.nodes = nodes;
        }

    }
}
