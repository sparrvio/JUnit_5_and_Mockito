package edu.school21.numbers;

import edu.school21.models.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    @Test
    void testEqualsSelf() {
        Product product = new Product(1L, "Product", 30);
        assertEquals(product, product);
    }

    @Test
    void testEqualsNull() {
        Product product = new Product(1L, "Product", 30);
        assertNotEquals(null, product);
    }

    @Test
    void testEqualsSameValues() {
        Product product1 = new Product(1L, "Product", 30);
        Product product2 = new Product(1L, "Product", 30);

        assertEquals(product1, product2);
    }

    @Test
    void testEqualsDifferentValues() {
        Product product1 = new Product(1L, "Product", 30);
        Product product2 = new Product(1L, "Product", 20);
        assertNotEquals(product1, product2);
    }

    @Test
    void testHashCodeSelf() {
        Product product = new Product(1L, "Product", 30);
        assertEquals(product.hashCode(), product.hashCode());
    }

    @Test
    void testHashCodeSameValues() {
        Product product1 = new Product(1L, "Product", 30);
        Product product2 = new Product(1L, "Product", 30);

        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    void testHashCodeDifferentValues() {
        Product product1 = new Product(1L, "Product", 30);
        Product product2 = new Product(1L, "Product", 20);

        assertNotEquals(product1.hashCode(), product2.hashCode());
    }
}
