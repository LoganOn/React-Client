package pl.femax.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import pl.femax.model.Producent;

import java.io.*;
import java.util.logging.FileHandler;

public class BorderEditController {
    private String tokenCersanit = "1223141251253";
    ObservableList<Producent> products;
    @FXML
    TableView<Producent> tokenTableView;
    @FXML
    TableColumn<Producent, String> producentTableColumn;
    @FXML
    TableColumn<Producent, String> tokenTableColumn;
    @FXML
    private Text logText;
    @FXML
    private TextField producentTextField;
    @FXML
    private TextField tokenTextField;

    @FXML
    public void initialize() {
        producentTableColumn.setCellValueFactory(new PropertyValueFactory<>("producent"));
        tokenTableColumn.setCellValueFactory(new PropertyValueFactory<>("token"));
        tokenTableView.setItems(getProducent());
        tokenTableView.getColumns().addAll(producentTableColumn, tokenTableColumn);
    }

    @FXML
    public void updateToken() {
        producentTextField.setText(producentTextField.getText().replaceAll(" ", ""));
        tokenTextField.setText(tokenTextField.getText().replaceAll(" ", ""));
        if (producentTextField.getLength() == 0 || tokenTextField.getLength() == 0) {
            if (producentTextField.getLength() == 0 && tokenTextField.getLength() == 0)
                logText.setText("Podaj Producenta i Token");
            else if (tokenTextField.getLength() == 0)
                logText.setText("Podaj Token");
            else
                logText.setText("Podaj Producenta");
            logText.setStyle("-fx-fill: red; -fx-font-size: 25px;");
        }
        String str = producentTextField.getText();
        System.out.println("str = " + str);
        String str1 = products.get(0).getProducent();
        System.out.println("str1 = " + str1);
        System.out.println(str.compareTo(str1));
        products.forEach(x -> {
            if (producentTextField.getText().equals(x.getProducent())) {
                x.setToken(tokenTextField.getText());
                logText.setText("Token zaktualizowany poprawnie");
                logText.setStyle("-fx-fill: green; -fx-font-size: 25px;");
                tokenTableView.refresh();
            } else if (producentTextField.getText().equals(x.getProducent())) {
                logText.setText("Niepoprawny producent");
                logText.setStyle("-fx-fill: red; -fx-font-size: 25px;");
            }
        });
        System.out.println("wykonalem sie");
        System.out.println(tokenTableColumn.getCellData(0));
        System.out.println(products.toString());
    }

    public ObservableList<Producent> getProducent() {
        products = FXCollections.observableArrayList();
        products.add(new Producent("Cersanit", "sadasdasdsadasdasd123123"));
        products.add(new Producent("Grohe", "12414sdasd124"));
        return products;
    }

}
