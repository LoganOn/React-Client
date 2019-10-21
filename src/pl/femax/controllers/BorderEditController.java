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

public class BorderEditController {
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
    private String str[] = new String[10];

    @FXML
    public void initialize() throws IOException {
        File f = new File("token.txt");
        BufferedReader r;
        int i= 0;
        try {
            r = new BufferedReader(new FileReader(f));
            String readLine ="";
            while((readLine = r.readLine()) != null){
                str[i] = readLine.replaceAll(" ","");
                i++;
            }
            logText.setText("Tokeny załadowanie poprawnie");
        } catch (FileNotFoundException e) {
            logText.setText("Błąd podczas ładowania tokenów");
            logText.setStyle("-fx-fill: red; -fx-font-size: 25px;");
        }
        producentTableColumn.setCellValueFactory(new PropertyValueFactory<>("producent"));
        tokenTableColumn.setCellValueFactory(new PropertyValueFactory<>("token"));
        tokenTableView.setItems(getProducent(str));
        tokenTableView.getColumns().addAll(producentTableColumn, tokenTableColumn);
    }

    @FXML
    public void updateToken() {
        producentTextField.setText(producentTextField.getText().replaceAll(" ", ""));
        tokenTextField.setText(tokenTextField.getText().replaceAll(" ", ""));
        if (producentTextField.getText().isEmpty()|| tokenTextField.getText().isEmpty()) {
            if (producentTextField.getText().isEmpty() && tokenTextField.getText().isEmpty()) {
                logText.setText("Podaj Producenta i Token");
                logText.setStyle("-fx-fill: red; -fx-font-size: 25px;");
            }
            else if (tokenTextField.getText().isEmpty()) {
                logText.setText("Podaj Token");
                logText.setStyle("-fx-fill: red; -fx-font-size: 25px;");
            }
            else if(producentTextField.getText().isEmpty()) {
                logText.setText("Podaj Producenta");
                logText.setStyle("-fx-fill: red; -fx-font-size: 25px;");
            }
        }
        else if(producentTextField.getLength() != 0 && tokenTextField.getLength() != 0) {
            for(Producent x : products){
                if (producentTextField.getText().equals(x.getProducent())) {
                    x.setToken(tokenTextField.getText());
                    logText.setText("Token zaktualizowany poprawnie");
                    logText.setStyle("-fx-fill: green; -fx-font-size: 25px;");
                    tokenTableView.refresh();
                    writeFile(products);
                    break;
                } else if (producentTextField.getText() != x.getProducent()) {
                    logText.setText("Niepoprawny producent");
                    logText.setStyle("-fx-fill: red; -fx-font-size: 25px;");
                }
            }
        }
    }

    public ObservableList<Producent> getProducent(String[] str) {
        products = FXCollections.observableArrayList();
        products.add(new Producent(str[0], str[1]));
        products.add(new Producent(str[2], str[3]));
        return products;
    }
    public void writeFile(ObservableList<Producent> products){
        try {
            BufferedWriter bW = new BufferedWriter(new FileWriter("token.txt"));
            products.forEach(x -> {
                try {
                    bW.write(x.getProducent());
                    bW.newLine();
                    bW.write(x.getToken());
                    bW.newLine();
                    System.out.println(products);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
