package com.ezzariy.controller;

import com.ezzariy.AppNavigator;
import com.ezzariy.dao.ClientDao;
import com.ezzariy.dao.ClientDaoImpl;
import com.ezzariy.dao.VenteDao;
import com.ezzariy.dao.VenteDaoImpl;
import com.ezzariy.model.Client;
import com.ezzariy.model.LigneCommande;
import com.ezzariy.model.Vente;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class VenteController implements Initializable {

    protected static Vente newVente = new Vente();

    private final ClientDao clientDao = new ClientDaoImpl();
    private final VenteDao venteDao = new VenteDaoImpl();

    @FXML
    private TextField idField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button insertButton;

    @FXML
    private Button plusCommandButton;

    @FXML
    private ComboBox<Client> clientComboBox;

    @FXML
    private TableView<Vente> TableView;

    @FXML
    private ListView<LigneCommande> ligneCommandeListView;

    @FXML
    private TableColumn<Vente, Long> idColumn;

    @FXML
    private TableColumn<Vente, Date> dateColumn;

    @FXML
    private TableColumn<Vente, String> clientColumn;

    @FXML
    private TableColumn<Vente, Double> totalColumn;

    @FXML
    private TableColumn<Vente, Button> supprimerColumn;

    @FXML
    private TableColumn<Vente, Button> editColumn;

    @FXML
    private void insertButton() {
        newVente.setDate(java.sql.Date.valueOf(datePicker.getValue()));
        venteDao.addVente(newVente);
        newVente = new Vente();
        listAllVents();
    }

    @FXML
    private void plusCommand() {
        var cmmandStage = AppNavigator.loadLigneCommandes();
        if (Objects.nonNull(cmmandStage))
            cmmandStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent -> initLigneCommandeListView());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initClientsComboBox();
        listAllVents();
    }

    private void initClientsComboBox() {
        var clients = FXCollections.observableArrayList(clientDao.findAllClients());
        clientComboBox.setValue(null);
        clientComboBox.setPromptText("");
        clientComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Client client) {
                return String.format("%s %s", client.getPrenom(), client.getNom());
            }

            @Override
            public Client fromString(String s) {
                return null;
            }
        });
        clientComboBox.setItems(clients);
        clientComboBox.valueProperty().addListener((obs, oldVal, newVal) -> newVente.setClient(newVal));
    }

    private void initLigneCommandeListView() {
        if (Objects.nonNull(newVente.getLigneCommandes())) {
            var ligneCommandes = FXCollections.observableArrayList(newVente.getLigneCommandes());
            ligneCommandeListView.setItems(ligneCommandes);
            ligneCommandeListView.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(LigneCommande item, boolean empty) {
                    super.updateItem(item, empty);

                    if (Objects.nonNull(item) && Objects.nonNull(item.getProduct()))
                        setText(String.format("Produit: %s / Qte: %s / Total: %s ", item.getProduct().getDesignation(), item.getQte(), item.getTotal()));
                    else
                        setText(null);
                }
            });
        }
    }

    public void listAllVents() {
        showVents(venteDao.findAllVentes());
    }

    public void showVents(List<Vente> ventes) {
        var list = FXCollections.observableArrayList(ventes);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        clientColumn.setCellValueFactory(dataFeatures -> {
                    var property = new SimpleObjectProperty<String>();
                    var vente = dataFeatures.getValue();
                    property.setValue(String.format("%s %s", vente.getClient().getPrenom(), vente.getClient().getNom()));
                    return property;
                }
        );
        totalColumn.setCellValueFactory(dataFeatures -> {
                    var property = new SimpleObjectProperty<Double>();
                    var vente = dataFeatures.getValue();
                    property.setValue(vente.getLigneCommandes().stream().map(LigneCommande::getTotal).mapToDouble(Double::doubleValue).sum());
                    return property;
                }
        );

        supprimerColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Remove", (Vente vente) -> {
            venteDao.deleteVente(vente);
            listAllVents();
            return vente;
        }));

        editColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Edit", (Vente vente) -> {
            idField.setText(String.valueOf(vente.getId()));
            datePicker.setValue(LocalDate.from(vente.getDate().toInstant()));
            newVente = vente;
            return vente;
        }));

        TableView.setItems(list);
    }
}
