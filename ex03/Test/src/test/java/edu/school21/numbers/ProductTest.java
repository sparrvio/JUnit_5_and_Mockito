package edu.school21.numbers;

import edu.school21.models.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    @Test
    @DisplayName("Тест переопределенного Equals с одним и тем же объкетом")
//    @Execution(ExecutionMode.CONCURRENT)
    void testEqualsSelf() {
        Product product = new Product(1L, "Product", 30);
        assertEquals(product, product);
    }

    @Test
    @DisplayName("Тест переопределенного Equals с null")
//    @Execution(ExecutionMode.CONCURRENT)
    void testEqualsNull() {
        Product product = new Product(1L, "Product", 30);
        assertNotEquals(null, product);
    }

    @Test
    @DisplayName("Тест переопределенного Equals с равными объектами")
//    @Execution(ExecutionMode.CONCURRENT)
    void testEqualsSameValues() {
        Product product1 = new Product(1L, "Product", 30);
        Product product2 = new Product(1L, "Product", 30);

        assertEquals(product1, product2);
    }

    @Test
    @DisplayName("Тест переопределенного Equals с НЕ равными объектами")
//    @Execution(ExecutionMode.CONCURRENT)
    void testEqualsDifferentValues() {
        Product product1 = new Product(1L, "Product", 30);
        Product product2 = new Product(1L, "Product", 20);
        assertNotEquals(product1, product2);
    }

    @Test
    @DisplayName("Тест переопределенного HashCode с одним и тем же объкетом")
//    @Execution(ExecutionMode.CONCURRENT)
    void testHashCodeSelf() {
        Product product = new Product(1L, "Product", 30);
        assertEquals(product.hashCode(), product.hashCode());
    }

    @Test
    @DisplayName("Тест переопределенного HashCode с равными объектами")
//    @Execution(ExecutionMode.CONCURRENT)
    void testHashCodeSameValues() {
        Product product1 = new Product(1L, "Product", 30);
        Product product2 = new Product(1L, "Product", 30);

        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    @DisplayName("Тест переопределенного HashCode с НЕ равными объектами")
//    @Execution(ExecutionMode.CONCURRENT)
    void testHashCodeDifferentValues() {
        Product product1 = new Product(1L, "Product", 30);
        Product product2 = new Product(1L, "Product", 20);

        assertNotEquals(product1.hashCode(), product2.hashCode());
    }
}
