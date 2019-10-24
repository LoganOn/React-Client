package pl.femax.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.femax.model.DataDownloader;
import pl.femax.model.Input;
import pl.femax.model.Producent;

import javax.xml.transform.sax.SAXSource;
import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BorderMainController {
    private ObservableList producentList = FXCollections.observableArrayList("Cersanit", "Grohe");
    private File selectedFile;
    private List<Input> inputList = new ArrayList<>();
    private final String headerPattern = "product_code;producer_code;name";
    private DataDownloader dataDownloader ;
    @FXML
    private ChoiceBox producentChoiceBox;
    @FXML
    private Text producentText;
    @FXML
    private Text loadFileText;
    @FXML
    private Text logText;

    @FXML
    private TextArea logBookTextArea;

    private PrintWriter writer;

    private String str[] = new String[10];

    @FXML
    private void initialize() {
        producentChoiceBox.setItems(producentList);
        dataDownloader = new DataDownloader();
    }

    @FXML
    public void editToken() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/BorderEdit.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1, 800, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void chooseFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Wczytaj plik");
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null)
            loadFileText.setText(selectedFile.getName());
        else
            loadFileText.setText("Brak pliku do wczytania");
    }

    @FXML
    public void uploadFile() {
        if (selectedFile != null) {
            readInput(inputList);
            logText.setText("Znaleziono: " + String.valueOf(inputList.size()) +" produktów do zaktualizowania");
            inputList.forEach(System.out::println);
            inputList.toString();
        } else
            loadFileText.setText("Brak pliku do wczytania");
    }

    @FXML
    public void goQuit() {
        Platform.exit();
    }

    @FXML
    public void generateNewFile() {
        writeImages();
    }

    public String[] writeImages() {
       // DataDownloader dataDownloader = new DataDownloader();
        try {
            String str = dataDownloader.downloadData();
            String strGood = str.substring(1, str.length() - 1);
            String[] str1 = strGood.split(",");
            writer = new PrintWriter("test.txt", "UTF-8");
            for (String a : str1)
                writer.print(a.trim() + ";");
            writer.close();
            return str1;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Input> readInput(List<Input> inputs) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(selectedFile));
            String st = br.readLine();
            String st1 = st.substring(1, st.length());
            if (st1.equals(headerPattern)) {
                while ((st = br.readLine()) != null) {
                    String[] str = st.split(";");
                    inputs.add(new Input(str[0], str[1], str[2]));
                }
                return inputs;
            } else {
                logBookTextArea.setText("Niewłaściwy plik");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
