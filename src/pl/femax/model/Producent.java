package pl.femax.model;

public class Producent {
    private String producent;
    private String token;

    public Producent(String producent, String token) {
        this.producent = producent;
        this.token = token;
    }

    public String getProducent() {
        return producent;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String toString() {
        String str = (getProducent() + " " + getToken());
        return str;
    }
}
