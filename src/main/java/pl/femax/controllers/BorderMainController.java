package pl.femax.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.femax.model.DataDownloader;
import pl.femax.model.Input;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BorderMainController {

    private final String headerPattern = "product_code;producer_code;name";
    private final String HEADER = "product_code;active;producer;producer_code;name;category;rank;rank_votes;\"images 1\";\"images 2\";\"images 3\";\"images 4\";\"images 5\";\"images 6\";\"images 7\";\"images 8\";" +
            "\"images 9\";\"images 10\";\"images 11\";\"images 12\";\"images 13\";\"images 14\";\"images 15\";\"images 16\";\"images 17\";\"images 18\";\"images 19\";\"images 20\";\"images 21\";\"images 22\"" +
            ";\"images 23\";\"images 24\";\"images 25\";\"images 26\";\"images 27\";\"images 28\";\"images 29\";\"images 30\";\"images 31\";\"images 32\";\"images 33\";\"images 34\";\"images 35\";\"images 36\"" +
            ";\"images 37\";\"images 38\";\"images 39\";\"images 40\";\"images 41\";\"images 42\";\"images 43\";\"images 44\";\"images 45\";\"images 46\";\"images 47\";\"images 48\";\"images 49\";\"images 50\"";
    private DataDownloader dataDownloader;
    private ObservableList producentList = FXCollections.observableArrayList("Grohe");
    private File selectedFile;
    private List<Input> inputList = new ArrayList<>();
    private PrintWriter writer;
    private String str[] = new String[10];
    private String id;
    private int correct = 0;
    private int incorrect = 0;
    @FXML
    private ChoiceBox producentChoiceBox;
    @FXML
    private Text producentText;
    @FXML
    private Text loadFileText;
    @FXML
    private Text logText;
    @FXML
    private Button gButton;
    @FXML
    private TextArea logBookTextArea;


    @FXML
    private void initialize() {
        producentChoiceBox.setItems(producentList);
        producentChoiceBox.setValue("Grohe");
        dataDownloader = new DataDownloader();
        gButton.setDisable(true);
        try {
            writer = new PrintWriter("test.csv", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
            logText.setText("Znaleziono: " + inputList.size() + " produktów do zaktualizowania");
            gButton.setDisable(false);
        } else
            loadFileText.setText("Brak pliku do wczytania");
    }

    @FXML
    public void goQuit() {
        Platform.exit();
    }

    @FXML
    public void generateNewFile() {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        dataDownloader.setToken(getChoice());
        logBookTextArea.setStyle("-fx-fill: black;-fx-font-size: 12px;");
        ex.execute(() -> {
            int i = 1;
            writer.println(HEADER);
            for (Input x : inputList) {
                id = dataDownloader.downloadID(x.getProducer_code());
                logBookTextArea.appendText("Czytam " + i + " produkt");
                logBookTextArea.appendText("\n");
                if (id.length() == 0) {
                    logBookTextArea.appendText("Nie można dodac produktu");
                    logBookTextArea.appendText("\n");
                    i++;
                    incorrect++;
                    continue;
                }
                writer.print(x.getProduct_code() + ';');
                writer.print("0;");
                writer.print(producentChoiceBox.getSelectionModel().getSelectedItem().toString().toUpperCase() + ";");
                writer.print(x.getProducer_code() + ";");
                writer.print(x.getName() + ";");
                writer.print("Import - bez kategorii;");
                writer.print("4.6;");
                writer.print("20;");
                writeImages(dataDownloader.downloadID(x.getProducer_code()));
                logBookTextArea.appendText("Produkt dodany prawidłowo");
                logBookTextArea.appendText("\n");
                i++;
                correct++;
            }
            logBookTextArea.appendText("Prawidłowo pobrano informacje dla: " + correct + " produktów");
            logBookTextArea.appendText("\n");
            logBookTextArea.appendText("Nie można pobrać informacje dla: " + incorrect + " produktów");
            writer.close();
            gButton.setDisable(true);
        });
    }

    public String getChoice() {
        return producentChoiceBox.getSelectionModel().getSelectedItem().toString();
    }

    public String[] writeImages(String id) {
        String str = dataDownloader.downloadData(id);
        String strGood = str.substring(1, str.length() - 1);
        String[] images = strGood.split(",");

        for (String a : images)
            writer.print(a.trim() + ";");
        for (int i = 0; i < (49 - images.length); i++)
            writer.print(';');
        writer.println();
        return images;
    }


    public List<Input> readInput(List<Input> inputs) {
        if (inputs.size() != 0)
            inputs.clear();
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
                logBookTextArea.setText("Niewłaściwe dane wejściowe");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
