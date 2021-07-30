package com.ezzariy.controller;

import com.ezzariy.AppNavigator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private StackPane vistaHolder;

    @FXML
    private Label productsLabel;

    @FXML
    private Label clientsLabel;

    @FXML
    private Label ventesLabel;

    @FXML
    private Label exitAppLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productsLabel.setOnMouseClicked(e -> AppNavigator.loadVista(AppNavigator.PRODUCTS));
        clientsLabel.setOnMouseClicked(e -> AppNavigator.loadVista(AppNavigator.CLIENTS));
        ventesLabel.setOnMouseClicked(e -> AppNavigator.loadVista(AppNavigator.VENTES));
        exitAppLabel.setOnMouseClicked(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public void setVista(Node node) {
        vistaHolder.getChildren().setAll(node);
    }
}