package pl.femax.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.femax.controllers.BorderMainController;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DataDownloader {
    private File f;
    private String data;
    private Reader reader;
    private final String urlSearch = "https://cl.estorecontent.com/api/v2/product-list/?token=";
    private final String urlID = " https://cl.estorecontent.com/api/v2/product-detail/";
    private String token = "";
    private BorderMainController borderMainController;
    private List<Producent> producents;

    public DataDownloader() {
        this.f = new File("token.txt");
    }

    public String downloadData(String id) throws MalformedURLException {
        String idUrl = (urlID + id + "/?token=" + token);
        System.out.println(idUrl);
        try {
            reader = new InputStreamReader(new URL(idUrl).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        try {
             DataObject dataObject = gson.fromJson(reader, DataObject.class);
            EAN ean = gson.fromJson(reader, EAN.class);
              return dataObject.toString();
        } catch (IllegalStateException e) {
            System.out.println(e);
        }
        return idUrl;
    }

    public String setToken(String producent) {
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
            for (i = 0; i < 4; i++) {
                if (0 == (producent.compareTo(str[i]))) {
                    token = str[i + 1];
                    break;
                }
            }
            System.out.println("Token to: " + token);
            //borderMainController.setLogs("Tokeny załadowanie poprawnie");
            // borderMainController.getLogBookTextArea().setText("siema");
            // borderMainController.updatedTextArea();
        } catch (FileNotFoundException e) {
            //borderMainController.setLogs("Tokenów nie załądowano");
            //borderMainController.setStyle("-fx-fill: red; -fx-font-size: 25px;");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String downloadID(String code) {
        String idUrl = (urlSearch + token + "&ean=" + code);
        try {
            reader = new InputStreamReader(new URL(idUrl).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        try {
            EAN ean = gson.fromJson(reader, EAN.class);
            System.out.println(ean.toString());
            String str = ean.toString().substring(1,ean.toString().length()-1);
            System.out.println(str);
            return str;
        } catch (IllegalStateException e) {
            System.out.println(e);
        }
        return null;
    }
}

