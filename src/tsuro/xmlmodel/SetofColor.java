package tsuro.xmlmodel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.HashSet;
import java.util.Set;

@XmlRootElement (name = "set")
public class SetofColor {
    Set<String> colors;

    public SetofColor() {
    }

    public SetofColor(Set<String> colors) {
        this.colors = colors;
    }

    @XmlElement (name = "color")
    public void setSetofColor(Set<String> set){
        this.colors = set;
    }

    public Set<String> getSetofColor(){
        return this.colors;
    }



}


