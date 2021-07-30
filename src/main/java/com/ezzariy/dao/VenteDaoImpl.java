package com.ezzariy.dao;

import com.ezzariy.model.Client;
import com.ezzariy.model.LigneCommande;
import com.ezzariy.model.Product;
import com.ezzariy.model.Vente;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VenteDaoImpl extends AbstractDao implements VenteDao {

    @Override
    public void addVente(Vente vente) {
        try {
            var query = "INSERT INTO ventes (date, client_id) VALUES (?,?)";

            var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, (Date) vente.getDate());
            statement.setLong(2, vente.getClient().getId());

            var affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating vente failed, no rows affected.");
            }
            try (var generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next())
                    vente.setId(generatedKeys.getLong(1));
                else
                    throw new SQLException("Creating vente failed, no ID obtained.");
            }
            statement.executeUpdate();
            if (Objects.nonNull(vente.getLigneCommandes())) {
                vente.setLigneCommandes(vente.getLigneCommandes().stream().map(this::saveLigneCommande).collect(Collectors.toList()));
                for (LigneCommande ligneCommande : vente.getLigneCommandes()) {
                    query = "INSERT INTO vente_commandes (vente_id, commandes_id) VALUES (?, ?)";
                    statement = connection.prepareStatement(query);
                    statement.setLong(1, vente.getId());
                    statement.setLong(2, ligneCommande.getId());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private LigneCommande saveLigneCommande(LigneCommande ligneCommande) {
        try {
            final var query = "INSERT INTO ligne_commandes(total, qte, product_id) VALUES (?,?,?)";

            var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, ligneCommande.getTotal());
            statement.setInt(2, ligneCommande.getQte());
            statement.setLong(3, ligneCommande.getProduct().getId());

            var affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating ligne commande failed, no rows affected.");
            }
            try (var generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next())
                    ligneCommande.setId(generatedKeys.getLong(1));
                else
                    throw new SQLException("Creating ligne commande failed, no ID obtained.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ligneCommande;
    }

    @Override
    public void updateVente(Vente vente) {
        try {
            final var query = "UPDATE ventes SET nom = ?, prenom = ?, telephone = ?, email = ?, adresse = ? WHERE (id = ?)";

            var statement = connection.prepareStatement(query);
//            statement.setString(1, vente.getNom());
//            statement.setString(2, vente.getPrenom());
//            statement.setString(3, vente.getTelephone());
//            statement.setString(4, vente.getEmail());
//            statement.setString(5, vente.getAdresse());
            statement.setLong(6, vente.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteVente(Vente vente) {
        try {
            final var query = "DELETE FROM ventes WHERE (id = ?)";

            var statement = connection.prepareStatement(query);
            statement.setLong(1, vente.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Vente findVenteById(long id) {
        try {
            final var query = "SELECT * FROM ventes WHERE (id = ?)";

            var statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return resultSetToVentes(statement.executeQuery()).get(0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Vente> findAllVentes() {
        try {
            final var query = "SELECT * FROM ventes LEFT JOIN clients ON ventes.client_id = clients.id";

            var statement = connection.prepareStatement(query);
            return resultSetToVentes(statement.executeQuery());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return List.of();
    }

    @Override
    public List<Vente> findAllVentesByKeyword(String keyword) {
        try {
            final var query = "SELECT * FROM ventes WHERE LOWER(nom) LIKE CONCAT('%',?,'%') OR LOWER(prenom) LIKE CONCAT('%',?,'%') OR LOWER(email) LIKE CONCAT('%',?,'%') OR LOWER(adresse) LIKE CONCAT('%',?,'%')";
            final var queryInput = keyword.toLowerCase();

            var statement = connection.prepareStatement(query);
            statement.setString(1, queryInput);
            statement.setString(2, queryInput);
            statement.setString(3, queryInput);
            statement.setString(4, queryInput);
            return resultSetToVentes(statement.executeQuery());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return List.of();
    }

    private List<Vente> resultSetToVentes(ResultSet rs) {
        var returnedValue = new ArrayList<Vente>();
        try {
            while (rs.next()) {
                var venteId = rs.getLong(1);
                var client = new Client(
                        rs.getLong(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9)
                );
                var vente = new Vente(
                        venteId, rs.getDate(2),
                        client, resultSetToLigneCommandes(venteId)
                );
                returnedValue.add(vente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return returnedValue;
    }

    private List<LigneCommande> resultSetToLigneCommandes(long venteId) {
        var returnedValue = new ArrayList<LigneCommande>();
        try {
            var query = "SELECT l.* ,p.* FROM ligne_commandes l, vente_commandes vc,products p " +
                    "where l.id = vc.commandes_id and p.id = l.product_id and vc.vente_id = ?";

            var statement = connection.prepareStatement(query);
            statement.setLong(1, venteId);
            var rs = statement.executeQuery();

            while (rs.next()) {
                var product = new Product(
                        rs.getLong(5), rs.getString(6),
                        rs.getInt(7), rs.getDouble(8),
                        rs.getDate(9)
                );

                var ligneCommande = new LigneCommande(
                        rs.getLong(1), rs.getInt(2),
                        rs.getDouble(3), product
                );

                returnedValue.add(ligneCommande);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return returnedValue;
    }
}
