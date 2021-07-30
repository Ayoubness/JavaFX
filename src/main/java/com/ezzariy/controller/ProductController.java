package com.ezzariy.controller;

import com.ezzariy.dao.ProductDao;
import com.ezzariy.dao.ProductDaoImpl;
import com.ezzariy.model.Product;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    private final ProductDao productDao = new ProductDaoImpl();

    @FXML
    private TextField idField;

    @FXML
    private TextField designationField;

    @FXML
    private TextField qteField;

    @FXML
    private TextField prixField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button insertButton;

    @FXML
    private TableView<Product> TableView;

    @FXML
    private TableColumn<Product, Long> idColumn;

    @FXML
    private TableColumn<Product, String> designationColumn;

    @FXML
    private TableColumn<Product, String> qteColumn;

    @FXML
    private TableColumn<Product, Integer> prixColumn;

    @FXML
    private TableColumn<Product, Integer> dateColumn;

    @FXML
    private void insertButton() {
        var product = new Product(designationField.getText(), Integer.parseInt(qteField.getText()), Double.parseDouble(prixField.getText()), java.sql.Date.valueOf(datePicker.getValue()));
        productDao.addProduct(product);
        showProducts();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showProducts();
    }

    public void showProducts() {

        var list = FXCollections.observableArrayList(productDao.findAllProducts());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        qteColumn.setCellValueFactory(new PropertyValueFactory<>("qte"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableView.setItems(list);
    }
}
