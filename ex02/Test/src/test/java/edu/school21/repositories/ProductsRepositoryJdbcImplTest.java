package edu.school21.repositories;

import edu.school21.models.Product;
import edu.school21.numbres.IllegalNumberException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductsRepositoryJdbcImplTest {
    List<Product> getAllProductList() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L, "Product 1", 10.99));
        productList.add(new Product(2L, "Product 2", 20.99));
        productList.add(new Product(3L, "Product 3", 30.99));
        productList.add(new Product(4L, "Product 4", 40.99));
        productList.add(new Product(5L, "Product 5", 50.99));
        return productList;
    }

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = getAllProductList();
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(5L, "Product 5", 50.99);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(10L, "UpdateProduct", 0.99);

    private ProductsRepositoryJdbcImpl productsRepository;
    private DataSource dataSource;
    EmbeddedDataSourceTest embeddedDataSource;

    @BeforeEach
    public void init() {
        embeddedDataSource = new EmbeddedDataSourceTest();
        embeddedDataSource.init();
        dataSource = embeddedDataSource.db;
        productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
    }


    @Test
    void findAll() {
        assertEquals(productsRepository.findAll(), EXPECTED_FIND_ALL_PRODUCTS);
    }

    @Test
    void disconnectFindAll() {
        embeddedDataSource.db.shutdown();
        assertThrows(IllegalNumberException.class, () -> productsRepository.findAll());
    }

    @Test
    void findById() {
        Optional<Product> optionalProduct = productsRepository.findById(5L);
        if (optionalProduct.isPresent()) {
            assertEquals(optionalProduct.get(), EXPECTED_FIND_BY_ID_PRODUCT);
        } else {
            throw new IllegalArgumentException("Product with id " + 5L + " not found");
        }
    }

    @Test
    void disconnectFindById() {
        embeddedDataSource.db.shutdown();
        assertThrows(IllegalNumberException.class, () -> productsRepository.findById(1L));
    }

    @Test
    void isNotFoundIdFindById() {
        embeddedDataSource.db.shutdown();
        assertThrows(IllegalNumberException.class, () -> productsRepository.findById(111L));
    }

    @ParameterizedTest
    @ValueSource(longs = {5L})
    void update(long id) {
        Optional<Product> optionalProduct = productsRepository.findById(id);
        Product product;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
            productsRepository.update(product);
        } else {
            throw new IllegalArgumentException("Product with id " + id + " not found");
        }
        optionalProduct = productsRepository.findById(10L);
        if (optionalProduct.isPresent()) {
            assertEquals(optionalProduct.get(), EXPECTED_UPDATED_PRODUCT);
        } else {
            throw new IllegalArgumentException("Product with id " + 10L + " not found");
        }
    }

    @Test
    void isDisconnectUpdate() {
        embeddedDataSource.db.shutdown();
        Product product = new Product(1L, "UpdateProduct", 0.99);
        assertThrows(IllegalNumberException.class, () -> productsRepository.update(product));
    }

    @Test
    void isNotFoundIdUpdate() {
        Product product = new Product(10L, "UpdateProduct", 0.99);
        assertThrows(IllegalArgumentException.class, () -> productsRepository.update(product));
    }


    @ParameterizedTest
    @ValueSource(longs = {6, 7, 8})
    void save(long id) {
        Product product = new Product(id, "ProductSave", 60.99);
        productsRepository.save(product);
        Optional<Product> optionalProduct = productsRepository.findById(id);
        assertTrue(optionalProduct.isPresent(), "Product with id " + id + " not found");
    }

    @Test
    void isDisconnectSave() {
        embeddedDataSource.db.shutdown();
        Product product = new Product(111L, "UpdateProduct", 0.99);
        assertThrows(IllegalNumberException.class, () -> productsRepository.save(product));
    }

    @Test
    void isSaveAlreadyExistsId() {
        Product product = new Product(1L, "UpdateProduct", 0.99);
        assertThrows(IllegalArgumentException.class, () -> productsRepository.save(product));
    }


    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    void delete(long id) {
        productsRepository.delete(id);
        assertThrows(IllegalArgumentException.class, () -> productsRepository.delete(id));
    }

    @Test
    void disconnectDelete() {
        embeddedDataSource.db.shutdown();
        assertThrows(IllegalNumberException.class, () -> productsRepository.delete(1L));
    }

    @AfterEach
    public void tearDown() {
        String sql = "DROP TABLE IF EXISTS product";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to drop table", e);
        }
    }

}
