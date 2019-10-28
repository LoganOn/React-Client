package pl.femax.model;

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
