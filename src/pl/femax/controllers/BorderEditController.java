package pl.femax.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class BorderEditController {
    ObservableList tokenList = FXCollections.observableArrayList("Cersanit", "asdasdasfafgas");
    @FXML
    private TableView tokenTableView;
    @FXML
    private Text logText;
    @FXML
    private TextField producentTextField;
    @FXML
    private TextField tokenTextField;
    @FXML
    public void initialize(){
        logText.setText("Wszystko ok");
        tokenTableView.setItems(tokenList);
    }
    @FXML
    public void updateToken() {

    }
}
