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

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class BorderMainController {
    private ObservableList producentList = FXCollections.observableArrayList("Cersanit", "Grohe");
    private File selectedFile;
    private List<Input> inputList = new ArrayList<>();
    private final String headerPattern = "product_code;producer_code;name";
    private DataDownloader dataDownloader;
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

    public BorderMainController() {
    }

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
            logText.setText("Znaleziono: " + String.valueOf(inputList.size()) + " produktów do zaktualizowania");
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
        dataDownloader.setToken(getChoice());
        String id = dataDownloader.downloadID("39538000");
        writeImages(id);

//        logBookTextArea.appendText(logs);
//        dataDownloader.setToken();
//        System.out.println(logs);
//        logBookTextArea.appendText(logs);
        //setLogBookTextArea(logs);
    }
    public String getChoice(){
        return producentChoiceBox.getSelectionModel().getSelectedItem().toString();
    }

    public String[] writeImages(String id) {
        try {
            String str = dataDownloader.downloadData(id);
            String strGood = str.substring(1, str.length() - 1);
            String[] images = strGood.split(",");
            writer = new PrintWriter("test.txt", "UTF-8");
            for (String a : images)
                writer.print(a.trim() + ";");
            writer.close();
            return images;
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

//    public static void setLogBookTextArea(String logs) {
//        logBookTextArea.appendText(logs);
//    }
//    public void setLogBookTextArea(String logs) {
//        this.logBookTextArea.setText(logs);
//    }
}
