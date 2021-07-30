package com.ezzariy.dao;

import com.ezzariy.model.Product;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends AbstractDao implements ProductDao {

    @Override
    public void addProduct(Product product) {
        try {
            final var query = "INSERT INTO products(designation,qte,prix,date) VALUES(?,?,?,?)";

            var statement = connection.prepareStatement(query);
            statement.setString(1, product.getDesignation());
            statement.setInt(2, product.getQte());
            statement.setDouble(3, product.getPrix());
            statement.setDate(4, (Date) product.getDate());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteProductById(long id) {
        try {
            final var query = "DELETE FROM products WHERE (id = ?)";

            var statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Product findProductById(long id) {
        try {
            final var query = "SELECT * FROM products WHERE (id = ?)";

            var statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return resultSetToProducts(statement.executeQuery()).get(0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findAllProducts() {
        try {
            final var query = "SELECT * FROM products ";

            var statement = connection.prepareStatement(query);
            return resultSetToProducts(statement.executeQuery());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return List.of();
    }

    @Override
    public List<Product> findAllProductsByDesignation(String designation) {
        try {
            final var query = "SELECT * FROM products WHERE designation LIKE ?";

            var statement = connection.prepareStatement(query);
            statement.setString(1, "'%" + designation + "%'");
            return resultSetToProducts(statement.executeQuery());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return List.of();
    }


    private List<Product> resultSetToProducts(ResultSet rs) {
        var returnedValue = new ArrayList<Product>();
        try {
            while (rs.next()) {
                var product = new Product(rs.getLong(1), rs.getString(2), rs.getInt(3), rs.getDouble(4), rs.getDate(5));
                returnedValue.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return returnedValue;
    }
}
