package tsuro;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement (name = "list")
public class ListofColor {
    List<String> colors;

    public ListofColor() {
    }

    public ListofColor(List<String> colors) {
        this.colors = colors;
    }

    @XmlElement (name = "color")
    public void setListofColor(List<String> list){
        this.colors = list;
    }

    public List<String> getListofColor(){
        return this.colors;
    }

    public List<String> backtoColors(){
        List<String> result = new ArrayList<>();

        for (String str: colors){
            result.add(str);
        }

        return result;
    }
}
