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

import java.io.File;

public class BorderMainController {
    ObservableList producentList = FXCollections.observableArrayList("Cersanit", "Grohe");
    File selectedFile;
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

    @FXML
    private void initialize() {
        producentText.setText("no siema");
        producentChoiceBox.setItems(producentList);
    }

    @FXML
    public void editToken() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/fxml/BorderEdit.fxml"));
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
    }

    @FXML
    public void goQuit() {
        Platform.exit();
    }

    @FXML
    public void generateNewFile() {

    }


}
