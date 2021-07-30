package com.ezzariy.dao;

import com.ezzariy.model.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl extends AbstractDao implements ClientDao {

    @Override
    public void addClient(Client client) {
        try {
            final var query = "INSERT INTO clients(nom,prenom,telephone,email,adresse) VALUES(?,?,?,?,?)";

            var statement = connection.prepareStatement(query);
            statement.setString(1, client.getNom());
            statement.setString(2, client.getPrenom());
            statement.setString(3, client.getTelephone());
            statement.setString(4, client.getEmail());
            statement.setString(5, client.getAdresse());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateClient(Client client) {
        try {
            final var query = "UPDATE clients SET nom = ?, prenom = ?, telephone = ?, email = ?, adresse = ? WHERE (id = ?)";

            var statement = connection.prepareStatement(query);
            statement.setString(1, client.getNom());
            statement.setString(2, client.getPrenom());
            statement.setString(3, client.getTelephone());
            statement.setString(4, client.getEmail());
            statement.setString(5, client.getAdresse());
            statement.setLong(6, client.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteClientById(long id) {
        try {
            final var query = "DELETE FROM clients WHERE (id = ?)";

            var statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Client findClientById(long id) {
        try {
            final var query = "SELECT * FROM clients WHERE (id = ?)";

            var statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return resultSetToClients(statement.executeQuery()).get(0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Client> findAllClients() {
        try {
            final var query = "SELECT * FROM clients";

            var statement = connection.prepareStatement(query);
            return resultSetToClients(statement.executeQuery());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return List.of();
    }

    @Override
    public List<Client> findAllClientsByKeyword(String keyword) {
        try {
            final var query = "SELECT * FROM clients WHERE LOWER(nom) LIKE CONCAT('%',?,'%') OR LOWER(prenom) LIKE CONCAT('%',?,'%') OR LOWER(email) LIKE CONCAT('%',?,'%') OR LOWER(adresse) LIKE CONCAT('%',?,'%')";
            final var queryInput = keyword.toLowerCase();

            var statement = connection.prepareStatement(query);
            statement.setString(1, queryInput);
            statement.setString(2, queryInput);
            statement.setString(3, queryInput);
            statement.setString(4, queryInput);
            return resultSetToClients(statement.executeQuery());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return List.of();
    }


    private List<Client> resultSetToClients(ResultSet rs) {
        var returnedValue = new ArrayList<Client>();
        try {
            while (rs.next()) {
                var client = new Client(
                        rs.getLong(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6)
                );
                returnedValue.add(client);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return returnedValue;
    }
}
