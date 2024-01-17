package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, price FROM product")
        ) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                long id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Product product = new Product(id, name, price);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Incorrect Select");
        }
        return productList;
    }

    @Override
    public Optional<Product> findById(Long id) {
        long id_product;
        String name;
        double price;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM product WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                id_product = rs.getInt("id");
                name = rs.getString("name");
                price = rs.getDouble("price");
                return Optional.of(new Product(id_product, name, price));
            } else {
                return Optional.empty();
            }
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Incorrect id");
        }
    }


    @Override
    public void update(Product product) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE product\n" +
                     "SET id = ?, name = ?, price = ? WHERE id = ?")
        ) {
            Optional<Product> existingProduct = findById(product.getId());
            if (!existingProduct.isPresent()) {
                throw new IllegalArgumentException("Product with id " + product.getId() + " not found");
            }
            preparedStatement.setLong(4, product.getId());
            preparedStatement.setLong(1, 10);
            preparedStatement.setString(2, "UpdateProduct");
            preparedStatement.setDouble(3, 0.99);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Incorrect");
        }

    }

    @Override
    public void save(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO product\n" +
                     "(id, name, price) VALUES (?, ?, ?)")
        ) {
            Optional<Product> existingProduct = findById((product.getId()));
            if (existingProduct.isPresent()) {
                throw new IllegalArgumentException("Product with id " + product.getId() + " already exists");
            }
            preparedStatement.setLong(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Incorrect");
        }
    }

    @Override
    public void delete(Long id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM product WHERE id = ?")
        ) {
        Optional<Product> existingProduct = findById(id);
        if (!existingProduct.isPresent()) {
            throw new IllegalArgumentException("Product with id " + id + " not found");
        }
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Incorrect");
        }
    }
}
