package pl.femax.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.print.DocFlavor;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class DataDownloader {
    private File f;
    private String data;
    private Reader reader;
    //https://cl.estorecontent.com/api/v2/product-list + /?token= + token + "&ean=" + ean(lub kod producenta)
    private final String urlSearch = "https://cl.estorecontent.com/api/v2/product-list/?token=";
    // https://cl.estorecontent.com/api/v2/product-detail/+ id + /?token= + token
    private final String urlID = " https://cl.estorecontent.com/api/v2/product-detail/";

    public DataDownloader(){
        this.f = new File("token.txt");
    }
    public String downloadData() throws MalformedURLException {
        String idUrl = (urlID + "20314" + "/?token=5c9c4caef99abd50b5d2456c95400eec896def38");
        try {
            reader = new InputStreamReader(new URL(idUrl).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        try {
            DataObject dataObject = gson.fromJson(reader, DataObject.class);
            return dataObject.toString();
        }
        catch (IllegalStateException e){
            System.out.println(e);
        }
        return idUrl;
    }
}
