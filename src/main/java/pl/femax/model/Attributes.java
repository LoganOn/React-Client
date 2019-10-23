package pl.femax.model;

import java.util.List;

public class Attributes {
    private String name;
   // private List<Value>value;
    private String value;
    private int type;

    public Attributes(){

    }

    public String toString() {
        if(type == 7)
            return name + " : " + "siema";
        return name + " : " + value;
    }
}
