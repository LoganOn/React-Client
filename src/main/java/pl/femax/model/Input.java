package pl.femax.model;

public class Input {
    private String product_code;
    private String producer_code;
    private String name;

    public Input(String product_code, String producer_code, String name) {
        this.product_code = product_code;
        this.producer_code = producer_code;
        this.name = name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public String getProducer_code() {
        return producer_code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return product_code + " : " + producer_code + " : " + name;
    }
}
