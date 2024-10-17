package com.mycompany.sistemagestioninventario;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge
 */
@XmlRootElement(name = "products")
public class ProductList {

    private List<Product> products;

    public ProductList() {
        this.products = new ArrayList<>();
    }

    @XmlElement(name = "product")
    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public Product getProductAt(int index) {
        if (index >= 0 && index < products.size()) {
            return products.get(index);
        } else {
            System.out.println("Índice fuera de rango.");
            return null;
        }
    }

    public void updateProductAt(int index, Product updatedProduct) {
        if (index >= 0 && index < products.size()) {
            products.set(index, updatedProduct);
        } else {
            System.out.println("Índice fuera de rango.");
        }
    }
}
