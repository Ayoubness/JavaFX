package com.ezzariy;

import com.ezzariy.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        var scene = new Scene(loadMainPane());

        stage.setTitle("Gestion de magasin");
        stage.setScene(scene);

        stage.show();
    }

    private Pane loadMainPane() throws IOException {
        var loader = new FXMLLoader();
        Pane mainPane = loader.load(
                getClass().getResourceAsStream(
                        AppNavigator.MAIN
                )
        );
        MainController mainController = loader.getController();
        AppNavigator.setMainController(mainController);
        AppNavigator.loadVista(AppNavigator.PRODUCTS);
        return mainPane;
    }
}