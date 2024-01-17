package edu.school21.models;

import java.util.Objects;

public class Product {
    Long id;
    String name;
    double price;

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Product(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Double.compare(product.price, price) == 0 && name.equals(product.name);
    }

    @Override
    public int hashCode() {

        int result = 31;
        result = (int) (17 * result + (id != null ? id.hashCode() : 0) +
                (name != null ? name.hashCode() : 0) + (price * 31));
        return result;
    }

    public Long getId() {
        return id;
    }
}


