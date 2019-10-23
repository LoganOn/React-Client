package pl.femax.model;

public class Value {
    private String id;
    private String url;

    public Value(){

    }
    @Override
    public String toString(){
        return id + url;
    }
}
