package pl.femax.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.femax.controllers.BorderMainController;

import java.io.*;
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
    private String token="";
    private BorderMainController borderMainController;

    public DataDownloader(){
        this.f = new File("token.txt");
        borderMainController = new BorderMainController();
        setToken();
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
    public String setToken(){
        String[] str = new String[10];
        BufferedReader r;
        int i = 0;
        try {
            r = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = r.readLine()) != null) {
                str[i] = readLine.replaceAll(" ", "");
                i++;
            }
          //  borderMainController.setLogBookTextArea("Tokeny załadowanie poprawnie");
        } catch (FileNotFoundException e) {
           // borderMainController.setLogBookTextArea("Błąd podczas ładowania tokenów");
            //borderMainController.setStyle("-fx-fill: red; -fx-font-size: 25px;");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    }

