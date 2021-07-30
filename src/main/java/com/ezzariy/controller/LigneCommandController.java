package com.ezzariy.controller;

import com.ezzariy.dao.ProductDao;
import com.ezzariy.dao.ProductDaoImpl;
import com.ezzariy.model.LigneCommande;
import com.ezzariy.model.Product;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LigneCommandController extends VenteController implements Initializable {

    private final ProductDao productDao = new ProductDaoImpl();

    private LigneCommande newCommand = new LigneCommande();

    @FXML
    private Button addCommandButton;

    @FXML
    private TextField qteCommand;

    @FXML
    private ComboBox<Product> productComboBox;

    @FXML
    private void addCommand() {
        newCommand.setQte(Integer.parseInt(qteCommand.getText()));
        if (Objects.nonNull(newCommand.getProduct()))
            newCommand.setTotal(newCommand.getProduct().getPrix() * newCommand.getQte());
        newVente.addLigneCommande(newCommand);
        clearCommandForm();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initProductsComboBox();
    }

    private void clearCommandForm() {
        if (Objects.nonNull(qteCommand)) qteCommand.clear();
        if (Objects.nonNull(productComboBox)) productComboBox.setValue(null);
        newCommand = new LigneCommande();
    }

    public void initProductsComboBox() {
        var products = FXCollections.observableArrayList(productDao.findAllProducts());
        productComboBox.setValue(null);
        productComboBox.setPromptText("");
        productComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Product product) {
                return product.getDesignation();
            }

            @Override
            public Product fromString(String s) {
                return null;
            }
        });
        productComboBox.setItems(products);
        productComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (Objects.nonNull(newVal))
                newCommand.setProduct(newVal);
        });
    }
}
