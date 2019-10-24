package pl.femax.model;

import java.util.List;

public class DataObject {
   // private String id;
    private List<Images> images;
    private List<Blocks> blocks;

    public DataObject(){
    }

    @Override
    public String toString(){
        return ""+images;
    }
}
