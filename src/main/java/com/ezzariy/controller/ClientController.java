package com.ezzariy.controller;

import com.ezzariy.dao.ClientDao;
import com.ezzariy.dao.ClientDaoImpl;
import com.ezzariy.model.Client;
import com.mysql.cj.util.StringUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    private final ClientDao clientDao = new ClientDaoImpl();

    @FXML
    private TextField idField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField searchField;

    @FXML
    private Button insertButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<Client> TableView;

    @FXML
    private TableColumn<Client, Long> idColumn;

    @FXML
    private TableColumn<Client, String> nomColumn;

    @FXML
    private TableColumn<Client, String> prenomColumn;

    @FXML
    private TableColumn<Client, String> telephoneColumn;

    @FXML
    private TableColumn<Client, String> emailColumn;

    @FXML
    private TableColumn<Client, String> adresseColumn;

    @FXML
    private TableColumn<Client, Button> supprimerColumn;

    @FXML
    private TableColumn<Client, Button> editColumn;

    @FXML
    private void insertButton() {
        var client = new Client(
                nomField.getText(),
                prenomField.getText(),
                telephoneField.getText(),
                emailField.getText(),
                adresseField.getText()
        );
        clientDao.addClient(client);
        listAllClients();
        clearFields();
    }

    @FXML
    private void saveButton() {
        var client = new Client(
                Long.parseLong(idField.getText()),
                nomField.getText(),
                prenomField.getText(),
                telephoneField.getText(),
                emailField.getText(),
                adresseField.getText()
        );
        clientDao.updateClient(client);
        listAllClients();
        clearFields();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!StringUtils.isEmptyOrWhitespaceOnly(newValue)) {
                showClients(clientDao.findAllClientsByKeyword(newValue));
            } else {
                listAllClients();
            }
        });
        listAllClients();
    }

    public void listAllClients() {
        showClients(clientDao.findAllClients());
    }

    public void showClients(List<Client> clients) {

        var list = FXCollections.observableArrayList(clients);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        supprimerColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Remove", (Client client) -> {
            clientDao.deleteClientById(client.getId());
            listAllClients();
            return client;
        }));

        editColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Edit", (Client client) -> {
            idField.setText(String.valueOf(client.getId()));
            nomField.setText(client.getNom());
            prenomField.setText(client.getPrenom());
            telephoneField.setText(client.getTelephone());
            emailField.setText(client.getEmail());
            adresseField.setText(client.getAdresse());
            return client;
        }));

        TableView.setItems(list);
    }

    private void clearFields() {
        if (Objects.nonNull(idField)) idField.clear();
        if (Objects.nonNull(nomField)) nomField.clear();
        if (Objects.nonNull(prenomField)) prenomField.clear();
        if (Objects.nonNull(emailField)) emailField.clear();
        if (Objects.nonNull(telephoneField)) telephoneField.clear();
        if (Objects.nonNull(adresseField)) adresseField.clear();
    }
}
