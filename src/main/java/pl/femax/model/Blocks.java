package pl.femax.model;

import java.util.List;

public class Blocks {
    private String name;
    private List<Attributes> attributes;

    public Blocks(){
    }

    @Override
    public String toString(){
        return name + attributes;
    }

}
