package tsuro.xmlmodel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.*;

@XmlType
public class Number {
    private List<Integer> numbers;

    public Number() {
        this.numbers = new ArrayList<>();
    }

    public Number(List<Integer> list) {
        this.numbers = list;
    }

    @XmlElement(name = "n")
    public List<Integer> getNumber() {
        return this.numbers;
    }

    public void setNumber(List<Integer> num) {
        this.numbers = num;
    }

    public void addNumber(int n) {
        this.numbers.add(n);
    }

}
