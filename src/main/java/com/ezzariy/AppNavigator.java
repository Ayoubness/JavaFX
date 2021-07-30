package com.ezzariy;

import com.ezzariy.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class AppNavigator {

    public static final String MAIN = "main.fxml";

    public static final String CLIENTS = "clients.fxml";
    public static final String PRODUCTS = "products.fxml";
    public static final String VENTES = "ventes.fxml";
    public static final String LIGNE_COMMANDES = "ligneCommandes.fxml";

    private static MainController mainController;

    public static void setMainController(MainController mainController) {
        AppNavigator.mainController = mainController;
    }

    public static void loadVista(String fxml) {
        try {
            mainController.setVista(
                    FXMLLoader.load(
                            AppNavigator.class.getResource(
                                    fxml
                            )
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage loadLigneCommandes() {
        try {
            final var stage = new Stage();
            final var loader = new FXMLLoader();
            final Pane mainPane = loader.load(
                    AppNavigator.class.getResourceAsStream(
                            AppNavigator.LIGNE_COMMANDES
                    )
            );
            var scene = new Scene(mainPane);

            stage.setTitle("Créer un ligne commande form");
            stage.setScene(scene);
            stage.show();
            return stage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}