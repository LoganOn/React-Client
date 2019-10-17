package pl.femax.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class Main extends Application {
    static BorderPane root;
    private static int width = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            root = FXMLLoader.load(getClass().getResource("../resources/fxml/BorderMain.fxml"));
            primaryStage.setTitle("React Client");
            primaryStage.setScene(new Scene(root, width, width));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {

        }
    }


    public static void main(String[] args) {
        launch(args);
        }
    }


